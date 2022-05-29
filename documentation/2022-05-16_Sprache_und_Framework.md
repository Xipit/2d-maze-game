# Wahl der Sprache und Framework

## Status

accepted  
2022-05-16

## Context

- Sprache ist sehr vom Framework abhängig, da Spieleentwicklung sehr spezialisiert ist und andere Anforderungen als typische Software hat. Keine Sprache alleine ist sinnvoll für effektive Spieleentwicklung.
- Wahl zwischen
 - C# mit
     - Unity
 - Java mit
     - jMonkeyEngine
     - LITIEngine
     - libGDX

## Decision

- Erste Tendenz geht zu Java, da ein Großteil der Projektmitglieder etwas bis gar keine Programmiererfahrung besitzt und Java Teil der OOP Vorlesung ist.  
Zudem ist ein simples Spiel geplant, welches die umfangreichen Möglichkeiten von Unity nicht benötigt.
- jMonkeyEngine ist abgelehnt, da es einen eigenen Editor zur Entwicklung voraussetzt und dieser ab zweiten Startup das Probeprojekt nicht mehr laden wollte.
- LITIEngine ist auch abgelehnt, da obwohl sie grundsätzlich funktionierte keine guten Tools, bzw. Tutorials für ein Tile-basiertes 2D Spiel bietet.
- LibGDX ist gewählt, weil das Setup sehr einfach ist und es sehr viele Tutorials und Tools bietet.

## Consequences
