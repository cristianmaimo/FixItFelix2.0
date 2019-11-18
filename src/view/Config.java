package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle.Control;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controler.Controler;
import model.Model;

public class Config extends JPanel{
	private JLabel background;
	private JComboBox<String> nivel;
	private JButton close;
	private JButton ok;
	private String[] niveles = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	
	public Config(Dimension tamañoVentana) {
		setLayout(null);
		setLocation(0,0);
		setSize(tamañoVentana);
		setBackground(Color.black);
		initComponents();
		paintComponents(getGraphics());
		setVisible(false);
	}
	private void initComponents() {

		nivel = new JComboBox<String>(niveles);
		nivel.setBorder(null);
		nivel.setLocation(225 - (nivel.getPreferredSize().width/2), 216 - (nivel.getPreferredSize().height/2));
		nivel.setSize(nivel.getPreferredSize());
		add(nivel);
		
		ok = new JButton(new ImageIcon("assets/images/mainmenu/OkBt1.png"));
		ok.setBorder(null);
		ok.setRolloverIcon(new ImageIcon("assets/images/mainmenu/OkBt2.png"));
		ok.setBorderPainted(false);
		ok.setOpaque(false);
		ok.setContentAreaFilled(false);
		ok.setLocation(175, 250);
		ok.setSize(ok.getPreferredSize());
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Model.getModel().setNivelActual(nivel.getSelectedIndex() + 1);
				setVisible(false);
				Controler.getControler().backToMenu();
			}
		});
		add(ok);
	
		background = new JLabel(new ImageIcon("assets/images/mainmenu/Configuracionbg.png"));
		background.setBorder(null);
		background.setLocation(75,114);
		background.setSize(background.getPreferredSize());
		add(background);
	}
}