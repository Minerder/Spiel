package contrib.utils.components.item.items;

import contrib.utils.components.item.ItemData;

public class ItemFactory {

    /**
     * Generates a new random Sword.
     *
     * @return A sword
     */
    public static ItemData getSword() {
        return new Sword().get();
    }

    /**
     * Generates a specific sword.
     *
     * @param swordType 0 = bronze, 1 = silver, 2 = ruby
     * @return The sword
     */
    public static ItemData getSpecificSword(int swordType) {
        return new Sword().getSpecific(swordType);
    }

    /**
     * Generates a new random bow.
     *
     * @return A bow
     */
    public static ItemData getBow() {
        return new Bow().get();
    }

    /**
     * Generates a new random book respecting the heros level.
     *
     * @return A book
     */
    public static ItemData getBook() {
        return new Book().get();
    }

    /**
     * Generates a new health potion.
     *
     * @param healAmount The amount the potion heals when used
     * @return A health potion
     */
    public static ItemData getHealthPotion(int healAmount) {
        return new HealthPotion().get(healAmount);
    }

    /**
     * Generates a new mana potion.
     *
     * @param recoverAmount The amount the potion recovers when used
     * @return A mana potion
     */
    public static ItemData getManaPotion(int recoverAmount) {
        return new ManaPotion().get(recoverAmount);
    }

    /**
     * Generates a new bag.
     *
     * @return A bag
     */
    public static Bag getBag() {
        return new Bag();
    }
}
