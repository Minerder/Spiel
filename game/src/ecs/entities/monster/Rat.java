package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.idle.SleepingAI;
import graphic.Animation;

public class Rat extends Monster {

    public Rat(){
        super(1, 0.1f, 0.1f);
        setupPositionComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupAnimationComponent();
        setupAIComponent();
        setupHitBoxComponent();
    }

    @Override
    protected void setupPositionComponent() {
        new PositionComponent(this);
    }

    @Override
    protected void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation("character/monster/rat/run/left");
        Animation moveLeft = AnimationBuilder.buildAnimation("character/monster/rat/run/left");
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    @Override
    protected void setupHealthComponent() {
        new HealthComponent(this);
    }

    @Override
    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/rat/idle/left");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/rat/idle/left");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    protected void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(new SleepingAI<>(new PatrouilleWalk(100, 6, 3, PatrouilleWalk.MODE.RANDOM)));
    }

    @Override
    protected void setupHitBoxComponent() {
        new HitboxComponent(this);
    }
}
