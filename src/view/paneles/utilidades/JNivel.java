package view.paneles.utilidades;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import model.Model;
import view.paneles.Header;

public class JNivel extends JComponent{
	private JLabel decimal;
	private JLabel unidad;
	
	public JNivel() {
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setSize(36, 16); 
		setLocation(432, 8);
		
		ArrayList<ImageIcon> imagenes = Header.numeroAImagenes(0,2);
		decimal = new JLabel();
		decimal.setIcon(imagenes.get(0));
		decimal.setSize(decimal.getPreferredSize());
		decimal.setLocation(0, 0);
		
		unidad = new JLabel();
		unidad.setIcon(imagenes.get(1));
		unidad.setSize(decimal.getPreferredSize());
		unidad.setLocation(16, 0);	
		
		add(decimal);
		add(unidad);
	}
	
	public void actualizar() {
		ArrayList<ImageIcon> imagenes = Header.numeroAImagenes(Model.getModel().getNivelActual(),2);
		decimal.setIcon(imagenes.get(0));
		unidad.setIcon(imagenes.get(1));
	}
}
