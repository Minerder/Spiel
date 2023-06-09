package contrib.components;

import core.Component;
import core.Entity;
import core.utils.Point;

public class ProjectileComponent extends Component {

    private final Point goalLocation;
    private final Point startPosition;
    private int bounceAmount;

    public ProjectileComponent(
            Entity entity, Point startPosition, Point goalLocation, int bounceAmount) {
        super(entity);
        this.goalLocation = goalLocation;
        this.startPosition = startPosition;
        this.bounceAmount = bounceAmount;
    }

    public ProjectileComponent(Entity entity, Point startPosition, Point goalLocation) {
        super(entity);
        this.goalLocation = goalLocation;
        this.startPosition = startPosition;
        this.bounceAmount = 0;
    }

    /**
     * gets the goal position of the projectile
     *
     * @return goal position of the projectile
     */
    public Point getGoalLocation() {
        return goalLocation;
    }

    /**
     * gets the start position of the projectile
     *
     * @return start position of the projectile
     */
    public Point getStartPosition() {
        return startPosition;
    }

    /**
     * gets the amount of bounces the projectile should perform
     *
     * @return amount of bounces to be performed
     */
    public int getBounceAmount() {
        return bounceAmount;
    }

    /**
     * sets the amount of bounces the projectile should perform
     *
     * @param bounceAmount amount of bounces to be performed
     */
    public void setBounceAmount(int bounceAmount) {
        this.bounceAmount = bounceAmount;
    }
}
