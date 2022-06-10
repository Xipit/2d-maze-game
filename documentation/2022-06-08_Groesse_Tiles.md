# Größe der Kacheln des Spielfeldes

## Status

TODO

## Context

Das Spiel soll zunächst eine gewisse Anzahl vordefinierter Level bereitstellen.  
Die Implementierung hat so zu erfolgen, dass die Level verschiedene Abmessungen in der Länge und Breite haben können. Das Spielfeld eines Levels besteht aus einer Menge Kacheln (tiles), die in  der einfachsten Form in einem Rechteck aneinander angeordnet ein Raster ohne Lücken ergeben.

Die Geometrie und die Größe in Pixel dieser Tiles ist festzulegen.  
Verbreitete Größen sind  
- 32x32  
- 64x64

## Decision

- Es werden rechtwinklige Tiles verwendet.
- Die Tiles haben eine Länge von 64 Pixel.
     - Durch die starke Varietät der Pixeldichte heutiger Monitore ist eine zu kleine Pixelgröße auszuschließen, da wenig umfangreiche Level bei entsprechender Auflösung gering bildschirmfüllend wären.
     - Ein größerer Detailgrad ist möglich. Eine manuelle Skalierung des Fensters durch den Nutzer (etwa bei dem zuvor beschriebenen Problem) ginge weniger zulasten der Optik der Details.
     - Die Entscheidung zur Kameraposition bestärkt diese Entscheidung, da nicht die bestehende Ressource Bildschirmfläche eingeteilt werden muss, die größeren Maße der Tiles also nicht Nachteil sind.

## Consequences
