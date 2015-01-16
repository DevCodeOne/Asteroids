package DevCodeOne.GameMechanics;

import DevCodeOne.Graphics.PixGraphics;
import DevCodeOne.Mathematics.Vector2f;

public class Player extends Entity {

    protected Vector2f direction = new Vector2f(0, -1);
    protected float life;
    protected int color;

    public Player(Vector2f[] vertices, Vector2f position, float life, int color) {
        super(vertices, position, color);
        createBoundingBox();
        setMaxVelocity(3.5f);
        this.life = life;
        this.color = color;
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

    @Override
    public Entity[] collideEvent(Entity entity, Map map) {
        if (entity instanceof Asteroid) {
            life -= 10;
        }
        if (life <= 0) {
            Particle particles[] = new Particle[512];
            int color = ((int) (Math.random()*122) + 122) << 16 | ((int) (Math.random()*122) + 122) << 8 | ((int) (Math.random()*122) + 122);
            float it = (float) Math.PI * 2 / particles.length;
            float val = 0;
            for (int i = 0; i < particles.length; i++) {
                float vel = (float) (Math.random() * 2.5f) + 1.25f;
                int life = (int) (Math.random() * 50) + 25;
                particles[i] = new Particle(new Vector2f(position), new Vector2f((float) Math.cos(val) * vel, (float) Math.sin(val) * vel), life, color);
                val += it;
            }
            map.addParticles(particles);
            destroy();
        }
        return null;
    }

    public float getLife() { return life; }

    public Vector2f getDirection() { return direction; }
}
