package contrib.components;

import contrib.utils.components.skill.IUpdateFunction;

import core.Component;
import core.Entity;

/** Enables an Entity to have an update function wich gets called every frame */
public class UpdateComponent extends Component {
    private final IUpdateFunction updateFunction;

    public UpdateComponent(Entity entity, IUpdateFunction updateFunction) {
        super(entity);
        this.updateFunction = updateFunction;
    }

    /**
     * Executes the update function
     *
     * @param entity
     */
    public void update(Entity entity) {
        if (updateFunction == null) return;
        this.updateFunction.update(entity);
    }
}
