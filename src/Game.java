import DevCodeOne.GameMechanics.*;
import DevCodeOne.Graphics.*;
import DevCodeOne.Input.KeyInput;
import DevCodeOne.Input.KeyboardHandler;
import DevCodeOne.Mathematics.Vector2f;

import java.awt.event.KeyEvent;

public class Game implements DrawInterface, Tick, KeyInput {

    private Display display;
    private GameClock clock;
    private Asteroid asteroids[];
    private Player player;
    private Map map;
    private KeyboardHandler handler;
    private int timer = 10;
    private boolean shot;
    private int resx, resy;
    private Menu menu, endingMenu;
    private boolean init;
    private boolean ending;

    public Game(int width, int height, int resx, int resy) {
        this.resx = resx;
        this.resy = resy;
        clock = new GameClock(256, 12);
        display = new Display(width, height, resx, resy, this);
        handler = new KeyboardHandler(this, clock, display);
        menu = new Menu(new String[]{"Easy", "Normal", "Difficult"}, 10, 100, 100);
        endingMenu = new Menu(new String[]{"Play Again"}, 10, 100, 100);
        display.addComponent(menu);
        clock.add(display);
        clock.start_clock();
    }

    public void initGame(int numberAsteroids) {
        map = new Map(resx, resy);
        asteroids = new Asteroid[numberAsteroids];
        for (int i = 0; i < asteroids.length; i++) {
            float rand = (float) (Math.random() * Math.PI * 2);
            int color = ((int) (Math.random()*122) + 122) << 16 | ((int) (Math.random()*122) + 122) << 8 | ((int) (Math.random()*122) + 122);
            asteroids[i] = new Asteroid(50, new Vector2f((float) Math.random() * resx, (float) Math.random() * resy), color);
            asteroids[i].setVelocityTo((float)Math.sin(rand), (float)Math.cos(rand));
            map.add(asteroids[i]);
        }
        int color = ((int) (Math.random()*122) + 122) << 16 | ((int) (Math.random()*122) + 122) << 8 | ((int) (Math.random()*122) + 122);
        player = new Player(new Vector2f[]{new Vector2f(0, -10), new Vector2f(-7.5f, 10), new Vector2f(0, 5), new Vector2f(7.5f, 10)}, new Vector2f(resx/2- 100, resy/2 - 100), 100, color);
        map.add(player);
        clock.add(map);
        clock.add(this);
        init = true;
    }

    public void tick() {
        if (init) {
            if (player.isDead()) {
                init = false;
                display.addComponent(menu);
            } else if (map.getAsteroidsCount() == 0 && !ending) {
                display.addComponent(endingMenu);
                ending = true;
            }
        }
    }

    public void draw(PixGraphics graphics) {
        if (init) {
            map.draw(graphics);
            float life = player.getLife() / 10;
            graphics.setColor(PixGraphics.BLUE);
            for (int i = 0; i <= life; i++) {
                graphics.dot_norm(i * 12, 20, 10);
            }
            if (map.getAsteroidsCount() == 0) {
                timer--;
                int color = ((int) (Math.random() * 122) + 122) << 16 | ((int) (Math.random() * 122) + 122) << 8 | ((int) (Math.random() * 122) + 122);
                if (timer == 0) {
                    Particle particles[] = new Particle[1024];
                    float it = (float) Math.PI * 2 / particles.length;
                    float val = 0;
                    float posx = (float) (Math.random() * map.getWidth());
                    float posy = (float) (Math.random() * map.getHeight());
                    for (int i = 0; i < particles.length; i++) {
                        float vel = (float) (Math.random() * .5f) + .25f;
                        int l = (int) (Math.random() * 200) + 25;
                        particles[i] = new Particle(new Vector2f(posx, posy), new Vector2f((float) Math.cos(val) * vel, (float) Math.sin(val) * vel), l, color);
                        val += it;
                        timer = 200;
                    }
                    map.addParticles(particles);
                }
            }
        }
        //graphics.dot(map.getWidth() / 2, map.getHeight() / 2, 1);
        //graphics.drawString("Hello World", 100, 100, 5);
    }

    @Override
    public void handleKeys(boolean[] keys) {
        if (init && !ending) {
            if (keys[KeyEvent.VK_W]) {
                player.incVelocityBy(player.getOrientation().getX() * 0.25f, 0);
                player.incVelocityBy(0, player.getOrientation().getY() * 0.25f);
            }
            if (keys[KeyEvent.VK_S]) {
                player.incVelocityBy(-player.getOrientation().getX() * 0.25f, 0);
                player.incVelocityBy(0, -player.getOrientation().getY() * 0.25f);
            }

            if (keys[KeyEvent.VK_D]) {
                player.rotate(0.05f);
            }

            if (keys[KeyEvent.VK_A]) {
                player.rotate(-0.05f);
            }

            if (keys[KeyEvent.VK_SPACE] && !shot) {
                Bullet bullet = new Bullet(2, new Vector2f(player.getVector(0)), player.getOrientation(), player.getColor());
                map.add(bullet);
                shot = true;
            } else if (!keys[KeyEvent.VK_SPACE]) {
                shot = false;
            }

            if (keys[KeyEvent.VK_ESCAPE]) {
                System.exit(0);
            }
        } else if (!ending) {
            if (keys[KeyEvent.VK_DOWN]) {
                menu.select(menu.getIndex() + 1);
                keys[KeyEvent.VK_DOWN] = false;
            } else if (keys[KeyEvent.VK_UP]) {
                menu.select(menu.getIndex() - 1);
                keys[KeyEvent.VK_UP] = false;
            }
            if (keys[KeyEvent.VK_ENTER]) {
                keys[KeyEvent.VK_ENTER] = false;
                int numberAsteroids = 0;
                if (menu.getSelectedItem().equals("Easy")) {
                    numberAsteroids = 1;
                } else if (menu.getSelectedItem().equals("Normal")) {
                    numberAsteroids = 15;
                } else  {
                    numberAsteroids = 20;
                }
                display.removeComponent(menu);
                initGame(numberAsteroids);
            }
        } else {
            if (keys[KeyEvent.VK_DOWN]) {
                menu.select(menu.getIndex() + 1);
                keys[KeyEvent.VK_DOWN] = false;
            } else if (keys[KeyEvent.VK_UP]) {
                menu.select(menu.getIndex() - 1);
                keys[KeyEvent.VK_UP] = false;
            }
            if (keys[KeyEvent.VK_ENTER]) {
                keys[KeyEvent.VK_ENTER] = false;
                if (endingMenu.getSelectedItem().equals("Play Again")) {
                    display.removeComponent(endingMenu);
                    ending = false;
                    player.destroy();
                }
            }
        }
    }
}
