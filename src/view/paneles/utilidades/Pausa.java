package view.paneles.utilidades;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import controler.Controler;
import view.View;

public class Pausa extends JLabel{
	private JButton continuar;
	
	public Pausa() {
		setIcon(View.getImagenes().get("pausa"));
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setLocation(185,112);
		setSize(getPreferredSize());
		setVisible(false);
		
		continuar = new JButton(View.getImagenes().get("continuar1"));
		continuar.setBorder(null);
		continuar.setRolloverIcon(View.getImagenes().get("continuar2"));
		continuar.setBorderPainted(false);
		continuar.setOpaque(false);
		continuar.setContentAreaFilled(false);
		continuar.setLocation(139, 155);
		continuar.setSize(continuar.getPreferredSize());
		
		continuar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Controler.getControler().continuar();
			}
		});
		add(continuar);
	}
}
