package ua.issoft.yurii.kupchyn.entities.categories;

import ua.issoft.yurii.kupchyn.entities.Category;

public class Headphones extends Category {
    private int id = 1;
    private final static String NAME = "Headphones";

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return NAME;
    }
}
