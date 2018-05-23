package CellularAutomata.model;

import javafx.scene.paint.Color;

import java.util.Random;
import java.util.Vector;

public class ForestFire extends CellularAutomaton{
    private Random random;
    private double newTreeChance;
    private double lightningChance;
    public ForestFire(int gridSize, double newTreeChance, double lightningChance) {
        super(gridSize);
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                grid.getCell(i,j).setAlive(true);
                grid2.getCell(i,j).setAlive(true);
            }
        }

        this.newTreeChance = newTreeChance;
        this.lightningChance = lightningChance;
        this.random = new Random();
        this.colorsArray = new Color[3];
        colorsArray[0] = Color.BLACK;
        colorsArray[1] = Color.GREEN;
        colorsArray[2] = Color.RED;
    }

    @Override
    public void calculateNextStep() {
        iterations++;
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                Cell currentCell = grid.getCell(i,j);
                Cell nextCell = grid2.getCell(i,j);
                if(currentCell.getId() == 0){//empty
                    if(random.nextInt((int)(1 / newTreeChance)) == 0){
                        nextCell.setId(1);
                    }
                }
                else if(currentCell.getId() == 1) {//tree
                    if(random.nextInt((int)(1 / lightningChance)) == 0){
                        nextCell.setId(2);
                    }
                }
                else if(currentCell.getId() == 2) {//burning tree
                    Vector<Cell> neighboursVector = currentCell.getNeighboursMooreNonperiodic();
                    for (Cell cell: neighboursVector) {
                        if(cell.getId() == 1){
                            grid2.getCell(cell.getRow(),cell.getColumn()).setId(2);
                        }
                    }
                    nextCell.setId(0);
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
}
