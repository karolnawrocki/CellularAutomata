package CellularAutomata.model;

import java.util.Random;
import java.util.Vector;

public class Cell implements Comparable<Cell>{
    private Grid parent;

    @Override
    public int compareTo(Cell cell) {
        return Integer.compare(cell.id, this.id);
    }

    private boolean isAlive;
    private int id;
    private int row;
    private int column;

    Cell(int row, int column, boolean isAlive, Grid parent, int id){
        this.id = id;
        this.isAlive = isAlive;
        this.row = row;
        this.column = column;
        this. parent = parent;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Vector<Cell> getNeighboursMoorePeriodic(){
        Vector<Cell> neighboursVector = new Vector<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int currentRowNumber = this.row + i;
                int currentColumnNumber = this.column + j;
                if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
                else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
                if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
                else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
                if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive && !(j==0 && i == 0) ) {
                    neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
                }
            }
        }
        return neighboursVector;
    }

    public Vector<Cell> getNeighboursMooreNonperiodic(){
        Vector<Cell> neighboursVector = new Vector<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int currentRowNumber = this.row + i;
                int currentColumnNumber = this.column + j;
                if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
                    if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive && !(j == 0 && i == 0) ) {
                        neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
                    }
                }
            }
        }
        return neighboursVector;
    }

    Vector<Cell> getNeighboursVonNeumannNonperiodic(){
        Vector<Cell> neighboursVector = new Vector<>();
        int currentRowNumber;
        int currentColumnNumber;
        currentRowNumber = this.row - 1;
        currentColumnNumber = this.column;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive ) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }
        currentRowNumber = this.row + 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive ) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }

        currentRowNumber = this.row;
        currentColumnNumber = this.column - 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive ) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }

        currentColumnNumber = this.column + 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }
        return neighboursVector;
    }

    public Vector<Cell> getNeighboursVonNeumannPeriodic(){
        Vector<Cell> neighboursVector = new Vector<>();
        int currentRowNumber;
        int currentColumnNumber;
        currentRowNumber = this.row - 1;
        currentColumnNumber = this.column;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }
        currentRowNumber = this.row + 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }

        currentRowNumber = this.row;
        currentColumnNumber = this.column - 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }

        currentColumnNumber = this.column + 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }
        return neighboursVector;
    }

    public Vector<Cell> getNeighboursHexagonalLeftPeriodic(){
        Vector<Cell> neighboursVector = new Vector<>();
        int currentRowNumber;
        int currentColumnNumber;
        currentRowNumber = this.row - 1;
        currentColumnNumber = this.column;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }
        currentRowNumber = this.row + 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }

        currentRowNumber = this.row;
        currentColumnNumber = this.column - 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }

        currentColumnNumber = this.column + 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }

        currentRowNumber = this.row - 1;
        currentColumnNumber = this.column - 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }

        currentRowNumber = this.row + 1;
        currentColumnNumber = this.column + 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }
        return neighboursVector;
    }

    public Vector<Cell> getNeighboursHexagonalLeftNonperiodic(){
        Vector<Cell> neighboursVector = new Vector<>();
        int currentRowNumber;
        int currentColumnNumber;
        currentRowNumber = this.row - 1;
        currentColumnNumber = this.column;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive ) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }
        currentRowNumber = this.row + 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive ) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }

        currentRowNumber = this.row;
        currentColumnNumber = this.column - 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive ) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }

        currentColumnNumber = this.column + 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }
        currentRowNumber = this.row - 1;
        currentColumnNumber = this.column - 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }

        currentRowNumber = this.row + 1;
        currentColumnNumber = this.column + 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }
        return neighboursVector;
    }

    public Vector<Cell> getNeighboursHexagonalRightPeriodic(){
        Vector<Cell> neighboursVector = new Vector<>();
        int currentRowNumber;
        int currentColumnNumber;
        currentRowNumber = this.row - 1;
        currentColumnNumber = this.column;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }
        currentRowNumber = this.row + 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }

        currentRowNumber = this.row;
        currentColumnNumber = this.column - 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }

        currentColumnNumber = this.column + 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }

        currentRowNumber = this.row - 1;
        currentColumnNumber = this.column + 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }

        currentRowNumber = this.row + 1;
        currentColumnNumber = this.column - 1;
        if (currentRowNumber == -1) currentRowNumber = parent.getHeight() - 1;
        else if (currentRowNumber == parent.getHeight()) currentRowNumber = 0;
        if (currentColumnNumber == -1) currentColumnNumber = parent.getWidth() - 1;
        else if (currentColumnNumber == parent.getWidth()) currentColumnNumber = 0;
        if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
            neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
        }
        return neighboursVector;
    }

    public Vector<Cell> getNeighboursHexagonalRightNonperiodic(){
        Vector<Cell> neighboursVector = new Vector<>();
        int currentRowNumber;
        int currentColumnNumber;
        currentRowNumber = this.row - 1;
        currentColumnNumber = this.column;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive ) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }
        currentRowNumber = this.row + 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive ) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }

        currentRowNumber = this.row;
        currentColumnNumber = this.column - 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive ) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }

        currentColumnNumber = this.column + 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }
        currentRowNumber = this.row - 1;
        currentColumnNumber = this.column + 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }

        currentRowNumber = this.row + 1;
        currentColumnNumber = this.column - 1;
        if(currentRowNumber > -1 && currentRowNumber < parent.getHeight() && currentColumnNumber > -1 && currentColumnNumber < parent.getWidth()){
            if (parent.getCell(currentRowNumber, currentColumnNumber).isAlive) {
                neighboursVector.add(parent.getCell(currentRowNumber, currentColumnNumber));
            }
        }
        return neighboursVector;
    }

    public Vector<Cell> getNeighboursHexagonalRandomNonperiodic(){
        Random random = new Random();
        Vector<Cell> neighboursVector;
        if(random.nextBoolean()) {
            neighboursVector = getNeighboursHexagonalLeftNonperiodic();
        }
        else {
            neighboursVector = getNeighboursHexagonalRightNonperiodic();
        }
        return neighboursVector;
    }

    public Vector<Cell> getNeighboursHexagonalRandomPeriodic(){
        Random random = new Random();
        Vector<Cell> neighboursVector;
        if(random.nextBoolean())
            neighboursVector = getNeighboursHexagonalLeftPeriodic();
        else
            neighboursVector = getNeighboursHexagonalRightPeriodic();
        return neighboursVector;
    }
}

