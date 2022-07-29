package ua.issoft.yurii.kupchyn.requests;

public class GetCatalogRequest implements Request {
    private static final String NAME = "getCatalog";

    @Override
    public String getName() {
        return NAME;
    }
}
