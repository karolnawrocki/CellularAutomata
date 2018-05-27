package CellularAutomata.model;

import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class SeedGrowth extends CellularAutomaton {
    private Random random;
    private NeighborhoodType neighborhoodType;
    private int numberOfSeeds;
    public SeedGrowth(int gridSize, int numberOfSeeds, NeighborhoodType neighborhoodType) {
        super(gridSize);
        this.numberOfSeeds = numberOfSeeds;
        this.random = new Random();
        this.colorsArray = new Color[numberOfSeeds];
        this.neighborhoodType = neighborhoodType;
        Vector<Cell> availableCells = new Vector<>();
        if(numberOfSeeds > gridSize * gridSize)
            this.numberOfSeeds = gridSize * gridSize;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                availableCells.add(grid.getCell(i,j));
            }
        }
        Collections.shuffle(availableCells);
        for(int i = 0; i < this.numberOfSeeds; i++){
            availableCells.get(i).setAlive(true);
            availableCells.get(i).setId(i);
            colorsArray[i] = new Color(random.nextDouble(),random.nextDouble(),random.nextDouble(),1.0);
        }
    }

    @Override
    public String toString() {
        return "Seed growth\n" +
                "Grid size: " + this.gridSize + "\n" +
                "Seeds: " + this.numberOfSeeds + "\n" +
                "Neighbourhood: " + this.neighborhoodType;
    }

    @Override
    public void calculateNextStep() {
        iterations++;
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                Cell currentCell = grid.getCell(i, j);
                Vector<Cell> neighboursArray;
                switch(neighborhoodType){
                    case VonNeumannPeriodic:
                        neighboursArray = currentCell.getNeighboursVonNeumannPeriodic();
                        break;
                    case VonNeumannNonperiodic:
                        neighboursArray = currentCell.getNeighboursVonNeumannNonperiodic();
                        break;
                    case MoorePeriodic:
                        neighboursArray = currentCell.getNeighboursMoorePeriodic();
                        break;
                    case MooreNonperiodic:
                        neighboursArray = currentCell.getNeighboursMooreNonperiodic();
                        break;
                    case HexagonalLeftPeriodic:
                        neighboursArray = currentCell.getNeighboursHexagonalLeftPeriodic();
                        break;
                    case HexagonalLeftNonperiodic:
                        neighboursArray = currentCell.getNeighboursHexagonalLeftNonperiodic();
                        break;
                    case HexagonalRightPeriodic:
                        neighboursArray = currentCell.getNeighboursHexagonalRightPeriodic();
                        break;
                    case HexagonalRightNonperiodic:
                        neighboursArray = currentCell.getNeighboursHexagonalRightNonperiodic();
                        break;
                    case HexagonalRandomPeriodic:
                        neighboursArray = currentCell.getNeighboursHexagonalRandomPeriodic();
                        break;
                    case HexagonalRandomNonperiodic:
                        neighboursArray = currentCell.getNeighboursHexagonalRandomNonperiodic();
                        break;
                    case PentagonalUpperLeftPeriodic:
                        neighboursArray = currentCell.getNeighboursPentagonalUpperLeftPeriodic();
                        break;
                    case PentagonalUpperRightPeriodic:
                        neighboursArray = currentCell.getNeighboursPentagonalUpperRightPeriodic();
                        break;
                    case PentagonalBottomLeftPeriodic:
                        neighboursArray = currentCell.getNeighboursPentagonalBottomLeftPeriodic();
                        break;
                    case PentagonalBottomRightPeriodic:
                        neighboursArray = currentCell.getNeighboursPentagonalBottomRightPeriodic();
                        break;
                    case PentagonalRandomPeriodic:
                        neighboursArray = currentCell.getNeighboursPentagonalRandomPeriodic();
                        break;
                    case PentagonalUpperLeftNonperiodic:
                        neighboursArray = currentCell.getNeighboursPentagonalUpperLeftNonperiodic();
                        break;
                    case PentagonalUpperRightNonperiodic:
                        neighboursArray = currentCell.getNeighboursPentagonalUpperRightNonperiodic();
                        break;
                    case PentagonalBottomLeftNonperiodic:
                        neighboursArray = currentCell.getNeighboursPentagonalBottomLeftNonperiodic();
                        break;
                    case PentagonalBottomRightNonperiodic:
                        neighboursArray = currentCell.getNeighboursPentagonalBottomRightNonperiodic();
                        break;
                    case PentagonalRandomNonperiodic:
                        neighboursArray = currentCell.getNeighboursPentagonalRandomNonperiodic();
                        break;
                    default:
                        neighboursArray = currentCell.getNeighboursMooreNonperiodic();

                }
                int numberOfNeighbours = neighboursArray.size();
                if(!currentCell.isAlive() && numberOfNeighbours > 0){
                    int id = getNewSeedGrowthId(neighboursArray);
                    grid2.getCell(i,j).setId(id);
                    grid2.getCell(i,j).setAlive(true);
                }
                else{
                    grid2.getCell(i,j).setId(currentCell.getId());
                    grid2.getCell(i,j).setAlive(currentCell.isAlive());
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
    private int getNewSeedGrowthId(Vector<Cell> neighboursVector){
        Collections.sort(neighboursVector);
        Vector<Integer> cellIdPool = new Vector<>();
        int max = 1;
        int localCount = 1;
        for (int i = 0; i < neighboursVector.size()-1; i++) {
            if(neighboursVector.get(i).getId() == neighboursVector.get(i+1).getId())
                localCount++;
            else{
                if(localCount == max){
                    cellIdPool.add(neighboursVector.get(i).getId());
                }
                else if (localCount > max) {
                    max = localCount;
                    cellIdPool.removeAllElements();
                    cellIdPool.add(neighboursVector.get(i).getId());
                }
                localCount = 1;
            }
        }
        if(localCount == max){
            cellIdPool.add(neighboursVector.get(neighboursVector.size()-1).getId());
        }
        else if (localCount > max) {
            cellIdPool.removeAllElements();
            cellIdPool.add(neighboursVector.get(neighboursVector.size()-1).getId());
        }
        return cellIdPool.get(random.nextInt(cellIdPool.size()));
    }
}