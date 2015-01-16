package DevCodeOne.Graphics;

import java.util.ArrayList;

public class Component {

    private int width, height;
    private int posx, posy;
    private ArrayList<Component> components;

    public Component() {
        this.components = new ArrayList<Component>();
    }

    public void addComponent(Component component) {
        this.components.add(component);
    }

    public void removeComponent(Component component) { this.components.remove(component); }

    public void draw(PixGraphics graphics) {
        this.draw(graphics, 0, 0);
        for (Component component : components) {
            component.draw(graphics, posx, posy);
        }
    }

    public void draw(PixGraphics graphics, int offx, int offy) {
        graphics.drawLine(posx+offx, posy+offy, posx+width+offx, posy+offy);
        graphics.drawLine(posx+width+offx, posy+offy, posx+width+offx, posy+height+offy);
        graphics.drawLine(posx+width+offx, posy+height+offy, posx+offx, posy+height+offy);
        graphics.drawLine(posx+offx, posy+height+offy, posx+offx, posy+offy);
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
