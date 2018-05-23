package CellularAutomata.view;

import CellularAutomata.model.*;
import CellularAutomata.model.Cell;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.swing.*;

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
    private ComboBox seedGrowthAlgorithmComboBox;
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
    private ComboBox seedIdComboBox;

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
        speedPercentageText.setText(Integer.toString((int)speedSlider.getValue()) + "%");




        canvas.setOnMouseClicked((event) -> {
            int column = (int)(event.getX() / (canvas.getWidth() / cellularAutomaton.getGridSize()));
            int row = (int)(event.getY() / (canvas.getWidth() / cellularAutomaton.getGridSize()));
            Cell currentCell = cellularAutomaton.getGrid().getCell(row,column);
            currentCell.setAlive(!currentCell.isAlive());
            currentCell.setId(Integer.parseInt(seedIdComboBox.getValue().toString())-1);
            drawGrid(cellularAutomaton);
        });



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

        gc = canvas.getGraphicsContext2D();

        gameOfLifeMenuItem.setOnAction(e->{
            cellularAutomaton = new GameOfLife(Integer.parseInt(gridSizeTextArea.getText()));
            currentConfigurationText.setText(cellularAutomaton.toString());
            drawGrid(cellularAutomaton);
        });
        seedGrowthMenuItem.setOnAction(e->{
            cellularAutomaton = new SeedGrowth(Integer.parseInt(gridSizeTextArea.getText()),Integer.parseInt(numberOfSeedsTextField.getText()),(NeighborhoodType)seedGrowthAlgorithmComboBox.getValue());

            for(int i = 0; i < cellularAutomaton.getColorsArray().length; i++){
                seedIdComboBox.getItems().add(i+1);
            }
            seedIdComboBox.getSelectionModel().select(0);

            currentConfigurationText.setText(cellularAutomaton.toString());
            drawGrid(cellularAutomaton);
        });
        forestFireMenuItem.setOnAction(e->{
            cellularAutomaton = new ForestFire(Integer.parseInt(gridSizeTextArea.getText()),0.001, 0.0005);
            currentConfigurationText.setText(cellularAutomaton.toString());
            drawGrid(cellularAutomaton);
        });
        cellularAutomaton = new GameOfLife(Integer.parseInt(gridSizeTextArea.getText()));
        currentConfigurationText.setText(cellularAutomaton.toString());
        for(int i = 0; i < cellularAutomaton.getColorsArray().length; i++){
            seedIdComboBox.getItems().add(i+1);
        }
        seedIdComboBox.getSelectionModel().select(0);
        drawGrid(cellularAutomaton);
    }

    @FXML
    private void handleResetButtonAction(){

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


    private void drawGrid(CellularAutomaton cellularAutomaton){
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        int rows = cellularAutomaton.getGrid().getHeight();
        int columns = cellularAutomaton.getGrid(). getWidth();
        double cellWidth = width/columns;
        double cellHeight = height/rows;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell currentCell = cellularAutomaton.getGrid().getCell(i,j);
                if(currentCell.isAlive()){
                    gc.setFill(cellularAutomaton.getColor(currentCell.getId()));
                    gc.fillRect(j * cellWidth+1,i * cellHeight+1,cellWidth-1,cellHeight-1);
                }
                else if(!currentCell.isAlive()){
                    gc.setFill(Color.WHITE);
                    gc.fillRect(j * cellWidth+1,i * cellHeight+1,cellWidth-1,cellHeight-1);
                }
            }
        }
        for (int i = 0; i < rows+1; i++) {
            gc.strokeLine(0, i * cellHeight,width,i * cellHeight);
        }
        for (int i = 0; i < columns+1; i++) {
            gc.strokeLine(i * cellWidth, 0,i *cellWidth,height);
        }
    }
}