package org.apache.commons.csv;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import org.junit.Test;



public class CSVLambdaParserTests {

    @Test
    public void CSVLambdaParserWithoutHeader() throws IOException {
        String csv = "a,b,c";

        CSVLambdaParser.parse(csv, CSVFormat.DEFAULT, record -> {
            CSVRecord r = (CSVRecord)record;

            assertEquals(r.get(0), "a");
            assertEquals(r.get(1), "b");
            assertEquals(r.get(2), "c");
        });
    }

    @Test
    public void CSVLambdaParserWithHeader() throws IOException {
        String csv = "f1,f2,f3,f4\n  a ,b ,c ,\"d d\"";

        //CSVRecord record;
        CSVLambdaParser.parse(csv, CSVFormat.RFC4180.withIgnoreSurroundingSpaces().withHeader(), record -> {
            Map<String,String> m = (Map<String,String>)record;

            assertEquals(m.get("f1"), "a");
            assertEquals(m.get("f2"), "b");
            assertEquals(m.get("f3"), "c");
            assertEquals(m.get("f4"), "d d");
        });
    }
}