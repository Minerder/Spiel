package ecs.components;

import ecs.entities.Entity;
import logging.CustomLogLevel;

import java.util.logging.Logger;

/**
 * This component is for the player character entity only. It should only be implemented by one
 * entity and mark this entity as the player character. This component stores data that is only
 * relevant for the player character. The PlayerSystems acts on the PlayableComponent.
 */
public class PlayableComponent extends Component {

    private boolean playable;
    private final Logger playableCompLogger = Logger.getLogger(this.getClass().getName());

    /**
     * @param entity associated entity
     */
    public PlayableComponent(Entity entity) {
        super(entity);
        playable = true;
    }

    /**
     * @return the playable state
     */
    public boolean isPlayable() {
        playableCompLogger.log(
            CustomLogLevel.DEBUG,
            "Checking if entity '"
                + entity.getClass().getSimpleName()
                + "' is playable: "
                + playable);
        return playable;
    }

    /**
     * @param playable set the playabale state
     */
    public void setPlayable(boolean playable) {
        this.playable = playable;
    }
}
