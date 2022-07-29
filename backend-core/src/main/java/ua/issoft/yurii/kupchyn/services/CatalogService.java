package ua.issoft.yurii.kupchyn.services;

import ua.issoft.yurii.kupchyn.entities.Catalog;

public class CatalogService {
    private final Catalog catalog;

    public CatalogService() {
        this.catalog = new Catalog();
    }

    public Catalog getCatalog() {
        return catalog;
    }

}
