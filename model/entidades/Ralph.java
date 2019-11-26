package model.entidades;

import model.Model;
import model.utilidades.Posicion;

import java.util.Random;
import java.util.TreeSet;

import controler.Constantes;

/**
 * Ralph se ubica en la parte superior de la seccion (arriba de las 3 filas de
 * ventanas). Puede estar en uno de los siguientes estados: <i>PARADO</i>,
 * <i>MOVIENDO</i>, <i>GOLPEANDO</i><br>
 * Cuando esta parado tiene una chance de elegir una posicion aleatoria (Sobre
 * le mismo eje horizontal), y moverse hacia ella, o golpear y crear ladrillos.
 * 
 */
public class Ralph {
	static private Ralph instancia;
	private int cantLadrillos;
	private TreeSet<Integer> frameTirar;
	private Posicion posicion;
	private EstadoRalph estado;
	private int posicionDestino;
	private Random randomizador = new Random();

	// CONSTRUCTOR
	private Ralph() {
		posicion = new Posicion(Constantes.RALPHSTART);
		cantLadrillos = Constantes.CANTLADRILLOS;
		frameTirar = new TreeSet<Integer>();
		posicionDestino = Constantes.RALPHSTART.getCoordenadaX();
		estado = new EstadoRalph();
	}

	// PUBLIC
	public static void iniciarRalph() {
		if (instancia == null) {
			instancia = new Ralph();
		}
	}
	
	public static Ralph getRalph() {
		return instancia;
	}
	
	public void actualizar() {
		switch (estado.accion) {
		case MOVIENDO:
			mover();
			break;
		case GOLPEANDO:
			golpear();
			break;
		case PARADO:
			siguienteAccion();
			break;
		}
	}
	public void reiniciar() {
		posicion = new Posicion(Constantes.RALPHSTART);
		posicionDestino = Constantes.RALPHSTART.getCoordenadaX();
		estado.setAccion(AccionRalph.PARADO);
		frameTirar = new TreeSet<Integer>();
	}
	public void reiniciar(int cantLadrillos) {
		reiniciar();
		this.cantLadrillos = cantLadrillos;
	}
	

	public Posicion getPosicion() {
		return posicion;
	}
	public String getNombreImagen() {
		String nombre = "ralph";
		switch (estado.accion) {
		case PARADO:
			nombre += "ralphParado";
			break;
		case MOVIENDO:
			nombre += "Moviendo";
			if (estado.frame < -30)
				nombre += "Left0";
			else if (estado.frame < 0)
				nombre += "Left1";
			else if (estado.frame < 30)
				nombre += "Right0";
			else
				nombre += "Right1";
			break;
		case GOLPEANDO:
			nombre += "Golpeando";
			int temp = estado.frame % 60;
			if ((temp > 0) && (temp < 15))
				nombre += "0";
			else if ((temp >= 15) && (temp < 30))
				nombre += "1";
			else if ((temp >= 30) && (temp < 45))
				nombre += "2";
			else
				nombre += "3";
			break;
		}
		return nombre;
	}

	// PRIVATE
	private void siguienteAccion() {
		int rand = randomizador.nextInt(101);
		if (rand < Constantes.CHANCEMOVER) {
			posicionDestino = randomizador.nextInt(Constantes.ANCHOSECCION - Constantes.RALPHANCHO);
			estado.setAccion(AccionRalph.MOVIENDO);
		} else if ((cantLadrillos > 0)
				&& (rand < (Constantes.CHANCEMOVER + Model.getModel().getDificultadActual().CHANCEGOLPEAR))) {
			estado.setAccion(AccionRalph.GOLPEANDO);
			frameTirar.add(randomizador.nextInt(180));
			int temp, i = 0;
			// elije 3 frames de la animacion para tirar ladrillos
			do {
				temp = randomizador.nextInt(180);
				if (!frameTirar.contains(temp)) {
					frameTirar.add(temp);
					i++;
				}
			} while (i < 3);
		}
	}
	private void mover() {
		if ((posicion.getCoordenadaX() >= (posicionDestino - Constantes.VELOCIDADRALPH / 2))
				&& (posicion.getCoordenadaX() <= (posicionDestino + Constantes.VELOCIDADRALPH / 2))) {
			estado.setAccion(AccionRalph.PARADO);
		} else {
			if (posicionDestino < posicion.getCoordenadaX()) {
				posicion.addX(-Constantes.VELOCIDADRALPH);
				estado.frame--;
				if (estado.frame < -60)
					estado.frame = 0;
			} else {
				posicion.addX(Constantes.VELOCIDADRALPH);
				estado.frame++;
				if (estado.frame > 60)
					estado.frame = 0;
			}
		}
	}
	private void golpear() {
		if (estado.frame == 180) {
			estado.setAccion(AccionRalph.PARADO);
		} else if (frameTirar.contains(estado.frame)) {
			// randomiza una posicion X para el ladrillo
			int rand = randomizador.nextInt(Constantes.RALPHANCHO + Constantes.LADRILLOHITBOX.getAncho());
			int ladrilloX = rand + posicion.getCoordenadaX() - (Constantes.LADRILLOHITBOX.getAncho()/2);
			Model.getModel().agregarLadrillo(new Posicion(ladrilloX, 0));
			frameTirar.remove(estado.frame);
		}
		estado.frame++;
	}

}

class EstadoRalph {
	AccionRalph accion;
	int frame;

	EstadoRalph() {
		accion = AccionRalph.PARADO;
		frame = 0;
	}
	void setAccion(AccionRalph accion) {
		this.accion = accion;
		frame = 0;

	}
}

enum AccionRalph {
	PARADO, MOVIENDO, GOLPEANDO;
}
