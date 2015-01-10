package DevCodeOne.GameMechanics;

import DevCodeOne.Mathematics.Collider;

import java.util.ArrayList;

public class Physics {

    public void doPhysics(ArrayList<Entity> entities) {
        ArrayList<Entity> spawn = new ArrayList<Entity>();
        for (Entity entity : entities) {
            entity.changePosBy(entity.getVelocity().getX(), entity.getVelocity().getY());
        }
        for (int i = 0; i < entities.size(); i++) {
            for (int j = 0; j < entities.size(); j++) {
                if (entities.get(i) != entities.get(j) && !(entities.get(i) instanceof Asteroid && entities.get(j) instanceof Asteroid)) {
                    if (Collider.collides(entities.get(i), entities.get(j))) {
                        Entity spawnarr[] = entities.get(i).collideEvent(entities.get(j));
                        if (spawnarr != null) {
                            for (Entity toadd : spawnarr) {
                                if (toadd != null) {
                                    spawn.add(toadd);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).isDead())
                entities.remove(i);
        }
        for (Entity entity : spawn) {
            entities.add(entity);
        }
    }
}
