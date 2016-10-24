# ReferenceData

Sposob testowego uruchomienia:
1) Nalezy skompilowac projekty:
a) referencedata-all
b) referencedata-tests

2) Projekt mozna uruchomic testowo na wbudowanym serwerze tomee:
a) w katalogu projektu referencedata-parent uruchomic polecenie:
mvn tomee:run
b) po uruchomieniu serwera dostepne sa aplikacje:

    http://localhost:8080/referencedata-sampledata-generator/generator.jsp - sluzy do ladowania testowych slownikow
    http://localhost:8080/referencedata-test-tld/test.jsp - testowa aplikacja do podgladu dzialania taglib-a i zaladowanych slownikow
