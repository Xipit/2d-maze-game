# Funktionsweise

Dies ist eine Beschreibung des fehlgeschlagenen Versuch ein eigenes Kollisionssystem zu implementieren

## Player

#### [Player.input]
1. Wird in der Render Methode des Screens aufgerufen, welche jedem Frame ausgeführt wird
2. Setze je nach Eingabe den x/y Wert eines 2D Vektor +1 oder -1 (Das Koordinatensystem von libGdx fängt unten links, anstatt wie häufig oben links an)
3. normalisiere den 2D Vektor um bei diagonalen Bewegungen nicht schneller zu laufen
4. rufe Player.move und gebe den 2D Vektor mit der Geschwindigkeit skaliert mit

```java
Vector2 vector = new Vector2(0, 0);

if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
    vector.x = -1;
}
else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
    vector.x = +1;
}
if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
    vector.y = -1;
}
else if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
    vector.y = +1;
}

vector.nor();  //normalize vector length = 1
if(!vector.isZero()) move(vector.scl(speed), map);
```

#### [Player.move]
1. skaliere den Bewegungsvektor mit der DeltaTime
2. addiere zum Bewegungsvektor den Korrektionsvektor von Map.accountForCollision (Bewegungsvektor und bisherige Position mitgegeben)

```java
float deltaTime = Gdx.graphics.getDeltaTime();

moveVector.scl(deltaTime);

moveVector.add(map.accountForCollision(moveVector, position));
```

## Map

#### [Map.accountForCollision]
1. berechne neue potentielle Position mithilfe des Bewegunsvektors
2. Da für die Kollision von rechteckigen Objekten die 4 Eckpunkte relevant sind wird jede Information in einem Array mit der Länge 4 gespeichert wo jeder Index einem Eckpunkt entspricht (0-&gt; topLeft(-,+); 1-&gt; topRight(+,+); 2-&gt; bottomLeft(-,-); 3-&gt; bottomRight(+,-). Falls ein Eckpunkt für die Bewegung nicht relevant für Kollisionsberechnung war, dann wurde er null gelassen. 
3. Berechnung der Eckpunkte mithilfe von Map.getCornerPoints (Bewegungsvektor und neue Position mitgegeben)
```java
Position position = previousPosition.update(moveVector);

Point[] cornerPoints = getCornerPoints(moveVector, position);
```

#### [Map.getCornerPoints]
1. Zuweisung des Eckpunkt Arrays mithilfe des Bewegungsvektorens. Bspw.: Wenn der x Wert &lt; 0 oder der y Wert &gt; 0 ist, dann wird am Index 0 der Eckpunkt mithilfe der neuen Position ermittelt und zugewisen. 
2. Rückgabe des Eckpunkt Array
```java
private Point[] getCornerPoints(Vector2 moveVector, Position playerPosition) {
    Point[] corners = {null, null, null, null};

    /*
        0 - 1           
        |   |           
        2 - 3           
    */

    if (moveVector.x < 0 || moveVector.y > 0){
        corners[0] = new Point(playerPosition.xMin, playerPosition.yMax); // topLeft
    }
    if (moveVector.x > 0 || moveVector.y > 0){
        corners[1] = new Point(playerPosition.xMax, playerPosition.yMax); // topRight
    }
    if (moveVector.x < 0 || moveVector.y < 0) {
        corners[2] = new Point(playerPosition.xMin, playerPosition.yMin); // bottomLeft
    }
    if (moveVector.x > 0 || moveVector.y < 0) {
        corners[3] = new Point(playerPosition.xMax, playerPosition.yMin); // bottomRight
    }

    return corners;
}
```
Zur korrekten berechnung der relevanten Eckpunkte wurde folgende Skizze verwendet:
![](https://user-images.githubusercontent.com/48943886/173302055-53f2b9ed-10fc-4881-9cf6-bee875665b13.jpg "CornerPoints Hilfsskizze")

#### [Map.accountForCollision]
1. Belegung der tatsächlichen Map Cell wie sie von libGdx sowie ihrem Index (mithilfe von Map.getTileIndex) für jeden relevanten Eckpunkt. Dafür sind zwei versch. Arrays notwendig, da die Map Cell nicht ihren eigenen Index speichert. Mit Index ist hierbei ihre Position wie in einer Tabelle zu verstehen (Bspw. [1,5]), alle anderen Koordinaten sind in Pixel (Bspw. [64,256]) angegeben.

```java
TiledMapTileLayer.Cell[] potentialCollisionCells = {null, null, null, null};
Point[] cornerPointTileIndices = {null, null, null, null};

for (int i = 0; i < cornerPoints.length; i ++) {
    if(cornerPoints[i] == null) continue;
    
    Point cornerPointTileIndex = getTileIndex(cornerPoints[i]);
    cornerPointTileIndices[i] = cornerPointTileIndex;
    potentialCollisionCells[i] = (this.tileLayer.getCell(cornerPointTileIndex.x, cornerPointTileIndex.y));
}
```

#### [map.getTileIndex]
1. Berechnung der Indizes mithilfe der globalen statischen Werte TILE_WIDTH und TILE_HEIGHT.
2. Rückgabe eines Punktes mit den Indizes.
```java
private Point getTileIndex(Point pixelCoordinates){
    int xIndex = (pixelCoordinates.x - pixelCoordinates.x % TILE_WIDTH) / TILE_WIDTH;
    int yIndex = (pixelCoordinates.y - pixelCoordinates.y % TILE_WIDTH) / TILE_WIDTH;
    return new Point(xIndex, yIndex);
}
```

#### [Map.accountForCollision]
1. Für jede potenzielle Kollisionszelle wird zuerst gecheckt ob sie relevant ist und es tatsächlich eine Zelle an dem Punkt befindet.
2. Falls diese Zelle die Property mit dem Key "wall_collision" (wird im Tile-Editor "Tiled" gesetzt) berechne zunächst die Pixel Koordinaten ihrer linken unteren Ecke.
3. Je nach der **relevanten Ecke des Spielers** wird die **Position der Tile** verändert. Hierbei ist zu beachten das jeweils die entgegengesetzten Ecken des Spielers die entsprechende Ecke der Tile berühren. So wird bei der Ecke topLeft (1) nichts geändert da für sie die Standardwerte (X und Y) der Tile Ecke bottomLeft (2) relevant sind.
4. Am Ende wird der benötigte Korrektionsvektor (der zu dem ursprünglichen Bewegungsvektor addiert wird) ausgerechnet.
```java
ArrayList<Vector2> correctionVectors = new ArrayList<>();

for(int i = 0; i < potentialCollisionCells.length; i ++){
    if(potentialCollisionCells[i] == null || potentialCollisionCells[i].getTile() == null){
        continue;
    }
    if (potentialCollisionCells[i].getTile().getProperties().containsKey("wall_collision")){
    
        Point tilePosition = new Point(cornerPointTileIndices[i].x * TILE_WIDTH, cornerPointTileIndices[i].y * TILE_HEIGHT); // bottomLeft of Tile
        //adjust tilePosition to match the relevant corner
        tilePosition.x += (i == 0 || i == 2) ? TILE_WIDTH : 0;
        tilePosition.y += (i == 2 || i == 3) ? TILE_HEIGHT : 0;

        correctionVectors.add(new Vector2(tilePosition.x - cornerPoints[i].x, tilePosition.y - cornerPoints[i].y)); // pushes player into available space
    }
}
```
An dieser Stelle ist auch der Fehler unseres Ansatzes zu finden. 
