package DevCodeOne.GameMechanics;

import DevCodeOne.Graphics.PixGraphics;
import DevCodeOne.Mathematics.Vector2f;

public class Player extends Entity {

    private Vector2f direction = new Vector2f(0, -1);

    public Player(Vector2f[] vertices, Vector2f position, int id, String name) {
        super(vertices, position, id);
        createBoundingBox();
        setMaxVelocity(2.5f);
    }

    public void draw(PixGraphics graphics, int offx, int offy) {
        //graphics.draw_line(100, 100, 100 + direction.getX() * 30, 100 + direction.getY() * 30);
        super.draw(graphics, offx, offy);
    }

    @Override
    public void rotate(float rot) {
        float cos = (float) Math.cos(rot);
        float sin = (float) Math.sin(rot);
        direction.set(direction.getX() * cos - direction.getY() * sin, direction.getY() * cos + direction.getX() * sin);
        super.rotate(rot);
    }

    public void incVelocityBy(float x, float y) {
        velocity.add(x, y);
        if (velocity.len() > MAX_VELOCITY) {
            velocity.sub(x, y);
        }
    }

    public void setVelocityTo(float x, float y) {
        Vector2f new_velocity = new Vector2f(x, y);
        if (new_velocity.len() < MAX_VELOCITY)
            velocity.set(x, y);
    }

    public Vector2f getDirection() { return direction; }
}
