package view.paneles.utilidades;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;

import controler.Constantes;
import model.entidades.Felix;
import view.View;

public class JVidas extends JComponent{
	private ArrayList<JLabel> imagenesVidas;
	
	public JVidas() {
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setSize(59, 20);
		setLocation(222, 5);
		
		imagenesVidas = new ArrayList<JLabel>(Constantes.VIDAS);
		
		JLabel imagenVida;
		for (int i = 0; i < Constantes.VIDAS; i++) {
			imagenVida = new JLabel();
			imagenVida.setIcon(View.getImagenes().get("vida"));
			imagenVida.setLayout(null);
			imagenVida.setBorder(null);
			imagenVida.setOpaque(false);
			imagenVida.setSize(17, 20);
			imagenVida.setLocation(i * 21, 0);
			imagenesVidas.add(imagenVida);
			add(imagenVida);
		}
	}
	
	public void actualizar() {
		for (int i = 0; i < Constantes.VIDAS; i++) {
			if (i < Felix.getFelix().getVidas()) imagenesVidas.get(i).setVisible(true);
			else imagenesVidas.get(i).setVisible(false);
		}
	}
}
