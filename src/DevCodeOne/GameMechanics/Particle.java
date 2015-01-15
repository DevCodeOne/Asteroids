package DevCodeOne.GameMechanics;

import DevCodeOne.Graphics.PixGraphics;
import DevCodeOne.Mathematics.Vector2f;

public class Particle {

    protected Vector2f position;
    protected Vector2f direction;
    protected int life;
    protected int color;
    protected int mr, mg, mb;

    public Particle(Vector2f position, Vector2f direction, int life) {
        this.life = life;
        this.position = position;
        this.direction = direction;
        this.color = PixGraphics.BLUE;
        this.mr = (((((color >> 16)) & 0xFF) / life));
        this.mg = (((((color >> 8)) & 0xFF) / life));
        this.mb = ((((color) & 0xFF) / life));
    }

    public void draw(PixGraphics graphics, int offx, int offy) {
        int r = Math.max(((color >> 16) & 0xFF) - mr, 0);
        int g = Math.max(((color >> 8) & 0xFF) - mr, 0);
        int b = Math.max(((color) & 0xFF) - mr, 0);
        color = r << 16 | g << 8 | b;
        float gesx = (position.getX() + offx);
        float gesy = (position.getY() + offy);
        graphics.setColor(color);
        graphics.dot(gesx, gesy, 2);
    }

    public void changePosByVelocity() {
        position.add(direction);
    }

    public void decLife() {
        life--;
    }

    public boolean isDead() {
        return life < 2;
    }
}
