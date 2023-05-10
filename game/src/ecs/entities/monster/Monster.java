package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.skill.skills.Skill;
import ecs.components.xp.XPComponent;
import ecs.entities.Entity;
import graphic.Animation;

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

    public Monster(int hitpoints, float xSpeed, float ySpeed, int lootXP, String pathToMoveRight, String pathToMoveLeft, String pathToIdleRight, String pathToIdleLeft) {
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

    public float getxSpeed() {
        return xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }
}
