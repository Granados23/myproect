package org.example;
import java.lang.constant.Constable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import static org.example.coon.*;

public class Kokos extends Conf {

    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + Host + ":" + Port + "/" + Name;

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, User, Pass);
        return dbConnection;
    }


    // Ниже мы создаем метод в котором прописываем sql запрос
    public void singUpUser(maxim max){
        String insert = "INSERT INTO " + USER_TABLE + "(" + USER_Имя+ "," + USER_Фамилия+ "," + USER_Почта+ ","+ USER_Пароль + ")"
                + "VALUES(?,?,?,?)";

        //нижний код выполняет запрос, который мы прописывали выше

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, max.getФамилия());
            prSt.setString(2, max.getИмя());
            prSt.setString(3, max.getПочта());
            prSt.setString(4, max.getПароль());
            prSt.executeUpdate(); // метод executeUpdate позволяет добавить что то в базу данных

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
       public ResultSet getUser(maxim max){ // Метод ResultSet позволяет взять значения из базы данных

        ResultSet resSet = null;
        String select = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_Имя + "=? AND " + USER_Пароль + "=?"; // Тут мы отправляем запрос к базе данных, чтобы она взяла поля имя и пароль.

           try {
               PreparedStatement prSt = getDbConnection().prepareStatement(select);
               prSt.setString(1, max.getИмя());
               prSt.setString(2, max.getПароль());
              resSet = prSt.executeQuery(); // метод executeQuery позволяет получить данные из базы данных

           } catch (SQLException e) {
               throw new RuntimeException(e);
           } catch (ClassNotFoundException e) {
               throw new RuntimeException(e);
           }
           return resSet; // тут мы возвращаем переменную resSet. Таким образом мы берем пользователя, который  вводит имя и пароль, которые есть в базе данных.
         }



}
