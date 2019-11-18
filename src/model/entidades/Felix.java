package model.entidades;
import controler.Constantes;
import model.edificio.*;
import model.utilidades.*;
/**
 * La clase Felix es un singleton para que las demás clases se puedan comunicar más facilmente.
 * 
 * @author Edelmiro
 *
 */
public class Felix extends Entidad {
	static private Felix instancia;
	private int vidas;
	private boolean inmune;
	private long tiempoInmune;
	private Posicion posicionHit;
	private AccionFelix accion;
	private int ocupado;
	
	public int getOcupado() {
		return ocupado;
	}

	public void setOcupado(int ocupado) {
		this.ocupado = ocupado;
	}

	public AccionFelix getAccion() {
		return accion;
	}

	public void setAccion(AccionFelix accion) {
		this.accion = accion;
	}

	/**
	 * Al crearse Felix busca su posicion y hitbox desde la clase Config
	 */
	private Felix() {
		vidas = Constantes.VIDAS;
		posicion = new Posicion(Constantes.FELIXSTART);
		posicionHit = new Posicion(Constantes.FELIXSTART);
		hitbox = new Hitbox(Constantes.FELIXHITBOX.getAncho(), Constantes.FELIXHITBOX.getAlto());
		accion = AccionFelix.PARADO;
		inmune = false;
		tiempoInmune = 0;
		ocupado = 0;
	}
	
	public int getVidas() {
		return vidas;
	}
	public void setVidas(int vidas) {
		this.vidas = vidas;
	}
	
	/**
	 * Este método retorna la posicion de la ventana sobre la cual está parado felix.
	 */
	public Posicion getVentana() {
		return posicion.aVentana();
	}
	public boolean isInmune() {
		return inmune;
	}
	public void setInmune(boolean inmune) {
		this.inmune = inmune;
	}
	public long getTiempoInmune() {
		return tiempoInmune;
	}
	public void setTiempoInmune(long tiempoInmune) {
		this.tiempoInmune = tiempoInmune;
	}
	
	/**
	 * Este método llama a reparar del edificio pasandole la ventana sobre la cual está parado Felix.
	 * @throws FinDeSeccionException 
	 * @throws FinDeNivelException 
	 */
	public void reparar() throws FinDeNivelException, FinDeSeccionException {
		Edificio.getEdificio().reparar(getVentana());
	}
	
	/**
	 * Al pararse sobre la puerta del edificio felix tiene una posición relativa distinta que en las demás ventanas, por lo tanto su método mover, que recibe la posición
	 * de la nueva ventana como parámetro, chequea si esta es la puerta principal y, dependiendo de eso suma el offset normal o el de puerta.
	 */
	public void mover(Posicion pos) {
		pos.add(Constantes.FELIXOFFSET);
		if (pos.getCoordenadaX() == posicion.getCoordenadaX()) {
			hitbox.setAlto(Math.abs(posicion.getCoordenadaY() - pos.getCoordenadaY()));
			if (pos.getCoordenadaY() > posicion.getCoordenadaY()) {
				posicionHit.setCoordenadaY(posicion.getCoordenadaY());
			} else {
				posicionHit.setCoordenadaY(pos.getCoordenadaY());
			}
		} else {
			hitbox.setAncho(Math.abs(posicion.getCoordenadaX() - pos.getCoordenadaX()));
			if (pos.getCoordenadaX() > posicion.getCoordenadaX()) {
				posicionHit.setCoordenadaX(posicion.getCoordenadaX());
			} else {
				posicionHit.setCoordenadaX(pos.getCoordenadaX());
			}
		}
		
		if (Edificio.getEdificio().getSeccionActual() == 0){
			if ((pos.aVentana().getCoordenadaX() == Constantes.ANCHOVENTANA*2) && (pos.aVentana().getCoordenadaY() == Constantes.ALTURAVENTANA * 2)){
				pos.addY(Constantes.FELIXOFFSETYPUERTA);
				hitbox.setAlto(hitbox.getAlto() + Constantes.FELIXOFFSETYPUERTA);
				posicion = pos;
			} else {
				posicion = pos;
			}
		} else {
			posicion = pos;
		}
	}
	public Posicion getPosicionHit() {
		return posicionHit;
	}

	public void setPosicionHit(Posicion posicionHit) {
		this.posicionHit = posicionHit;
	}

	public void perderVida() {
		this.vidas--;
	}
	static public Felix getFelix() {
		if (instancia == null)
			instancia = new Felix();
		return instancia;
	}
	public void actualizar() throws FinDeNivelException, FinDeSeccionException {
		switch (accion){
		case ARREGLANDO:
			if (ocupado == 1) {
				reparar();
				ocupado--;
			} else if (ocupado == 0) {
				accion = AccionFelix.PARADO;
			} else 
				ocupado--;
			break;
		case MOVIENDO_ABA:
			if (ocupado == 1) {
				mover((Edificio.getEdificio().nuevaPosicion(Felix.getFelix().getVentana(), Direccion.ABAJO)));
				ocupado--;
			} else {
				Felix.getFelix().setHitbox(new Hitbox(Constantes.FELIXHITBOX.getAncho(), Constantes.FELIXHITBOX.getAlto()));
				Felix.getFelix().setPosicionHit(Felix.getFelix().getPosicion());
				accion = AccionFelix.PARADO;
			}break;
		case MOVIENDO_ARR:
			if (ocupado == 1) {
				mover((Edificio.getEdificio().nuevaPosicion(Felix.getFelix().getVentana(), Direccion.ARRIBA)));
				ocupado--;
			} else {
				Felix.getFelix().setHitbox(new Hitbox(Constantes.FELIXHITBOX.getAncho(), Constantes.FELIXHITBOX.getAlto()));
				Felix.getFelix().setPosicionHit(Felix.getFelix().getPosicion());
				accion = AccionFelix.PARADO;
			} break;
		case MOVIENDO_DER:
			if (ocupado == 1) {
				mover((Edificio.getEdificio().nuevaPosicion(Felix.getFelix().getVentana(), Direccion.DERECHA)));
				ocupado--;
			} else {
				Felix.getFelix().setHitbox(new Hitbox(Constantes.FELIXHITBOX.getAncho(), Constantes.FELIXHITBOX.getAlto()));
				Felix.getFelix().setPosicionHit(Felix.getFelix().getPosicion());
				accion = AccionFelix.PARADO;
			} break;
		case MOVIENDO_IZQ:
			if (ocupado == 1) {
				mover((Edificio.getEdificio().nuevaPosicion(Felix.getFelix().getVentana(), Direccion.IZQUIERDA)));
				ocupado--;
			} else {
				Felix.getFelix().setHitbox(new Hitbox(Constantes.FELIXHITBOX.getAncho(), Constantes.FELIXHITBOX.getAlto()));
				Felix.getFelix().setPosicionHit(Felix.getFelix().getPosicion());
				accion = AccionFelix.PARADO;
			} break;
		case MURIENDO:
			break;
		case PARADO:
			break;
		default:
			break;
		}
	}
}
