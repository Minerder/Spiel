package contrib.utils.components.item;

import contrib.utils.components.item.items.ItemFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemDataGenerator {

    /**
     * Generates new random Items
     *
     * @param amount amount of items in list
     * @return list of random items
     */
    public static List<ItemData> generateRandomItems(int amount) {
        List<ItemData> items = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            switch (new Random().nextInt(0, 6)) {
                case 0 -> items.add(ItemFactory.getBag());

                case 1 -> items.add(ItemFactory.getHealthPotion(5));

                case 2 -> items.add(ItemFactory.getManaPotion(5));

                case 3 -> items.add(ItemFactory.getBow());

                case 4 -> items.add(ItemFactory.getSword());

                case 5 -> items.add(ItemFactory.getBook());
            }
        }
        return items;
    }
}
