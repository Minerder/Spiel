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
     * remove a skill from this component
     *
     * @param skill to remove
     */
    public void removeSkill(Skill skill) {
        skillSet.remove(skill);
    }

    /**
     * @return ArrayList with all skills of this component
     */
    public ArrayList<Skill> getSkillSet() {
        return skillSet;
    }

    /**
     * Returns the Skill Object at the given index
     *
     * @param index index in the List
     * @return the skill in the given index
     */
    public Skill getSkillFromList(int index) {
        if (index > skillSet.size() - 1) return null;
        return skillSet.get(index);
    }

    /**
     * reduces the cool down of each skill by 1 frame
     */
    public void reduceAllCoolDowns() {
        for (Skill skill : skillSet) skill.reduceCoolDown();
    }

    /**
     * @return maxMana
     */
    public int getMaxMana() {
        return maxMana;
    }

    /**
     * @param maxMana new maxMana
     */
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    /**
     * @return currentMana
     */
    public int getCurrentMana() {
        return currentMana;
    }

    /**
     * @param currentMana new currentMana
     */
    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }
}
