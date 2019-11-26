package model.entidades;
import java.util.Random;

import controler.Constantes;
import model.edificio.Edificio;
import model.utilidades.*;
/**
 * La clase Pastel describe los pasteles.
 * 
 * @author Edelmiro
 * @see #esAlcanzado()
 */

public class Pastel {
	private Posicion posicion;
	private EstadoPastel estado;
	private Random randomizador;

	//CONSTRUCTOR
	/**
	 * Para instanciar un pastel se pasa la posicion por parámetro.
	 */
	public Pastel() {
		posicion = new Posicion();
		estado = new EstadoPastel();
		randomizador = new Random();
	}
	
	//PUBLIC
	public void actualizar() {
		switch (estado.accion) {
		case ADENTRO:
			if ((estado.frame > Constantes.DELAYPASTEL) && (!Felix.getFelix().esInmune())) {
				estado.setAccion(AccionPastel.SALIENDO);
			} else estado.frame++;
			break;
		case SALIENDO:
			//El constructor default de posicion es -1,-1;
			if (posicion.getCoordenadaX() != -1) {
				estado.frame++;
			} else {
				Posicion pos = Edificio.getEdificio().abierta();
				if (pos.getCoordenadaX() != -1) {
					posicion = pos;
					if (randomizador.nextInt(101) < Constantes.CHANCEPASTEL);
				}
			}
			break;
		case AFUERA:
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
		if (Felix.getFelix().getPosicion().aVentana() == posicion.aVentana()){

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
