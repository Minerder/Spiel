package contrib.utils.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import contrib.utils.components.ai.AITools;
import contrib.utils.components.ai.IIdleAI;
import core.Entity;
import core.level.Tile;

public class FollowHero implements IIdleAI{

    private final float detectionRange;
    private final RadiusWalk defaultIdle = new RadiusWalk(3, 2);

    /**
     * Follows the hero if he is in range. Otherwise, walks around in a radius.
     *
     * @param detectionRange The range in which the hero is detected
     */
    public FollowHero(float detectionRange) {
        this.detectionRange = detectionRange;
    }

    @Override
    public void idle(Entity entity) {
        if (!AITools.playerInRange(entity, detectionRange)) {
            defaultIdle.idle(entity);
            return;
        }

        GraphPath<Tile> path = AITools.calculatePathToHero(entity);
        AITools.move(entity, path);
    }
}
