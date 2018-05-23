package CellularAutomata.model;

public class Grid {
    private Cell[][] grid;
    private int height;
    private int width;
    Grid(int height, int width){
        this.grid = new Cell[height][width];
        this. height = height;
        this.width = width;
        for(int i = 0; i < width; i++){
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Cell(i,j, false, this,0);
            }
        }
    }

    public Cell getCell(int row, int column){
        return this.grid[row][column];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
