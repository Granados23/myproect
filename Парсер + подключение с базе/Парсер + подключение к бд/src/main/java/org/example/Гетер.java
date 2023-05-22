package org.example;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import javafx.scene.control.TextField;

public class Гетер implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Text eur;

    @FXML
    private Text usd;

    @FXML
    private TableView<User> Колонка;

    @FXML
    private TableColumn<User, String> Название1;

    @FXML
    private TableColumn<User, String> Объем;

    @FXML
    private TextField Поиск;

    @FXML
    private TableColumn<User, String> Цена;

    @FXML
    private TableColumn<User, String> изменение;

    // Ниже мы создаем список, где будут храниться наши котировки по акциям
    ObservableList<User> listx = FXCollections.observableArrayList();

    //  public void initialize(URL url, ResourceBundle resources)





    @Override
    public void initialize(URL url, ResourceBundle resources){
        // Строчка ниже запускает работу класса time
        tabl();
        time();
    }


    private void tabl(){
        Task<Void> task = new Task<Void>() {
            protected Void call() throws Exception {
                while (true) {
                    tablice();
                    Thread.sleep(1000000000);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

// Код ниже отвечает за регулярные обновления данных из метода updateCurrencyRates.

    private void time() {
        Task<Void> task = new Task<Void>() {
            protected Void call() throws Exception {
                while (true) {
                    updateCurrencyRates();
                  //  Thread.sleep(10);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void updateCurrencyRates() throws IOException, InterruptedException {
        Document ss = Jsoup.connect("https://www.banki.ru/products/currency/cb/").get();
        Elements many = ss.select("tr[data-test$=currency-table-row]");

        String бакс = "";
        String евро = "";
        бакс = "  " + many.text().substring(17,22);
        евро = "  " + many.text().substring(44,49);

        usd.setText(бакс);  // метод setText позволяет выводить команды в программе
        usd.setFont(new Font(15));    // метод setFront позволяет менять размер текста.

        eur.setText(евро);
        eur.setFont(new Font(15));

    }


    public void Title() throws IOException {


    }

 private void tablice() throws IOException {


     Document doc = Jsoup.connect("https://smart-lab.ru/q/shares/").get();
     Elements ss = doc.select("table[class$=simple-little-table trades-table]");
     Elements ценапарс =  ss.select("tr"); // в этой строчке мы берем каждый 6-ой элемент с тегом "td"
     Elements sk =  ценапарс.select("td:nth-of-type(8n)");
     Elements sl =  ценапарс.select("td:nth-of-type(10n)");
       Elements mm =  ценапарс.select("td:nth-of-type(11n)");



     String названия = "";
     String цена = "";
     String Ссылки = "";
     String изменен = "";


     for ( int i = 1; i< ценапарс.size(); i++ ){

         названия += ценапарс.get(i).text().replaceAll("[^А-Яа-яA-Za-z]","") + "\n";

         for (int m = 0; m<sk.size(); m+=2) {
             цена += sk.get(m).text() +"\n";
         }
         for (int n = 0; n< sl.size(); n+=2) {
             Ссылки += sl.get(n).text() + "\n";
         }
         for ( int b = 0; b < mm.size(); b++){
             изменен += mm.get(b).text() + "\n";
         }

     }
     // Тут мы создаем массивы, которые принимают значения из циклов выше.

     String[] названияfull = названия.split("\n");
     String[] ценаfull = цена.split("\n");
     String[] ссылкиfull = Ссылки.split("\n");
     String[] изменениеfull = изменен.split("\n");



     // Ниже мы создаем массив и выделяем память, чтобы данные из массивов сверху заполняли колонки

     for (int z = 0; z< названияfull.length; z++) {                                             // названияfull.length
         listx.add(new User(ценаfull[z], названияfull[z], ссылкиfull[z], изменениеfull[z]));                                                           //  User user = new User(ценаfull[z], названияfull[z], ссылкиfull[z], изменениеfull[z]);

     }

     // Тут мы связываем колонки

     Цена.setCellValueFactory(new PropertyValueFactory<User, String>("Цена"));
     Название1.setCellValueFactory(new PropertyValueFactory<User, String>("Название"));
     изменение.setCellValueFactory(new PropertyValueFactory<User, String>("Изменение"));
     Объем.setCellValueFactory(new PropertyValueFactory<User, String>("Объем"));

     Колонка.setItems(listx);


     // Ниже мы создаем поиск по нашим колонкам

     FilteredList<User> filteredData = new FilteredList<>(listx, p -> true);
     Поиск.textProperty().addListener((observable, oldValue, newValue) -> {
         filteredData.setPredicate(User -> {
             if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                 return true;
             }
             String lowerCaseFilter = newValue.toLowerCase();
             if (User.getНазвание().toString().indexOf(lowerCaseFilter) > -1) {
                 return true;
             }else if (User.getЦена().toString().indexOf(lowerCaseFilter) > -1) {
                 return true;
             } else if (User.getОбъем().toString().indexOf(lowerCaseFilter) > -1){
                 return true;
             }else if (User.getИзменение().toString().indexOf(lowerCaseFilter) > -1){
                 return true;
             }else
                 return false;
         });
     });
     SortedList<User> sortedData = new SortedList<>(filteredData);

     sortedData.comparatorProperty().bind(Колонка.comparatorProperty());

     Колонка.setItems(sortedData);


 }



}


