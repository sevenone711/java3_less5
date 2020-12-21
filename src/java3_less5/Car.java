package java3_less5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }
    Lock lock = new ReentrantLock();
    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier cyclicBarrier;
    private static boolean winCars = false;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car (Race race, int speed, CyclicBarrier cyclicBarrier) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cyclicBarrier = cyclicBarrier;
    }
    @Override
    public void run() {

        try {
            System.out.println(this.name + " готовится");

            Thread.sleep(500 + (int)(Math.random() * 800));

            System.out.println(this.name + " готов");
            cyclicBarrier.await(); //cyclicBarrier подождал первый раз 5 раз(почему 5 а не 4, когда ставиш 4 не работает правильно)
            cyclicBarrier.await(); // сразу после ожидания еще раз начинает ждать вот только что он ждет?(получается это третий  раз щелкает)

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        whoerWinner(this);
        try {
            cyclicBarrier.await(); // тут уже ждет понятное дело пока все закончат гонку (тут 6 раз)
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void whoerWinner (Car car) {
        if(!winCars){
            winCars = true;
            System.out.println("Пришел первым " + car.getName());

        }
    }
}
