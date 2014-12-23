package DevCodeOne.GameMechanics;

import DevCodeOne.Graphics.PixGraphics;
import DevCodeOne.Mathematics.Vector2f;

import java.util.ArrayList;

public class Map implements Tick {

    private ArrayList<Entity> entities;
    private Vector2f offset;
    private Physics physics;

    public Map() {
        this.entities = new ArrayList<Entity>();
        this.physics = new Physics();
        this.offset = new Vector2f();

    }

    public void draw(PixGraphics graphics) {
        for (Entity entity : entities) {
            entity.draw(graphics, (int)offset.getX(), (int)offset.getY());
        }
    }

    public void scroll(float x, float y) {
        this.offset.add(x, y);
    }

    public void add(Entity entity) {
        entities.add(entity);
    }

    public void tick() {
        physics.doPhysics(entities);
    }
}
