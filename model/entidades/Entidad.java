package model.entidades;

import model.utilidades.Hitbox;
import model.utilidades.Posicion;

/**
 * Las clases descendientes de Entidades son todas aquellas que pueden generar colisiones entre si.
 * Es extendida por Proyectiles y Felix. 
 * @author Edelmiro
 *
 */

public abstract class Entidad {
	protected Hitbox hitbox;
	protected Posicion posicion;

	//ABSTRACT
	public abstract String getNombreImagen();	
	//GETTERS Y SETTERS
	public Hitbox getHitbox() {
		return hitbox;
	}
	public Posicion getPosicion() {
		Posicion pos = new Posicion(posicion);
		return pos;
	}
	/**
	 * Se sobrecargó el setter de posicion para poder cargarlo con sus coordenadas además de con una Posicion.
	 */
	public void setPosicion(Posicion pos) {
		posicion.copiar(pos);
	}

}
