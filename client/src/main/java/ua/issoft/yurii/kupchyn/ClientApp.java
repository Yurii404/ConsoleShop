package ua.issoft.yurii.kupchyn;

import ua.issoft.yurii.kupchyn.commands.Command;
import ua.issoft.yurii.kupchyn.commands.ExitCommand;
import ua.issoft.yurii.kupchyn.commands.OrderProductCommand;
import ua.issoft.yurii.kupchyn.commands.PrintCatalogCommand;
import ua.issoft.yurii.kupchyn.commands.PrintOrdersCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientApp {
    private Menu menu;


    public static void main(String[] args) {
        new ClientApp().run();
    }

    private void configureMenu(ServerClientProtocol serverClientProtocol) {
        HashMap<Integer, Command> menuItems = new HashMap<>();

        menuItems.put(1, new PrintCatalogCommand(serverClientProtocol));
        menuItems.put(2, new OrderProductCommand(serverClientProtocol));
        menuItems.put(3, new PrintOrdersCommand(serverClientProtocol));
        menuItems.put(4, new ExitCommand(serverClientProtocol));

        this.menu = new Menu(menuItems);
    }


    private void run() {
        try (
                Socket clientSocket = new Socket("localhost", 4004);
                ServerClientProtocol serverClientProtocol = new ServerClientProtocol(clientSocket);
        ) {

            configureMenu(serverClientProtocol);
            menu.run();

        } catch (Exception e) {
            throw new ClientAppException("Failed run client " + e);
        }
    }

}
