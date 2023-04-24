package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class HomingSparkSkill extends DamageProjectileSkill {
    public HomingSparkSkill(ITargetSelection selectionFunction) {
        super(
                "game/assets/animation",
                0.5f,
                new Damage(1, DamageType.MAGIC, null),
                new Point(0.5f, 0.5f),
                selectionFunction,
                5f);
    }
}
