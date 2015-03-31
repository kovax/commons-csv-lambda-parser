package org.apache.commons.csv;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.function.Consumer;

@SuppressWarnings("rawtypes")
public class CSVLambdaParser {
    
    //block is either Consumer<Map<String, String>> or Consumer<CSVRecord>

    public static void parse(final URL url, final Charset charset, final CSVFormat format, Consumer block) throws IOException {
        executeLoop( CSVParser.parse(url, charset, format), block );
    }

    public static void parse(final File file, final Charset charset, final CSVFormat format, Consumer block) throws IOException {
        executeLoop( CSVParser.parse(file, charset, format), block );
    }

    public static void parse(final String string, final CSVFormat format, Consumer block) throws IOException {
        executeLoop(CSVParser.parse(string, format), block);
    }

    @SuppressWarnings("unchecked")
    private static void executeLoop(CSVParser parser, Consumer block) throws IOException {

        for(CSVRecord record : parser) {
            if( parser.getHeaderMap() == null) block.accept(record);
            else                               block.accept(record.toMap());
        }
        
        parser.close();
    }
}
