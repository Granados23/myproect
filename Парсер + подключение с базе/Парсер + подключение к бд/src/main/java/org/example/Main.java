package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(new File("src/main/java/org/example/логин.fxml").toURI().toURL());  // D:/Новая папка/prilog/src/main/java/org/example/Графончик.fxml
        Parent root = loader.load();

        // создание сцены
        Scene scene = new Scene(root);

        // отображение сцены
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

