package DevCodeOne.GLGraphics;

import DevCodeOne.Graphics.PixGraphics;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class GLComponent {

    private int width, height;
    private int posx, posy;
    private ArrayList<GLComponent> components;

    public GLComponent() {
        this.components = new ArrayList<GLComponent>();
    }

    public void addComponent(GLComponent component) {
        this.components.add(component);
    }

    public void removeComponent(GLComponent component) { this.components.remove(component); }

    public void draw() {
        this.draw(0, 0);
        for (GLComponent component : components) {
            component.draw(posx, posy);
        }
    }

    public void draw(int offx, int offy) {
        GL11.glBegin(GL11.GL_LINE);
            GL11.glVertex2f(posx+offx, posy+offy);
            GL11.glVertex2f(posx+width+offx, posy+offy);

            GL11.glVertex2f(posx+width+offx, posy+offy);
            GL11.glVertex2f(posx+width+offx, posy+height+offy);

            GL11.glVertex2f(posx+width+offx, posy+height+offy);
            GL11.glVertex2f(posx+offx, posy+height+offy);

            GL11.glVertex2f(posx+offx, posy+height+offy);
            GL11.glVertex2f(posx+offx, posy+offy);
        GL11.glBegin(GL11.GL_LINE);
    }

    public void setLocation(int x, int y) {
        this.posx = x;
        this.posy = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }


}
