package contrib.entities;

import contrib.components.*;
import contrib.utils.components.interaction.DropItemsInteraction;
import contrib.utils.components.item.ItemData;
import contrib.utils.components.item.ItemDataGenerator;
import contrib.utils.components.item.items.ItemFactory;

import core.Entity;
import core.Game;
import core.components.DrawComponent;
import core.components.PlayerComponent;
import core.components.PositionComponent;
import core.components.VelocityComponent;
import core.level.utils.LevelElement;
import core.utils.Point;
import core.utils.components.draw.Animation;

import dslToGame.AnimationBuilder;

import starter.GameOverScreen;

import java.util.List;

/**
 * A utility class for building entities in the game world. The {@link EntityFactory} class provides
 * static methods to construct various types of entities with different components.
 */
public class EntityFactory {

    /**
     * Create a new Entity that can be used as a playable character. It will have a {@link
     * PlayerComponent}. {@link PositionComponent}, {@link VelocityComponent} {@link DrawComponent},
     * {@link SkillComponent}, {@link CollideComponent}.
     *
     * @return Created Entity
     */
    public static Entity getHero() {
        final float xSpeed = 0.2f;
        final float ySpeed = 0.2f;
        final String pathToIdleLeft = "knight/idleLeft";
        final String pathToIdleRight = "knight/idleRight";
        final String pathToRunLeft = "knight/runLeft";
        final String pathToRunRight = "knight/runRight";

        Entity hero = new Entity();
        new PositionComponent(hero);
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(hero, xSpeed, ySpeed, moveLeft, moveRight);
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new DrawComponent(hero, idleLeft, idleRight);
        new CollideComponent(
                hero,
                (you, other, direction) -> System.out.println("heroCollisionEnter"),
                (you, other, direction) -> System.out.println("heroCollisionLeave"));
        new HealthComponent(
                hero,
                20,
                hero2 -> new GameOverScreen(),
                AnimationBuilder.buildAnimation("character/knight/hit"),
                AnimationBuilder.buildAnimation("character/knight/hit"));
        new PlayerComponent(hero);
        SkillComponent sc = new SkillComponent(hero, 5);
        new XPComponent(hero, new HeroLevelUp());
        InventoryComponent invc = new InventoryComponent(hero, 20);
        ItemData sword = ItemFactory.getSpecificSword(0);
        invc.setEquipment(sword);
        sc.addSkill(sword.getSkill());
        return hero;
    }

    /**
     * Create a new Entity that can be used as a chest.
     *
     * <p>It will have a {@link InteractionComponent}. {@link PositionComponent}, {@link
     * DrawComponent}, {@link CollideComponent} and {@link InventoryComponent}. It will use the
     * {@link DropItemsInteraction} on interaction.
     *
     * <p>{@link ItemDataGenerator} is used to generate random items
     *
     * @return Created Entity
     */
    public static Entity getChest() {
        List<ItemData> itemData = ItemDataGenerator.generateRandomItems(3);
        return getChest(
                itemData,
                Game.currentLevel.getRandomTile(LevelElement.FLOOR).getCoordinate().toPoint());
    }

    /**
     * Create a new Entity that can be used as a chest.
     *
     * <p>It will have a {@link InteractionComponent}. {@link PositionComponent}, {@link
     * DrawComponent}, {@link CollideComponent} and {@link InventoryComponent}. It will use the
     * {@link DropItemsInteraction} on interaction.
     *
     * @param itemData The {@link ItemData} for the Items inside the chest.
     * @param position The position of the chest.
     * @return Created Entity
     */
    public static Entity getChest(List<ItemData> itemData, Point position) {
        final float defaultInteractionRadius = 1f;
        final List<String> DEFAULT_CLOSED_ANIMATION_FRAMES =
                List.of("objects/treasurechest/chest_full_open_anim_f0.png");
        final List<String> DEFAULT_OPENING_ANIMATION_FRAMES =
                List.of(
                        "objects/treasurechest/chest_full_open_anim_f0.png",
                        "objects/treasurechest/chest_full_open_anim_f1.png",
                        "objects/treasurechest/chest_full_open_anim_f2.png",
                        "objects/treasurechest/chest_empty_open_anim_f2.png");

        Entity chest = new Entity();
        new PositionComponent(chest, position);
        InventoryComponent ic = new InventoryComponent(chest, itemData.size());
        itemData.forEach(ic::addItem);
        new InteractionComponent(
                chest, defaultInteractionRadius, false, new DropItemsInteraction());
        new DrawComponent(
                chest,
                new Animation(DEFAULT_CLOSED_ANIMATION_FRAMES, 100, false),
                new Animation(DEFAULT_OPENING_ANIMATION_FRAMES, 100, false));
        return chest;
    }
}
