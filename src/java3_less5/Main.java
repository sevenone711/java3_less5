package java3_less5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {

        public static final int CARS_COUNT = 4;

        public static void main(String[] args) {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

            CyclicBarrier cyclicBarrier = new CyclicBarrier(5); // почему если поставить 4 то не работает нудным образом, ждет готовность только 4 машин? не могу понят

            Semaphore semaphore = new Semaphore(CARS_COUNT/2);

            Race race = new Race(new Road(60), new Tunnel(semaphore), new Road(40));
            Car[] cars = new Car[CARS_COUNT];
            for (int i = 0; i < cars.length; i++) {
                cars[i] = new Car(race, 20 + (int) (Math.random() * 10),cyclicBarrier); // передали cyclicBarrier при созданиии он щелкает там первые 5 раз
            }

            for (int i = 0; i < cars.length; i++) {
                new Thread(cars[i]).start();
            }
            try {
                cyclicBarrier.await(); // не понятно как это работает. Нашел шпаргалку в интернете путем проб и кучи ошибок получилось расставить правильно. Но все равно не могу понять почему так работает, ведь мы передали в каждую машину этот обьект!
                //тут второй раз подождал
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
                cyclicBarrier.await();// тут 4 раз
                cyclicBarrier.await();// тут 5 раз
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
//после 6 чека он выводит это сообщение
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        }


}
