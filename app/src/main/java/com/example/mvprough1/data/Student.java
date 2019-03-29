package com.example.mvprough1.data;

public class Student {

    private int id;
    private String name;
    private String emai;

    public Student(int id, String name, String emai) {
        this.id = id;
        this.name = name;
        this.emai = emai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmai() {
        return emai;
    }

    public void setEmai(String emai) {
        this.emai = emai;
    }
}
