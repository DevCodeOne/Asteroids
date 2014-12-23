package DevCodeOne.GameMechanics;

import java.util.ArrayList;

public class Physics {

    public static final float MAX_VELOCITY_SQUARE = 10;

    public void doPhysics(ArrayList<Entity> entities) {
        for (Entity entity : entities) {
            entity.changePosBy(entity.getVelocity().getX(), entity.getVelocity().getY());
        }
    }
}
