package view.paneles;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controler.Constantes;
import controler.Controler;
import view.View;

public class Reglas extends JPanel{
	private JLabel background;
	private JButton close;
	
	public Reglas() {
		setLayout(null);
		setLocation(0,0);
		setSize(Constantes.TAMANOFRAME);
		setBackground(Color.black);
		initComponents();
		setVisible(false);
	}
	private void initComponents() {

		close = new JButton(View.getImagenes().get("cerrarBt1"));
		close.setBorder(null);
		close.setRolloverIcon(View.getImagenes().get("cerrarBt2"));
		close.setBorderPainted(false);
		close.setOpaque(false);
		close.setContentAreaFilled(false);
		close.setLocation(Constantes.ANCHOFRAME/2 - 47, 325);
		close.setSize(close.getPreferredSize());
		
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Controler.getControler().backToMenu();
			}
		});
		add(close);
		
		background = new JLabel(View.getImagenes().get("howTo"));
		background.setBorder(null);
		background.setLocation(Constantes.ANCHOFRAME/2 - 157, Constantes.ALTURAFRAME/2 - 130);
		background.setSize(background.getPreferredSize());
		add(background);
	}
}