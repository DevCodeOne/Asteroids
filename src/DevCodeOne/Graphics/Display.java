package DevCodeOne.Graphics;

import DevCodeOne.GameMechanics.Tick;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Display extends Canvas implements Tick {

    private JFrame display;
    private BufferedImage offscreen;
    private BufferStrategy strategy;
    private Pixmap pixmap;
    private PixGraphics pixGraphics;
    private DrawInterface drawInterface;

    public Display(int width, int height, int resx, int resy, DrawInterface drawInterface) {

        this.offscreen = new BufferedImage(resx, resy, BufferedImage.TYPE_INT_RGB);
        this.pixmap = new Pixmap(offscreen);
        this.pixGraphics = new PixGraphics(pixmap);
        this.drawInterface = drawInterface;

        this.display = new JFrame();
        this.display.setSize(width, height);
        this.display.setLocationRelativeTo(null);
        this.display.setVisible(true);
        this.display.setLayout(null);
        this.display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(width, height);
        setLocation(0, 0);
        createBufferStrategy(1);

        this.display.add(this);
        this.strategy = getBufferStrategy();
    }

    public void tick() {
        if (this.strategy.contentsLost())
            return;
        drawInterface.draw(pixGraphics);
        this.strategy.getDrawGraphics().drawImage(offscreen, 0, 0, getWidth(), getHeight(), null);
    }
}
