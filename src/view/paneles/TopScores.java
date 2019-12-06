package view.paneles;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controler.Constantes;
import controler.Controler;
import model.Model;
import view.View;


public class TopScores extends JPanel {
	private JLabel background;
	private JTextField[] nombres = new JTextField[5];
	private JTextField[] puntajes = new JTextField[5];
	private JButton close;
	
	private final int offsetX1 = 295;
	private final int offsetX2 = 442;
	private final int offsetY1 = 157;
	private final int offsetY2 = 48;

	public TopScores() {
		setLayout(null);
		setLocation(0,0);
		setSize(Constantes.TAMANOFRAME);
		setBackground(Color.cyan);
		initComponents();
		setVisible(false);
	}

	private void initComponents() {
		for (int i = 0; i < Model.getModel().getPuntajesMaximos().size() && i < 5; i++) {
			nombres[i] = new JTextField(Model.getModel().getPuntajesMaximos().get(i).getNombre());			
			nombres[i].setOpaque(false);
			nombres[i].setEditable(false);
			nombres[i].setForeground(Color.yellow);
			nombres[i].setBorder(BorderFactory.createEmptyBorder());
			nombres[i].setLocation(offsetX1, offsetY1 + (offsetY2 * i));
			nombres[i].setSize(nombres[i].getPreferredSize());
			add(nombres[i]);
			
			int siguiente = Model.getModel().getPuntajesMaximos().get(i).getPuntaje();
			if (siguiente != -1) puntajes[i] = new JTextField(Integer.toString(siguiente));
			else puntajes[i] = new JTextField("");
			puntajes[i].setOpaque(false);
			puntajes[i].setEditable(false);
			puntajes[i].setForeground(Color.yellow);
			puntajes[i].setBorder(BorderFactory.createEmptyBorder());
			puntajes[i].setLocation(offsetX2, offsetY1 + (offsetY2 * i));
			puntajes[i].setSize(puntajes[i].getPreferredSize());
			add(puntajes[i]);
		}
		
		close = new JButton(View.getImagenes().get("cerrarBt3"));
		close.setBorder(null);
		close.setRolloverIcon(View.getImagenes().get("cerrarBt4"));
		close.setBorderPainted(false);
		close.setOpaque(false);
		close.setContentAreaFilled(false);
		close.setLocation(300, 430);
		close.setSize(close.getPreferredSize());
				
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Controler.getControler().backToMenu();
			}
		});

		add(close);

		background = new JLabel(View.getImagenes().get("top5"));
		background.setBorder(null);
		background.setLocation(Constantes.ANCHOFRAME/2 - 210, 0);
		background.setSize(background.getPreferredSize());
		add(background);
	}

	public void actualizarPuntajes() {
		for (int i = 0; i < Model.getModel().getPuntajesMaximos().size() && i < 5; i++) {
			int siguiente = Model.getModel().getPuntajesMaximos().get(i).getPuntaje();
			if (siguiente != -1) {
				nombres[i].setText(Model.getModel().getPuntajesMaximos().get(i).getNombre());
				puntajes[i].setText(Integer.toString(siguiente));
				nombres[i].setSize(nombres[i].getPreferredSize());
				puntajes[i].setSize(puntajes[i].getPreferredSize());
			}
		}
	}
}