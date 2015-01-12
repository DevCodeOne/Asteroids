package DevCodeOne.GameMechanics;

import DevCodeOne.Graphics.PixGraphics;
import DevCodeOne.Mathematics.Vector2f;

public class Particle {

    protected Vector2f position;
    protected Vector2f direction;
    protected int life;

    public Particle(Vector2f position, Vector2f direction, int life) {
        this.life = life;
        this.position = position;
        this.direction = direction;
    }

    public void draw(PixGraphics graphics, int offx, int offy) {
        float gesx = (position.getX() + offx);
        float gesy = (position.getY() + offy);
        graphics.dot(gesx, gesy);
    }

    public void changePosByVelocity() {
        position.add(direction);
    }

    public void decLife() {
        life--;
    }

    public boolean isDead() {
        return life < 1;
    }
}
