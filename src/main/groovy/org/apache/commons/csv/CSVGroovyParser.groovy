package org.apache.commons.csv

import java.nio.charset.Charset;

import groovy.transform.CompileStatic;

@CompileStatic
class CSVGroovyParser {


    public static void parse(final URL url, final Charset charset, final CSVFormat format, Closure block) throws IOException {
        executeLoop( CSVParser.parse(url, charset, format), block )
    }

    public static void parse(final File file, final Charset charset, final CSVFormat format, Closure block) throws IOException {
        executeLoop( CSVParser.parse(file, charset, format), block )
    }

    public static void parse(final String string, final CSVFormat format, Closure block) throws IOException {
        executeLoop( CSVParser.parse(string, format), block )
    }

    private static void executeLoop(CSVParser parser, Closure block) {

        for(CSVRecord record : parser) {
            if( parser.headerMap == null) block(record)
            else                          block(record.toMap())
        }

        parser.close()
    }


}
