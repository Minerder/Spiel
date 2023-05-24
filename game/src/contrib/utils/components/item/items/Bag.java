package contrib.utils.components.item.items;

import contrib.utils.components.item.IOnUse;
import contrib.utils.components.item.ItemClassification;
import contrib.utils.components.item.ItemData;
import core.Entity;
import dslToGame.AnimationBuilder;

import java.util.ArrayList;
import java.util.List;

public class Bag extends ItemData implements IOnUse {
    private final List<ItemData> inventory;
    private final int maxSize;

    /**
     * Generates a new bag
     */
    Bag() {
        super(ItemClassification.Active,
            ItemKind.BAG,
            AnimationBuilder.buildAnimation("items/bag/bag_small.png"),
            AnimationBuilder.buildAnimation("items/bag/bag_small.png"),
            "Bag",
            "A leather bag that can hold 2 items of the same type.",
            ItemData::defaultCollect,
            ItemData::defaultDrop,
            null,
            null,
            null);
        super.setOnUse(this);
        inventory = new ArrayList<>();
        maxSize = 2;
    }

    /**
     * Starts the print loop for the bag.
     *
     * @param e    The entity that used the item.
     * @param item The item that was used.
     */
    @Override
    public void onUse(Entity e, ItemData item) {
        InventoryVisuals.printBag(e, this);
    }

    /**
     * Adding an Element to the Inventory does not allow adding more items than the size
     * of the inventory and only of the same ItemKind.
     *
     * @param itemData the item which should be added
     * @return true if the item was added, otherwise false
     */
    public boolean addItem(ItemData itemData) {
        if (inventory.size() >= maxSize) return false;
        if (inventory.size() == 1 && inventory.get(0).getItemKind() != itemData.getItemKind()) return false;
        return inventory.add(itemData);
    }

    /**
     * removes the given Item from the inventory
     *
     * @param itemData the item which should be removed
     * @return true if the element was removed, otherwise false
     */
    public boolean removeItem(ItemData itemData) {
        return inventory.remove(itemData);
    }

    /**
     * @return a copy of the inventory
     */
    public List<ItemData> getItems() {
        return new ArrayList<>(inventory);
    }

    /**
     * @return the number of slots already filled with items
     */
    public int filledSlots() {
        return inventory.size();
    }

    /**
     * @return the size of the inventory
     */
    public int getMaxSize() {
        return maxSize;
    }
}
