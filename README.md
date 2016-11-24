# ReferenceData

Sposob testowego uruchomienia<br/>
1) Nalezy skompilowac projekty:<br/>
a) referencedata-all<br/>
b) referencedata-tests<br/>

2) Projekt mozna uruchomic testowo na wbudowanym serwerze tomee:<br/>
a) w katalogu projektu referencedata-parent uruchomic polecenie:<br/>
mvn tomee:run<br/>
b) po uruchomieniu serwera dostepne sa aplikacje:<br/>

    http://localhost:8080/referencedata-sampledata-generator/generator.jsp - sluzy do ladowania testowych slownikow
    http://localhost:8080/referencedata-test-tld/test.jsp - testowa aplikacja do podgladu dzialania taglib-a i zaladowanych slownikow
    
Oprogramowanie zostało zaprojektowane i wykonane przez firmę SKG S.A. w Bielsku-Białej, jako jeden z rezultatów projektu „Modularny system teleinformatyczny wspomagający obsługę procesów międzynarodowej dostawy towarów”, zrealizowanego w ramach Programu Operacyjnego Innowacyjna Gospodarka 2007-2013. 
