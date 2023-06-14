package contrib.components;

import contrib.utils.components.item.ItemData;
import contrib.utils.components.item.items.Bag;
import contrib.utils.components.item.items.ItemKind;
import core.Component;
import core.Entity;
import core.utils.logging.CustomLogLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/** Allows an Entity to carry Items */
public class InventoryComponent extends Component {

    private final List<ItemData> inventory;
    private final int maxSize;
    private ItemData equipment;
    private ItemData skillslot1;
    private ItemData skillslot2;
    private final Logger inventoryLogger = Logger.getLogger(this.getClass().getName());

    /**
     * creates a new InventoryComponent
     *
     * @param entity the Entity where this Component should be added to
     * @param maxSize the maximal size of the inventory
     */
    public InventoryComponent(Entity entity, int maxSize) {
        super(entity);
        inventory = new ArrayList<>(maxSize);
        this.maxSize = maxSize;
    }

    /**
     * Adding an Element to the Inventory does not allow adding more items than the size of the
     * Inventory. When the inventory is full tries to add the item to a bag in inventory.
     *
     * @param itemData the item which should be added
     * @return true if the item was added, otherwise false
     */
    public boolean addItem(ItemData itemData) {
        if (inventory.size() >= maxSize) {
            for (ItemData item : inventory) {
                if (item instanceof Bag
                        && !(itemData instanceof Bag)
                        && ((Bag) item).filledSlots() < ((Bag) item).getMaxSize()) {
                    inventoryLogger.log(
                            CustomLogLevel.DEBUG,
                            "Item '"
                                    + this.getClass().getSimpleName()
                                    + "' was added to the inventory of a bag from entity '"
                                    + entity.getClass().getSimpleName()
                                    + "'.");
                    return ((Bag) item).addItem(itemData);
                }
            }
            return false;
        }
        inventoryLogger.log(
                CustomLogLevel.DEBUG,
                "Item '"
                        + this.getClass().getSimpleName()
                        + "' was added to the inventory of entity '"
                        + entity.getClass().getSimpleName()
                        + "'.");
        return inventory.add(itemData);
    }

    /**
     * Removes the given item from the inventory if the item is in the inventory. Else it gets
     * removed from the bag that contains the item.
     *
     * @param itemData the item which should be removed
     * @return true if the element was removed, otherwise false
     */
    public boolean removeItem(ItemData itemData) {
        if (inventory.contains(itemData)) {
            inventoryLogger.log(
                    CustomLogLevel.DEBUG,
                    "Removing item '"
                            + this.getClass().getSimpleName()
                            + "' from inventory of entity '"
                            + entity.getClass().getSimpleName()
                            + "'.");
            return inventory.remove(itemData);
        } else {
            for (ItemData item : inventory) {
                if (item.getItemKind() == ItemKind.BAG
                        && ((Bag) item).getItems().contains(itemData)) {
                    inventoryLogger.log(
                            CustomLogLevel.DEBUG,
                            "Removing item '"
                                    + this.getClass().getSimpleName()
                                    + "' from a bag from entity '"
                                    + entity.getClass().getSimpleName()
                                    + "'.");
                    return ((Bag) item).removeItem(itemData);
                }
            }
        }
        return false;
    }

    /**
     * @return the number of slots already filled with items
     */
    public int filledSlots() {
        return inventory.size();
    }

    /**
     * @return the number of slots still empty
     */
    public int emptySlots() {
        return maxSize - inventory.size();
    }

    /**
     * @return the size of the inventory
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * @return a copy of the inventory
     */
    public List<ItemData> getItems() {
        return new ArrayList<>(inventory);
    }

    /**
     * @return item in equipment slot
     */
    public ItemData getEquipment() {
        return equipment;
    }

    /**
     * replaces equipment slot
     *
     * @param equipment new item
     */
    public void setEquipment(ItemData equipment) {
        this.equipment = equipment;
    }

    /**
     * @return item in first skill slot
     */
    public ItemData getSkillslot1() {
        return skillslot1;
    }

    /**
     * replaces old item in skill slot 1 with new item
     *
     * @param skillslot1 item that replaces
     */
    public void setSkillslot1(ItemData skillslot1) {
        this.skillslot1 = skillslot1;
    }

    /**
     * @return item in skill slot 2
     */
    public ItemData getSkillslot2() {
        return skillslot2;
    }

    /**
     * replaces old item in skill slot 2 with new item
     *
     * @param skillslot2 item that replaces
     */
    public void setSkillslot2(ItemData skillslot2) {
        this.skillslot2 = skillslot2;
    }
}
