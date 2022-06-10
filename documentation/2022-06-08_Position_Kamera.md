# Position der Kamera

## Status

TODO

## Context

Es kann auf eine starre Anzeige des Spielfeldes oder auf eine bewegte Kamera gesetzt werden.

## Decision

- Die Kamera soll sich dynamisch dem Spielgeschehen anpassen, indem der Spieler zentriert fixiert ist. Sind die Maße des Bildschirmes mindestens gleich derer, die für die Anzeige des gesamten Spielfeldes benötigt werden, ist eine Änderung der Kamera nicht notwendig. Andernfalls befinden sich Inhalte außerhalb des dargestellten Bereiches und wird mit einer Veränderung der Spielerposition sichtbar. 
Dadurch ist das Spiel unabhängig von der Bildschirmauflösung des Anwenders und Level beliebiger Ausmaße können genutzt werden.

## Consequences
