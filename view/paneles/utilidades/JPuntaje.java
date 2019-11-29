package view.paneles.utilidades;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import model.Model;
import view.View;
import view.paneles.Header;

public class JPuntaje extends JComponent{
	private int puntaje;
	private ArrayList<JLabel> numeros; 
	
	public JPuntaje(){
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setSize(125, 16);
		setLocation(609, 8);
		
		puntaje = 0;
		numeros = new ArrayList<JLabel>(8);
		JLabel actual;
		for (int i = 0; i < 8; i++) {
			actual = new JLabel();
			actual.setIcon(View.getImagenes().get("0"));
			actual.setLayout(null);
			actual.setBorder(null);
			actual.setOpaque(false);
			actual.setSize(16, 16);
			actual.setLocation(i * 16, 0);
			numeros.add(i, actual);
			add(numeros.get(i));
		}
	}
	
	public void actualizar(){
		if (puntaje != Model.getModel().getPuntajeActual()) {
			puntaje = Model.getModel().getPuntajeActual();
			ArrayList<ImageIcon> imagenesPuntaje = Header.numeroAImagenes(puntaje, 8);
			for (int i = 0; i < 8; i++) {
				numeros.get(i).setIcon(imagenesPuntaje.get(i));
			}
		}
	}
}
