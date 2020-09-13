package de.thu.theulmencyclopedia;

public class Location {
    private int id;
    private String name;
    private String description;

    public Location(int id, String name) {
        this(id, name, null);
    }

    public Location(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
