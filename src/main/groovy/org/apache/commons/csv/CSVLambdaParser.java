package org.apache.commons.csv;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.function.Consumer;

public class CSVLambdaParser {

    public static void parse(final URL url, final Charset charset, final CSVFormat format, Consumer<Map<String, String>> block) throws IOException {
        executeLoop( CSVParser.parse(url, charset, format), block );
    }

    public static void parse(final File file, final Charset charset, final CSVFormat format, Consumer<Map<String, String>> block) throws IOException {
        executeLoop( CSVParser.parse(file, charset, format), block );
    }

    public static void parse(final String string, final CSVFormat format, Consumer<Map<String, String>> block) throws IOException {
        executeLoop(CSVParser.parse(string, format), block);
    }

    private static void executeLoop(CSVParser parser, Consumer<Map<String, String>> block) throws IOException {
        if( parser.getHeaderMap() == null) throw new IllegalArgumentException("Header must be set");

        for(CSVRecord record : parser) {
            block.accept(record.toMap());
        }
    }
}
