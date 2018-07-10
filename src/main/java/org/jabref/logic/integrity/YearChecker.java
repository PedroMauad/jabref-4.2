package org.jabref.logic.integrity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.jabref.logic.l10n.Localization;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.FieldProperty;
import org.jabref.model.entry.InternalBibtexFields;
import org.jabref.model.strings.StringUtil;

public class YearChecker implements ValueChecker {

    private static final Predicate<String> CONTAINS_FOUR_DIGIT = Pattern.compile("([^0-9]|^)[0-9]{4}([^0-9]|$)")
            .asPredicate();
    private static final Predicate<String> ENDS_WITH_FOUR_DIGIT = Pattern.compile("[0-9]{4}$").asPredicate();
    private static final String PUNCTUATION_MARKS = "[(){},.;!?<>%&$]";

    /**
     * Checks, if the number String contains a four digit year and ends with it.
     * Official bibtex spec:
     * Generally it should consist of four numerals, such as 1984, although the standard styles
     * can handle any year whose last four nonpunctuation characters are numerals, such as ‘(about 1984)’.
     * Source: http://ftp.fernuni-hagen.de/ftp-dir/pub/mirrors/www.ctan.org/biblio/bibtex/base/btxdoc.pdf
     */

    public Optional<String> checkValue(String value) {

        System.out.println("checkValue - Enter");

        if (StringUtil.isBlank(value)) {
            return Optional.empty();
        }

        if (!CONTAINS_FOUR_DIGIT.test(value.trim())) {
            System.out.println("checkValue - CONTAINS_FOUR_DIGIT");
            return Optional.of(Localization.lang("should contain a four digit number"));
        }

        if (!ENDS_WITH_FOUR_DIGIT.test(value.replaceAll(PUNCTUATION_MARKS, ""))) {
            System.out.println("checkValue - ENDS_WITH_FOUR_DIGIT");
            return Optional.of(Localization.lang("last four nonpunctuation characters should be numerals"));
        }

        if (!value.matches("^[0-9]+")) {
            System.out.println("checkValue - value.matches");
            return Optional.of(Localization.lang("should contain a four digit number"));
        }

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            df.setLenient(false);
            df.parse(value);
        } catch (ParseException e) {
            System.out.println("checkValue - TRY");
            return Optional.of(Localization.lang("não é uma data valida"));
        }

        System.out.println("checkValue - empty");
        return Optional.empty();
    }

}
