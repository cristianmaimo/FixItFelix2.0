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
			case PAUSA:
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
			default:
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
			View.getView().panelJuego.actualizar();
		} catch (ChoquePajaroException e) {
			Edificio.getEdificio().reiniciarSeccion();
			View.getView().panelJuego.actualizarMarcos();
		} catch (ChoqueLadrilloException e) {
			Model.getModel().reiniciarNivel();
			View.getView().panelJuego.actualizarMarcos();
		} catch (FinDeSeccionException e) {
			estado.setEscena(Escena.SUBIENDO);
			Model.getModel().reiniciarProyectiles();
			View.getView().panelJuego.actualizar();
		} catch (FinDeJuegoException e) {
			estado.setEscena(Escena.TERMINANDO);
		}
		try {
			Thread.sleep(16, 666666);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void subiendo() {
		if (estado.frame < 150)
			View.getView().panelJuego.subiendo(estado.frame++);
		else {
			try {
				Edificio.getEdificio().avanzarSeccion();
				estado.setEscena(Escena.JUGANDO);
			} catch (FinDeNivelException e2) {
				estado.setEscena(Escena.PASANDO);
			}
		}
		try {
			Thread.sleep(16, 666666); // 60FPS
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void pasando() {
		View.getView().panelJuego.pasando();
		try {
			Model.getModel().avanzarNivel();
			View.getView().panelJuego.actualizarMarcos();
			estado.setEscena(Escena.JUGANDO);
		} catch (FinDeJuegoException e3) {
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
