package contrib.utils.components.item.items;

import static java.lang.System.out;

import contrib.components.InventoryComponent;
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

public class InventoryVisuals {
    static Scanner scan = new Scanner(java.lang.System.in);

    /**
     * Starts the inventory visualization using the console and user input
     *
     * @param e From entity prints InventoryComponent
     */
    public static void print(Entity e) {
        InventoryComponent invc =
                (InventoryComponent) e.getComponent(InventoryComponent.class).orElseThrow();
        Game.systems.forEach(System::stop);

        printInventory(invc);

        // getItem
        int itemIndex = itemSelection(invc.getItems());
        if (itemIndex == -1) return;
        ItemData item;
        boolean itemInSkillSlot = false;
        if (itemIndex == 0) {
            item = invc.getSkillslot1();
            itemInSkillSlot = true;
        } else if (itemIndex == 1) {
            item = invc.getSkillslot2();
            itemInSkillSlot = true;
        } else {
            item = invc.getItems().get(itemIndex - 2);
        }

        if (item == null) {
            cancelPrint();
            return;
        }

        // item actions
        out.print("Actions possible: ");
        if (item.getItemType().equals(ItemClassification.Active) && !itemInSkillSlot) {
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
                if (item.getItemType().equals(ItemClassification.Active) && !itemInSkillSlot)
                    item.getOnUse().onUse(e, item);
            }
            case "drop" -> {
                PositionComponent pc =
                        (PositionComponent) e.getComponent(PositionComponent.class).orElseThrow();
                item.getOnDrop().onDrop(e, item, pc.getPosition());
            }
            case "inspect" -> out.println("\n" + item.getDescription() + "\n");
            case "exit" -> {
                cancelPrint();
                return;
            }
        }
        InventoryVisuals.print(e);
    }

    /**
     * Prints the inventory in the console
     *
     * @param invc items
     */
    private static void printInventory(InventoryComponent invc) {
        // equipment slot
        out.println("Equipment slot:");
        if (invc.getEquipment() != null)
            out.println("  " + invc.getEquipment().getItemName() + "\n");
        else out.println("  empty\n");
        // skill slots
        out.println("  Skill slots:");
        if (invc.getSkillslot1() != null) out.println("0 " + invc.getSkillslot1().getItemName());
        else out.println("0 empty");
        if (invc.getSkillslot2() != null)
            out.println("1 " + invc.getSkillslot2().getItemName() + "\n");
        else out.println("1 empty\n");
        // inventory
        out.println("Inventory slots:");
        for (int i = 0; i < invc.getItems().size(); i++) {
            out.println((i + 2) + " " + invc.getItems().get(i).getItemName());
        }
    }

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
