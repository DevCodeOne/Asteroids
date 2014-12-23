import DevCodeOne.GameMechanics.*;
import DevCodeOne.Graphics.Display;
import DevCodeOne.Graphics.DrawInterface;
import DevCodeOne.Graphics.PixGraphics;
import DevCodeOne.Input.KeyInput;
import DevCodeOne.Input.KeyboardHandler;
import DevCodeOne.Mathematics.Collider;
import DevCodeOne.Mathematics.Vector2f;

import java.awt.event.KeyEvent;

public class Game implements DrawInterface, Tick, KeyInput {

    private Display display;
    private GameClock clock;
    private Asteroid asteroids[] = new Asteroid[1];
    private Player player;
    private Map map;
    private KeyboardHandler handler;

    public Game(int width, int height, int resx, int resy) {
        map = new Map();
        for (int i = 0; i < asteroids.length; i++) {
            float rand = (float) (Math.random() * Math.PI * 2);
            //asteroids[i] = new Asteroid(50, new Vector2f((float) Math.random() * resx, (float) Math.random() * resy), i);
            asteroids[i] = new Asteroid(50, new Vector2f(resx/2, resy/2), i);
            //asteroids[i].setVelocityTo((float)Math.sin(rand), (float)Math.cos(rand));
            map.add(asteroids[i]);
        }
        player = new Player(new Vector2f[]{new Vector2f(0, -10), new Vector2f(-7.5f, 10), new Vector2f(0, 5), new Vector2f(7.5f, 10)}, new Vector2f(resx/2, resy/2), 1337, "Player");
        map.add(player);
        clock = new GameClock(256, 16);
        display = new Display(width, height, resx, resy, this);
        handler = new KeyboardHandler(this, clock, display);
        clock.add(this);
        clock.add(display);
        clock.add(map);
        clock.start_clock();
    }

    public void tick() {
        for (int i = 0; i < asteroids.length; i++) {
            float rand = (float)Math.random() * 0.025f;
            asteroids[i].rotate(rand);
        }
        System.out.println(player.getVelocity());
    }

    public void draw(PixGraphics graphics) {
        graphics.clear(0);
        map.draw(graphics);
    }

    @Override
    public void handleKeys(boolean[] keys) {
        if (keys[KeyEvent.VK_W]) {
            player.incVelocityBy(player.getDirection().getX()*0.5f, 0);
            player.incVelocityBy(0, player.getDirection().getY()*0.5f);
        }
        if (keys[KeyEvent.VK_S]) {
            player.incVelocityBy(-player.getDirection().getX()*0.5f, 0);
            player.incVelocityBy(0, -player.getDirection().getY()*0.5f);
        }

        if (keys[KeyEvent.VK_D]) {
            player.rotate(0.1f);
        }

        if (keys[KeyEvent.VK_A]) {
            player.rotate(-0.1f);
        }

        if (keys[KeyEvent.VK_SPACE]) {
            Entity bullet = new Entity(new Vector2f[]{new Vector2f(0, 0),
                    new Vector2f(player.getDirection())}, new Vector2f(player.getVector(0)), 1337);
            Vector2f direction = new Vector2f(player.getVector(0));
            direction.sub(player.getVector(2));
            direction.norm();
            bullet.setVelocityTo(direction.getX() * 7.5f, direction.getY() * 7.5f);
            map.add(bullet);
        }
    }
}
