package CellularAutomata;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/layout.fxml"));
        primaryStage.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });
        primaryStage.setTitle("CA");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1000, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
