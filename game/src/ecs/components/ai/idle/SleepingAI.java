package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.components.ai.transition.RangeTransition;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.elements.tile.Tile;
import starter.Game;
import tools.Constants;

public class SleepingAI<T> implements IIdleAI {

    private boolean awake = false; // if monster is awake
    public SleepingAI(T walk) {

        RangeTransition range = new RangeTransition(3);
        if(range.isInFightMode(Game.getHero().orElseThrow())){ // when hero is in range of 3x3
            awake = true;
        }
        if(awake == true){ // sets after awake to the parameter walk
            if(walk.getClass() == PatrouilleWalk.class){
                new PatrouilleWalk(100,5,2, PatrouilleWalk.MODE.RANDOM);
            }
            if(walk.getClass() == RadiusWalk.class){
                new RadiusWalk(4,3);
            }
            if(walk.getClass() == StaticRadiusWalk.class){
                new StaticRadiusWalk(4,3);
            }
        }
    }

    @Override
    public void idle(Entity entity) {

    }
}
