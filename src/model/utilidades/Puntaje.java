package model.utilidades;
import org.apache.commons.lang3.StringUtils;
/**
 * Objeto que lleva el conteo del el puntaje obtenido en una partida.
 */

public class Puntaje {
	private int puntaje;
	private String nombre;

	public Puntaje(int puntaje, String nombre) {
		this.puntaje = puntaje;
		this.nombre = nombre;
	}
	
	public int getPuntaje() {
		return puntaje;
	}

	public void sumar(int puntaje) {
		this.puntaje += puntaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString() {
		return StringUtils.truncate(StringUtils.rightPad(nombre, 12, " ·"), 12) + StringUtils.leftPad(String.format("%d", puntaje), 40, " ·");
	}

}
