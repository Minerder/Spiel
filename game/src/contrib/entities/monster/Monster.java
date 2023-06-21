package contrib.entities.monster;

import contrib.components.CollideComponent;
import contrib.components.HealthComponent;
import contrib.components.XPComponent;
import contrib.utils.components.skill.Skill;

import core.Entity;
import core.components.DrawComponent;
import core.components.PositionComponent;
import core.components.VelocityComponent;
import core.utils.components.draw.Animation;

import dslToGame.AnimationBuilder;

public abstract class Monster extends Entity {

    protected int hitpoints;
    protected Skill skill;
    protected float xSpeed;
    protected float ySpeed;
    protected int lootXP;
    protected String pathToMoveRight;
    protected String pathToMoveLeft;
    protected String pathToIdleRight;
    protected String pathToIdleLeft;

    /**
     * Creates a new Monster with the given parameters
     *
     * @param hitpoints The amount of hitpoints the monster has
     * @param xSpeed The speed of the monster in x direction
     * @param ySpeed The speed of the monster in y direction
     * @param lootXP The amount of XP the monster drops when killed
     * @param pathToMoveRight The path to the animation for moving right
     * @param pathToMoveLeft The path to the animation for moving left
     * @param pathToIdleRight The path to the animation for idling right
     * @param pathToIdleLeft The path to the animation for idling left
     */
    public Monster(
            int hitpoints,
            float xSpeed,
            float ySpeed,
            int lootXP,
            String pathToMoveRight,
            String pathToMoveLeft,
            String pathToIdleRight,
            String pathToIdleLeft) {
        this.hitpoints = hitpoints;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.lootXP = lootXP;
        this.pathToMoveRight = pathToMoveRight;
        this.pathToMoveLeft = pathToMoveLeft;
        this.pathToIdleRight = pathToIdleRight;
        this.pathToIdleLeft = pathToIdleLeft;
    }

    protected void setupPositionComponent() {
        new PositionComponent(this);
    }

    protected void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(this.pathToMoveRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(this.pathToMoveLeft);
        new VelocityComponent(this, this.xSpeed, this.ySpeed, moveLeft, moveRight);
    }

    protected void setupHealthComponent() {
        HealthComponent hc = new HealthComponent(this);
        hc.setMaximalHealthpoints(this.hitpoints);
        hc.setCurrentHealthpoints(this.hitpoints);
    }

    protected abstract void setupAIComponent();

    protected void setupDrawComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(this.pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(this.pathToIdleLeft);
        new DrawComponent(this, idleLeft, idleRight);
    }

    protected void setupCollideComponent() {
        new CollideComponent(this);
    }

    protected void setupXPComponent() {
        new XPComponent(this, null, this.lootXP);
    }

    public float getxSpeed() {
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }
}
