package DevCodeOne.GLGraphics;

import DevCodeOne.GameMechanics.Tick;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class GLDisplay implements Tick {

    private int width, height;
    private GLDrawInterface drawInterface;
    private ArrayList<GLComponent> components;
    private boolean init;

    public GLDisplay(int width, int height, GLDrawInterface drawInterface) {
        this.width = width;
        this.height = height;
        this.drawInterface = drawInterface;
        this.components = new ArrayList<GLComponent>();
        openWindow();
    }

    private void openWindow() {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setVSyncEnabled(true);
            Display.create();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        init = true;
    }

    public void tick() {
        if (!init)
            return;
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        drawInterface.draw();

        for (GLComponent component : components) {
            component.draw();
        }

        Display.update();
    }

    public void addComponent(GLComponent component) {
        this.components.add(component);
    }

    public void removeComponent(GLComponent component) {
        this.components.remove(component);
    }
}
