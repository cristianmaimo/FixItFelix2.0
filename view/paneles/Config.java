package view.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controler.Controler;
import view.View;

public class Config extends JPanel{
	private JLabel background;
	private JComboBox<String> nivel;
	private JButton ok;
	private String[] niveles = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	
	public Config(Dimension tamañoFrame) {
		setLayout(null);
		setLocation(0,0);
		setSize(tamañoFrame);
		setBackground(Color.black);
		initComponents();
		setVisible(false);
	}
	private void initComponents() {

		nivel = new JComboBox<String>(niveles);
		nivel.setBorder(null);
		nivel.setLocation(225 - (nivel.getPreferredSize().width/2), 216 - (nivel.getPreferredSize().height/2));
		nivel.setSize(nivel.getPreferredSize());
		add(nivel);
		
		ok = new JButton(View.getImagenes().get("OkBt1"));
		ok.setBorder(null);
		ok.setRolloverIcon(View.getImagenes().get("OkBt2"));
		ok.setBorderPainted(false);
		ok.setOpaque(false);
		ok.setContentAreaFilled(false);
		ok.setLocation(175, 250);
		ok.setSize(ok.getPreferredSize());
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controler.getControler().setNivelInicial(nivel.getSelectedIndex() + 1);
				setVisible(false);
				Controler.getControler().backToMenu();
			}
		});
		add(ok);
	
		background = new JLabel(View.getImagenes().get("Configuracionbg"));
		background.setBorder(null);
		background.setLocation(75,114);
		background.setSize(background.getPreferredSize());
		add(background);
	}
}