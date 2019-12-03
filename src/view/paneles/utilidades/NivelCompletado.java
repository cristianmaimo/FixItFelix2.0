package view.paneles.utilidades;

import javax.swing.JLabel;

import view.View;

public class NivelCompletado extends JLabel{
	public NivelCompletado() {
		setIcon(View.getImagenes().get("nivelCompletado"));
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setLocation(185, 112);
		setSize(getPreferredSize());
		setVisible(false);
	}
}

