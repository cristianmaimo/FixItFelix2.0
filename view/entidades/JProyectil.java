package view.entidades;

import javax.swing.JLabel;

import view.View;

public class JProyectil extends JLabel{

	public JProyectil(){
		setLayout(null);
		setBorder(null);
//		setOpaque(true);
		setSize(42, 38);
		setIcon(View.getImagenes().get("placeholder"));
		setLocation(-100,-100);
	}
}
