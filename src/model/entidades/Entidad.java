package model.entidades;
import model.utilidades.*;

/**
 * Las clases descendientes de Entidades son todas aquellas que pueden generar colisiones entre si.
 * Es extendida por Proyectiles y Felix. 
 * @author Edelmiro
 *
 */

public abstract class Entidad {
	protected Hitbox hitbox;
	protected Posicion posicion;
	public Hitbox getHitbox() {
		return hitbox;
	}
	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}
	public Posicion getPosicion() {
		Posicion pos = new Posicion(posicion);
		return pos;
	}
	
	/**
	 * Se sobrecargó el setter de posicion para poder cargarlo con sus coordenadas además de con una Posicion.
	 */
	public void setPosicion(int x, int y) {
		posicion.setCoordenadaX(x);
		posicion.setCoordenadaY(y);
	}
	public void setPosicion(Posicion pos) {
		posicion.copiar(pos);
	}
}
