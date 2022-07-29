package ua.issoft.yurii.kupchyn.commands;

import java.util.Scanner;

public interface Command {
    String getName();

    void execute(Scanner scanner);

}
