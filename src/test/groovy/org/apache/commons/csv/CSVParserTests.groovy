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
        CSVGroovyParser.parse(csv, CSVFormat.DEFAULT) { println it }

        then:
        thrown(IllegalArgumentException)
    }
 

    def "CSVGroovyParser with header"() {
        given: "CSV string with header"

        def csv = '''f1,f2,f3,f4
                     a ,b ,c ,"d d"'''

        def format = CSVFormat.RFC4180.withIgnoreSurroundingSpaces().withHeader()

        when: "CSVGroovyParser reads the string using a Closure"

        def map
        CSVGroovyParser.parse(csv, format) { map = it }

        then: "It gives a map the the Closure"
        map['f1'] == 'a'
        map.'f2' == 'b'
        map.f3 == 'c'
        map.f4 == 'd d'
    }
    
    def "CSVLambdaParser with header"() {
        given: "CSV string with header"

        def csv = '''f1,f2,f3,f4
                     a ,b ,c ,"d d"'''

        def format = CSVFormat.RFC4180.withIgnoreSurroundingSpaces().withHeader()

        when: "CSVGroovyParser reads the string using a Closure"

        def map
        CSVLambdaParser.parse(csv, format) { map = it }

        then: "It gives a map the the Closure"
        map['f1'] == 'a'
        map.'f2' == 'b'
        map.f3 == 'c'
        map.f4 == 'd d'
    }

}