package race;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MainClass {
    public static final int CARS_COUNT = 4;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(CARS_COUNT);
    public static AtomicInteger atomicCount = new AtomicInteger(0);


    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT + 1, () ->
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!"));

        CountDownLatch cdl = new CountDownLatch(CARS_COUNT);

        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];


        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cb, cdl, atomicCount);

        }
        for (Car car : cars) {
            executorService.execute(car);
        }

        try{
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }


            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
                executorService.shutdown();
            }



    }
}


