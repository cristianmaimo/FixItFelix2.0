package view.paneles.utilidades;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import controler.Controler;
import view.View;

public class FinDeJuego extends JLabel{
	private JButton ok;
	
	public FinDeJuego() {
		setIcon(View.getImagenes().get("derrota"));
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setLocation(185,112);
		setSize(getPreferredSize());
		setVisible(false);
		
		ok = new JButton(View.getImagenes().get("okBt1"));
		ok.setBorder(null);
		ok.setRolloverIcon(View.getImagenes().get("okBt2"));
		ok.setBorderPainted(false);
		ok.setOpaque(false);
		ok.setContentAreaFilled(false);
		ok.setLocation(139, 155);
		ok.setSize(ok.getPreferredSize());
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Controler.getControler().verificarPuntaje();
			}
		});
		add(ok);
	}
}
