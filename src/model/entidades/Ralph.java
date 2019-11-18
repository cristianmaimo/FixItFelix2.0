package model.entidades;
import model.Model;
import model.utilidades.*;

import java.util.Random;

import controler.Constantes;

/**
 * Ralph se ubica en la parte superior de la seccion (arriba de las 3 filas de ventanas).
 * Puede estar en uno de los siguientes estados: <i>PARADO</i>, <i>MOVIENDO</i>, <i>GOLPEANDO</i><br>
 * Cuando esta parado tiene una chance de elegir una posicion aleatoria (Sobre le mismo eje horizontal),
 * y moverse hacia ella, o golpear y crear ladrillos.
 * 
 */public class Ralph {
		static private Ralph instancia;
		private int cantLadrillos;
		private int cantTirar;
		private Posicion posicion;
		private AccionRalph accion;
		private int posicionDestino;
		private long ultimoLadrillo;
		Random randomizador = new Random();
		
		static public Ralph getRalph() {
			if (instancia == null)
				instancia = new Ralph();
			return instancia;
		}
		private Ralph() {
			posicion = new Posicion (Constantes.RALPHSTART.getCoordenadaX(), Constantes.RALPHSTART.getCoordenadaY());
			cantLadrillos = Constantes.CANTLADRILLOS;
			cantTirar = 0;
			posicionDestino = (int) Constantes.ANCHOSECCION / 2;
			ultimoLadrillo = 0;
			accion = AccionRalph.MOVIENDO;
		}
		
		public void actualizar() {
			switch (accion) {
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
			posicionDestino = (int) Constantes.ANCHOSECCION / 2;
			accion = AccionRalph.MOVIENDO;
		}
		public void reiniciar(int cantLadrillos) {
			posicionDestino = (int) Constantes.ANCHOSECCION / 2;
			accion = AccionRalph.MOVIENDO;
			this.cantLadrillos = cantLadrillos;
		}

		private void mover() {
			if ((posicion.getCoordenadaX() >= (posicionDestino - Constantes.VELOCIDADRALPH / 2)) &&  (posicion.getCoordenadaX() <= (posicionDestino + Constantes.VELOCIDADRALPH / 2))){
				accion = AccionRalph.PARADO;
			} else {
				if (posicionDestino < posicion.getCoordenadaX()) {
				posicion.addX(-Constantes.VELOCIDADRALPH);
				} else {
					posicion.addX(Constantes.VELOCIDADRALPH);
				}
			}
		}
//		private void golpear(){
//			if (((System.currentTimeMillis() - empiezaGolpear) < Config.TIEMPOGOLPEANDO)) {
//				if ((cantLadrillos > 0) && (randomizador.nextInt(101) < Admin.getAdmin().dificultadActual().PROBABILIDADLADRILLOS)) {
//					Admin.getAdmin().agregarLadrillo(new Posicion(posicion.getCoordenadaX() - Config.ANCHOVENTANA + randomizador.nextInt(Config.ANCHOVENTANA * 2),
//																  posicion.getCoordenadaY()));
//					cantLadrillos -= 1;
//					System.out.println(" Ralph tira un ladrillo ");
//				}
//			}
//			else {
//				accion = Accion.PARADO;
//			}
//		}
		private void golpear() {
			if (cantTirar <= 0) {
				accion = AccionRalph.PARADO;
			} else if ((System.currentTimeMillis() - ultimoLadrillo) > 1000) {
				Model.getModel().agregarLadrillo(new Posicion(posicion.getCoordenadaX() - Constantes.ANCHOVENTANA + randomizador.nextInt(Constantes.ANCHOVENTANA * 2), posicion.getCoordenadaY()));
				cantTirar --;
				System.out.println(" Ralph tira un ladrillo ");
				ultimoLadrillo = System.currentTimeMillis();
			}
		}
		private void siguienteAccion() {
			int rand = randomizador.nextInt(101);
			if (rand < Constantes.CHANCEMOVER) {
				posicionDestino = Constantes.RALPHANCHO/2 + randomizador.nextInt((int)(Constantes.ANCHOSECCION - Constantes.RALPHANCHO * 1.5));
				accion = AccionRalph.MOVIENDO;
			}
			else if ((cantLadrillos > 0) && (rand < (Constantes.CHANCEMOVER + Constantes.CHANCEGOLPEAR))) {
				accion = AccionRalph.GOLPEANDO;
				cantTirar = 3;
				cantLadrillos -= 3;
				ultimoLadrillo = System.currentTimeMillis();
			}
		}

		public void setCantLadrillos(int cantLadrillos) {
			this.cantLadrillos = cantLadrillos;
		}
		public AccionRalph getAccion() {
			return accion;
		}
		public Posicion getPosicion() {
			return this.posicion;
		}
		public int getPosicionDestino() {
			return posicionDestino;
		}
	}
