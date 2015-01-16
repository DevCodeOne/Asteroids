package DevCodeOne.GameMechanics;

import DevCodeOne.Graphics.PixGraphics;
import DevCodeOne.Mathematics.Vector2f;

import java.util.ArrayList;

public class Map implements Tick {

    private ArrayList<Entity> entities;
    private ParticleList particleList;
    private Vector2f offset;
    private Physics physics;
    private int width, height;
    private int asteroidsCount;

    public Map(int width, int height) {
        this.entities = new ArrayList<Entity>();
        this.physics = new Physics();
        this.offset = new Vector2f();
        this.width = width;
        this.height = height;
        this.particleList = new ParticleList();
        this.asteroidsCount = -1;
    }

    public void draw(PixGraphics graphics) {
        for (Entity entity : entities) {
            entity.draw(graphics, (int)offset.getX(), (int)offset.getY());
        }
        particleList.draw(graphics, (int) offset.getX(), (int) offset.getY());
    }

    public void addParticles(Particle particles[]) {
        for (Particle particle : particles) {
            particleList.addParticle(particle);
        }
    }

    public void addParticle(Particle particle) {
        particleList.addParticle(particle);
    }

    public void scroll(float x, float y) {
        this.offset.add(x, y);
    }

    public void add(Entity entity) {
        entities.add(entity);
    }

    public void tick() {
        resetPositions();
        physics.doPhysics(entities, this);
        particleList.iterate();
        asteroidsCount = 0;
        for (Entity entity : getEntities()) {
            if (entity instanceof Asteroid) {
                float rand = (float)Math.random() * 0.025f;
                ((Asteroid)(entity)).rotate(rand);
                asteroidsCount++;
            }
        }
    }

    public void resetPositions() {
        for (Entity entity : entities) {
            int clip = 0;
            if (entity.getMax().getX() < 0) {
                clip |= 1;
            } else if (entity.getMin().getX() > width) {
                clip |= 1 << 1;
            }
            if (entity.getMax().getY() < 0) {
                clip |= 1 << 2;
            } else if (entity.getMin().getY() > height) {
                clip |= 1 << 3;
            }

            if ((entity instanceof Bullet) && clip != 0)
                entity.destroy();

            if ((clip & 1) == 1 ) {
                entity.setPosTo(width, entity.getPosition().getY());
            } else if ((clip & 2) == 2) {
                entity.setPosTo(0, entity.getPosition().getY());
            }
            if ((clip & 4) == 4) {
                entity.setPosTo(entity.getPosition().getX(), height);
            } else if ((clip & 8) == 8) {
                entity.setPosTo(entity.getPosition().getX(), 0);
            }
        }
    }

    public ArrayList<Entity> getEntities() { return entities; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public int getAsteroidsCount() { return asteroidsCount; }
}
