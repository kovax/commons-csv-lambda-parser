package org.apache.commons.csv


import spock.lang.Specification


class CSVParserTests extends Specification {

    def "CSVParser without header"() {
        setup:
        def csv = 'a,b,c'
        
        when:
        def parser = CSVParser.parse(csv, CSVFormat.DEFAULT)

        then:
        parser != null
        parser.getHeaderMap() == null
        parser.getRecordNumber() == 0
        def record = parser.records[0]
        parser.getRecordNumber() == 1
        record.toMap().isEmpty() == true
        record[0] == 'a'
        record[1] == 'b'
        record[2] == 'c'
    }


    def "CSVParser with header"() {
        given:
        def csv = '''f1,f2,f3,f4
                     a ,b ,c ,"d d"'''

        def format = CSVFormat.RFC4180.withIgnoreSurroundingSpaces().withHeader()
        
        when:
        def parser = CSVParser.parse(csv, format)

        then: "CSVParser "
        parser != null
        parser.getHeaderMap() != null
        parser.getRecordNumber() == 0
        def record = parser.records[0]
        parser.getRecordNumber() == 1
        def map = record.toMap()
        map['f1'] == 'a'
        map.f2 == 'b'
        map.f3 == 'c'
        map.f4 == 'd d'
    }


    def "CSVGroovyParser without header"() {
        setup:
        def csv = 'a,b,c'

        when:
        CSVRecord record
        CSVGroovyParser.parse(csv, CSVFormat.DEFAULT) { record = it }

        then:
        record[0] == 'a'
        record[1] == 'b'
        record[2] == 'c'
    }
 

    def "CSVGroovyParser with header"() {
        given: "CSV string with header"

        def csv = '''f1,f2,f3,f4
                     a ,b ,c ,"d d"'''

        def format = CSVFormat.RFC4180.withIgnoreSurroundingSpaces().withHeader()

        when: "CSVGroovyParser reads the line using a Closure"

        Map record
        CSVGroovyParser.parse(csv, format) { record = it }

        then: "It gives a map the the Closure"
        record['f1'] == 'a'
        record.'f2' == 'b'
        record.f3 == 'c'
        record.f4 == 'd d'
    }


    def "CSVLambdaParser without header"() {
        setup:
        def csv = 'a,b,c'

        when:
        CSVRecord record
        CSVLambdaParser.parse(csv, CSVFormat.DEFAULT) { record = it }

        then:
        record[0] == 'a'
        record[1] == 'b'
        record[2] == 'c'
    }


    def "CSVLambdaParser with header"() {
        given: "CSV string with header"

        def csv = '''f1,f2,f3,f4
                     a ,b ,c ,"d d"'''

        def format = CSVFormat.RFC4180.withIgnoreSurroundingSpaces().withHeader()

        when: "CSVLambdaParser reads the line using a Lambda funtion"

        Map record
        CSVLambdaParser.parse(csv, format) { record = it }

        then: "It gives a map the the Lambda"
        record['f1'] == 'a'
        record.'f2' == 'b'
        record.f3 == 'c'
        record.f4 == 'd d'
    }

}