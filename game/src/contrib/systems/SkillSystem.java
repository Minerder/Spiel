package contrib.systems;

import contrib.components.SkillComponent;
import core.Game;
import core.System;

public class SkillSystem extends System {

    private int increaseManaAfterFrames = 60;

    /** reduces the cool down for all skills and increases currentMana if the entity has mana */
    @Override
    public void update() {
        increaseManaAfterFrames = Math.max(0, --increaseManaAfterFrames);
        Game.getEntities().stream()
                // Consider only entities that have a SkillComponent
                .flatMap(e -> e.getComponent(SkillComponent.class).stream())
                .forEach(
                        sc -> {
                            ((SkillComponent) sc).reduceAllCoolDowns();
                            if (((SkillComponent) sc).getCurrentMana()
                                            < ((SkillComponent) sc).getMaxMana()
                                    && increaseManaAfterFrames == 0) {
                                increaseManaAfterFrames = 60;
                                ((SkillComponent) sc)
                                        .setCurrentMana(((SkillComponent) sc).getCurrentMana() + 1);
                            }
                        });
    }
}
