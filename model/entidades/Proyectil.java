package model.entidades;

import model.utilidades.ChoqueLadrilloException;
import model.utilidades.ChoquePajaroException;

/**
 * La clase abstracta Proyectil es extendida por aquellas clases que pueden colicionar con felix y provocar la pérdida de una vida; las clases Ladrillo y Pajaro.
 * Incluye métodos abstractos para moverse y actualizar su estado.
 * @author Edelmiro
 *
 */
public abstract class Proyectil extends Entidad {
	int velocidad;
	int indexAnim;
	
	//ABSTRACT
	public abstract void mover();
	public abstract void actualizar() throws ChoquePajaroException, ChoqueLadrilloException;
	public abstract String getNombreImagen();
	
	//PUBLIC
	/**
	 * El método hayColicion chequea si Felix es inmune, de ser así retorna false, si no es inmune devuelve true si la hitbox del proyectil 
	 * se superpone con la de Felix, y false en caso contrario.
	 */
	public boolean hayColicion() {
		if (!Felix.getFelix().esInmune()) {	
			if (
				(posicion.getCoordenadaY() <= (Felix.getFelix().getPosicion().getCoordenadaY() + Felix.getFelix().getHitbox().getAlto()) ) && 
			    ((posicion.getCoordenadaY() + hitbox.getAlto())  >=  Felix.getFelix().getPosicion().getCoordenadaY()) && 
			    (posicion.getCoordenadaX() <= (Felix.getFelix().getPosicion().getCoordenadaX() + Felix.getFelix().getHitbox().getAncho())) && 
			    ((posicion.getCoordenadaX() + hitbox.getAncho()) >=  Felix.getFelix().getPosicion().getCoordenadaX())
				)
				return true;
		}
		return false;
	}

	//GETTERS Y SETTERS
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
}
