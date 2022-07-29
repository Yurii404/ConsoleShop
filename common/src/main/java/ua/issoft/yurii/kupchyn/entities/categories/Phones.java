package ua.issoft.yurii.kupchyn.entities.categories;

import ua.issoft.yurii.kupchyn.entities.Category;

public class Phones extends Category {
    private int id = 3;
    private final static String NAME = "Phones";

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return NAME;
    }

}
