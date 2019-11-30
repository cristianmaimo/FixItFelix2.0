package controler;

import model.Model;
import model.edificio.Edificio;
import model.utilidades.ChoqueLadrilloException;
import model.utilidades.ChoquePajaroException;
import model.utilidades.FinDeJuegoException;
import model.utilidades.FinDeNivelException;
import model.utilidades.FinDeSeccionException;
import view.View;

public class MainThread extends Thread {
	private EstadoJuego estado;
	private volatile boolean exit = false;
	
	public MainThread() {
		estado = new EstadoJuego();
	}
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
		} catch (FinDeJuegoException e) {
			estado.setEscena(Escena.TERMINANDO);
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
		View.getView().panelJuego.finDeJuego();
		setEscena(Escena.REINICIANDO);
	}
	
	public void setEscena(Escena escena) {
		estado.setEscena(escena);	
	}
}
