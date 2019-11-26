package controler;

import view.View;

import java.awt.KeyboardFocusManager;

import model.Model;
import model.edificio.Edificio;
import model.entidades.Felix;
import model.utilidades.ChoqueLadrilloException;
import model.utilidades.ChoquePajaroException;
import model.utilidades.FinDeJuegoException;
import model.utilidades.FinDeNivelException;
import model.utilidades.FinDeSeccionException;

public class Controler{
	private static Controler instancia;
	private KeyboardFocusManager teclado;
	private EstadoJuego estado;
	
	private MainThread mainThread;
	private boolean iniciado;
	private int nivelInicial;

	//MAIN
	public static void main (String args[]) {
		Controler.iniciarControler();
	}
	
	//CONSTRUCTOR
	private Controler() {
		Model.iniciarModel();
		View.iniciarView();
		
		mainThread = new MainThread();
		nivelInicial = 1;
		estado = new EstadoJuego();
		teclado = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	    teclado.addKeyEventDispatcher(new KeyboardDispacher());
		
	}
	private static void iniciarControler() {
		if (instancia == null) {
			instancia = new Controler();
		}
	}
	
	//PUBLIC
	public static Controler getControler() {
		return instancia;
	}
	public void abrirConfig() {
		View.getView().config.setVisible(true);
		View.getView().mainMenu.setVisible(false);
	}
	public void abrirReglas() {
		View.getView().reglas.setVisible(true);
		View.getView().mainMenu.setVisible(false);
	}	
	public void abrirTopScores() {
		View.getView().top5.setVisible(true);
		View.getView().mainMenu.setVisible(false);
	}

	public void abrirJuego() {
		Model.getModel().setNivelInicial(nivelInicial);
		View.getView().panelJuego.setVisible(true);
		View.getView().mainMenu.setVisible(false);
		if (!iniciado) {
			mainThread.start();
			iniciado = true;
		}
		else estado.setEscena(Escena.INICIANDO);
	}
	
	public void backToMenu() {
		View.getView().mainMenu.setVisible(true);
	}

	public void reiniciar() {
		Model.iniciarModel();
		View.getView().reiniciarPanelJuego();
	}
	
	private class MainThread extends Thread{
		private volatile boolean exit = false;
		@Override
		public void run() {
			do {
				switch (estado.escena) {
				case INICIANDO:
					iniciando();
					break;
				case JUGANDO:
					jugando();
					break;
				case PASANDO:
					pasando();
					break;
				case SUBIENDO:
					subiendo();
					break;
				case TERMINANDO:
					terminando();
					break;
				case REINICIANDO:
					break;
				}
			} while (!exit);
		}

		private void iniciando() {
			estado.setEscena(Escena.JUGANDO);
		}

		private void jugando() {
			try {
				Model.getModel().actualizar();
			} catch (ChoquePajaroException e) {
				Edificio.getEdificio().reiniciarSeccion();
				View.getView().panelJuego.actualizarMarcos();
			} catch (ChoqueLadrilloException e) {
				Model.getModel().reiniciarNivel();
				View.getView().panelJuego.actualizarMarcos();
			} catch (FinDeSeccionException e) {
				estado.setEscena(Escena.SUBIENDO);
			}
			try {
				Thread.sleep(16, 666666); // 60FPS
//				Thread.sleep(33, 333333); // 30FPS
//				Thread.sleep(50); // 20FPS
//				Thread.sleep(100); // 10FPS
			} catch (InterruptedException e) {
			}
			View.getView().panelJuego.actualizar();
		}

		private void subiendo() {
			try {
				Edificio.getEdificio().avanzarSeccion();
				View.getView().panelJuego.actualizarMarcos();
				estado.setEscena(Escena.JUGANDO);
			} catch (FinDeNivelException e) {
				estado.setEscena(Escena.PASANDO);
			}
		}

		private void pasando() {
			try {
				Model.getModel().avanzarNivel();
				View.getView().panelJuego.actualizarMarcos();
				estado.setEscena(Escena.JUGANDO);
			} catch (FinDeJuegoException e1) {
				estado.setEscena(Escena.TERMINANDO);
			}
		}
	
		private void terminando() {
			View.getView().panelJuego.setVisible(false);
			//Verificar y Mostras Scores
			backToMenu();
			Controler.getControler().reiniciar();
			estado.setEscena(Escena.REINICIANDO);
		}
	}

	public void setNivelInicial(int nivelInicial) {
		this.nivelInicial = nivelInicial;
	}
}