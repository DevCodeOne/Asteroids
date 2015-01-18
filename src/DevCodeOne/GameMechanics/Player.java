package DevCodeOne.GameMechanics;

import DevCodeOne.Mathematics.Vector2f;

import java.util.ArrayList;

public class Player extends Entity {

    protected Vector2f orientation = new Vector2f(0, -1);
    protected float life;
    protected ArrayList<Item> items = new ArrayList<Item>();

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
        orientation.set(orientation.getX() * cos - orientation.getY() * sin, orientation.getY() * cos + orientation.getX() * sin);
        super.rotate(rot);
        if (hasItems()) {
            for (Item item : items) {
                item.rotate(rot);
            }
        }
    }

    public void setPosTo(float x, float y) {
        super.setPosTo(x, y);
        if (hasItems()) {
            for (Item item : items) {
                item.setPosTo(x, y);
            }
        }
    }

    public void changePosBy(float x, float y) {
        super.changePosBy(x, y);
        if (hasItems()) {
            for (Item item : items) {
                item.changePosBy(x, y);
            }
        }
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

    public void shoot(Map map) {
        if (!hasItems()) {
            Bullet bullet = new Bullet(2, new Vector2f(getVector(0)), getOrientation(), getColor());
            map.add(bullet);
        } else {
            for (Item item : items) {
                item.shoot(this, map);
            }
        }
    }

    public void addItem(Item item) {
        if (items.indexOf(item) != -1)
            return;
        items.add(item);
        item.setPosTo(getPosition().getX(), getPosition().getY());
    }

    public boolean hasItems() { return items.size() > 0; }

    public float getLife() { return life; }

    public Vector2f getOrientation() { return orientation; }
}
