package org.example;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.stage.EmbeddedWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Регистрация {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField имя;

    @FXML
    private TextField пароль;

    @FXML
    private TextField почта;

    @FXML
    private Button регистрация;

    @FXML
    private TextField фамилия;

    @FXML
    void initialize() {


        регистрация.setOnAction(actionEvent -> { // Это строчка отвечает за то, что будет после того, как нажмут кнопку регистрации
            регистрация.getScene().getWindow().hide();

            UpUser();
            try {
                окно();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }


        });


    }

    private void UpUser() {  // Тут мы создаем метод, который получает данные и помещяет их в переменные
        Kokos db = new Kokos();
        String Фэмили1 = фамилия.getText();
        String Имя1 = имя.getText();
        String Почта1 = почта.getText();
        String Пароль1 = пароль.getText();

        maxim max = new maxim(Фэмили1, Имя1, Почта1, Пароль1);  // Эта строчка связывает данные с классом maxim, где у нас сетеры и геттеры
        db.singUpUser(max);
    }

    private void окно() throws MalformedURLException { // Этот метод открывает окно логина после того, как пользователь зарегестрировался.

            FXMLLoader loader = new FXMLLoader(new File("src/main/java/org/example/логин.fxml").toURI().toURL());
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
         stage.show(); // строчка отображает окно на экране и блокирует выполнение кода до закрытия этого окна.


    }



}
