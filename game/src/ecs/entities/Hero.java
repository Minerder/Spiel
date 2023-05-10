package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.skill.SkillComponent;
import ecs.components.skill.SkillTools;
import ecs.components.skill.skills.*;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
import graphic.Animation;
import starter.GameOverScreen;
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
    private final int hitpoints = 20;
    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
    private SkillComponent sc = null;

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
        new PlayableComponent(this);
        setupSkillComponent();
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupHealthComponent() {
        new HealthComponent(
            this,
            this.hitpoints,
            hero -> new GameOverScreen(),
            AnimationBuilder.buildAnimation("character/knight/hit"),
            AnimationBuilder.buildAnimation("character/knight/hit"));
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupSkillComponent() {
        sc = new SkillComponent(this, 5);
        sc.addSkill(new Skill(new MeeleSwordSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown));
        sc.addSkill(new Skill(new HomingSparkSkill(() -> SkillTools.getNearestEntityPosition(this)), fireballCoolDown, 2));
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

        sc.setMaxMana(sc.getMaxMana() + 1);

        switch ((int) nexLevel) {
            case 5 -> {
                System.out.println("You unlocked the Frost Nova Skill! Press '1' to create a Frost Nova that slows nearby enemies by 50%");
                sc.addSkill(new Skill(new FrostNovaSkill(SkillTools::getHeroPosition), 10, 2));
            }
            case 10 -> {
                System.out.println("You unlocked the Gravity Storm Skill! Press '2' to shoot a Storm which pulls enemies towards it.");
                sc.addSkill(new Skill(new GravityStormSkill(SkillTools::getCursorPositionAsPoint), 30, 5));
            }
        }
    }
}
