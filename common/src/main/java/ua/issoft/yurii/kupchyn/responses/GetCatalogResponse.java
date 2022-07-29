package ua.issoft.yurii.kupchyn.responses;

import ua.issoft.yurii.kupchyn.entities.Catalog;

public class GetCatalogResponse implements Response {
    private Catalog catalog;

    public GetCatalogResponse(Catalog catalog) {
        this.catalog = catalog;
    }

    public Catalog getCatalog() {
        return catalog;
    }
}
