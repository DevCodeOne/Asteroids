package DevCodeOne.GameMechanics;

public class GameClock implements Runnable {

    private Tick ticks[];
    private Thread loop;
    private boolean interrupted;
    private int sleep_time;
    private int tick_index;

    public GameClock(int max_ticks, int sleep_time) {
        this.ticks = new Tick[max_ticks];
        this.sleep_time = sleep_time;
        this.loop = new Thread(this);
    }

    public void start_clock() {
        this.loop.start();
    }

    public void run() {
        while (!interrupted) {
            try {
                long time = System.currentTimeMillis();
                for (Tick tick : ticks) {
                    if (tick != null)
                        tick.tick();
                }
                time = System.currentTimeMillis() - time;
                Thread.sleep(sleep_time < time ? 0 : sleep_time - time);
            } catch (Exception e) {
                System.out.println("Error @ GameClock");
                e.printStackTrace();
            }
        }
    }

    public void add(Tick tick) {
        ticks[tick_index++] = tick;
    }

    public void interrupt() {
        interrupted = true;
    }
}
