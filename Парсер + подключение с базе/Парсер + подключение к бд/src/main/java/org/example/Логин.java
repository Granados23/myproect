package org.example;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.animations.Shake;
import org.w3c.dom.Node;

public class Логин {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Вход;

    @FXML
    private Button Регистрация;

    @FXML
    private TextField логин;

    @FXML
    private TextField пароль;

    @FXML
    private Text текстовик;

    @FXML
    void initialize() {


        Вход.setOnAction(actionEvent -> {
            String loginText = логин.getText().trim();
            String PassText = пароль.getText().trim();
            if (!loginText.equals("") && !PassText.equals("")) {
                try {
                    LoginUser(loginText, PassText);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }


        });






        Регистрация.setOnAction(actionEvent -> { // Эта строчка отвечает за действия, которые будут происходить после нажатия кнопки
            Регистрация.getScene().getWindow().hide(); // Эта строчка закрывает окно

            try {   // Тут мы создаем исключение для класса "окно", чтобы загружалась новая сцена
                окно();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });

        }

    private void окно() throws MalformedURLException {
    FXMLLoader loader = new FXMLLoader(new File("src/main/java/org/example/Регистрация.fxml").toURI().toURL());  // src/main/java/org/example/Регистрация.fxml

    try {
        loader.load();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    Parent root = loader.getRoot();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.showAndWait(); // строчка отображает окно на экране и блокирует выполнение кода до закрытия этого окна.

}

    private void LoginUser(String loginText, String passText) throws SQLException, MalformedURLException {
        Kokos db = new Kokos();
        maxim max = new maxim();
        max.setИмя(loginText);
        max.setПароль(passText);
         ResultSet result = db.getUser(max);

         int counter = 0;
         while (result.next()){
             counter++;
         }
         if (counter >=1){
             Вход.getScene().getWindow().hide();
             переход();

         }else
             текстовик.setText("Вы ввели неверный логин или пароль");



    }


    private void переход() throws MalformedURLException {
        FXMLLoader loader = new FXMLLoader(new File("src/main/java/org/example/Графончик.fxml").toURI().toURL());
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait(); // строчка отображает окно на экране и блокирует выполнение кода до закрытия этого окна.
    }


    public void anim(){
        TranslateTransition trans = new TranslateTransition();  // Тут мы подгружаем метод, который отвечает за анимацию
        trans.setNode(текстовик); // устанавливаем объект, который будет анимироваться
        trans.setDuration(Duration.millis(10000)); // Устанавливаем замедление на анимацию
        trans.setCycleCount(TranslateTransition.INDEFINITE);  //
        trans.setByX(250); // Устанавливаем перемещение по Х
        trans.setAutoReverse(true); // Объект будет возвращаться по изначальному маршруту
        trans.play(); // Активируем анимацию
    }

}
