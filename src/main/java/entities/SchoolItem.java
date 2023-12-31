package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class SchoolItem extends Item {
    // TODO: fill these in
    private static final HashMap<String, ArrayList<String>> filterableAttributes = new HashMap<>();

    private String brand;
    private String colour;

    public SchoolItem(String id, String name, String description,
            int condition, int price, int age, boolean soldYet,
            String pickupAddress, String ownerId,
            String type, String picture, LocalDateTime creationTime,
            String brand, String colour) {
        super(id, name, description, condition, price, age, soldYet, pickupAddress,
                ownerId, type, picture, creationTime);
        this.brand = brand;
        this.colour = colour;
    }

    public SchoolItem(String name, String description,
            int condition, int price, int age, boolean soldYet,
            String pickupAddress, String ownerId,
            String type, String picture, LocalDateTime creationTime,
            String brand, String colour) {
        super(name, description, condition, price, age, soldYet, pickupAddress,
                ownerId, type, picture, creationTime);
        this.brand = brand;
        this.colour = colour;
    }

    public void setBrand(String brand) { this.brand = brand; }

    public String getBrand() { return this.brand; }

    public void setColour(String colour) { this.colour = colour; }

    public String getColour() { return this.colour; }

    public static HashMap<String, ArrayList<String>> getFilterableattributes() {
        return filterableAttributes;
    }
}
