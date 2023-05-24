package contrib.entities.traps;

import contrib.components.CollideComponent;
import contrib.components.HealthComponent;
import contrib.components.SkillComponent;
import contrib.utils.components.skill.ITargetSelection;
import contrib.utils.components.skill.Skill;
import contrib.utils.components.skill.skills.BouncingArrowSkill;
import core.Game;
import core.components.PositionComponent;
import core.level.utils.LevelElement;
import core.utils.Point;
import dslToGame.AnimationBuilder;

public class ArrowTrap extends Trap implements ITargetSelection {

    private Point size;
    private Point offset;
    private PositionComponent pcc;
    private final SkillComponent sc;
    private int direction; // direction the trap shoots 1 = left, -1 = right, 0 = down

    /**
     * Creates a new ArrowTrap which shoots arrows at the player
     */
    public ArrowTrap() {
        super("dungeon/traps/arrowTrap/arrowTrap_N.png", "dungeon/traps/arrowTrap/arrowTrap_N.png");
        setupPositionComponent();
        sc = new SkillComponent(this);
        sc.addSkill(new Skill(new BouncingArrowSkill(this, 0), 2));
        generateHitbox();
        setupCollideComponent();
    }

    private void setupCollideComponent() {
        new CollideComponent(this, this.offset, this.size,
            (a, b, from) -> {
                if (b.getComponent(HealthComponent.class).isPresent()) {
                    sc.getSkillFromList(0).execute(a);
                }
            }, null);
    }

    @Override
    public void setupPositionComponent() {
        pcc = new PositionComponent(this);
        setupRandomWallPosition();
    }

    /**
     * Sets the position of the ArrowTrap next to a wall to the right left or up
     * and gives the trap the right texture
     */
    private void setupRandomWallPosition() {
        float newX = pcc.getPosition().x + 0.5f; // Sets the position to the middle of the tile
        float newY = pcc.getPosition().y + 0.2f;
        float x = pcc.getPosition().x;
        float y = pcc.getPosition().y;
        pcc.setPosition(new Point(newX, newY));
        // searches for the nearest wall traveling 10 tiles
        // searches up left and right
        for (int i = 0; i < 10; i++) {
            if (Game.currentLevel.getTileAt(new Point(x + i + 1, y).toCoordinate()).getLevelElement() == LevelElement.WALL) {
                pcc.setPosition(new Point(newX + i + 0.4f, newY));
                super.setIdleAnimation(AnimationBuilder.buildAnimation("dungeon/traps/arrowTrap/arrowTrap_E.png"));
                this.direction = 1;
                break;
            } else if (Game.currentLevel.getTileAt(new Point(x, y + i + 1).toCoordinate()).getLevelElement() == LevelElement.WALL) {
                pcc.setPosition(new Point(newX, newY + i + 0.5f));
                super.setIdleAnimation(AnimationBuilder.buildAnimation("dungeon/traps/arrowTrap/arrowTrap_N.png"));
                this.direction = 0;
                break;
            } else if (Game.currentLevel.getTileAt(new Point(x - i - 1, y).toCoordinate()).getLevelElement() == LevelElement.WALL) {
                pcc.setPosition(new Point(newX - i - 0.5f, newY));
                super.setIdleAnimation(AnimationBuilder.buildAnimation("dungeon/traps/arrowTrap/arrowTrap_W.png"));
                this.direction = -1;
                break;
            }
        }
    }

    private void generateHitbox() {
        if (direction == 1) {
            //left 5 tiles
            this.size = new Point(5, 0.5f);
            this.offset = new Point(-4, 0.2f);
        } else if (direction == -1) {
            //right 5 tiles
            this.size = new Point(5, 0.5f);
            this.offset = new Point(0, 0);
        } else if (direction == 0) {
            //down 5 tiles
            this.size = new Point(0.5f, 5);
            this.offset = new Point(0.2f, -4);
        }
    }


    @Override
    public Point selectTargetPoint() {
        if (direction == 1) {
            // left
            return new Point(pcc.getPosition().x - 5, pcc.getPosition().y);
        } else if (direction == -1) {
            // right
            return new Point(pcc.getPosition().x + 5, pcc.getPosition().y);
        } else if (direction == 0) {
            // down
            return new Point(pcc.getPosition().x, pcc.getPosition().y - 5);
        } else {
            return new Point(0, 0);
        }
    }
}

