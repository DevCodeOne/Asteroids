package DevCodeOne.GameMechanics;

import DevCodeOne.Mathematics.Vector2f;
import org.lwjgl.opengl.GL11;

public class Entity {

    protected Vector2f vertices[];
    protected Vector2f position;
    protected Vector2f velocity;
    protected Vector2f min;
    protected Vector2f max;
    protected int color;
    protected float MAX_VELOCITY = 20;
    protected boolean dead;

    public Entity(Vector2f vertices[], Vector2f position, int color) {
        this.vertices = vertices;
        this.position = position;
        this.velocity = new Vector2f();
        this.color = color;
        if (this.vertices != null) {
            this.createBoundingBox();
        }
    }

    public void draw(int offx, int offy) {
        float gesx = (float) (position.getX()+offx);
        float gesy = (float) (position.getY()+offy);
        GL11.glColor3ub((byte) ((color >> 16) & 0xFF), (byte) ((color >> 8) & 0xFF), (byte) ((color) & 0xFF));
        GL11.glBegin(GL11.GL_LINES);
        for (int i = 0; i < vertices.length; i++)  {
            int next = (i + 1) % vertices.length;
            GL11.glVertex2f(vertices[i].getX() + gesx, vertices[i].getY() + gesy);
            GL11.glVertex2f(vertices[next].getX() + gesx, vertices[next].getY() + gesy);
        }
        GL11.glEnd();
    }

    public void setMaxVelocity(float velocity) {
        this.MAX_VELOCITY = velocity;
    }

    public float getMaxVelocity() {
        return MAX_VELOCITY;
    }

    public void createBoundingBox() {
        float minx = vertices[0].getX();
        float maxx = vertices[0].getX();
        float miny = vertices[0].getY();
        float maxy = vertices[0].getY();
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].getX() < minx) {
                minx = vertices[i].getX();
            }
            if (vertices[i].getX() > maxx) {
                maxx = vertices[i].getX();
            }
            if (vertices[i].getY() < miny) {
                miny = vertices[i].getY();
            }
            if (vertices[i].getY() > maxy) {
                maxy = vertices[i].getY();
            }
        }
        this.max = new Vector2f(maxx, maxy);
        this.min = new Vector2f(minx, miny);
        this.max.add(position);
        this.min.add(position);
    }

    public void changePosBy(float x, float y) {
        position.add(x, y);
        if (min != null && max != null) {
            min.add(x, y);
            max.add(x, y);
        }
    }

    public void setPosTo(float x, float y) {
        if (min != null && max != null) {
            min.sub(position.getX(), position.getY());
            max.sub(position.getX(), position.getY());
            position.set(x, y);
            max.add(x, y);
            min.add(x, y);
        } else {
            position.set(x, y);
        }
    }

    public void rotate(float rot) {
        float cos = (float) Math.cos(rot);
        float sin = (float) Math.sin(rot);

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].set(vertices[i].getX() * cos - vertices[i].getY() * sin, vertices[i].getY() * cos + vertices[i].getX() * sin);
        }
        createBoundingBox();
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

    public Entity[] collideEvent(Entity entity, Map map) { return null; }

    public Vector2f getMin() { return min; }

    public Vector2f getMax() { return max; }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public Vector2f getVector(int index) {
        return new Vector2f(vertices[index].getX() + position.getX(), vertices[index].getY() + position.getY());
    }

    public void destroy() { dead = true; }

    public Vector2f[] getVertices() { return vertices; }

    public int getColor() { return color; }

    public boolean isDead() { return dead; }
}
