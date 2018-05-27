package CellularAutomata.model;

import javafx.scene.paint.Color;

public abstract class CellularAutomaton{
    long iterations;
    int gridSize;
    Grid grid,grid2;
    Color[] colorsArray;

    public abstract void calculateNextStep();
    CellularAutomaton(int gridSize) {
        this.gridSize = gridSize;
        grid = new Grid(gridSize, gridSize);
        grid2 = new Grid(gridSize, gridSize);
        this.iterations = 0;
    }
    public int getGridSize(){ return gridSize; }
    public Grid getGrid() {
        return grid;
    }
    public Color getColor(int id){
        return this.colorsArray[id];
    }

    public long getIterations() {
        return iterations;
    }

    public Color[] getColorsArray() {
        return colorsArray;
    }
}