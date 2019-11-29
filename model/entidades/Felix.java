package model.entidades;

import controler.Constantes;
import model.edificio.Edificio;
import model.utilidades.ChoqueLadrilloException;
import model.utilidades.ChoquePajaroException;
import model.utilidades.Direccion;
import model.utilidades.FinDeJuegoException;
import model.utilidades.FinDeSeccionException;
import model.utilidades.Hitbox;
import model.utilidades.Posicion;


/**
 * La clase Felix es un singleton para que las demás clases se puedan comunicar
 * más facilmente.
 * 
 * @author Edelmiro
 *
 */
public class Felix extends Entidad {
	static private Felix instancia;
	private int vidas;
	private boolean inmune;
	private int tiempoInmune;
	private EstadoFelix estado;
	private boolean ocupado;
	private Posicion destino;
	private boolean reiniciarNivel;

	// CONSTRUCTOR
	/**
	 * Al crearse Felix busca su posicion y hitbox desde la clase Config
	 */
	private Felix() {
		vidas = Constantes.VIDAS;
		posicion = new Posicion(Constantes.FELIXSTART);
		destino = new Posicion(Constantes.FELIXSTART);
		hitbox = new Hitbox(Constantes.FELIXHITBOX);
		estado = new EstadoFelix();
		inmune = false;
		tiempoInmune = 0;
		ocupado = false;
		reiniciarNivel = false;

	}

	// PUBLIC
	public static void iniciarFelix() {
			instancia = new Felix();
	}
	public static Felix getFelix() {
		return instancia;
	}
	public void actualizar() throws FinDeSeccionException, ChoqueLadrilloException, ChoquePajaroException, FinDeJuegoException{
		if (inmune) {
			if (tiempoInmune == 0) inmune = false;
			else tiempoInmune--;
		}
		switch (estado.accion) {
		case PARADO:
			break;
		case MOVIENDO:
			moviendo();
			break;
		case REPARANDO:
			reparando();
			break;
		case MURIENDO:
			muriendo();
			break;
		case COMIENDO:
			comiendo();
			break;
		default:
			break;
		}
	}
	/**
	 * Este método llama a reparar del edificio pasandole la ventana sobre la cual
	 * está parado Felix.
	 * 
	 * @throws FinDeSeccionException
	 * @throws FinDeNivelException
	 */
	public void reparar() {
		if (!ocupado) {
			estado.setAccion(AccionFelix.REPARANDO);
			ocupado = true;
		}
	}
	public void mover(Direccion direccion) {
		if (!ocupado) {
			destino = Edificio.getEdificio().avanzar(posicion.aVentana(), direccion);
			if ((Edificio.getEdificio().getSeccionActual() == 0) &&
				(destino.getCoordenadaX() == (Constantes.ANCHOVENTANA * 2))	&&
				(destino.getCoordenadaY() == (Constantes.ALTURAVENTANA * 2))) {
				destino.addY(Constantes.FELIXOFFSETYPUERTA);
			} else {
				destino.addY(Constantes.FELIXOFFSETY);
			}
			destino.addX(Constantes.FELIXOFFSETX);
			estado.setAccion(AccionFelix.MOVIENDO);
//			ocupado = true;
		}
	}
	
 	public void colicion(boolean reiniciarNivel){
		estado.setAccion(AccionFelix.MURIENDO);
		inmune = true;
		tiempoInmune = 999;
		this.reiniciarNivel = reiniciarNivel;
	}
	public void comer() {
		estado.setAccion(AccionFelix.COMIENDO);
	}
	
	// PRIVATE
	private void moviendo() {
		switch (estado.frame) {
		case 0:
			if (posicion.getCoordenadaX() < destino.getCoordenadaX()) {
				hitbox.setAncho((destino.getCoordenadaX() + Constantes.FELIXHITBOX.getAncho()) - posicion.getCoordenadaX());
			}
			if (posicion.getCoordenadaY() < destino.getCoordenadaY()) {
				hitbox.setAlto((destino.getCoordenadaY() + Constantes.FELIXHITBOX.getAlto()) - posicion.getCoordenadaY());
			}
			estado.frame++;
			break;
		case 1: 
			if (posicion.getCoordenadaX() > destino.getCoordenadaX()) {
				hitbox.setAncho((posicion.getCoordenadaX() + Constantes.FELIXHITBOX.getAncho()) - destino.getCoordenadaX());
				posicion = destino;
			} else if (posicion.getCoordenadaY() > destino.getCoordenadaY()) {
				hitbox.setAlto((posicion.getCoordenadaY() + Constantes.FELIXHITBOX.getAlto()) - destino.getCoordenadaY());
				posicion = destino;
			} else {
				hitbox = new Hitbox(Constantes.FELIXHITBOX);
				posicion = destino;
				estado.frame++;
			}
			break;
		case 2: 
			hitbox = new Hitbox(Constantes.FELIXHITBOX);
			estado.frame++;
			break;

		case 5:	
			estado.frame++;
			estado.setAccion(AccionFelix.PARADO);
//			ocupado = false;
			break;
		default:
			estado.frame++;
			break;
		}
	}
	private void reparando() throws FinDeSeccionException{
		if (estado.frame == 28) {
			Edificio.getEdificio().reparar(posicion.aVentana());
			estado.setAccion(AccionFelix.PARADO);
			ocupado = false;
		} else estado.frame++;
	}
	private void muriendo() throws ChoqueLadrilloException, ChoquePajaroException, FinDeJuegoException {
		switch (estado.frame) {
		case 0:
			posicion = destino;
			ocupado = true;
			estado.frame++;
			break;
		case 60:
			estado.setAccion(AccionFelix.PARADO);
			ocupado = false;
			inmune = false;
			tiempoInmune = 0;
			if (--vidas == 0) throw new FinDeJuegoException();
			if (reiniciarNivel == true) throw new ChoqueLadrilloException();
			else throw new ChoquePajaroException();
		default:
			estado.frame++;
		}
	}
	private void comiendo() {
		switch(estado.frame) {
		case 0:
			ocupado = true;
			estado.frame++;
			break;
		case 30:
			inmune = true;
			tiempoInmune = Constantes.TIEMPOINMUNE;
			ocupado = false;
			estado.setAccion(AccionFelix.PARADO);
			break;
		default:
			estado.frame++;
			break;
		
		}
	}

	// GETTERS Y SETTERS
	public int getVidas() {
		return vidas;
	}
	@Override
	public String getNombreImagen() {
		String nombre = "felix";
		int frame = estado.frame;
		switch (estado.accion){
		case PARADO:
			nombre += "Parado";
			if (inmune) nombre += "I";
			break;
		case MOVIENDO:
			nombre += "Moviendo";
			if (inmune) nombre += "I";
			break;
		case REPARANDO:
			if (frame < 7) nombre += "Reparando0";
			else if ((frame >= 7) && (frame < 14)) nombre += "Reparando1";
			else if ((frame >= 14) && (frame < 21)) nombre += "Reparando2";
			else nombre += "Reparando3";
			if (inmune) nombre += "I";
			break;
		case MURIENDO:
			if (frame < 12) nombre += "Muriendo0";
			else if ((frame >= 12) && (frame < 24)) nombre += "Muriendo1";
			else if ((frame >= 24) && (frame < 36)) nombre += "Muriendo2";
			else if ((frame >= 36) && (frame < 48)) nombre += "Muriendo3";
			else nombre += "Muriendo4";
			break;
		case COMIENDO:
			if (frame < 7) nombre += "Comiendo0";
			else if ((frame >= 7) && (frame < 14)) nombre += "Comiendo1";
			else if ((frame >= 14) && (frame < 21)) nombre += "Comiendo2";
			else nombre += "Comiendo3";
			break;
		}
		
		return nombre;
	}
	public boolean esInmune() {
		return inmune;
	}
	public boolean estaOcupado() {
		return ocupado;
	}
	
}

class EstadoFelix {
	AccionFelix accion;
	int frame;

	EstadoFelix() {
		accion = AccionFelix.PARADO;
		frame = 0;
	}

	void setAccion(AccionFelix accion) {
		this.accion = accion;
		frame = 0;
	}
}

enum AccionFelix {
	PARADO, MOVIENDO, REPARANDO, COMIENDO, MURIENDO;
}
