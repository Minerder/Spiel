package contrib.utils.components.item.items;

import contrib.utils.components.item.ItemClassification;
import contrib.utils.components.item.ItemData;
import core.Entity;
import core.Game;
import core.System;
import core.components.PositionComponent;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

public class InventoryVisuals {
    static Scanner scan = new Scanner(java.lang.System.in);

    /**
     * Prints the inventory of the bag in the console
     *
     * @param e user
     * @param b bag to print
     */
    public static void printBag(Entity e, Bag b) {
        out.println("Inventory slots:");
        for (int i = 0; i < b.getItems().size(); i++) {
            out.println((i) + " " + b.getItems().get(i).getItemName());
        }
        // item seleciton
        int itemIndex = itemSelection(b.getItems());
        if (itemIndex == -1) return;
        // get item
        ItemData item = b.getItems().get(itemIndex);
        // item action
        out.print("Possible actions: ");
        if (item.getItemType().equals(ItemClassification.Active)) {
            out.print("use, ");
        }
        out.println("drop, inspect, exit");
        String actionInput = scan.next();

        Pattern pattern = Pattern.compile("(?i)(use|drop|exit|inspect)");
        Matcher matcher = pattern.matcher(actionInput);
        if (!matcher.find()) {
            out.println("Wrong input");
            cancelPrint();
            return;
        }
        switch (matcher.group()) {
            case "use" -> {
                if (item.getItemType().equals(ItemClassification.Active))
                    item.getOnUse().onUse(e, item);
            }
            case "drop" -> {
                PositionComponent pc =
                        (PositionComponent) e.getComponent(PositionComponent.class).orElseThrow();
                item.getOnDrop().onDrop(e, item, pc.getPosition());
            }
            case "inspect" -> out.println("\n" + item.getDescription() + "\n");
            case "exit" -> {
                return;
            }
        }
        printBag(e, b);
    }

    private static void cancelPrint() {
        out.println("\n\n");
        Game.systems.forEach(System::run);
    }

    private static int itemSelection(List<ItemData> list) {
        out.println("\nSelect item to use or close inventory with exit:");
        String temp = scan.next();
        if (temp.equals("exit")) {
            cancelPrint();
            return -1;
        }

        int itemIndex;
        try {
            itemIndex = Integer.parseInt(temp);
            if (itemIndex < 0 || (itemIndex > 2 && itemIndex > list.size() + 1))
                throw new NumberFormatException();
        } catch (NumberFormatException v) {
            out.println("Wrong input");
            cancelPrint();
            return -1;
        }
        return itemIndex;
    }
}
