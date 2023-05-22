package org.example;

public class maxim {
    private String Имя;
    private String Фамилия;
    private String Почта;
    private String пароль;

    public maxim(String имя, String фамилия, String почта, String пароль) {
        Имя = имя;
        Фамилия = фамилия;
        Почта = почта;
        this.пароль = пароль;
    }

    public maxim() {

    }

    public String getИмя() {
        return Имя;
    }

    public void setИмя(String имя) {
        Имя = имя;
    }

    public String getФамилия() {
        return Фамилия;
    }

    public void setФамилия(String фамилия) {
        Фамилия = фамилия;
    }

    public String getПочта() {
        return Почта;
    }

    public void setПочта(String почта) {
        Почта = почта;
    }

    public String getПароль() {
        return пароль;
    }

    public void setПароль(String пароль) {
        this.пароль = пароль;
    }
}
