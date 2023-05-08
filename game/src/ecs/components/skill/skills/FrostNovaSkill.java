package ecs.components.skill.skills;

import ecs.components.skill.ITargetSelection;
import ecs.entities.Entity;
import starter.Game;
import tools.Point;

public class FrostNovaSkill implements ISkillFunction {

    private final ITargetSelection targetSelection;

    public FrostNovaSkill(ITargetSelection targetSelection) {
        this.targetSelection = targetSelection;
    }

    @Override
    public void execute(Entity entity) {
        Point target = targetSelection.selectTargetPoint();

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (Game.currentLevel.getTileAt(new Point(target.x + (1 - x), target.y + (1 - y)).toCoordinate()).isAccessible()) {
                    if (x == 0 && y == 0)
                        new FrostNova(new Point(target.x + (1 - x), target.y + (1 - y)), true);
                    else
                        new FrostNova(new Point(target.x + (1 - x), target.y + (1 - y)), false);
                    Game.currentLevel.getTileAt(new Point(target.x + (1 - x), target.y + (1 - y)).toCoordinate()).setTexturePath("dungeon/ice/floor/floor_1.png");
                }
            }
        }
    }
}
