package ua.issoft.yurii.kupchyn.commands;

import java.util.Scanner;

public class ExitCommand implements Command {
    private static final String NAME = "Exit";

    @Override
    public void execute(Scanner input) {
        System.out.println("Exit");
    }

    public String getName() {
        return NAME;
    }
}
