package controler;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import model.entidades.Felix;
import model.utilidades.Direccion;

public class KeyboardDispacher implements KeyEventDispatcher{
	boolean soltoI = true;
	boolean soltoD = true;
	boolean soltoAR = true;
	boolean soltoAB = true;
	boolean soltoS = true;
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if ((e.getID() == KeyEvent.KEY_PRESSED) &&
				soltoI &&
				soltoD &&
				soltoAR &&
				soltoAB &&
				soltoS) {
			switch (e.getKeyCode()){
			case KeyEvent.VK_LEFT:
				Felix.getFelix().mover(Direccion.IZQUIERDA);
				soltoI = false;
				break;
			case KeyEvent.VK_RIGHT:
				Felix.getFelix().mover(Direccion.DERECHA);
				soltoD = false;
				break;
			case KeyEvent.VK_UP:
				Felix.getFelix().mover(Direccion.ARRIBA);
				soltoAR = false;
				break;
			case KeyEvent.VK_DOWN:
				Felix.getFelix().mover(Direccion.ABAJO);
				soltoAB = false;
				break;
			case KeyEvent.VK_SPACE:
				Felix.getFelix().reparar();
				soltoS = false;
				break;
			}
		}
		if ((e.getID() == KeyEvent.KEY_RELEASED)){
			switch (e.getKeyCode()){
			case KeyEvent.VK_LEFT:
				soltoI = true;
				break;
			case KeyEvent.VK_RIGHT:
				soltoD = true;
				break;
			case KeyEvent.VK_UP:
				soltoAR = true;
				break;
			case KeyEvent.VK_DOWN:
				soltoAB = true;
				break;
			case KeyEvent.VK_SPACE:
				soltoS = true;
				break;
			}
		}
		return false;
	}
}