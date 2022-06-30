# Level: Erweiterung des Spiels

LibGDX stellt eine umfangreiche Funktionalität für die Verwendung von TMX Dateien bereit, die durch den 2D Level Editor Tiled erzeugt werden können.

Die Implementierung von MazeGame zielt darauf ab, dass beim Erfüllen einer Menge von Vorgaben bezüglich dieser Dateistruktur keine weiteren Änderungen im Quellcode notwendig sind als das Einlesen der betreffenden Datei. Dadurch können neue Level, auch auf Grundlage neuer Texturen, mit minimalen Änderungen des Quellcodes ergänzt werden.

Eine TMX Datei repräsentiert eine Map, das Design eines Levels in Form der verschiedenen Texturen und derer für die Spiellogik relevanten Eigenschaften.
Die Grundlage ist eine TSX Datei, die diese Texturen enthält.

## Vorgaben
- gültig für die Version 1.0.0 von MazeGame  
verkürzte Schreibweise: Textur (in Größe einer Kachel) mit den Eigenschaften „1“ und „2“ → (1,2)
- TSX
    - beliebig viele (), Wegstück ohne Aktion
    - beliebig viele (victory), erfolgreiches Beenden des Levels
    - beliebig viele (trap), Neustart des Levels
    - beliebig viele (wall_collision), Kollision mit Spieler
    - für jeden Türtyp und dabei für jede Himmelsrichtung, aus welcher dieser angesteuert werden kann  
      eine (door_direction, door_type , door_status)  
      &nbsp;&nbsp;&nbsp;- door_direction = [string] ∈{N,E,S,W}  
      &nbsp;&nbsp;&nbsp;- door_type = [int] ℕ   
      &nbsp;&nbsp;&nbsp;- door_status [int] 1  
      und eine (door_direction, door_type , door_status),  
      &nbsp;&nbsp;&nbsp;- door_direction = [string] ∈{N,E,S,W}  
      &nbsp;&nbsp;&nbsp;- door_type = [int] ℕ   
      &nbsp;&nbsp;&nbsp;- door_status [int] 0  
      wobei door_type identisch ist, und letztere Textur eine um den Wert 2 größere Tile ID hat
    - für jeden Türtypen eine (key_status, key_type)  
      &nbsp;&nbsp;&nbsp;- key_status = [int] 1  
      &nbsp;&nbsp;&nbsp;- key_type = [int] ℕ  
     dabei ist key_type gleich dem door_type des dazugehörigen Türtypen
    - eine (transparent)

- TMX
    - Nutzung zweier Kachelebenen:  
    „interaction“ enthält die Schlüssel (key_status, key_type)  
    „base“ enthält alle weiten Texturen
    - enthält eine „entry“-Tile, die in einer der Außenwände angeordnet ist

- Alle Eigenschaften sind überblicksweise in der Klasse Properties gelistet. Die hier beschriebenen Eigenschaften lösen in der derzeitigen Programmversion eine Aktion aus.  
- Die korrekte Nutzung von Tiled wird voraussetzt.