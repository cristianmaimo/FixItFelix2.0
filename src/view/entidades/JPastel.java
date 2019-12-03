package view.entidades;

import javax.swing.JLabel;

import controler.Constantes;
import model.Model;
import model.utilidades.Posicion;
import view.View;

public class JPastel extends JLabel{

	public JPastel() {
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setSize(44, 52);
		setIcon(View.getImagenes().get("placeholder"));
		setLocation(-100, -100);
	}
	
	public void actualizar() {
		setIcon(View.getImagenes().get(Model.getModel().getPastel().getNombreImagen()));
		Posicion posActual = Model.getModel().getPastel().getPosicion();
		if (posActual.getCoordenadaX() != -100){
			int x = posActual.getCoordenadaX() + Constantes.PASTELOFFSETX + Constantes.OFFSETXVISUAL;
			int y = posActual.getCoordenadaY() + Constantes.PASTELOFFSETY + Constantes.OFFSETYVISUAL;
			setLocation(x, y);
		} else setLocation (-100, -100);
		
	}
}
