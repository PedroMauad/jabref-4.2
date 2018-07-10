package org.jabref.logic.integrity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jabref.logic.integrity.IntegrityCheck.Checker;
import org.jabref.logic.l10n.Localization;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.FieldName;
import org.jabref.model.strings.StringUtil;

/**
 * Currently only checks the key if there is an author, year, and title present.
 */
public class BibtexKeyChecker implements Checker {

    @Override
    public List<IntegrityMessage> check(BibEntry entry) {
        Optional<String> author = entry.getField(FieldName.AUTHOR);
        Optional<String> title = entry.getField(FieldName.TITLE);
        Optional<String> year = entry.getField(FieldName.YEAR);

        String[] name =  author.get().split(" ");
        String lastName = name[name.length-1].toUpperCase();
        String yearString = year.get().toUpperCase();
        String[] titleString = title.get().split(" ");
        String titleWord = titleString[titleString.length-1].toUpperCase();

        if (!author.isPresent() || !title.isPresent() || !year.isPresent()) {
            return Collections.emptyList();
        }




        if (StringUtil.isBlank(entry.getCiteKeyOptional())) {
            String authorTitleYear = entry.getAuthorTitleYear(100);
            return Collections.singletonList(new IntegrityMessage(
                    Localization.lang("empty BibTeX key") + ": " + authorTitleYear, entry, BibEntry.KEY_FIELD));
        }

        return Collections.emptyList();
    }
}
