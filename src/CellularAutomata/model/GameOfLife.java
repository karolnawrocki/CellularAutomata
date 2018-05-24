package CellularAutomata.model;

import javafx.scene.paint.Color;

import java.util.Vector;

public class GameOfLife extends CellularAutomaton{
    @Override
    public String toString() {
        return "Game of life\n" +
                "Grid size: " + this.gridSize;
    }

    public GameOfLife(int gridSize) {
        super(gridSize);
        this.colorsArray = new Color[1];
        this.colorsArray[0] = Color.RED;
//        grid.getCell(0,3).setAlive(true);
//        grid.getCell(1,4).setAlive(true);
//        grid.getCell(2,2).setAlive(true);
//        grid.getCell(2,3).setAlive(true);
//        grid.getCell(2,4).setAlive(true);
//
//        grid.getCell(8,2).setAlive(true);
//        grid.getCell(9,2).setAlive(true);
//        grid.getCell(10,2).setAlive(true);
//
//        grid.getCell(2,11).setAlive(true);
//        grid.getCell(2,12).setAlive(true);
//        grid.getCell(1,11).setAlive(true);
//        grid.getCell(1,12).setAlive(true);
    }

    @Override
    public void calculateNextStep() {
        iterations++;
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                Vector<Cell> neighboursArray = grid.getCell(i,j).getNeighboursMoorePeriodic();
                int numberOfNeighbours = neighboursArray.size();
                if(grid.getCell(i,j).isAlive()){
                    if(numberOfNeighbours == 2 || numberOfNeighbours == 3){
                        grid2.getCell(i,j).setAlive(true);
                    }
                    else{
                        grid2.getCell(i,j).setAlive(false);
                    }
                }
                else if(!grid.getCell(i,j).isAlive()){
                    if(numberOfNeighbours == 3){
                        grid2.getCell(i,j).setAlive(true);
                    }
                    else{
                        grid2.getCell(i,j).setAlive(false);
                    }
                }
            }
        }
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid.getCell(i,j).setAlive(grid2.getCell(i,j).isAlive());
            }
        }
    }
}