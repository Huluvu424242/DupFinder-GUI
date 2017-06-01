[![Stories in Ready](https://badge.waffle.io/FunThomas424242/DupFinder-GUI.svg?label=ready&title=Ready)](http://waffle.io/FunThomas424242/DupFinder-GUI)
[![Build Status](https://travis-ci.org/FunThomas424242/DupFinder-GUI.svg?branch=master)](https://travis-ci.org/FunThomas424242/DupFinder-GUI)
[![codecov](https://codecov.io/gh/funthomas424242/DupFinder-GUI/branch/master/graph/badge.svg)](https://codecov.io/gh/funthomas424242/DupFinder-GUI)
[ ![Download](https://api.bintray.com/packages/funthomas424242/funthomas424242-libs/DupFinder-GUI/images/download.svg) ](https://bintray.com/funthomas424242/funthomas424242-libs/DupFinder-GUI/_latestVersion)
# DupFinder-GUI
Simple Oberfläche zur Bedienung der Duplikate Finder Bibliothek DupFinder.

[Projektstatistik at BlackDuck (Ohloh)](https://www.openhub.net/p/DupFinder-GUI)

## Benutzung

```
java -jar DupFinder-GUI-0.0.1-jar-with-dependencies.jar <directorypath>
```

## Weiterentwicklung

### Bau eines Releases
```
mvn unleash:perform 
```
**Hinweis**: Aktuell können wir kein automatisches Release über maven bauen, da die DupFinder lib noch nicht als Release verfügbar ist und wir somit eine SNAPSHOT Abhängigkeit besitzen.

## Lizenz - GPL v3
Da die Programmbibliothek [DupFinder](https://github.com/mkymikky/DupFinder) unter der GPL-v3 lizensiert ist,
müssen alle abgeleiteten Werke ebenfalls unter der GPL-v3 lizensiert werden. Da geplant ist aus der GUI Anwendung 
auf das JAR der DupFinder Bibliothek zu "verlinken" bzw. dieses sogar zu embedden, muss diese GUI Anwendung
ebenfalls unter GPL v3 stehen. 

Die GPL betrachtet Verlinkung von Programmkode (Objektkode Ebene per Linker bzw. bei Java ByteCode Ebene per Compiler) 
als Ableitung eines Werkes.

