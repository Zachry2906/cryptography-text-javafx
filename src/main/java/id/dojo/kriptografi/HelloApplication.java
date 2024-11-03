package id.dojo.kriptografi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    double x,y = 0;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        stage.setScene(new Scene(root));
        root.setOnMousePressed(evt->{
            x = evt.getSceneX();
            y = evt.getSceneY();
        });
        root.setOnMouseDragged(evt->{
            stage.setX(evt.getScreenX() - x);
            stage.setY(evt.getScreenY() - y);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}