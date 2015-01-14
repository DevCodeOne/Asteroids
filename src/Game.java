import DevCodeOne.GameMechanics.*;
import DevCodeOne.Graphics.Display;
import DevCodeOne.Graphics.DrawInterface;
import DevCodeOne.Graphics.PixGraphics;
import DevCodeOne.Input.KeyInput;
import DevCodeOne.Input.KeyboardHandler;
import DevCodeOne.Mathematics.Vector2f;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class Game implements DrawInterface, Tick, KeyInput {

    private Display display;
    private GameClock clock;
    private Asteroid asteroids[] = new Asteroid[1];
    private Player player;
    private Map map;
    private KeyboardHandler handler;
    private int asteroids_count;
    private int timer = 10;

    public Game(int width, int height, int resx, int resy) {
        map = new Map(resx, resy);
        for (int i = 0; i < asteroids.length; i++) {
            float rand = (float) (Math.random() * Math.PI * 2);
            asteroids[i] = new Asteroid(50, new Vector2f((float) Math.random() * resx, (float) Math.random() * resy), i);
            asteroids[i].setVelocityTo((float)Math.sin(rand), (float)Math.cos(rand));
            map.add(asteroids[i]);
        }
        player = new Player(new Vector2f[]{new Vector2f(0, -10), new Vector2f(-7.5f, 10), new Vector2f(0, 5), new Vector2f(7.5f, 10)}, new Vector2f(resx/2- 100, resy/2 - 100), 1337, "Player", 50);
        map.add(player);
        clock = new GameClock(256, 12);
        display = new Display(width, height, resx, resy, this);
        handler = new KeyboardHandler(this, clock, display);
        clock.add(this);
        clock.add(display);
        clock.add(map);
        clock.start_clock();
    }

    public void tick() {
        asteroids_count = 0;
        for (Entity entity : map.getEntities()) {
            if (entity instanceof Asteroid) {
                float rand = (float)Math.random() * 0.025f;
                ((Asteroid)(entity)).rotate(rand);
                asteroids_count++;
            }
        }
    }

    public void draw(PixGraphics graphics) {
        graphics.clear(0);
        map.draw(graphics);
        float life = player.getLife() / 10;
        for (int i = 0; i <= life; i++) {
            graphics.dot_norm(i * 12, 20, 10);
        }
        if (asteroids_count == 0) {
            timer--;
            if (timer == 0) {
                Particle particles[] = new Particle[512];
                float it = (float) Math.PI * 2 / particles.length;
                float val = 0;
                float posx = (float) (Math.random() * map.getWidth());
                float posy = (float) (Math.random() * map.getHeight());
                for (int i = 0; i < particles.length; i++) {
                    float vel = (float) (Math.random() * 2.5f) + 1.25f;
                    int l = (int) (Math.random() * 50) + 25;
                    particles[i] = new Particle(new Vector2f(posx, posy), new Vector2f((float) Math.cos(val) * vel, (float) Math.sin(val) * vel), l);
                    val += it;
                    timer = 10;
                }
                map.addParticles(particles);
            }
        }
    }

    @Override
    public void handleKeys(boolean[] keys) {
        if (keys[KeyEvent.VK_W]) {
            player.incVelocityBy(player.getDirection().getX()*0.125f, 0);
            player.incVelocityBy(0, player.getDirection().getY()*0.125f);
        }
        if (keys[KeyEvent.VK_S]) {
            player.incVelocityBy(-player.getDirection().getX()*0.125f, 0);
            player.incVelocityBy(0, -player.getDirection().getY()*0.125f);
        }

        if (keys[KeyEvent.VK_D]) {
            player.rotate(0.05f);
        }

        if (keys[KeyEvent.VK_A]) {
            player.rotate(-0.05f);
        }

        if (keys[KeyEvent.VK_SPACE]) {
            Bullet bullet = new Bullet(2, new Vector2f(player.getVector(0)), player.getDirection(), 1337);
            map.add(bullet);
            keys[KeyEvent.VK_SPACE] = false;
        }
    }
}
