package ecs.entities;

import ecs.components.skill.Skill;

public abstract class Monster {
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

    protected abstract void setupPositionComponent();

    protected abstract void setupVelocityComponent();

    protected abstract void setupHealthComponent();

    protected abstract void setupAnimationComponent();

    protected abstract void setupAIComponent();

    protected abstract void setupHitBoxComponent();
}
