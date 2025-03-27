package controller;

import java.util.concurrent.Semaphore;

public class ThreadCorrida extends Thread {

	private static int[][] ranking = new int[25][2];
	private static int ordemChegada;

	private Semaphore semaphore;
	private int id;

	private int pontuacao;

	public ThreadCorrida(int id, Semaphore semaphore) {
		this.id = id;
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		corrida();
		try {
			semaphore.acquire();
			tiro();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
			ciclismo();
			chegada();
		}
	}

	private void corrida() {
		
		int distancia = 3000;
		int distanciaPercorrida = 0;

		while (distanciaPercorrida < distancia) {
			try {
				int metros = (int) (Math.random() * 6) + 20;
				distanciaPercorrida += metros;

				if (distanciaPercorrida > 3000)
					distanciaPercorrida = 3000;

				System.out.println("O atleta " + id + " correu " + metros + " metros! Faltam "
						+ (distancia - distanciaPercorrida) + " metros");
				sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("AVISO: O atleta " + id + " finalizou a corrida de 3km");
	}

	private void ciclismo() {
		int distancia = 5000;
		int distanciaPercorrida = 0;

		while (distanciaPercorrida < distancia) {

			try {
				int metros = (int) (Math.random() * 11) + 30;
				distanciaPercorrida += metros;

				if (distanciaPercorrida > 5000)
					distanciaPercorrida = 5000;

				System.out.println("O atleta " + id + " pedalou " + metros + " metros! Faltam "
						+ (distancia - distanciaPercorrida) + " metros");
				sleep(40);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

		System.out.println("AVISO: O atleta " + id + " pedalou os 5km inteiros");
	}

	private void tiro() {
		for (int i = 0; i < 3; i++) {
			try {
				int tempo = (int) (Math.random() * 2501) + 500;
				int pontuacaoTiro = (int) (Math.random() * 11);

				System.out.println("O atleta " + id + " deu o " + (i + 1) + "o tiro! Pontuação obtida: " + pontuacaoTiro
						+ " | Tempo gasto: " + tempo / 1000.0 + "s");

				this.pontuacao += pontuacaoTiro;
				sleep(tempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void chegada() {
		
		System.out.println("O atleta " + id + " foi o " + (++ordemChegada) + "o a chegar no fim da corrida!");
		pontuacao += 250 - (ordemChegada - 1) * 10;

		int[] dados = { this.pontuacao, this.id };
		ranking[ordemChegada - 1] = dados;

		if (ordemChegada == 25) {
			
			System.out.println("RANKING DE PONTUAÇÃO:");
			for (int i = 0; i < 24; i++) {
				for (int j = i; j < 25; j++) {
					if (ranking[i][0] < ranking[j][0]) {
						int[] temp = ranking[i];
						ranking[i] = ranking[j];
						ranking[j] = temp;
					}
				}
			}
			
			System.out.println("POSIÇÃO | ATLETA | PONTUAÇÃO");
			for (int i = 0; i < 25; i++) {
					System.out.println((i + 1) + "        " + ((i < 9) ? " " : "") + ranking[i][1] + "       " +(ranking[i][1] < 10 ? " " : "") + ranking[i][0]);
			}
			
		}
	}

}
