package code;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.DoubleAdder;

public class Marcador {
	
	private DoubleAdder[] marcador = new DoubleAdder[6];
	private DoubleAdder total = new DoubleAdder();
	
	public Marcador(int n) {
		final MyThreadFactory threadFactory = new MyThreadFactory("MyThreadFactory");
		ArrayList<Thread> threads = new ArrayList<>();
		
		for(int i=0; i< marcador.length; i++) {
			marcador[i] = new DoubleAdder();
		}
		
		for(int i = 0; i < n; i++) {
			Thread thread = threadFactory.newThread(new Dice(this));
			threads.add(thread);
			thread.start();
		}
		
		for (Thread thread : threads) {
            try {
                thread.join();
            }
            catch (InterruptedException exception) {
                return;
            }
		}
		
		System.out.print("Número 1: " + marcador[0] +
						 "\nNúmero 2: " + marcador[1] +
						 "\nNúmero 3: " + marcador[2] +
						 "\nNúmero 4: " + marcador[3] +
						 "\nNúmero 5: " + marcador[4] +
						 "\nNúmero 6: " + marcador[5]);
		
		System.out.printf("\n\nTotal de veces: %s + %s + %s + %s + %s + %s = %s", marcador[0].toString(), marcador[1].toString(), marcador[2].toString(), marcador[3].toString(), marcador[4].toString(), marcador[5].toString(), total.toString());
	}
	
	public void increment() {
		int random = ThreadLocalRandom.current().nextInt(1,7);
		marcador[random-1].add(1);
		total.add(1);
	}

}
