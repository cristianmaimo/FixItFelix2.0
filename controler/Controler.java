package controler;

import view.View;

import java.awt.KeyboardFocusManager;

import model.Model;

public class Controler{
	private static Controler instancia;
	private KeyboardFocusManager teclado;

	
	private MainThread mainThread;
	private boolean threadIniciado;
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
		if (!threadIniciado) {
			mainThread.start();
			threadIniciado = true;
		}
		else mainThread.setEscena(Escena.INICIANDO);
	}
	
	public void backToMenu() {
		View.getView().mainMenu.setVisible(true);
	}

	public void reiniciar() {
		Model.iniciarModel();
		View.getView().reiniciarPanelJuego();
	}

	public void setNivelInicial(int nivelInicial) {
		this.nivelInicial = nivelInicial;
	}
}