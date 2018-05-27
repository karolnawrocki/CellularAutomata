package CellularAutomata.view;

import CellularAutomata.model.*;
import CellularAutomata.model.Cell;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.swing.*;
import java.util.Collections;
import java.util.Vector;

public class Controller {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    @FXML
    private Button startButton;
    @FXML
    private Slider speedSlider;
    @FXML
    private ComboBox<NeighborhoodType> seedGrowthAlgorithmComboBox;
    @FXML
    private TextField gridSizeTextArea;
    @FXML
    private MenuItem gameOfLifeMenuItem;
    @FXML
    private MenuItem seedGrowthMenuItem;
    @FXML
    private MenuItem forestFireMenuItem;
    @FXML
    private TextField numberOfSeedsTextField;
    @FXML
    private Text currentConfigurationText;
    @FXML
    private Text speedPercentageText;
    @FXML
    private ComboBox<Integer> seedIdComboBox;
    @FXML
    private CheckBox showCellIdCheckBox;
    @FXML
    private TextField radiusTextField;
    @FXML
    private Pane seedGrowthPane;

    private CellularAutomaton cellularAutomaton;
    private GraphicsContext gc;
    private boolean isAppRunning = false;
    private int speedInMilliseconds = 50;
    private Timer drawingTimer = null;

    @FXML
    public void initialize() {
        anchorPane.setOnKeyPressed((event) ->{
            if(event.getCode() == KeyCode.N){
                cellularAutomaton.calculateNextStep();
                drawGrid(cellularAutomaton);
            }
        });

        numberOfSeedsTextField.textProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue.matches("\\d+")){
                loadCellularAutomatonData();
            }
        });

        gridSizeTextArea.textProperty().addListener((observable,oldValue,newValue) -> {
            if(newValue.matches("\\d+")){
                loadCellularAutomatonData();
            }
        });

        speedPercentageText.setText(Integer.toString((int)speedSlider.getValue()) + "%");

        canvas.setOnMouseClicked((event) -> {
            int column = (int)(event.getX() / (canvas.getWidth() / cellularAutomaton.getGridSize()));
            int row = (int)(event.getY() / (canvas.getWidth() / cellularAutomaton.getGridSize()));
            Cell currentCell = cellularAutomaton.getGrid().getCell(row,column);
            currentCell.setAlive(!currentCell.isAlive());
            currentCell.setId(Integer.parseInt(seedIdComboBox.getValue().toString())-1);
            drawGrid(cellularAutomaton);
        });

        showCellIdCheckBox.selectedProperty().addListener(e -> drawGrid(cellularAutomaton));

        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            speedInMilliseconds = (int)((1 / newValue.doubleValue()) * 5000.0);
            speedPercentageText.setText(newValue.intValue() + "%");
            if(isAppRunning)
                startDrawing();
        });

        for (NeighborhoodType neighborhoodType: NeighborhoodType.values()) {
            seedGrowthAlgorithmComboBox.getItems().add(neighborhoodType);
        }
        seedGrowthAlgorithmComboBox.getSelectionModel().select(0);
        seedGrowthAlgorithmComboBox.valueProperty().addListener((observable, oldValue, newValue)->{
            cellularAutomaton = new SeedGrowth(Integer.parseInt(gridSizeTextArea.getText()),Integer.parseInt(numberOfSeedsTextField.getText()), newValue);
            loadCellularAutomatonData();
        });

        gc = canvas.getGraphicsContext2D();

        gameOfLifeMenuItem.setOnAction(e->{
            cellularAutomaton = new GameOfLife();
            loadCellularAutomatonData();
        });

        seedGrowthMenuItem.setOnAction(e->{
            cellularAutomaton = new SeedGrowth();
            loadCellularAutomatonData();
        });

        forestFireMenuItem.setOnAction(e->{
            cellularAutomaton = new ForestFire();
            loadCellularAutomatonData();
        });

        cellularAutomaton = new SeedGrowth(Integer.parseInt(gridSizeTextArea.getText()),Integer.parseInt(numberOfSeedsTextField.getText()), NeighborhoodType.MoorePeriodic);
        loadCellularAutomatonData();
    }

    private void loadCellularAutomatonData(){
        if(cellularAutomaton.getClass() == GameOfLife.class){
            seedGrowthPane.setVisible(false);
            cellularAutomaton = new GameOfLife(Integer.parseInt(gridSizeTextArea.getText()), NeighborhoodType.MoorePeriodic);
        }
        else if(cellularAutomaton.getClass() == SeedGrowth.class){
            seedGrowthPane.setVisible(true);
            cellularAutomaton = new SeedGrowth(Integer.parseInt(gridSizeTextArea.getText()),Integer.parseInt(numberOfSeedsTextField.getText()), seedGrowthAlgorithmComboBox.getValue());
        }
        else if(cellularAutomaton.getClass() == ForestFire.class){
            seedGrowthPane.setVisible(false);
            cellularAutomaton = new ForestFire(Integer.parseInt(gridSizeTextArea.getText()),0.001, 0.0005);
        }
        seedIdComboBox.getItems().clear();
        for(int i = 0; i < cellularAutomaton.getColorsArray().length; i++){
            seedIdComboBox.getItems().add(i+1);
        }
        seedIdComboBox.getSelectionModel().select(0);
        currentConfigurationText.setText(cellularAutomaton.toString());
        drawGrid(cellularAutomaton);
    }

    @FXML
    private void handleRadiusSeedDistribution(){
        cellularAutomaton.getGrid().clear();
        int numberOfSeeds = Integer.parseInt(numberOfSeedsTextField.getText());
        double radius = Double.parseDouble(radiusTextField.getText());
        Vector<Cell> availableCells = new Vector<>();
        for(int i = 0; i < cellularAutomaton.getGridSize(); i++){
            for (int j = 0; j < cellularAutomaton.getGridSize(); j++) {
                availableCells.add(cellularAutomaton.getGrid().getCell(i,j));
            }
        }
        double x1,x2,y1,y2;
        Collections.shuffle(availableCells);
        Vector<Cell> cellsToRemove = new Vector<>();
        for (int i = 0; i < numberOfSeeds; i++) {
            if(availableCells.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(numberOfSeeds - i + " seeds weren't placed: no cell available");
                alert.show();
                break;
            }
            Cell currentCell = availableCells.lastElement();
            availableCells.remove(currentCell);
            x2 = currentCell.getColumn() + 0.5;
            y2 = currentCell.getRow() + 0.5;
            for(Cell cell: availableCells){
                x1 = cell.getColumn() + 0.5;
                y1 = cell.getRow() + 0.5;
                if(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) < radius){
                    cellsToRemove.add(cell);
                }
            }
            availableCells.removeAll(cellsToRemove);
            cellsToRemove.removeAllElements();
            cellularAutomaton.getGrid().getCell(currentCell.getRow(),currentCell.getColumn()).setAlive(true);
            cellularAutomaton.getGrid().getCell(currentCell.getRow(),currentCell.getColumn()).setId(i);
        }
        drawGrid(cellularAutomaton);
    }

    @FXML
    private void handleEqualSeedDistribution(){
        cellularAutomaton.getGrid().clear();
        int numberOfSeeds = Integer.parseInt(numberOfSeedsTextField.getText());
        double radius = 0.9;
        Vector<Cell> finalVectorOfSeeds = new Vector<>();
        Vector<Cell> currentVectorOfSeeds = new Vector<>();
        double x1,x2,y1,y2;
        Vector<Cell> availableCells;
        Vector<Cell> cellsToRemove = new Vector<>();
        boolean loopBroken = false;
        while(true){
            availableCells = new Vector<>();
            for(int i = 0; i < cellularAutomaton.getGridSize(); i++){
                for (int j = 0; j < cellularAutomaton.getGridSize(); j++) {
                    availableCells.add(cellularAutomaton.getGrid().getCell(i,j));
                }
            }
            int borderToRemove = (int)(radius/1.5 - 0.5);
            Vector<Cell> borderCellsToRemove = new Vector<>();
            for(Cell c: availableCells){
                if(c.getRow() < borderToRemove
                        || c.getColumn() < borderToRemove
                        || cellularAutomaton.getGridSize() - c.getRow() <= borderToRemove
                        || cellularAutomaton.getGridSize() - c.getColumn() <= borderToRemove){
                    borderCellsToRemove.add(c);
                }
            }
            availableCells.removeAll(borderCellsToRemove);
            Collections.shuffle(availableCells);
            for (int i = 0; i < numberOfSeeds; i++) {
            if(availableCells.isEmpty()){
                loopBroken = true;
                break;
            }
                Cell currentCell = availableCells.lastElement();
                availableCells.remove(currentCell);
                x2 = currentCell.getColumn() + 0.5;
                y2 = currentCell.getRow() + 0.5;
                for(Cell c: availableCells){
                    x1 = c.getColumn() + 0.5;
                    y1 = c.getRow() + 0.5;
                    if(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) < radius)
                        cellsToRemove.add(c);
                }
                availableCells.removeAll(cellsToRemove);
                cellsToRemove.removeAllElements();
                currentVectorOfSeeds.add(currentCell);
            }
            if(loopBroken)
                break;
            radius += 0.1;
            finalVectorOfSeeds.removeAllElements();
            finalVectorOfSeeds.addAll(currentVectorOfSeeds);
            currentVectorOfSeeds.removeAllElements();
        }
        for (int i = 0; i < finalVectorOfSeeds.size(); i++) {
            Cell currentCell = finalVectorOfSeeds.get(i);
            cellularAutomaton.getGrid().getCell(currentCell.getRow(),currentCell.getColumn()).setAlive(true);
            cellularAutomaton.getGrid().getCell(currentCell.getRow(),currentCell.getColumn()).setId(i);
        }
        drawGrid(cellularAutomaton);
    }

    @FXML
    private void handleClearButtonAction(){
        cellularAutomaton.getGrid().clear();
        drawGrid(cellularAutomaton);
    }

    @FXML
    private void handleStartButtonAction() {
        if(isAppRunning){
            stopDrawing();
            startButton.setText("Start");
        }
        else{
            startDrawing();
            startButton.setText("Stop");
        }
        isAppRunning = !isAppRunning;
    }

    private void startDrawing(){
        stopDrawing();
        drawingTimer = new Timer(speedInMilliseconds, e -> {
            cellularAutomaton.calculateNextStep();
            drawGrid(cellularAutomaton);
        });
        drawingTimer.start();
    }

    private void stopDrawing() {
        if (drawingTimer != null) {
            drawingTimer.stop();
            drawingTimer = null;
        }
    }

    private Color getOppositeColor(Color color){
        double r = 1.0 - color.getRed();
        double g = 1.0 - color.getGreen();
        double b = 1.0 - color.getBlue();
        return new Color(r,g,b,1.0);
    }

    private void drawGrid(CellularAutomaton cellularAutomaton){
        if(cellularAutomaton.getIterations() % 30 == 0)
            gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        int rows = cellularAutomaton.getGrid().getHeight();
        int columns = cellularAutomaton.getGrid().getWidth();
        double cellWidth = width / columns;
        double cellHeight = height / rows;
        gc.setFont(new Font(cellHeight / 1.5));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell currentCell = cellularAutomaton.getGrid().getCell(i, j);
                if(currentCell.isAlive()){
                    gc.setFill(cellularAutomaton.getColor(currentCell.getId()));
                    gc.fillRect(j * cellWidth+1,i * cellHeight+1, cellWidth, cellHeight);
                    if(showCellIdCheckBox.isSelected()){
                        gc.setFill(getOppositeColor(cellularAutomaton.getColor(currentCell.getId())));
                        gc.fillText(String.valueOf(currentCell.getId() + 1),
                                j * cellWidth + (cellWidth / 2) - (gc.getFont().getSize() / 4),
                                i * cellHeight + (cellHeight / 2) + (gc.getFont().getSize() / 3));
                    }
                }
                else if(!currentCell.isAlive()){
                    gc.setFill(Color.WHITE);
                    gc.fillRect(j * cellWidth+1,i * cellHeight+1,cellWidth-1,cellHeight-1);
                }
            }
        }
//        for (int i = 0; i < rows+1; i++) {
//            gc.strokeLine(0, i * cellHeight,width,i * cellHeight);
//        }
//        for (int i = 0; i < columns+1; i++) {
//            gc.strokeLine(i * cellWidth, 0,i *cellWidth,height);
//        }
    }
}