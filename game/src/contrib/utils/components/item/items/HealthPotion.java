package contrib.utils.components.item.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

import contrib.components.HealthComponent;
import contrib.components.InventoryComponent;
import contrib.utils.components.item.IOnUse;
import contrib.utils.components.item.ItemClassification;
import contrib.utils.components.item.ItemData;

import core.Entity;

import dslToGame.AnimationBuilder;

import java.util.logging.Logger;

public class HealthPotion extends ItemData implements IOnUse {
    private static final Logger LOGGER = Logger.getLogger(HealthPotion.class.getName());
    private int healingAmount;

    /**
     * Creates a new health potion.
     *
     * @param healingAmount amount the potion heals
     */
    public ItemData get(int healingAmount) {
        this.healingAmount = healingAmount;
        return new ItemData(
                ItemClassification.Active,
                ItemKind.POTION,
                AnimationBuilder.buildAnimation("items/potions/healthPotion.png"),
                AnimationBuilder.buildAnimation("items/potions/healthPotion.png"),
                "Health Potion",
                "A potion that looks delicious. Restores " + healingAmount + " health.",
                ItemData::defaultCollect,
                ItemData::defaultDrop,
                this,
                null,
                null);
    }

    @Override
    public void onUse(Entity e, ItemData item) {
        HealthComponent healthComponent =
                (HealthComponent) e.getComponent(HealthComponent.class).orElseThrow();
        InventoryComponent inventoryComponent =
                (InventoryComponent) e.getComponent(InventoryComponent.class).orElseThrow();
        // adds the healingAmount to currentHealth if the sum is less than maxHealth else
        // currentHealth gets set to maxHealth
        healthComponent.setCurrentHealthpoints(
                Math.min(
                        healthComponent.getCurrentHealthpoints() + healingAmount,
                        healthComponent.getMaximalHealthpoints()));
        inventoryComponent.removeItem(item);
        try {
            Sound sound =
                    Gdx.audio.newSound(Gdx.files.internal("game/assets/sounds/items/POTION-2.mp3"));
            sound.play(0.5f);
            LOGGER.info("Sounds from HealthPotion played successfully");
        } catch (GdxRuntimeException g) {
            LOGGER.warning("Sound file could not be found!");
        }
    }
}
