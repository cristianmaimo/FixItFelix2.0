package view.edificio;

import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import controler.Constantes;
import model.edificio.InfoVentana;
import view.View;

class JVentana extends JComponent {
	JLabel marco;
	JLabel moldura;
	JLabel macetero;
	JLabel[] paneles;
	
	JVentana(InfoVentana infoventana) {
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setSize(Constantes.ANCHOVENTANA, Constantes.ALTURAVENTANA);
		
		int x = infoventana.getPosicion().getCoordenadaX();
		int y = infoventana.getPosicion().getCoordenadaY();
		setLocation(x,y);
		
		HashMap<String, ImageIcon> imagenes = View.getImagenes();
		
		if (infoventana.hayMoldura()) {
			moldura = new JLabel(imagenes.get("moldura"));
			moldura.setLocation(9, 8);
			moldura.setSize(moldura.getPreferredSize());
			add(moldura);
		}
		if (infoventana.hayMacetero()) {
			macetero = new JLabel(imagenes.get("macetero"));
			macetero.setLocation(11, 80);
			macetero.setSize(macetero.getPreferredSize());
			add(macetero);
			
		}
		
		marco = new JLabel(imagenes.get(infoventana.getImgMarco()));
		marco.setLocation(0, 0);
		marco.setSize(marco.getPreferredSize());
		add(marco);
			
		paneles = new JLabel[infoventana.getPosicionPaneles().size()];
		for (int i=0; i<infoventana.getPosicionPaneles().size(); i++) {
			paneles[i] = new JLabel(View.getImagenes().get(infoventana.getImgPaneles()[i]));
			paneles[i].setBorder(null);
			x = infoventana.getPosicionPaneles().get(i).getCoordenadaX();
			y = infoventana.getPosicionPaneles().get(i).getCoordenadaY();
			paneles[i].setLocation(x, y);
			paneles[i].setSize(paneles[i].getPreferredSize());
			add(paneles[i]);
		}
	}

	public void actualizarPaneles(InfoVentana ventanaActual) {
		for (int i=0; i<ventanaActual.getPosicionPaneles().size(); i++) {
			paneles[i].setIcon(View.getImagenes().get(ventanaActual.getImgPaneles()[i]));
		}
	}
}