package ecs.systems;

import ecs.components.AnimationComponent;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.ProjectileComponent;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

/**
 * MovementSystem is a system that updates the position of entities
 */
public class VelocitySystem extends ECS_System {

    private record VSData(Entity e, VelocityComponent vc, PositionComponent pc) {
    }

    /**
     * Updates the position of all entities based on their velocity
     */
    public void update() {
        Game.getEntities().stream().flatMap(e -> e.getComponent(VelocityComponent.class).stream()).map(vc -> buildDataObject((VelocityComponent) vc)).forEach(this::updatePosition);
    }

    private void updatePosition(VSData vsd) {
        float newX = vsd.pc.getPosition().x + vsd.vc.getCurrentXVelocity();
        float newY = vsd.pc.getPosition().y + vsd.vc.getCurrentYVelocity();
        Point newPosition = new Point(newX, newY);
        ProjectileComponent projectileComponent = (ProjectileComponent) vsd.e.getComponent(ProjectileComponent.class).orElse(null);
        if (Game.currentLevel.getTileAt(newPosition.toCoordinate()).isAccessible()) {
            vsd.pc.setPosition(newPosition);
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

        PositionComponent pc = (PositionComponent) e.getComponent(PositionComponent.class).orElseThrow(VelocitySystem::missingPC);

        return new VSData(e, vc, pc);
    }

    private void movementAnimation(Entity entity) {
        AnimationComponent ac = (AnimationComponent) entity.getComponent(AnimationComponent.class).orElseThrow(() -> new MissingComponentException("AnimationComponent"));
        Animation newCurrentAnimation;
        VelocityComponent vc = (VelocityComponent) entity.getComponent(VelocityComponent.class).orElseThrow(() -> new MissingComponentException("VelocityComponent"));
        float x = vc.getCurrentXVelocity();
        if (x > 0) newCurrentAnimation = vc.getMoveRightAnimation();
        else if (x < 0) newCurrentAnimation = vc.getMoveLeftAnimation();
            // idle
        else {
            if (ac.getCurrentAnimation() == ac.getIdleLeft() || ac.getCurrentAnimation() == vc.getMoveLeftAnimation())
                newCurrentAnimation = ac.getIdleLeft();
            else newCurrentAnimation = ac.getIdleRight();
        }
        ac.setCurrentAnimation(newCurrentAnimation);
    }

    private static MissingComponentException missingPC() {
        return new MissingComponentException("PositionComponent");
    }

    private void bounceProjectile(VSData vsd, ProjectileComponent projectileComponent, float newX, float newY) {
        VelocityComponent v = (VelocityComponent) vsd.e.getComponent(VelocityComponent.class).orElseThrow();

        if (!Game.currentLevel.getTileAt(new Point(newX, vsd.pc.getPosition().y).toCoordinate()).isAccessible()) {
            v.setXVelocity(v.getXVelocity() * -1);
        } else if (!Game.currentLevel.getTileAt(new Point(vsd.pc.getPosition().x, newY).toCoordinate()).isAccessible()) {
            v.setYVelocity(v.getYVelocity() * -1);
        } else if (!Game.currentLevel.getTileAt(new Point(newX, newY).toCoordinate()).isAccessible()) {
            v.setYVelocity(v.getYVelocity() * -1);
            v.setXVelocity(v.getXVelocity() * -1);
        }
        projectileComponent.setBounceAmount(projectileComponent.getBounceAmount() - 1);
    }
}
