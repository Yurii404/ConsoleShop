package ua.issoft.yurii.kupchyn.requests;

public class ExitRequest implements Request {
    private static final String NAME = "exit";

    @Override
    public String getName() {
        return NAME;
    }
}
