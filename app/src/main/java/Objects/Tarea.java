package Objects;

import java.lang.ref.Reference;

public class Tarea {

    private String id;
    private String name;
    private java.util.Date date;
    private Reference<User> user;

    public Tarea(){}
    public Tarea(String id, String name, java.util.Date date, Reference<User> userEmail){
        this.id = id;
        this.name = name;
        this.date = date;
        this.user = userEmail;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public java.util.Date getDate() {
        return date;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDate(java.util.Date date) { this.date = date;}
    public Reference<User> getUser() {
        return user;
    }
    public void setUser(Reference<User> user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "Tarea{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", userEmail='" + user + '\'' +
                '}';
    }
}
