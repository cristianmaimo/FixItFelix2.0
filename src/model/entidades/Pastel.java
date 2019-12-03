package model.entidades;

import controler.Constantes;
import model.edificio.Edificio;
import model.utilidades.Posicion;

/**
 * La clase Pastel describe los pasteles.
 * 
 * @author Edelmiro
 * @see #esAlcanzado()
 */

public class Pastel {
	private Posicion posicion;
	private EstadoPastel estado;

	//CONSTRUCTOR
	/**
	 * Para instanciar un pastel se pasa la posicion por parámetro.
	 */
	public Pastel() {
		posicion = new Posicion();
		estado = new EstadoPastel();
	}
	
	//PUBLIC
	public void actualizar() {
		switch (estado.accion) {
		case ADENTRO:
			if (estado.frame > Constantes.DELAYPASTEL) {
				estado.setAccion(AccionPastel.SALIENDO);
			} else if (!Felix.getFelix().esInmune()) estado.frame++;
			break;
		case SALIENDO:
			//El constructor default de posicion es -100,-100;
			if (posicion.getCoordenadaX() == -100) {
				posicion = Edificio.getEdificio().abierta();
			} else {
				if (estado.frame < 240)	estado.frame++;
				else estado.setAccion(AccionPastel.AFUERA);
			}
			break;
		case AFUERA:
			if (estado.frame++ == 60) estado.frame = 0;
			if (esAlcanzado()) {
				Felix.getFelix().comer();
				posicion = new Posicion();
				estado.setAccion(AccionPastel.ADENTRO);
			}
			break;
		}
	}
	
	/**
	 * El método esAlcanzado chequea si Felix está en la misma ventana que el pastel y si es así setea la inmunidad de felix y retorna true; caso contrario retorna false; 
	 */
	private boolean esAlcanzado() {
		if (Felix.getFelix().getPosicion().aVentana().equals(posicion)){
			return true;
		}
		return false;
	}
	
	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion.copiar(posicion);
	}
	
	public String getNombreImagen() {
		String nombreImagen = "";
		switch (estado.accion) {
		case ADENTRO:
			nombreImagen += "placeholder";
			break;
		case SALIENDO:
			if (estado.frame < 120) nombreImagen = "nicelander0";
			else nombreImagen += "nicelander1";
			break;
		case AFUERA:
			if (estado.frame < 30) nombreImagen = "pastel0";
			else nombreImagen += "pastel1";
			break;
		}
		return nombreImagen;
	}
}

class EstadoPastel {
	AccionPastel accion;
	int frame;

	EstadoPastel() {
		accion = AccionPastel.ADENTRO;
		frame = 0;
	}

	void setAccion(AccionPastel accion) {
		this.accion = accion;
		frame = 0;
	}
}

enum AccionPastel {
	ADENTRO, SALIENDO, AFUERA;
}
