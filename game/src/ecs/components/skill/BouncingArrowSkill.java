package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class BouncingArrowSkill extends DamageProjectileSkill {

    private int bounceAmount;
    public BouncingArrowSkill(ITargetSelection selectionFunction, int bounceAmount) {
        super("skills/arrow/up",
            0.5f,
            new Damage(1, DamageType.PHYSICAL, null),
            new Point(0.5f, 0.5f),
            selectionFunction,
            6f,
            bounceAmount);
        this.bounceAmount = bounceAmount;
    }

    public int getBounceAmount(){
        return bounceAmount;
    }
}
