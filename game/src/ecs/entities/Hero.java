package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.components.skill.skills.HomingSparkSkill;
import ecs.components.skill.skills.MeeleSwordSkill;
import ecs.components.skill.skills.Skill;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
import graphic.Animation;
import starter.Game;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to set up the hero
 * with all its components and attributes .
 */
public class Hero extends Entity implements ILevelUp {

    private final int fireballCoolDown = 1;
    private int hitpoints = 20;
    private final float xSpeed = 0.2f;
    private final float ySpeed = 0.2f;
    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
    private Skill firstSkill;
    private Skill secondSkill;

    /**
     * Entity with Components
     */
    public Hero() {
        super();
        new PositionComponent(this);
        setupVelocityComponent();
        setupHealthComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        setupXPComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupFireballSkill();
        pc.setSkillSlot1(firstSkill);
        pc.setSkillSlot2(secondSkill);
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupHealthComponent() {
        HealthComponent hc = new HealthComponent(this);
        hc.setMaximalHealthpoints(this.hitpoints);
        hc.setCurrentHealthpoints(this.hitpoints);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupFireballSkill() {
        firstSkill = new Skill(new MeeleSwordSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);
        secondSkill = new Skill(new HomingSparkSkill(() -> SkillTools.getNearestEntityPosition(this)), fireballCoolDown);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> System.out.println("heroCollisionEnter"),
            (you, other, direction) -> System.out.println("heroCollisionLeave"));
    }

    private void setupXPComponent() {
        new XPComponent(this, this);
    }

    @Override
    public void onLevelUp(long nexLevel) {
        System.out.println("Hero has reached Lvl " + nexLevel + "! You gained 1 extra hitpoint.");

        HealthComponent hc = (HealthComponent) Game.getHero().orElseThrow().getComponent(HealthComponent.class).orElseThrow();
        hc.setMaximalHealthpoints(this.hitpoints += 1);
        hc.setCurrentHealthpoints(this.hitpoints);
        // TODO: Also increase maxMana by 1

        // TODO: Let the Hero unlock the new Skills
        switch ((int) nexLevel) {
            case 5 ->
                System.out.println("You unlocked the Frost Nova Skill! Press 'button' to slow nearby enemies by 30%");
            case 10 ->
                System.out.println("You unlocked the Gravity Storm Skill! Press 'button' to shoot a Storm which pulls enemies towards it.");
        }
    }
}
