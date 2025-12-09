package se.chasacademy.databaser.v5.boilerroom.Models;

public class Library {
    private int libraryId;
    private String name;

    // Konstruktor för att skapa nytt Library (utan ID)
    public Library(String name) {
        this.name = name;
    }

    // Konstruktor med ID (används av RowMapper)
    public Library(int libraryId, String name) {
        this.libraryId = libraryId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }
}
