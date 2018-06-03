package fr.eisti.sensorcontrol.model;

/**
 * Created by eisti on 10/12/17.
 *
 * Model class describing system user
 */

public class User {
    private String name;
    private String surname;
    private String position;
    private String email;
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String name, String surname, String position, String email, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.email = email;
        this.login = login;
        this.password = password;
    }
}
