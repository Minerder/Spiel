package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.idle.GhostWalk;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.ai.transition.SelfDefendTransition;
import graphic.Animation;


public class Ghost extends Monster {

    public Ghost(){
        super(2,0.25f, 0.25f);
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
        Animation moveRight = AnimationBuilder.buildAnimation("character/monster/ghost/idleLeft");
        Animation moveLeft = AnimationBuilder.buildAnimation("character/monster/ghost/idleLeft");
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    @Override
    protected void setupHealthComponent() {
        new HealthComponent(this);
    }

    @Override
    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/ghost/idleLeft");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/ghost/idleLeft");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    protected void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setIdleAI(new GhostWalk(30,5,3,GhostWalk.MODE.RANDOM));
        //PatrouilleWalk pat =new PatrouilleWalk(100,5,3, PatrouilleWalk.MODE.RANDOM);
        //ai.setIdleAI(new SleepingAI<PatrouilleWalk>(pat));
        ai.setTransitionAI(new RangeTransition(0.1f));
        //new MeleeAI();
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


