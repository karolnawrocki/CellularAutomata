package CellularAutomata.model;

import javafx.scene.paint.Color;

import java.util.Vector;

public class GameOfLife extends CellularAutomaton{
    private NeighborhoodType neighborhoodType;
    @Override
    public String toString() {
        return "Game of life\n" +
                "Grid size: " + this.gridSize+"\n"+
                "Neighbourhood: " + this.neighborhoodType;
    }

    public GameOfLife(int gridSize, NeighborhoodType neighborhoodType) {
        super(gridSize);
        this.colorsArray = new Color[1];
        this.colorsArray[0] = Color.RED;
        this.neighborhoodType = neighborhoodType;
    }

    @Override
    public void calculateNextStep() {
        iterations++;
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                Cell currentCell = grid.getCell(i, j);
                Vector<Cell> neighboursArray;
                switch(neighborhoodType) {
                    case MoorePeriodic:
                        neighboursArray = currentCell.getNeighboursMoorePeriodic();
                        break;
                    case MooreNonperiodic:
                        neighboursArray = currentCell.getNeighboursMooreNonperiodic();
                        break;
                    default:
                        neighboursArray = currentCell.getNeighboursMoorePeriodic();
                }
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