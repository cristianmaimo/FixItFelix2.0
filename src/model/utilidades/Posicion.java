package model.utilidades;

import controler.Constantes;

/**
 * Esta clase sirve para representar y comparar las posiciones de todos los ojetos.
 *  
 * @author Edelmiro
 *
 */

public class Posicion {
	int coordenadaX;
	int coordenadaY;
	
	public Posicion () {
		coordenadaX = 0;
		coordenadaY = 0;
	}

	public Posicion (Posicion pos) {
		if (pos != null){
			this.coordenadaX = pos.coordenadaX;
			this.coordenadaY = pos.coordenadaY;
		}
		else {
			coordenadaX = 0;
			coordenadaY = 0;
		}
	}

	public Posicion(int coordenadaX, int coordenadaY) {
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
	}

	public void avanzar(Direccion direccion) {
		switch (direccion) {
		case IZQUIERDA:
			coordenadaX -= Constantes.ANCHOVENTANA;
			break;
		case DERECHA:
			coordenadaX += Constantes.ANCHOVENTANA;
			break;
		case ARRIBA:
			coordenadaY -= Constantes.ALTURAVENTANA;
			break;
		case ABAJO:
			coordenadaY += Constantes.ALTURAVENTANA;
			break;
		}
	}
	
	/**
	 * Este m�todo retorna true si la posici�n est� dentro del cuadrado formado por los dos v�rtices pasados como par�metros. 
	 */
	public boolean dentroDe(Posicion a, Posicion b) {
		if (coordenadaX > a.coordenadaX && coordenadaY > a.coordenadaY && coordenadaX < b.coordenadaX && coordenadaY < b.coordenadaY) {
			return true;
		}
		return false;
	}
	
	/**
	 * Este m�todo retorna la posici�n de la ventana sobre la cual est� ubicada la posicion.
	 */
	public Posicion aVentana() {
		return new Posicion(coordenadaX - coordenadaX % Constantes.ANCHOVENTANA, coordenadaY - coordenadaY % Constantes.ALTURAVENTANA);
	}
	
	/**
	 * Un m�todo para sumar una posici�n a otra.
	 */
	public void add(Posicion pos) {
		
		coordenadaX += pos.getCoordenadaX();
		coordenadaY += pos.getCoordenadaY();
	}
	
	/**
	 * Un m�todo para sumar sobre la coordenadaX.
	 */
	public void addX(int x) {
		coordenadaX += x;
	}
	
	/**
	 * Un m�todo para sumar sobre la coordenadaY.
	 */
	public void addY(int y) {
		coordenadaY += y;
	}
	
	/**
	 * Un m�todo para copiar la posicion pasada por pr�metro.
	 */
	public void copiar(Posicion pos) {
		if (pos != null){
			this.coordenadaX = pos.getCoordenadaX();
			this.coordenadaY = pos.getCoordenadaY();
		}
	}

	public int getCoordenadaX() {
		return coordenadaX;
	}
	public void setCoordenadaX(int coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	public int getCoordenadaY() {
		return coordenadaY;
	}
	public void setCoordenadaY(int coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	
	/**
	 * Se sobreescribe el m�todo equals para poder comparar correctamente posiciones.
	 */
	public boolean equals(Object obj) {
		// Si ambos tienen la misma referencia.
		if (this == obj)
			return true;
		// compara el tipo de objeto.
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		// Casteo para poder comparar valores.
		Posicion pos = (Posicion) obj;
		// Comparacion de valores
		if ((this.coordenadaX == pos.getCoordenadaX()) && (this.coordenadaY == pos.getCoordenadaY()))
			return true;
		else
			return false;
	}
	
	public int hashCode() {
		// Devuelve la concatenacion de ambas coordenadas dividido el ancho/alto, facilita el debugg.
		if (coordenadaY<0) coordenadaY = 0;
		String concat = Integer.toString(coordenadaX/Constantes.ANCHOVENTANA) + Integer.toString(coordenadaY/Constantes.ALTURAVENTANA);
		return Integer.parseInt(concat);
	}
	
	/**
	 * Devuelve la concatenacion de ambas coordenadas
	 */
	public String toString() {
		String concat = Integer.toString(coordenadaX) + Integer.toString(coordenadaY);
		return concat;
	}
}
