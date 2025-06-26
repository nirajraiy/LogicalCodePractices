package com.testHibernet.hibernetDemo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id   //this is primarry key identifier this is required
    private int id;
    private String name;
    private String febColor;

    // Getters and Setters
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

    public String getFebColor() {
        return febColor;
    }

    public void setFebColor(String febColor) {
        this.febColor = febColor;
    }
}
