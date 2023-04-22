package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.idle.SleepingAI;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.FireballSkill;
import ecs.components.skill.Skill;
import ecs.components.skill.SkillTools;
import graphic.Animation;
import starter.Game;

public class Imp extends Monster {

    private Skill firstSkill;
    private int fireballCoolDown = 1;
    public Imp(){
        super(2,0.15f, 0.15f);
        setupPositionComponent();
        setupVelocityComponent();
        setupFireballSkill();
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
        Animation moveRight = AnimationBuilder.buildAnimation("character/monster/imp/runRight");
        Animation moveLeft = AnimationBuilder.buildAnimation("character/monster/imp/runLeft");
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    @Override
    protected void setupHealthComponent() {
        new HealthComponent(this);
    }

    @Override
    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation("character/monster/imp/idleRight");
        Animation idleLeft = AnimationBuilder.buildAnimation("character/monster/imp/idleLeft");
        new AnimationComponent(this, idleLeft, idleRight);
    }

    @Override
    protected void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
       ai.setIdleAI(new StaticRadiusWalk(5,1));
        //PatrouilleWalk pat =new PatrouilleWalk(100,5,3, PatrouilleWalk.MODE.RANDOM);
        //ai.setIdleAI(new SleepingAI<PatrouilleWalk>(pat));
        ai.setTransitionAI(new RangeTransition(5));
        //new MeleeAI();
    }

    private void setupFireballSkill() {
        firstSkill =
            new Skill(
                new FireballSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);
    }

    @Override
    protected void setupHitBoxComponent() {
        /*new HitboxComponent(
            this,
            (you, other, direction) -> System.out.println("heroCollisionEnter"),
            (you, other, direction) -> System.out.println("heroCollisionLeave"));*/
        new HitboxComponent(this);
    }
}
