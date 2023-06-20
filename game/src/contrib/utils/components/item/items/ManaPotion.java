package contrib.utils.components.item.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

import contrib.components.InventoryComponent;
import contrib.components.SkillComponent;
import contrib.utils.components.item.IOnUse;
import contrib.utils.components.item.ItemClassification;
import contrib.utils.components.item.ItemData;

import core.Entity;

import dslToGame.AnimationBuilder;

import java.util.logging.Logger;

public class ManaPotion extends ItemData implements IOnUse {
    private static final Logger LOGGER = Logger.getLogger(ManaPotion.class.getName());
    private int recoverAmount;

    /**
     * Creates a new health potion.
     *
     * @param recoverAmount amount the potion recovers
     */
    public ItemData get(int recoverAmount) {
        this.recoverAmount = recoverAmount;
        return new ItemData(
                ItemClassification.Active,
                ItemKind.POTION,
                AnimationBuilder.buildAnimation("items/potions/manaPotion.png"),
                AnimationBuilder.buildAnimation("items/potions/manaPotion.png"),
                "Mana Potion",
                "A strange liquid that smells salty. Restores " + recoverAmount + " mana",
                ItemData::defaultCollect,
                ItemData::defaultDrop,
                this,
                null,
                null);
    }

    @Override
    public void onUse(Entity e, ItemData item) {
        SkillComponent skillComponent =
                (SkillComponent) e.getComponent(SkillComponent.class).orElseThrow();
        InventoryComponent inventoryComponent =
                (InventoryComponent) e.getComponent(InventoryComponent.class).orElseThrow();
        // adds the recoverAmount to currentMana if the sum is less than maxMana else currentMana
        // gets set to maxMana
        skillComponent.setCurrentMana(
                Math.min(
                        skillComponent.getCurrentMana() + recoverAmount,
                        skillComponent.getMaxMana()));
        inventoryComponent.removeItem(item);
        try {
            Sound sound =
                    Gdx.audio.newSound(Gdx.files.internal("game/assets/sounds/items/POTION-2.mp3"));
            sound.play(0.5f);
            LOGGER.info("Sounds from ManaPotion played successfully");
        } catch (GdxRuntimeException g) {
            LOGGER.warning("Sound file could not be found!");
        }
    }
}
