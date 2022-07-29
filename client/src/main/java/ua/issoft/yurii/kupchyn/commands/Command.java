package ua.issoft.yurii.kupchyn.commands;

import java.io.BufferedReader;

public interface Command {
    String getName();

    void execute(BufferedReader consoleIn);

}
