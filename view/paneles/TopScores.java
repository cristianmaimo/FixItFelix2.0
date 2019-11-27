package view.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controler.Controler;
import model.Model;
import view.View;


public class TopScores extends JPanel {
	private JLabel background;
	private JTextField[] puntajes = new JTextField[5];
	private JButton close;

	public TopScores(Dimension tamañoFrame) {
		setLayout(null);
		setLocation(0,0);
		setSize(tamañoFrame);
		setBackground(Color.black);
		initComponents();
		setVisible(false);
	}

	private void initComponents() {

		close = new JButton(View.getImagenes().get("CloseBt"));
		close.setBorder(null);
		close.setRolloverIcon(View.getImagenes().get("CloseBtt2"));
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
		actualizarPuntajes();

		background = new JLabel(View.getImagenes().get("Top5"));
		background.setBorder(null);
		background.setLocation(67,86);
		background.setSize(background.getPreferredSize());
		add(background);
	}

	public void actualizarPuntajes() {
		for (int i = 0; i < Model.getModel().getPuntajesMaximos().size() && i < 5; i++) {
			puntajes[i] = new JTextField(Model.getModel().getPuntajesMaximos().get(i).toString());
			puntajes[i].setOpaque(false);
			puntajes[i].setForeground(Color.ORANGE);
			puntajes[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
			puntajes[i].setBounds(110, 190 + 35 * i, 420, 20);
			add(puntajes[i]);
		}
	}
}