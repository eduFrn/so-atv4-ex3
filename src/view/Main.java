package view;

import java.util.concurrent.Semaphore;

import controller.ThreadCorrida;

public class Main {

	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(5);

		for (int i = 0; i < 25; i++) {
			Thread atleta = new ThreadCorrida(i + 1, semaphore);
			atleta.start();
		}

	}

}
