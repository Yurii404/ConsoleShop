package ua.issoft.yurii.kupchyn.entities.categories;


import ua.issoft.yurii.kupchyn.entities.Category;

public class Laptops extends Category {
    private int id = 2;
    private final static String NAME = "Laptops";

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return NAME;
    }

}