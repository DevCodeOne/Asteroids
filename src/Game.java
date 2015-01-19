import DevCodeOne.GLGraphics.GLDisplay;
import DevCodeOne.GLGraphics.GLDrawInterface;
import DevCodeOne.GLGraphics.GLMenu;
import DevCodeOne.GameMechanics.*;
import DevCodeOne.Mathematics.Vector2f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Game implements GLDrawInterface, Tick {

    private GLDisplay display;
    private GameClock clock;
    private Asteroid asteroids[];
    private Player player;
    private Map map;
    private int timer = 10;
    private boolean shot;
    private int width, height;
    private GLMenu menu, endingMenu;
    private boolean init;
    private boolean ending;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        clock = new GameClock(256, 12);
        display = new GLDisplay(width, height, this);
        menu = new GLMenu(new String[]{"Easy", "Normal", "Difficult"}, 10, 100, 100);
        endingMenu = new GLMenu(new String[]{"Play Again"}, 10, 100, 100);
        display.addComponent(menu);
        clock.add(display);
        gameLoop();
    }

    public void gameLoop() {
        while(!org.lwjgl.opengl.Display.isCloseRequested()) {
            clock.run();
            events();
        }
    }

    public void initGame(int numberAsteroids) {
        map = new Map(width, height);
        asteroids = new Asteroid[numberAsteroids];
        for (int i = 0; i < asteroids.length; i++) {
            float rand = (float) (Math.random() * Math.PI * 2);
            int color = ((int) (Math.random()*122) + 122) << 16 | ((int) (Math.random()*122) + 122) << 8 | ((int) (Math.random()*122) + 122);
            asteroids[i] = new Asteroid(50, new Vector2f((float) Math.random() * width, (float) Math.random() * height), color);
            asteroids[i].setVelocityTo((float)Math.sin(rand), (float)Math.cos(rand));
            map.add(asteroids[i]);
        }
        int color = ((int) (Math.random()*122) + 122) << 16 | ((int) (Math.random()*122) + 122) << 8 | ((int) (Math.random()*122) + 122);
        player = new Player(new Vector2f[]{new Vector2f(0, -10), new Vector2f(-7.5f, 10), new Vector2f(0, 5), new Vector2f(7.5f, 10)}, new Vector2f(width/2- 100, height/2 - 100), 100, color);
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

    public void draw() {
        if (init) {
            map.draw();
            float life = player.getLife() / 10;
            GL11.glColor3b((byte) 200, (byte) 50, (byte) 50);
            GL11.glPointSize(10);
            GL11.glBegin(GL11.GL_POINTS);
            for (int i = 0; i <= life; i++) {
                GL11.glVertex2f(i * 12 + 10, 20);
            }
            GL11.glEnd();
            GL11.glPointSize(1);
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
    }

    public void events() {
        if (init && !ending) {
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                player.incVelocityBy(player.getOrientation().getX() * 0.25f, 0);
                player.incVelocityBy(0, player.getOrientation().getY() * 0.25f);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                player.incVelocityBy(-player.getOrientation().getX() * 0.25f, 0);
                player.incVelocityBy(0, -player.getOrientation().getY() * 0.25f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                player.rotate(-0.05f);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                player.rotate(0.05f);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !shot) {
                player.shoot(map);
                shot = true;
            } else if (!Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                shot = false;
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                System.exit(0);
            }
        } else if (!ending) {
            while (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                    if (Keyboard.getEventKey() == Keyboard.KEY_DOWN)
                        menu.select(menu.getIndex() + 1);
                    else if (Keyboard.getEventKey() == Keyboard.KEY_UP)
                        menu.select(menu.getIndex() - 1);
                    if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
                        int numberAsteroids = 0;
                        if (menu.getSelectedItem().equals("Easy")) {
                            numberAsteroids = 1;
                        } else if (menu.getSelectedItem().equals("Normal")) {
                            numberAsteroids = 15;
                        } else {
                            numberAsteroids = 20;
                        }
                        display.removeComponent(menu);
                        initGame(numberAsteroids);
                    }
                }
            }
        } else {
            while (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                    if (Keyboard.getEventKey() == Keyboard.KEY_DOWN)
                        menu.select(menu.getIndex() + 1);
                    else if (Keyboard.getEventKey() == Keyboard.KEY_UP)
                        menu.select(menu.getIndex() - 1);
                    else if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
                        if (endingMenu.getSelectedItem().equals("Play Again")) {
                            display.removeComponent(endingMenu);
                            ending = false;
                            player.destroy();
                        }
                    }
                }
            }
        }
    }

    public void handleKeys(boolean[] keys) {
        /*if (init && !ending) {
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
                player.shoot(map);
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
        }*/
    }
}
