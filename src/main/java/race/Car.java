package race;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable {
    private final CountDownLatch cdl;
    private final CyclicBarrier cb;
    private final AtomicInteger atomicCount;
    private static int CARS_COUNT;
    private final Race race;
    private final int speed;
    private final String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }


    public Car(Race race, int speed,  CyclicBarrier cb, CountDownLatch cdl, AtomicInteger atomicCount) {
        this.race = race;
        this.speed = speed;
        this.cb = cb;
        this.cdl = cdl;
        this.atomicCount = atomicCount;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {

        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        cdl.countDown();

        if (atomicCount.incrementAndGet() == 1){
            System.out.println(this.name + " race winner!!!");
        }
    }
}


