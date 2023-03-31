package Objects;

import java.util.Date;

public class Tarea {

    private String id;
    private String name;
    private java.util.Date date;

    public Tarea(){}
    public Tarea(String id, String name, java.util.Date date){
        this.id = id;
        this.name = name;
        this.date = date;
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

    public String toString() {
        return "name: "+this.name+", date: "+this.date;
    }
}
