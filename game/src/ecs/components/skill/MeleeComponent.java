package ecs.components.skill;

import ecs.components.Component;
import ecs.entities.Entity;

public class MeleeComponent extends Component {
    private final DamageMeleeSkill skill;

    public MeleeComponent(Entity entity, DamageMeleeSkill skill) {
        super(entity);
        this.skill = skill;
    }

    /**
     * Gets the DamageMeleeSkill object of a melee skill
     *
     * @return the DamageMeleeSkill object of a melee skill
     */
    public DamageMeleeSkill getMeleeSkill() {
        return skill;
    }
}
