package core.systems;

import contrib.components.HealthComponent;
import contrib.components.ProjectileComponent;

import core.Entity;
import core.Game;
import core.System;
import core.components.DrawComponent;
import core.components.PositionComponent;
import core.components.VelocityComponent;
import core.utils.Point;
import core.utils.components.MissingComponentException;
import core.utils.components.draw.Animation;

import java.util.concurrent.atomic.AtomicBoolean;

/** MovementSystem is a system that updates the position of entities */
public class VelocitySystem extends System {

    private record VSData(Entity e, VelocityComponent vc, PositionComponent pc) {}

    /** Updates the position of all entities based on their velocity */
    public void update() {
        Game.getEntities().stream()
                .flatMap(e -> e.getComponent(VelocityComponent.class).stream())
                .map(vc -> buildDataObject((VelocityComponent) vc))
                .forEach(this::updatePosition);
    }

    private void updatePosition(VSData vsd) {
        float newX = vsd.pc.getPosition().x + vsd.vc.getCurrentXVelocity();
        float newY = vsd.pc.getPosition().y + vsd.vc.getCurrentYVelocity();
        Point newPosition = new Point(newX, newY);
        ProjectileComponent projectileComponent =
                (ProjectileComponent) vsd.e.getComponent(ProjectileComponent.class).orElse(null);
        if (Game.currentLevel.getTileAt(newPosition.toCoordinate()).isAccessible()) {
            vsd.pc.setPosition(newPosition);
            movementAnimation(vsd.e);
        } else if (Game.currentLevel
                        .getTileAt(new Point(newX, vsd.pc.getPosition().y).toCoordinate())
                        .isAccessible()
                && projectileComponent == null) {
            vsd.pc.setPosition(new Point(newX, vsd.pc.getPosition().y));
            movementAnimation(vsd.e);
        } else if (Game.currentLevel
                        .getTileAt(new Point(vsd.pc.getPosition().x, newY).toCoordinate())
                        .isAccessible()
                && projectileComponent == null) {
            vsd.pc.setPosition(new Point(vsd.pc.getPosition().x, newY));
            movementAnimation(vsd.e);
        }
        // remove projectiles that hit the wall or other non-accessible
        // tiles unless the ProjectileComponent.bounceAmount is > than 0
        else if (projectileComponent != null) {
            if (projectileComponent.getBounceAmount() > 0) {
                bounceProjectile(vsd, projectileComponent, newX, newY);
            } else {
                Game.removeEntity(vsd.e);
            }
        }
        vsd.vc.setCurrentYVelocity(0);
        vsd.vc.setCurrentXVelocity(0);
    }

    private VSData buildDataObject(VelocityComponent vc) {
        Entity e = vc.getEntity();

        PositionComponent pc =
                (PositionComponent)
                        e.getComponent(PositionComponent.class)
                                .orElseThrow(VelocitySystem::missingPC);

        return new VSData(e, vc, pc);
    }

    private void movementAnimation(Entity entity) {

        AtomicBoolean isDead = new AtomicBoolean(false);
        entity.getComponent(HealthComponent.class)
                .ifPresent(
                        component -> {
                            HealthComponent healthComponent = (HealthComponent) component;
                            isDead.set(healthComponent.isDead());
                        });

        if (isDead.get()) {
            return;
        }

        DrawComponent ac =
                (DrawComponent)
                        entity.getComponent(DrawComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("AnimationComponent"));
        Animation newCurrentAnimation;
        VelocityComponent vc =
                (VelocityComponent)
                        entity.getComponent(VelocityComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("VelocityComponent"));
        float x = vc.getCurrentXVelocity();
        if (x > 0) newCurrentAnimation = vc.getMoveRightAnimation();
        else if (x < 0) newCurrentAnimation = vc.getMoveLeftAnimation();
        // idle
        else {
            if (ac.getCurrentAnimation() == ac.getIdleLeft()
                    || ac.getCurrentAnimation() == vc.getMoveLeftAnimation())
                newCurrentAnimation = ac.getIdleLeft();
            else newCurrentAnimation = ac.getIdleRight();
        }
        ac.setCurrentAnimation(newCurrentAnimation);
    }

    private static MissingComponentException missingPC() {
        return new MissingComponentException("PositionComponent");
    }

    private void bounceProjectile(
            VSData vsd, ProjectileComponent projectileComponent, float newX, float newY) {
        VelocityComponent v =
                (VelocityComponent) vsd.e.getComponent(VelocityComponent.class).orElseThrow();

        if (!Game.currentLevel
                .getTileAt(new Point(newX, vsd.pc.getPosition().y).toCoordinate())
                .isAccessible()) {
            v.setXVelocity(v.getXVelocity() * -1);
        } else if (!Game.currentLevel
                .getTileAt(new Point(vsd.pc.getPosition().x, newY).toCoordinate())
                .isAccessible()) {
            v.setYVelocity(v.getYVelocity() * -1);
        } else if (!Game.currentLevel
                .getTileAt(new Point(newX, newY).toCoordinate())
                .isAccessible()) {
            v.setYVelocity(v.getYVelocity() * -1);
            v.setXVelocity(v.getXVelocity() * -1);
        }
        projectileComponent.setBounceAmount(projectileComponent.getBounceAmount() - 1);
    }
}
