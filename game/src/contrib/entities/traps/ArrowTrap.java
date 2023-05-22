package contrib.entities.traps;

import contrib.components.CollideComponent;
import contrib.components.HealthComponent;
import contrib.components.SkillComponent;
import contrib.utils.components.skill.Skill;
import contrib.utils.components.skill.SkillTools;
import contrib.utils.components.skill.skills.BouncingArrowSkill;
import core.Game;
import core.components.PositionComponent;
import core.level.utils.LevelElement;
import core.utils.Point;

public class ArrowTrap extends Trap {

    private final Point size = new Point(5,5);
    private final Point offset = new Point(0,0);
    private PositionComponent pcc;
    private final SkillComponent sc;

    /**
     * Creates a new ArrowTrap which shoots arrows at the player
     */
    public ArrowTrap() {
        // TODO: fix randomWallPosition and generateHitbox
        super("dungeon/traps/arrowTrap/arrowTrap.png", "dungeon/traps/arrowTrap/arrowTrap.png");
        setupPositionComponent();
        sc = new SkillComponent(this);
        sc.addSkill(new Skill(new BouncingArrowSkill(SkillTools::getHeroPosition, 0), 2));
        //generateHitbox();
        setupCollideComponent();
    }

    private void setupCollideComponent() {
        new CollideComponent(this, offset, size,
            (a, b, from) -> {
                if (b.getComponent(HealthComponent.class).isPresent()) {
                    sc.getSkillFromList(0).execute(a);
                }
        }, null);
    }

    @Override
    public void setupPositionComponent() {
        pcc = new PositionComponent(this, setupRandomWallPosition());
    }

    private Point setupRandomWallPosition(){
        Point wall = Game.currentLevel.getRandomTilePoint(LevelElement.FLOOR);
        for(float i = 0; i <= 15; i+=0.05){
            if (!Game.currentLevel.getTileAt(new Point(wall.x + i, wall.y).toCoordinate()).isAccessible()) {
                wall.x = wall.x + i;
                break;
            }

            if (!Game.currentLevel.getTileAt(new Point(wall.x, wall.y+i).toCoordinate()).isAccessible()) {
                wall.y = wall.y + i;
                break;
            }

            if (!Game.currentLevel.getTileAt(new Point(wall.x - i, wall.y).toCoordinate()).isAccessible()) {
                wall.x = wall.x - i;
                break;
            }

            if (!Game.currentLevel.getTileAt(new Point(wall.x, wall.y-i).toCoordinate()).isAccessible()) {
                wall.y = wall.y - i;
                break;
            }
        }
        return wall;
    }

    private void generateHitbox() {
        if (Game.currentLevel.getTileAt(new Point(pcc.getPosition().x+1, pcc.getPosition().y).toCoordinate()).isAccessible()) {
            // nach rechts
            for(float i = 0; i < 5; i++) {
                if (!Game.currentLevel.getTileAt(new Point(pcc.getPosition().x + i, pcc.getPosition().y).toCoordinate()).isAccessible()) {
                    size.x = i + 1;
                    size.y = 1;
                    offset.x = (float) Math.ceil(size.x/2);
                    offset.y = 0;
                }
            }
        } else if (Game.currentLevel.getTileAt(new Point(pcc.getPosition().x, pcc.getPosition().y+1).toCoordinate()).isAccessible()) {
            // nach oben
            for(float i = 0; i < 5; i++) {
                if (!Game.currentLevel.getTileAt(new Point(pcc.getPosition().x , pcc.getPosition().y + i).toCoordinate()).isAccessible()) {
                    size.x = 1;
                    size.y = i + 1;
                    offset.y = (float) Math.ceil(size.y/2);
                    offset.x = 0;
                }
            }
        } else if (Game.currentLevel.getTileAt(new Point(pcc.getPosition().x-1, pcc.getPosition().y).toCoordinate()).isAccessible()) {
            // nach links
            for(float i = 0; i < 5; i++) {
                if (!Game.currentLevel.getTileAt(new Point(pcc.getPosition().x - i , pcc.getPosition().y).toCoordinate()).isAccessible()) {
                    size.x = i * -1;
                    size.y = 1;
                    offset.x = (float) Math.ceil(size.y/2)*-1;
                    offset.y = 0;
                }
            }
        } else if (Game.currentLevel.getTileAt(new Point(pcc.getPosition().x, pcc.getPosition().y-1).toCoordinate()).isAccessible()) {
            // nach unten
            for(float i = 0; i < 5; i++) {
                if (!Game.currentLevel.getTileAt(new Point(pcc.getPosition().x , pcc.getPosition().y - i).toCoordinate()).isAccessible()) {
                    size.x = 1;
                    size.y = i * -1;
                   offset.y = (float) Math.ceil(size.y/2)*-1;
                    offset.x = 0;
                }
            }
        }
        size.x = size.x/2;
        size.y = size.y/2;
    }
}

