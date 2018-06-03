package fr.eisti.sensorcontrol.model;

/**
 * Created by eisti on 19/12/17.
 */

public class Session {
    private String body;
    private String user;
    private String insertDate;

    public Session(String body, String user, String insertDate) {
        this.body = body;
        this.user = user;
        this.insertDate = insertDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }
}
