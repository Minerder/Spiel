package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.components.skill.skills.ISkillFunction;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

public abstract class DamageProjectileSkill implements ISkillFunction {

    private final String pathToTexturesOfProjectile;
    private final float projectileSpeed;

    private final float projectileRange;
    private final Damage projectileDamage;
    private final Point projectileHitboxSize;

    private final int bounceAmount;

    private final ITargetSelection selectionFunction;

    public DamageProjectileSkill(
        String pathToTexturesOfProjectile,
        float projectileSpeed,
        Damage projectileDamage,
        Point projectileHitboxSize,
        ITargetSelection selectionFunction,
        float projectileRange,
        int bounceAmount) {
        this.pathToTexturesOfProjectile = pathToTexturesOfProjectile;
        this.projectileDamage = projectileDamage;
        this.projectileSpeed = projectileSpeed;
        this.projectileRange = projectileRange;
        this.projectileHitboxSize = projectileHitboxSize;
        this.selectionFunction = selectionFunction;
        this.bounceAmount = bounceAmount;
    }

    public DamageProjectileSkill(
        String pathToTexturesOfProjectile,
        float projectileSpeed,
        Damage projectileDamage,
        Point projectileHitboxSize,
        ITargetSelection selectionFunction,
        float projectileRange) {
        this.pathToTexturesOfProjectile = pathToTexturesOfProjectile;
        this.projectileDamage = projectileDamage;
        this.projectileSpeed = projectileSpeed;
        this.projectileRange = projectileRange;
        this.projectileHitboxSize = projectileHitboxSize;
        this.selectionFunction = selectionFunction;
        this.bounceAmount = 0;
    }

    @Override
    public void execute(Entity entity) {
        Entity projectile = new Entity();
        PositionComponent epc =
            (PositionComponent)
                entity.getComponent(PositionComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("PositionComponent"));

        new PositionComponent(projectile, epc.getPosition());

        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfProjectile);
        new AnimationComponent(projectile, animation);

        Point aimedOn = selectionFunction.selectTargetPoint();
        Point targetPoint =
            SkillTools.calculateLastPositionInRange(
                epc.getPosition(), aimedOn, projectileRange);
        Point velocity =
                SkillTools.calculateVelocity(epc.getPosition(), targetPoint, projectileSpeed);

        new VelocityComponent(projectile, velocity.x, velocity.y, animation, animation);
        ProjectileComponent pc =
                new ProjectileComponent(projectile, epc.getPosition(), targetPoint, bounceAmount);
        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((HealthComponent) hc).receiveHit(
                                                new Damage(projectileDamage.damageAmount(), projectileDamage.damageType(), entity));
                                            SkillTools.receiveKnockback(pc.getStartPosition(), b);
                                            Game.removeEntity(projectile);
                                        });
                    }
                };

        new HitboxComponent(
            projectile, new Point(0.25f, 0.25f), projectileHitboxSize, collide, null);
    }
}
