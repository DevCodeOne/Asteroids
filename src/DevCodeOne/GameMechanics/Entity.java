package DevCodeOne.GameMechanics;

import DevCodeOne.Graphics.PixGraphics;
import DevCodeOne.Mathematics.Vector2f;

import java.awt.geom.Rectangle2D;

public class Entity {

    protected Vector2f vertices[];
    protected Vector2f position;
    protected Vector2f velocity;
    protected Vector2f min;
    protected Vector2f max;
    protected int id;

    public Entity(Vector2f vertices[], Vector2f position, int id) {
        this.vertices = vertices;
        this.position = position;
        this.velocity = new Vector2f();
    }

    public void draw(PixGraphics graphics, int offx, int offy) {
        //drawBoundingBox(graphics, offx, offy);
        float gesx = (float) (position.getX()+offx);
        float gesy = (float) (position.getY()+offy);
        for (int i = 0; i < vertices.length; i++)  {
            int next = (i + 1) % vertices.length;
            graphics.draw_line(vertices[i].getX()+gesx, vertices[i].getY()+gesy, vertices[next].getX()+gesx, vertices[next].getY()+gesy);
        }
    }

    public void drawBoundingBox(PixGraphics graphics, int offx, int offy) {
        graphics.draw_line(min.getX()+offx, min.getY()+offy, max.getX()+offx, min.getY()+offy);
        graphics.draw_line(max.getX()+offx, min.getY()+offy, max.getX()+offx, max.getY()+offy);
        graphics.draw_line(max.getX()+offx, max.getY()+offy, min.getX()+offx, max.getY()+offy);
        graphics.draw_line(min.getX()+offx, max.getY()+offy, min.getX()+offx, min.getY()+offy);
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
        if (velocity.len() > Physics.MAX_VELOCITY_SQUARE) {
            velocity.sub(x, y);
        }
    }

    public void setVelocityTo(float x, float y) {
        Vector2f new_velocity = new Vector2f(x, y);
        if (new_velocity.len() < Physics.MAX_VELOCITY_SQUARE)
            velocity.set(x, y);
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public Vector2f getVector(int index) {
        return new Vector2f(vertices[index].getX() + position.getX(), vertices[index].getY() + position.getY());
    }

    public Vector2f[] getVertices() { return vertices; }

    public int getID() { return id; }
}
