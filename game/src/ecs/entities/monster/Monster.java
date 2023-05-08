package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.skill.skills.Skill;
import ecs.components.xp.XPComponent;
import ecs.entities.Entity;
import graphic.Animation;

public abstract class Monster extends Entity {
    protected int hitPoints;
    protected Skill skill;
    protected float xSpeed;
    protected float ySpeed;
    protected int lootXP;
    protected String pathToMoveRight;
    protected String pathToMoveLeft;
    protected String pathToIdleRight;
    protected String pathToIdleLeft;

    public Monster(
        int hitPoints,
        float xSpeed,
        float ySpeed,
        int lootXP,
        String pathToMoveRight,
        String pathToMoveLeft,
        String pathToIdleRight,
        String pathToIdleLeft) {
        this.hitPoints = hitPoints;
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
        hc.setMaximalHealthpoints(this.hitPoints);
        hc.setCurrentHealthpoints(this.hitPoints);
    }

    protected abstract void setupAIComponent();

    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(this.pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(this.pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    protected void setupHitBoxComponent() {
        new HitboxComponent(this);
    }

    protected void setupXPComponent() {
        new XPComponent(this, null, this.lootXP);
    }

    public float getxSpeed(){
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }
}
