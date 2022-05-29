package com.maze.game;

import com.badlogic.gdx.Gdx;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Grid {
    /*
    Das Gitter der Kacheln orientiert sich mit den Indizes an dem 1. Quadranten eines zweidimensionalen kartesischen Koordinatensystems.
    Damit konform mit libGDX, (0,0) fixiert in der unteren linken Ecke.

    0 Wand
    1 Pfad
     o (opening) Eingangspfadfeld
     e (ending) Ausgangspfadfeld
    Gitter gleichbleibend je Level, zur Anordnung der Texturen in der render-Methode von MazeGame zu jedem zu berechnenden Frame.
    Erweiterbar um Position von Items oder Ähnlichem.
    */
    private int sizeX;
    private int sizeY;
    private Point opening;
    private Point ending;
    private List<List<Character>> grid;

    public int getSizeX() {return this.sizeX;}
    public int getSizeY() {return this.sizeY;}
    public Point getOpening() {return this.opening;}
    public Point getEnding() {return this.ending;}
    public char getValue(int x, int y) {return this.grid.get(y).get(x);}

    public void printGrid() {
        for (int i = sizeY - 1; i >= 0; i--) {
            for (int j = 0; j < sizeX; j++) {
                System.out.print(this.grid.get(i).get(j));
            }
            if (i > 0)
                System.out.println();
        }
    }

    public Grid load(
            int level)
            throws IOException {
        // Einlesen des Level-Aufbaus aus Textdatei in eine 2D Arraylist.

        int fileSizeX = 0, currentFileSizeX, currentFileSizeY = 0;
        Point fileOpening = null, fileEnding = null;
        List<List<Character>> fileGrid = new ArrayList<List<Character>>();

        String legalChars = new String(new char[] {'o', 'e', '0', '1'});

        BufferedReader br = new BufferedReader(new FileReader(String.valueOf(Paths.get(Gdx.files.getLocalStoragePath(), "core/src/com/maze/game/level/", Integer.toString(level)))));
        String line = br.readLine();

        /*
        Fehlerbehandlung:
        - ein Eingang, dieser auf linker Seite
        - ein Ausgang, dieser auf rechter Seite
        - Zeichen einer bestimmten Auswahl
        - gleiche Zeilenlänge
        */
        // todo umfangreiche Fehlerbehandlung notwendig, wenn Level nur vorgegeben sind? Oder beibehalten: Verwendung "selbsterstellter" Level als mögliches Feature?
        while (line != null) {
            if (fileSizeX == 0) {
                fileSizeX = line.length();
            } else {
                if (line.length() != fileSizeX)
                    throw new IllegalArgumentException();
            }

            List<Character> lineList = new ArrayList<Character>();
            currentFileSizeX = 0;

            for (char c : line.toCharArray()) {
                lineList.add(c);
                if (c == 'o') {
                    if (fileOpening == null && currentFileSizeX == 0)
                        fileOpening = new Point(currentFileSizeX, currentFileSizeY);
                    else {
                        System.out.println(c);
                        throw new IllegalArgumentException();
                    }
                }
                else if (c == 'e') {
                    if (fileEnding == null && currentFileSizeX == fileSizeX - 1)
                        fileEnding = new Point(currentFileSizeX, currentFileSizeY);
                    else {
                        System.out.println(c);
                        System.out.println(fileSizeX - 1);
                        throw new IllegalArgumentException();
                    }
                }
                else if (!legalChars.contains("" + c)) {
                    System.out.println(c);
                    throw new IllegalArgumentException();
                }
                currentFileSizeX++;
            }

            // neue Zeile an den Index 0 anfügen, da die Datei entgegen der gewollten Orientierung gelesen wird
            fileGrid.add(0, lineList);
            line = br.readLine();

            currentFileSizeY++;
        }

        if (fileEnding == null || fileOpening == null)
            throw new IllegalArgumentException();

        this.sizeX = fileSizeX;
        this.sizeY = fileGrid.size();
        this.opening = fileOpening;
        this.ending = fileEnding;
        this.grid = fileGrid;

        return this;
    }
}
