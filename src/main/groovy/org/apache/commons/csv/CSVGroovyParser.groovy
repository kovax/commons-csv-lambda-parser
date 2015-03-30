package org.apache.commons.csv

import java.nio.charset.Charset;

import groovy.transform.CompileStatic;

@CompileStatic
class CSVGroovyParser {


    public static void parse(final URL url, final Charset charset, final CSVFormat format, Closure cl) throws IOException {
        executeLoop( CSVParser.parse(url, charset, format), cl )
    }

    public static void parse(final File file, final Charset charset, final CSVFormat format, Closure cl) throws IOException {
        executeLoop( CSVParser.parse(file, charset, format), cl )
    }

    public static void parse(final String string, final CSVFormat format, Closure cl) throws IOException {
        executeLoop( CSVParser.parse(string, format), cl )
    }
    
    private static void executeLoop(CSVParser parser, Closure cl) {
        if( parser.headerMap == null) throw new IllegalArgumentException("Header must be set");

        for(CSVRecord record : parser) {
            cl(record.toMap())
        }
    }


}
