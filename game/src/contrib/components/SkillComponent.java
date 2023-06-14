package contrib.components;

import contrib.utils.components.skill.Skill;
import core.Component;
import core.Entity;

import java.util.ArrayList;

public class SkillComponent extends Component {

    public static String name = "SkillComponent";
    private final ArrayList<Skill> skillSet;
    private int maxMana;
    private int currentMana;

    /**
     * Creates a new SkillComponent containing a list of skills
     *
     * @param entity associated entity
     * @param maxMana max mana of the entity
     */
    public SkillComponent(Entity entity, int maxMana) {
        super(entity);
        skillSet = new ArrayList<>();
        this.maxMana = maxMana;
        this.currentMana = maxMana;
    }

    /**
     * @param entity associated entity
     */
    public SkillComponent(Entity entity) {
        super(entity);
        skillSet = new ArrayList<>();
        this.maxMana = 0;
        this.currentMana = 0;
    }

    /**
     * Add a skill to this component
     *
     * @param skill to add
     */
    public void addSkill(Skill skill) {
        skillSet.add(skill);
    }

    /**
     * Remove a skill from this component
     *
     * @param skill to remove
     */
    public void removeSkill(Skill skill) {
        skillSet.remove(skill);
    }

    /**
     * Replaces a skill at the same index as oldSkill
     *
     * @param oldSkill Skill that gets replaced
     * @param newSkill Skill that is placed into the set
     */
    public void replaceSkill(Skill oldSkill, Skill newSkill) {
        int index = skillSet.indexOf(oldSkill);
        skillSet.set(index, newSkill);
    }

    /**
     * Places the skill at the given index
     *
     * @param skill skill to add
     * @param index position in set
     */
    public void setSkill(Skill skill, int index) {
        skillSet.add(index, skill);
    }

    /**
     * @return ArrayList with all skills of this component
     */
    public ArrayList<Skill> getSkillSet() {
        return skillSet;
    }

    /**
     * Returns the skill object at the given index
     *
     * @param index index of the skill
     * @return The skill object at the given index
     */
    public Skill getSkillFromList(int index) {
        if (index > skillSet.size() - 1) return null;
        return skillSet.get(index);
    }

    /** Reduces the cooldown of each skill by 1 frame */
    public void reduceAllCoolDowns() {
        for (Skill skill : skillSet) skill.reduceCoolDown();
    }

    /**
     * Gets the maxMana
     *
     * @return maxMana
     */
    public int getMaxMana() {
        return maxMana;
    }

    /**
     * Sets a new maxMana
     *
     * @param maxMana new maxMana
     */
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    /**
     * Gets the currentMana
     *
     * @return currentMana
     */
    public int getCurrentMana() {
        return currentMana;
    }

    /**
     * Sets a new currentMana
     *
     * @param currentMana new currentMana
     */
    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }
}
