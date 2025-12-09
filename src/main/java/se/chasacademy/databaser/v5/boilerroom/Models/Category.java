package se.chasacademy.databaser.v5.boilerroom.Models;

public class Category {
    private int category_Id;
    private String name;

    public Category(int category_Id, String name) {
        this.category_Id = category_Id;
        this.name = name;
    }

    public int getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(int category_Id) {
        this.category_Id = category_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
