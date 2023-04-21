package ecs.components.skill;

import ecs.components.Component;
import ecs.entities.Entity;
import tools.Point;

public class ProjectileComponent extends Component {

    private Point goalLocation;
    private Point startPosition;

    private int bounceAmount;

    public ProjectileComponent(Entity entity, Point startPosition, Point goalLocation, int bounceAmount) {
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

    public int getBounceAmount(){
        return bounceAmount;
    }
    public void setBounceAmount(int bounceAmount){
        this.bounceAmount = bounceAmount;
    }
}
