package DevCodeOne.GameMechanics;

import DevCodeOne.Mathematics.Vector2f;

public class Bullet extends Entity {

    protected float size;

    public Bullet(float size, Vector2f position, Vector2f direction, int id) {
        super(new Vector2f[]{new Vector2f(0, 0), new Vector2f(direction.getX() * 4, direction.getY() * 4)}, position, id);
        Vector2f dir = new Vector2f(direction);
        dir.norm();
        setMaxVelocity(30);
        setVelocityTo(dir.getX() * 15, dir.getY() * 15);
        createBoundingBox();
    }

    @Override
    public Entity[] collideEvent(Entity entity) {
        destroy();
        return null;
    }
}
