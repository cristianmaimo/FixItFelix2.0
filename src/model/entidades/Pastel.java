package model.entidades;
import controler.Constantes;
import model.utilidades.*;
/**
 * La clase Pastel describe los pasteles.
 * 
 * @author Edelmiro
 * @see #esAlcanzado()
 */

public class Pastel {
	private Posicion posicion;

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion.copiar(posicion);
	}
	
	/**
	 * El método esAlcanzado chequea si Felix está en la misma ventana que el pastel y si es así setea la inmunidad de felix y retorna true; caso contrario retorna false; 
	 */
	public boolean esAlcanzado() {
		if (Felix.getFelix().getVentana()  == posicion.aVentana()){
			Felix.getFelix().setInmune(true);
			Felix.getFelix().setTiempoInmune(System.currentTimeMillis());
			System.out.println(" Pastel Comido ");
			return true;
		}
		return false;
	}
	
	/**
	 * Para instanciar un pastel se pasa la posicion por parámetro.
	 */
	public Pastel(Posicion pos) {
		posicion = new Posicion(pos.getCoordenadaX() + Constantes.PASTELOFFSETX, pos.getCoordenadaY() + Constantes.PASTELOFFSETY);
	}
}
