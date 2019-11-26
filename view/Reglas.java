package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controler.Controler;

public class Reglas extends JPanel{
	private JLabel background;
	private JButton close;
	
	public Reglas(Dimension tamañoFrame) {
		setLayout(null);
		setLocation(0,0);
		setSize(tamañoFrame);
		setBackground(Color.black);
		initComponents();
		setVisible(false);
	}
	private void initComponents() {

		close = new JButton(View.getImagenes().get("CerrarBt1"));
		close.setBorder(null);
		close.setRolloverIcon(View.getImagenes().get("CerrarBt2"));
		close.setBorderPainted(false);
		close.setOpaque(false);
		close.setContentAreaFilled(false);
		close.setLocation(179, 300);
		close.setSize(close.getPreferredSize());
		
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Controler.getControler().backToMenu();
			}
		});
		add(close);
		
		background = new JLabel(View.getImagenes().get("HowTo"));
		background.setBorder(null);
		background.setLocation(67,86);
		background.setSize(background.getPreferredSize());
		add(background);
	}
}