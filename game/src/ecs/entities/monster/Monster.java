package ecs.entities.monster;

import ecs.components.skill.skills.Skill;
import ecs.entities.Entity;

public abstract class Monster extends Entity {
    protected int hitpoints;
    protected Skill skill;
    protected float xSpeed;
    protected float ySpeed;

    public Monster(int hitpoints, float xSpeed, float ySpeed) {
        this.hitpoints = hitpoints;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    protected abstract void setupPositionComponent();

    protected abstract void setupVelocityComponent();

    protected abstract void setupHealthComponent();

    protected abstract void setupAnimationComponent();

    protected abstract void setupAIComponent();

    protected abstract void setupHitBoxComponent();
}
