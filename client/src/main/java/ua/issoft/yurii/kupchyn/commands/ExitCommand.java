package ua.issoft.yurii.kupchyn.commands;

import ua.issoft.yurii.kupchyn.ServerClientProtocol;
import ua.issoft.yurii.kupchyn.requests.ExitRequest;
import ua.issoft.yurii.kupchyn.responses.ExitResponse;

import java.io.BufferedReader;
import java.io.IOException;


public class ExitCommand implements Command {
    private static final String NAME = "Exit";
    ServerClientProtocol serverClientProtocol;

    public ExitCommand(ServerClientProtocol serverClientProtocol) {
        this.serverClientProtocol = serverClientProtocol;
    }

    @Override
    public void execute(BufferedReader consoleIn) {
        try {
            serverClientProtocol.sendRequest(new ExitRequest());

            ExitResponse response = (ExitResponse) serverClientProtocol.receiveResponse();
            System.out.println("\n" + response.getMessage());

        } catch (IOException | ClassNotFoundException e) {
            throw new FailedExecuteCommandException("Failed execute command " + NAME + " : " + e);
        }
    }

    public String getName() {
        return NAME;
    }
}
