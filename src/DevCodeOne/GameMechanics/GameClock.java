package DevCodeOne.GameMechanics;

public class GameClock {

    private Tick ticks[];
    private boolean interrupted;
    private int sleep_time;
    private int tick_index;

    public GameClock(int max_ticks, int sleep_time) {
        this.ticks = new Tick[max_ticks];
        this.sleep_time = sleep_time;
    }

    public void run() {
        synchronized (this) {
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
        synchronized (this) {
            ticks[tick_index++] = tick;
        }
    }

    public void interrupt() {
        interrupted = true;
    }
}
