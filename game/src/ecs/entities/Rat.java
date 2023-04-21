package ecs.entities;

import ecs.components.skill.Skill;

public class Rat extends Monster{

    Rat(int hitPoints, Skill skill, float xSpeed, float ySpeed){
        super(hitPoints,skill,xSpeed,ySpeed);
    }
    @Override
    protected void setupPositionComponent() {

    }

    @Override
    protected void setupVelocityComponent() {

    }

    @Override
    protected void setupHealthComponent() {

    }

    @Override
    protected void setupAnimationComponent() {

    }

    @Override
    protected void setupAIComponent() {

    }

    @Override
    protected void setupHitBoxComponent() {

    }
}
