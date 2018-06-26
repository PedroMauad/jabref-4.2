package org.jabref.logic.importer.fileformat;

import org.jabref.logic.importer.ImportFormatPreferences;
import org.jabref.logic.importer.ImportFormatReader;
import org.jabref.logic.importer.Importer;
import org.jabref.logic.importer.ParserResult;
import org.jabref.logic.util.FileType;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.BiblatexEntryTypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVImporter extends Importer {

    public CSVImporter() {
        System.out.println("Construiu");
    }

    private final String STRING_SPLIT = ",";

    private String getTypeFromID(String id) {

        System.out.println("getTypeFromID | " + id);

        if(BiblatexEntryTypes.ARTICLE.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.ARTICLE.getName();
        else if(BiblatexEntryTypes.BOOK.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.BOOK.getName();
        else if(BiblatexEntryTypes.MVBOOK.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.MVBOOK.getName();
        else if(BiblatexEntryTypes.INBOOK.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.INBOOK.getName();
        else if(BiblatexEntryTypes.BOOKINBOOK.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.BOOKINBOOK.getName();
        else if(BiblatexEntryTypes.SUPPBOOK.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.SUPPBOOK.getName();
        else if(BiblatexEntryTypes.BOOKLET.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.BOOKLET.getName();
        else if(BiblatexEntryTypes.COLLECTION.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.COLLECTION.getName();
        else if(BiblatexEntryTypes.MVCOLLECTION.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.MVCOLLECTION.getName();
        else if(BiblatexEntryTypes.INCOLLECTION.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.INCOLLECTION.getName();
        else if(BiblatexEntryTypes.SUPPCOLLECTION.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.SUPPCOLLECTION.getName();
        else if(BiblatexEntryTypes.MANUAL.getName().equals(id))
            return BiblatexEntryTypes.MANUAL.getName();
        else if(BiblatexEntryTypes.MISC.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.MISC.getName();
        else if(BiblatexEntryTypes.ONLINE.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.ONLINE.getName();
        else if(BiblatexEntryTypes.PATENT.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.PATENT.getName();
        else if(BiblatexEntryTypes.PERIODICAL.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.PERIODICAL.getName();
        else if(BiblatexEntryTypes.SUPPPERIODICAL.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.SUPPPERIODICAL.getName();
        else if(BiblatexEntryTypes.PROCEEDINGS.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.PROCEEDINGS.getName();
        else if(BiblatexEntryTypes.MVPROCEEDINGS.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.MVPROCEEDINGS.getName();
        else if(BiblatexEntryTypes.INPROCEEDINGS.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.INPROCEEDINGS.getName();
        else if(BiblatexEntryTypes.REFERENCE.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.REFERENCE.getName();
        else if(BiblatexEntryTypes.MVREFERENCE.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.MVREFERENCE.getName();
        else if(BiblatexEntryTypes.INREFERENCE.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.INREFERENCE.getName();
        else if(BiblatexEntryTypes.REPORT.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.REPORT.getName();
        else if(BiblatexEntryTypes.SET.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.SET.getName();
        else if(BiblatexEntryTypes.THESIS.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.THESIS.getName();
        else if(BiblatexEntryTypes.UNPUBLISHED.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.UNPUBLISHED.getName();
        else if(BiblatexEntryTypes.CONFERENCE.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.CONFERENCE.getName();
        else if(BiblatexEntryTypes.ELECTRONIC.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.ELECTRONIC.getName();
        else if(BiblatexEntryTypes.MASTERSTHESIS.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.MASTERSTHESIS.getName();
        else if(BiblatexEntryTypes.PHDTHESIS.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.PHDTHESIS.getName();
        else if(BiblatexEntryTypes.TECHREPORT.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.TECHREPORT.getName();
        else if(BiblatexEntryTypes.WWW.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.WWW.getName();
        else if(BiblatexEntryTypes.IEEETRANBSTCTL.getName().equalsIgnoreCase(id))
            return BiblatexEntryTypes.IEEETRANBSTCTL.getName();
        else
            return "other";
    }

    @Override
    public boolean isRecognizedFormat(BufferedReader input) throws IOException {

        String str;
        System.out.println("isRecognizedFormat");

        while ((str = input.readLine()) != null) {
            System.out.println("isRecognizedFormat | " + str);
            if (str.split(STRING_SPLIT).length < 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ParserResult importDatabase(BufferedReader input) throws IOException {

        System.out.println("importDatabase");

        List<BibEntry> bibitems = new ArrayList<>();

        String headerTags[];
        String str;
        String type;

        // Get first line to load read fields
        if ((str = input.readLine()) == null)
            return new ParserResult(bibitems);

        System.out.println("importDatabase | " + str);
        headerTags = str.split(STRING_SPLIT);

        while ((str = input.readLine()) != null) {
            String values[] = str.split(STRING_SPLIT);
            Map<String, String> h = new HashMap<>();

            //type = getTypeFromID(values[0]);
            if (values[0].matches("\".*\"")) {
                values[0] = values[0].substring(1, values[0].length() - 1);
            }
            type = values[0];

            System.out.println("importDatabase | " + type );

            for (int i = 1; i < values.length; ++i) {
                if ("\"\"".equals(values[i]) || (values[i].length() == 0)) {
                    continue;
                }

                String value = values[i];
                if (value.matches("\".*\"")) {
                    value = values[i].substring(1, values[i].length() - 1);
                }

                if (headerTags[i].matches("\".*\"")) {
                    headerTags[i] = headerTags[i].substring(1, headerTags[i].length() - 1);
                }

                System.out.println("importDatabase | " + headerTags[i].toLowerCase() + " | " + value);

                h.put(headerTags[i].toLowerCase(), value);
            }

            System.out.println("importDatabase Final Importe ");
            BibEntry entry = new BibEntry(type);
            entry.setField(h);
            bibitems.add(entry);
        }

        System.out.println("importDatabase Final Return");
        return new ParserResult(bibitems);
    }

    @Override
    public String getName() {
        System.out.println("getName");
        return "CSV";
    }

    @Override
    public FileType getFileType() {
        System.out.println("getFileType");
        return FileType.CSV;
    }

    @Override
    public String getDescription() {
        System.out.println("getDescription");
        return "Importe CSV File.";
    }
}
