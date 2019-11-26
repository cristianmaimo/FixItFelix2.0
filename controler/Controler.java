package controler;

import view.View;

import java.awt.KeyboardFocusManager;

import model.Model;
import model.entidades.Felix;
import model.utilidades.FinDeJuegoException;
import model.utilidades.FinDeNivelException;
import model.utilidades.FinDeSeccionException;

public class Controler{
	private static Controler instancia;
	private KeyboardFocusManager teclado;
	private EstadoJuego estado;
	private MainThread mainThread;

	//MAIN
	public static void main (String args[]) {
		Controler.iniciarControler();
	}
	
	//CONSTRUCTOR
	private Controler() {
		Model.iniciarModel();
		View.iniciarView();
		
		mainThread = new MainThread();
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
		View.getView().panelJuego.setVisible(true);
		View.getView().mainMenu.setVisible(false);
		mainThread.start();
		}
	
	public void backToMenu() {
		View.getView().mainMenu.setVisible(true);
	}
	
	private class MainThread extends Thread{
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
				default:
					break;
				}
			} while (estado.escena != Escena.TERMINANDO);
		}
		
		private void iniciando() {
			estado.setEscena(Escena.JUGANDO);
		}

		private void jugando() {
			try {
				Model.getModel().actualizar();
			} catch (FinDeSeccionException e) {
				estado.setEscena(Escena.SUBIENDO);
			} catch (FinDeNivelException e) {
				try {
					Model.getModel().avanzarNivel();
					estado.setEscena(Escena.PASANDO);
				} catch (FinDeJuegoException e1) {
					estado.setEscena(Escena.TERMINANDO);
				}
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
			// TODO Auto-generated method stub
			
		}

		private void pasando() {
			// TODO Auto-generated method stub
			
		}
	}
	
}