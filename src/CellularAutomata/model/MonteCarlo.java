package CellularAutomata.model;

import java.util.Collections;
import java.util.Vector;

public class MonteCarlo extends SeedGrowth{
    public MonteCarlo(SeedGrowth seedGrowth) {
        super(seedGrowth.getGridSize(), seedGrowth.getNumberOfSeeds(), seedGrowth.getNeighborhoodType());
        for(int i = 0; i < getGridSize(); i++){
            for (int j = 0; j < getGridSize(); j++) {
                this.getGrid().getCell(i,j).setAlive(seedGrowth.getGrid().getCell(i,j).isAlive());
                this.getGrid().getCell(i,j).setId(seedGrowth.getGrid().getCell(i,j).getId());
                for(int k = 0; k < seedGrowth.colorsArray.length; k++){
                    this.colorsArray[k] = seedGrowth.colorsArray[k];
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Monte Carlo\n" +
                "Grid size: " + this.gridSize + "\n" +
                "Seeds: " + this.numberOfSeeds + "\n" +
                "Neighbourhood: " + this.neighborhoodType + "\n" +
                "Monte Carlo Steps: " + this.iterations;
    }

    @Override
    public void calculateNextStep() {
        iterations++;
        Vector<Cell> cellsToHandle = new Vector<>();
        for(int i = 0; i < this.getGridSize(); i++){
            for (int j = 0; j < this.getGridSize(); j++) {
                cellsToHandle.add(this.getGrid().getCell(i,j));
            }
        }
        Vector<Cell> neighboursVector;
        Collections.shuffle(cellsToHandle);
        for(Cell cell: cellsToHandle){
            neighboursVector = cell.getNeighboursMooreNonperiodic();
            double cellEnergy = 0;
            for(Cell neighbour: neighboursVector){
                if(neighbour.getId() != cell.getId())
                    cellEnergy += 1;
            }
            int possibleNewId = random.nextInt(this.numberOfSeeds);
            double newCellEnergy = 0;
            for(Cell neighbour: neighboursVector){
                if(neighbour.getId() != possibleNewId)
                    newCellEnergy += 1;
            }
            if(newCellEnergy - cellEnergy <= 0)
                cell.setId(possibleNewId);
        }
    }
}