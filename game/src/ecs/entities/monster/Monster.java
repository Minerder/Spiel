package ecs.entities.monster;

import ecs.components.skill.Skill;
import ecs.entities.Entity;

public abstract class Monster extends Entity {
    protected int hitPoints;
    protected Skill skill;
    protected float xSpeed;
    protected float ySpeed;

    Monster(int hitPoints, Skill skill, float xSpeed, float ySpeed) {
        this.hitPoints = hitPoints;
        this.skill = skill;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    Monster(int hitPoints, float xSpeed, float ySpeed) {
        this.hitPoints = hitPoints;
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
