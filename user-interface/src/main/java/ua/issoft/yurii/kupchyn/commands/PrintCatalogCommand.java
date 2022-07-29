package ua.issoft.yurii.kupchyn.commands;

import ua.issoft.yurii.kupchyn.commands.printers.CatalogPrinter;
import ua.issoft.yurii.kupchyn.services.CatalogService;

import java.util.Scanner;

public class PrintCatalogCommand implements Command {
    private static final String NAME = "Print catalog";
    private final CatalogService catalogService;
    private final CatalogPrinter catalogPrinter;

    public PrintCatalogCommand(CatalogService catalogService, CatalogPrinter catalogPrinter) {
        this.catalogService = catalogService;
        this.catalogPrinter = catalogPrinter;
    }

    @Override
    public void execute(Scanner input) {

        System.out.println();
        System.out.println(catalogService.getCatalog().getName() + ": ");
        catalogPrinter.printContent(catalogService.getCatalog());
        System.out.println();

    }

    public String getName() {
        return NAME;
    }
}
