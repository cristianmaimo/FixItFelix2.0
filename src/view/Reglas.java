package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controler.Controler;

public class Reglas extends JPanel{
	private JLabel background;
	private JButton close;
	private JButton mas;
	private MainMenu l;
	
	public Reglas(Dimension tamañoVentana) {
		setLayout(null);
		setLocation(0,0);
		setSize(tamañoVentana);
		setBackground(Color.black);
		initComponents();
		paintComponents(getGraphics());
		setVisible(false);
	}
	private void initComponents() {

		close = new JButton(new ImageIcon("assets/images/mainmenu/CerrarBt1.png"));
		close.setBorder(null);
		close.setRolloverIcon(new ImageIcon("assets/images/mainmenu/CerrarBt2.png"));
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
		
		background = new JLabel(new ImageIcon("assets/images/mainmenu/HowToBg1.png"));
		background.setBorder(null);
		background.setLocation(67,86);
		background.setSize(background.getPreferredSize());
		add(background);
	}
}