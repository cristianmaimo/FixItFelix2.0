package controler;

import view.VentanaJuego;
import view.View;

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
	public static VentanaJuego ventanaJuego;
	private boolean finDeJuego = false;

	public static Controler getControler() {
		return instancia;
	}

	public Controler() {
		View.iniciarView();
		Model.iniciarModel();
//		abrirJuego();
	}
	
	public static void iniciarControler() {
		instancia = new Controler();
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
		//View.getView().dispose();
		ventanaJuego = new VentanaJuego();
		Ciclo juego = new Ciclo();
		juego.start();
		}
	
	public void backToMenu() {
		View.getView().mainMenu.setVisible(true);
	}
	
	private class Ciclo extends Thread{
		@Override
		public void run() {
			do {
				try {
					Model.getModel().actualizar();
				} catch (ChoquePajaroException e) {
					Felix.getFelix().perderVida();
					Edificio.getEdificio().reiniciarSeccion();
				} catch (ChoqueLadrilloException e) {
					Felix.getFelix().perderVida();
					Model.getModel().reiniciarNivel();
				} catch (FinDeNivelException e) {
					try {
						Model.getModel().avanzarNivel();
					} catch (FinDeJuegoException e1) {
						finDeJuego = true;
					}
				} catch (FinDeSeccionException e) {
					Felix.getFelix().setPosicion(Constantes.FELIXSTART2);
				}
				ventanaJuego.paint(ventanaJuego.getGraphics());
				try {
					Thread.sleep(100);
				}
				catch(InterruptedException e) {
				}
			} while (!finDeJuego);
			
		}
	}
}