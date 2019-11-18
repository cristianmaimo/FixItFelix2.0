package model.utilidades;

/**
 * Esta simple clase representa el espacio ocupado por entidades para chequear si hay colición entre ellas.
 * @author Edelmiro
 *
 */

public class Hitbox {
	private int alto;
	private int ancho;
	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	public int getAncho() {
		return ancho;
	}
	public void setAncho(int largo) {
		this.ancho = largo;
	}	
	public Hitbox (int x, int y) {
		ancho = x;
		alto = y;
	}
}
