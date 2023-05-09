package ecs.components.skill.skills;

import ecs.components.skill.SkillComponent;
import ecs.entities.Entity;
import tools.Constants;

public class Skill {

    private final ISkillFunction skillFunction;
    private final int coolDownInFrames;
    private int currentCoolDownInFrames;
    private int manaCost;

    /**
     * @param skillFunction Function of this skill
     */
    public Skill(ISkillFunction skillFunction, float coolDownInSeconds) {
        this.skillFunction = skillFunction;
        this.coolDownInFrames = (int) (coolDownInSeconds * Constants.FRAME_RATE);
        this.currentCoolDownInFrames = 0;
    }

    /**
     *
     * @param skillFunction function of this skill
     * @param coolDownInSeconds cooldown of this skill
     * @param manaCost mana cost of this skill
     */
    public Skill(ISkillFunction skillFunction, float coolDownInSeconds, int manaCost) {
        this.skillFunction = skillFunction;
        this.coolDownInFrames = (int) (coolDownInSeconds * Constants.FRAME_RATE);
        this.currentCoolDownInFrames = 0;
        this.manaCost = manaCost;
    }

    /**
     * Execute the method of this skill if the entity has enough mana
     *
     * @param entity entity which uses the skill
     */
    public void execute(Entity entity) {
        SkillComponent sc = (SkillComponent) entity.getComponent(SkillComponent.class).orElseThrow();
        if (!isOnCoolDown() && sc.getCurrentMana() >= manaCost) {
            sc.setCurrentMana(sc.getCurrentMana() - manaCost);
            skillFunction.execute(entity);
            activateCoolDown();
        }
    }

    /**
     * @return true if cool down is not 0, else false
     */
    public boolean isOnCoolDown() {
        return currentCoolDownInFrames > 0;
    }

    /**
     * activate cool down
     */
    public void activateCoolDown() {
        currentCoolDownInFrames = coolDownInFrames;
    }

    /**
     * reduces the current cool down by frame
     */
    public void reduceCoolDown() {
        currentCoolDownInFrames = Math.max(0, --currentCoolDownInFrames);
    }
}
