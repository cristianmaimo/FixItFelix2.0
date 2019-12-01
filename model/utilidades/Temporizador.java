package model.utilidades;

import controler.Constantes;

public class Temporizador {
	private int minutos;
	private int segundos;
	private long nanosegundos;
	
	public Temporizador(int segundos) {
		minutos = segundos/60;
		this.segundos = segundos % 60;
		nanosegundos = 0;
	}
	
	public void actualizar() throws FinDeJuegoException {
		nanosegundos -= Constantes.NANOSEGUNDOSPORFRAME;
		if (nanosegundos < 0) {
			if (segundos == 0) {
				if (minutos == 0) throw new FinDeJuegoException();
				else minutos--;
				segundos = 59;
				}
			else segundos--;
			nanosegundos = 1000000000 + nanosegundos;
		}		
	}
	
	public int minutosRestantes() {
		return minutos;
	}
	
	public int segundosRestantes() {
		return segundos;
	}
}
