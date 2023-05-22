package org.example;

public class User {
    private String Цена;
    private String Название;
    private String Объем;
    private String Изменение;

    public User(String цена, String название, String объем, String изменение) {
        Цена = цена;
        Название = название;
        Объем = объем;
        Изменение = изменение;
    }

    public String getЦена() {
        return Цена;
    }

    public String getНазвание() {
        return Название;
    }

    public String getОбъем() {
        return Объем;
    }

    public String getИзменение() {
        return Изменение;
    }

    public void setЦена(String цена) {
        Цена = Цена;
    }

    public void setНазвание(String название) {
        Название = название;
    }

    public void setОбъем(String объем) {
        Объем = объем;
    }

    public void setИзменение(String изменение) {
        Изменение = изменение;
    }
}
