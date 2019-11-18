package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controler.Controler;
import model.Model;

public class TopScores extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5960072700612439295L;
	private JLabel background;
	private JTextField[] puntajes = new JTextField[5];
	private JButton close;

	public TopScores(Dimension tamañoVentana) {
		setLayout(null);
		setLocation(0,0);
		setSize(tamañoVentana);
		setBackground(Color.black);
		initComponents();
		paintComponents(getGraphics());
		setVisible(false);
	}

	private void initComponents() {

		close = new JButton(new ImageIcon("assets/images/mainmenu/CloseBt.png"));
		close.setBorder(null);
		close.setRolloverIcon(new ImageIcon("assets/images/mainmenu/CloseBtt2.png"));
		close.setBorderPainted(false);
		close.setOpaque(false);
		close.setContentAreaFilled(false);
		close.setLocation(180, 363);
		close.setSize(close.getPreferredSize());
				
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Controler.getControler().backToMenu();
			}
		});

		add(close);
//		for (int i = 0; i < Model.getModel().getPuntajesMaximos().size() && i < 5; i++) {
//			puntajes[i] = new JTextField(Model.getModel().getPuntajesMaximos().get(i).toString());
//			puntajes[i].setOpaque(false);
//			puntajes[i].setForeground(Color.ORANGE);
//			puntajes[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
//			puntajes[i].setBounds(110, 190 + 35 * i, 420, 20);
//			add(puntajes[i]);
//		}
		
		
		background = new JLabel(new ImageIcon("assets/images/mainmenu/Top5.png"));
		background.setBorder(null);
		background.setLocation(67,86);
		background.setSize(background.getPreferredSize());
		add(background);
	}
}