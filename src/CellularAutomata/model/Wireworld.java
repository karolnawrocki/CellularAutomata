package CellularAutomata.model;

import javafx.scene.paint.Color;

import java.util.Vector;

public class Wireworld extends CellularAutomaton {

    public Wireworld(int gridSize) {
        super(gridSize);
        this.colorsArray = new Color[3];
        colorsArray[0] = Color.BLUE;
        colorsArray[1] = Color.RED;
        colorsArray[2] = Color.YELLOW;
    }

    @Override
    public void calculateNextStep() {
        iterations++;
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                Cell currentCell = grid.getCell(i, j);
                if (currentCell.isAlive()) {
                    Cell nextCell = grid2.getCell(i,j);
                    nextCell.setAlive(true);
                    if(currentCell.getId() == 0)
                        nextCell.setId(1);
                    else if(currentCell.getId() == 1)
                        nextCell.setId(2);
                    else if(currentCell.getId() == 2){
                        Vector<Cell> neighboursArray = currentCell.getNeighboursMooreNonperiodic();
                        int numberOfElectronHeadNeighbours = 0;
                        for(Cell c: neighboursArray)
                            if(c.getId() == 0)
                                numberOfElectronHeadNeighbours++;
                        if(numberOfElectronHeadNeighbours == 1 || numberOfElectronHeadNeighbours == 2)
                            nextCell.setId(0);
                        else
                            nextCell.setId(2);
                    }
                }
            }
        }
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                grid.getCell(i,j).setAlive(grid2.getCell(i,j).isAlive());
                grid.getCell(i,j).setId(grid2.getCell(i,j).getId());
            }
        }
    }
    @Override
    public String toString() {
        return "Wireworld\n" +
                "Grid size: " + this.gridSize;
    }
}
