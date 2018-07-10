package org.jabref.model.entry.specialfields;

import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.BibtexEntryTypes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NovasValidacoesTest {

    private BibEntry entry;

    @Before
    public void setUp() {
        entry = new BibEntry();
        entry.setType(BibtexEntryTypes.ARTICLE);
    }

    @Test
    public void testYearField() throws Exception {

        String year = "2018";
        entry.setField("year", year);

        String regex = "[1-9][0-9][0-9][0-9]";

        Assert.assertEquals(true, year.matches(regex));
    }

}
