package DevCodeOne.GameMechanics;

import DevCodeOne.Graphics.PixGraphics;
import DevCodeOne.Mathematics.Vector2f;

public class Asteroid extends Entity {

    public static final int EDGES = 12;
    protected float size;
    protected int color;

    public Asteroid(float size, Vector2f position, int color) {
        super(null, position, color);
        vertices = new Vector2f[EDGES];
        float factor = (float) (2*Math.PI) / (float) EDGES;
        float change = size*1.25f;
        for (int i = 0; i < EDGES; i++) {
            float rand = (float) (Math.random() * 0.5f) - (0.25f);
            vertices[i] = new Vector2f((float) ((Math.sin(i*factor) * size) - (Math.sin(i*factor) * rand * change)), (float) ((Math.cos(i*factor) * size) - (Math.cos(i*factor) * rand * change)));
        }
        this.size = size;
        this.color = color;
        createBoundingBox();
    }

    public Asteroid(Vector2f vertices[], Vector2f position, int id) {
        super(vertices, position, id);
        createBoundingBox();
    }

    @Override
    public Entity[] collideEvent(Entity entity, Map map) {
        if (entity instanceof Asteroid || (entity instanceof Item && !((Item)(entity)).isAttached)) {
            return null;
        }
        if (getSize() <= 12.5f) {
            destroy();
            return null;
        }
        float ran = (float) Math.random();
        boolean item = false;
        if (ran > 0.7f)
            item = true;
        Entity entities[] = new Entity[!item ? 2 : 3];
        entities[0] = new Asteroid(getSize() / 2, new Vector2f(getPosition()), color);
        entities[1] = new Asteroid(getSize() / 2, new Vector2f(getPosition()), color);
        entities[0].setVelocityTo(-getVelocity().getY(), getVelocity().getX());
        entities[1].setVelocityTo(getVelocity().getY(), -getVelocity().getX());
        if (item) {
            Vector2f vertices[] = new Vector2f[36];
            float rad = 0;
            float step = (float) (2 * Math.PI / 36.0f);
            for (int i = 0; i < 36; i++) {
                vertices[i] = new Vector2f((float) Math.cos(rad) * getSize(), (float) Math.sin(rad) * getSize());
                rad += step;
            }
            entities[2] = new Item(vertices, getPosition(), getColor());
        }
        Particle particles[] = new Particle[512];
        float it = (float) Math.PI * 2 / particles.length;
        float val = 0;
        for (int i = 0; i < particles.length; i++) {
            float vel = (float) (Math.random() * 5.0f) + 2.5f;
            int life = (int) (Math.random() * 50) + 25;
            particles[i] = new Particle(new Vector2f(position), new Vector2f((float) Math.cos(val) * vel, (float) Math.sin(val) * vel), life, color);
            val += it;
        }
        map.addParticles(particles);
        destroy();
        if (entity instanceof Item)
            return null;
        return entities;
    }

    public float getSize() { return size; }
}
