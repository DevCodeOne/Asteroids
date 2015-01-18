package DevCodeOne.GameMechanics;

import DevCodeOne.Mathematics.Vector2f;

public class Bullet extends Entity {

    protected float size;

    public Bullet(float size, Vector2f position, Vector2f direction, int color) {
        super(new Vector2f[]{new Vector2f(0, 0), new Vector2f(direction.getX() * size, direction.getY() * size)}, position, color);
        Vector2f dir = new Vector2f(direction);
        dir.norm();
        setMaxVelocity(30);
        setVelocityTo(dir.getX() * 15, dir.getY() * 15);
        createBoundingBox();
    }

    @Override
    public Entity[] collideEvent(Entity entity, Map map) {
        if (entity instanceof Asteroid)
            destroy();
        return null;
    }
}
