package ecs.components.skill.skills;

import ecs.entities.Entity;

public interface IUpdateFunction {
    /**
     * Implements an update function
     * @param entity
     */
    void update(Entity entity);
}
