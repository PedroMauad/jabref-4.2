package org.jabref.gui.importer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.jabref.Globals;
import org.jabref.gui.BasePanel;
import org.jabref.gui.DialogService;
import org.jabref.gui.EntryMarker;
import org.jabref.gui.FXDialogService;
import org.jabref.gui.JabRefFrame;
import org.jabref.gui.undo.NamedCompound;
import org.jabref.gui.util.DefaultTaskExecutor;
import org.jabref.gui.util.FileDialogConfiguration;
import org.jabref.gui.worker.AbstractWorker;
import org.jabref.logic.bibtex.DuplicateCheck;
import org.jabref.logic.importer.ImportException;
import org.jabref.logic.importer.ImportFormatReader;
import org.jabref.logic.importer.Importer;
import org.jabref.logic.importer.ParserResult;
import org.jabref.logic.l10n.Localization;
import org.jabref.logic.util.UpdateField;
import org.jabref.model.database.BibDatabase;
import org.jabref.model.database.KeyCollisionException;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.BibtexString;
import org.jabref.preferences.JabRefPreferences;

/*
 * TODO: could separate the "menu item" functionality from the importing functionality
 */
public class ImportMenuItem extends JMenuItem implements ActionListener {

    private final JabRefFrame frame;
    private final boolean openInNew;
    private final Importer importer;
    private Exception importError;

    public ImportMenuItem(JabRefFrame frame, boolean openInNew) {
        this(frame, openInNew, null);
    }

    public ImportMenuItem(JabRefFrame frame, boolean openInNew, Importer importer) {
        super(importer == null ? Localization.lang("Autodetect format") : importer.getName());
        this.importer = importer;
        this.frame = frame;
        this.openInNew = openInNew;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyWorker worker = new MyWorker();
        worker.init();
        worker.getWorker().run();
        worker.getCallBack().update();
    }

    /**
     * Automatically imports the files given as arguments
     * @param filenames List of files to import
     */
    public void automatedImport(List<String> filenames) {
        // replace the work of the init step:
        MyWorker worker = new MyWorker();
        worker.fileOk = true;
        worker.filenames = filenames.stream().map(Paths::get).collect(Collectors.toList());

        worker.getWorker().run();
        worker.getCallBack().update();
    }

    class MyWorker extends AbstractWorker {

        private List<Path> filenames;
        private ParserResult bibtexResult; // Contains the merged import results
        private boolean fileOk;

        @Override
        public void init() {
            importError = null;

            FileDialogConfiguration fileDialogConfiguration = new FileDialogConfiguration.Builder()
                    .withInitialDirectory(Globals.prefs.get(JabRefPreferences.WORKING_DIRECTORY)).build();

            DialogService ds = new FXDialogService();

            filenames = DefaultTaskExecutor
                    .runInJavaFXThread(() -> ds.showFileOpenDialogAndGetMultipleFiles(fileDialogConfiguration));

            if (!filenames.isEmpty()) {
                frame.block();
                frame.output(Localization.lang("Starting import"));
                fileOk = true;

                Globals.prefs.put(JabRefPreferences.WORKING_DIRECTORY, filenames.get(0).getParent().toString());
            }
        }

        @Override
        public void run() {
            if (!fileOk) {
                return;
            }

            // We import all files and collect their results:
            List<ImportFormatReader.UnknownFormatImport> imports = new ArrayList<>();
            for (Path filename : filenames) {

                try {
                    if (importer == null) {
                        // Unknown format:
                        frame.output(Localization.lang("Importing in unknown format") + "...");
                        // This import method never throws an IOException:
                        System.out.println("importMenuItem run(1) | " + filename.getFileName());
                        imports.add(Globals.IMPORT_FORMAT_READER.importUnknownFormat(filename, Globals.getFileUpdateMonitor()));
                        System.out.println("importMenuItem run(1) Complete");
                    } else {
                        frame.output(Localization.lang("Importing in %0 format", importer.getName()) + "...");
                        // Specific importer:
                        System.out.println("importMenuItem run(2) | " + filename.getFileName());
                        ParserResult pr = importer.importDatabase(filename, Globals.prefs.getDefaultEncoding());
                        System.out.println("importMenuItem run(2) Complete");
                        imports.add(new ImportFormatReader.UnknownFormatImport(importer.getName(), pr));
                    }
                } catch (ImportException | IOException e) {
                    // This indicates that a specific importer was specified, and that
                    // this importer has thrown an IOException. We store the exception,
                    // so a relevant error message can be displayed.
                    System.out.println("importMenuItem run() Error | " + e.getMessage());
                    importError = e;
                }
            }

            // Ok, done. Then try to gather in all we have found. Since we might
            // have found
            // one or more bibtex results, it's best to gather them in a
            // BibDatabase.
            System.out.println("importMenuItem mergeImportResults(imports)");
            bibtexResult = mergeImportResults(imports);
            System.out.println("importMenuItem mergeImportResults(imports) Complete");

            /* show parserwarnings, if any. */
            for (ImportFormatReader.UnknownFormatImport p : imports) {
                System.out.println("importMenuItem ImportFormatReader.UnknownFormatImport");
                if (p != null) {
                    System.out.println("importMenuItem ImportFormatReader.UnknownFormatImport != null");
                    ParserResult pr = p.parserResult;
                    ParserResultWarningDialog.showParserResultWarningDialog(pr, frame);
                }
            }

            System.out.println("importMenuItem Finish");
        }

        @Override
        public void update() {
            if (!fileOk) {
                return;
            }

            if (bibtexResult == null) {
                if (importer == null) {
                    frame.output(Localization.lang("Could not find a suitable import format."));
                } else {
                    // Import in a specific format was specified. Check if we have stored error information:
                    if (importError == null) {
                        JOptionPane.showMessageDialog(frame,
                                Localization
                                        .lang("No entries found. Please make sure you are using the correct import filter."),
                                Localization.lang("Import failed"), JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, importError.getMessage(),
                                Localization.lang("Import failed"), JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                if (openInNew) {
                    frame.addTab(bibtexResult.getDatabaseContext(), true);
                    frame.output(
                            Localization.lang("Imported entries") + ": " + bibtexResult.getDatabase().getEntryCount());
                } else {
                    final BasePanel panel = (BasePanel) frame.getTabbedPane().getSelectedComponent();

                    boolean duplicates = willAddDuplicates();
                    int answer = -1;

                    if (duplicates) {
                        // TODO Adicionar localização para essa Dialog
                        String msg = "A importação dessas entradas no database atual \ngerará duplicatas.\n Deseja importá-las em um novo banco de dados?";
                        answer = JOptionPane.showOptionDialog(frame, msg, "Conflito de duplicatas detectado",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                                new String[] {Localization.lang("Sim"), Localization.lang("Não")},
                                Localization.lang("Sim"));
                    }

                    if (duplicates && (answer == 0)) { // Sim
                        frame.addTab(
                                bibtexResult.getDatabaseContext(), true);
                        frame.output(Localization.lang("Imported entries") + ": "
                                + bibtexResult.getDatabase().getEntryCount());
                    }else {
                        ImportInspectionDialog diag = new ImportInspectionDialog(frame, panel, Localization.lang("Import"),
                                openInNew);
                        diag.addEntries(bibtexResult.getDatabase().getEntries());
                        diag.entryListComplete();
                        diag.setLocationRelativeTo(frame);
                        diag.setVisible(true);
                        diag.toFront();
                    }
                }
            }
            frame.unblock();
        }


        private boolean willAddDuplicates() {
            final BasePanel panel = (BasePanel) frame.getTabbedPane().getSelectedComponent();

            if ((panel.getDatabase() == null) || (panel.getDatabase().getEntryCount() == 0)) {
                return false;
            }

            for (BibEntry entry : bibtexResult.getDatabase().getEntries()) {
                Optional<BibEntry> op = DuplicateCheck.containsDuplicate(panel.getDatabase(), entry,
                        bibtexResult.getDatabaseContext().getMode());
                if (op.isPresent()) {
                    return true;
                }
            }

            return false;
        }
    }
    }

    private ParserResult mergeImportResults(List<ImportFormatReader.UnknownFormatImport> imports) {
        BibDatabase database = new BibDatabase();
        ParserResult directParserResult = null;
        boolean anythingUseful = false;

        for (ImportFormatReader.UnknownFormatImport importResult : imports) {
            if (importResult == null) {
                continue;
            }
            if (ImportFormatReader.BIBTEX_FORMAT.equals(importResult.format)) {
                // Bibtex result. We must merge it into our main base.
                ParserResult pr = importResult.parserResult;

                anythingUseful = anythingUseful || pr.getDatabase().hasEntries() || (!pr.getDatabase().hasNoStrings());

                // Record the parserResult, as long as this is the first bibtex result:
                if (directParserResult == null) {
                    directParserResult = pr;
                }

                // Merge entries:
                for (BibEntry entry : pr.getDatabase().getEntries()) {
                    database.insertEntry(entry);
                }

                // Merge strings:
                for (BibtexString bs : pr.getDatabase().getStringValues()) {
                    try {
                        database.addString((BibtexString) bs.clone());
                    } catch (KeyCollisionException e) {
                        // TODO: This means a duplicate string name exists, so it's not
                        // a very exceptional situation. We should maybe give a warning...?
                    }
                }
            } else {

                ParserResult pr = importResult.parserResult;
                Collection<BibEntry> entries = pr.getDatabase().getEntries();

                anythingUseful = anythingUseful | !entries.isEmpty();

                // set timestamp and owner
                UpdateField.setAutomaticFields(entries, Globals.prefs.getUpdateFieldPreferences()); // set timestamp and owner

                boolean markEntries = !openInNew && EntryMarker.shouldMarkEntries();
                for (BibEntry entry : entries) {
                    if (markEntries) {
                        EntryMarker.markEntry(entry, EntryMarker.IMPORT_MARK_LEVEL, false, new NamedCompound(""));
                    }
                    database.insertEntry(entry);
                }
            }
        }

        if (!anythingUseful) {
            return null;
        }

        if ((imports.size() == 1) && (directParserResult != null)) {
            return directParserResult;
        } else {

            return new ParserResult(database);

        }
    }

}
