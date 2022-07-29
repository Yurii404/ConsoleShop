package ua.issoft.yurii.kupchyn;

import ua.issoft.yurii.kupchyn.commands.Command;
import ua.issoft.yurii.kupchyn.commands.ExitCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Menu {
    private final HashMap<Integer, Command> menuItems;

    public Menu(HashMap<Integer, Command> menuItems) {
        this.menuItems = menuItems;
    }

    public void run() {
        try (
                BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            boolean needToExit = false;

            do {
                printMenu();

                System.out.print("Enter menu number and press enter: ");
                int menuItemIndex = Integer.parseInt(consoleIn.readLine());

                if (menuItemIndex > menuItems.size()) {
                    System.out.println("Invalid menu item!");
                } else {
                    Command currentMenuItem = menuItems.get(menuItemIndex);

                    if (currentMenuItem instanceof ExitCommand) {
                        needToExit = true;
                    }
                    currentMenuItem.execute(consoleIn);
                }

            } while (!needToExit);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printMenu() {
        System.out.println("--------------------------------");
        System.out.println("Choose menu item: ");
        menuItems.forEach((k, v) -> {
            System.out.printf("%d. %s\n", k, v.getName());
        });
        System.out.println();
    }
}