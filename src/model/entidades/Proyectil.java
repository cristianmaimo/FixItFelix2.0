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
	protected int velocidad;

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	
	public abstract void mover();
	public abstract void actualizar() throws ChoquePajaroException, ChoqueLadrilloException;
	
	/**
	 * El método hayColicion chequea si Felix es inmune, de ser así retorna false, si no es inmune devuelve true si la hitbox del proyectil 
	 * se superpone con la de Felix, y false en caso contrario.
	 */
	public boolean hayColicion() {
		if (!Felix.getFelix().isInmune()) {	
			if (
				(posicion.getCoordenadaY() <= (Felix.getFelix().getPosicionHit().getCoordenadaY() + Felix.getFelix().getHitbox().getAlto()) ) && 
			    ((posicion.getCoordenadaY() + hitbox.getAlto())  >=  Felix.getFelix().getPosicionHit().getCoordenadaY()) && 
			    (posicion.getCoordenadaX() <= (Felix.getFelix().getPosicionHit().getCoordenadaX() + Felix.getFelix().getHitbox().getAncho())) && 
			    ((posicion.getCoordenadaX() + hitbox.getAncho()) >=  Felix.getFelix().getPosicionHit().getCoordenadaX())
				)
				return true;
		}
		return false;
	}
}
