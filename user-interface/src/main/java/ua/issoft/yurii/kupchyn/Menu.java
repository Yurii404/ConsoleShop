package ua.issoft.yurii.kupchyn;

import ua.issoft.yurii.kupchyn.commands.Command;
import ua.issoft.yurii.kupchyn.commands.ExitCommand;

import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    private final HashMap<Integer, Command> menuItems;

    public Menu(HashMap<Integer, Command> menuItems) {
        this.menuItems = menuItems;
    }

    public void run() {
        try (Scanner input = new Scanner(System.in)) {
            boolean needToExit = false;

            do {
                printMenu();

                System.out.print("Enter menu number and press enter: ");
                int menuItemIndex = input.nextInt();

                if (menuItemIndex > menuItems.size()) {
                    System.out.println("Invalid menu item!");
                } else {
                    Command currentMenuItem = menuItems.get(menuItemIndex);

                    if (currentMenuItem instanceof ExitCommand) {
                        needToExit = true;
                    }
                    currentMenuItem.execute(input);
                }

            } while (!needToExit);
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
