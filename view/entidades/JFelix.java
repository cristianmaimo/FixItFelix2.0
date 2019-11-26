package view.entidades;

import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controler.Constantes;
import model.entidades.Felix;
import view.View;

public class JFelix extends JLabel {
	
	public JFelix() {
		setLayout(null);
		setBorder(null);
//		setOpaque(true);
		setSize(70,68);
		setIcon(View.getImagenes().get("felixParado"));
		int x = Felix.getFelix().getPosicion().getCoordenadaX() + Constantes.OFFSETXVISUAL;
		int y = Felix.getFelix().getPosicion().getCoordenadaY() + Constantes.OFFSETYVISUAL;
		setLocation(x, y);
	}

	public void actualizar() {
		setIcon(View.getImagenes().get(Felix.getFelix().getNombreImagen()));
		int x = Felix.getFelix().getPosicion().getCoordenadaX() + Constantes.OFFSETXVISUAL;
		int y = Felix.getFelix().getPosicion().getCoordenadaY() + Constantes.OFFSETYVISUAL;
		setLocation(x, y);
	}
}