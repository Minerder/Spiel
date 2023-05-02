package ecs.components.skill.skills;

import ecs.entities.Entity;

public interface ISkillFunction {

    /**
     * Implements the concrete skill of an entity
     *
     * @param entity which uses the skill
     */
    void execute(Entity entity);
}
