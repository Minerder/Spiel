package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.idle.SleepingAI;
import ecs.components.ai.transition.SelfDefendTransition;
import graphic.Animation;

public class Rat extends Monster {

    public Rat(){
        super(1, 0.1f, 0.1f);
        setupPositionComponent();
        setupVelocityComponent();
        setupAnimationComponent();
        setupAIComponent();
        setupHitBoxComponent();
        setupHealthComponent();
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
        PatrouilleWalk pat = new PatrouilleWalk(100,5,3, PatrouilleWalk.MODE.RANDOM);
        ai.setTransitionAI(new SelfDefendTransition());
        ai.setIdleAI(new SleepingAI<PatrouilleWalk>(pat,this,ai));


    }

    @Override
    protected void setupHitBoxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> System.out.println("heroCollisionEnter"),
            (you, other, direction) -> System.out.println("heroCollisionLeave"));
        new HitboxComponent(this);
    }
}
