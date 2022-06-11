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

    if (moveVector.x &lt; 0 || moveVector.y &gt; 0){
        corners[0] = new Point(playerPosition.xMin, playerPosition.yMax); // topLeft
    }
    if (moveVector.x &gt; 0 || moveVector.y &gt; 0){
        corners[1] = new Point(playerPosition.xMax, playerPosition.yMax); // topRight
    }
    if (moveVector.x &lt; 0 || moveVector.y &lt; 0) {
        corners[2] = new Point(playerPosition.xMin, playerPosition.yMin); // bottomLeft
    }
    if (moveVector.x &gt; 0 || moveVector.y &lt; 0) {
        corners[3] = new Point(playerPosition.xMax, playerPosition.yMin); // bottomRight
    }

    return corners;
}
```


#### [Map.accountForCollision]
1.
