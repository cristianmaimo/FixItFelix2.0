package view.paneles;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controler.Constantes;
import controler.Controler;
import view.View;

public class Config extends JPanel{
	private JLabel background;
	private JComboBox<String> nivel;
	private JButton ok;
	private String[] niveles = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	
	public Config() {
		setLayout(null);
		setLocation(0,0);
		setSize(Constantes.TAMANOFRAME);
		setBackground(Color.black);
		initComponents();
		setVisible(false);
	}
	private void initComponents() {

		nivel = new JComboBox<String>(niveles);
		nivel.setBorder(null);
		nivel.setLocation(Constantes.ANCHOFRAME/2 - 20, Constantes.ALTURAFRAME/2);
		nivel.setSize(nivel.getPreferredSize());
		add(nivel);
		
		ok = new JButton(View.getImagenes().get("okBt1"));
		ok.setBorder(null);
		ok.setRolloverIcon(View.getImagenes().get("okBt2"));
		ok.setBorderPainted(false);
		ok.setOpaque(false);
		ok.setContentAreaFilled(false);
		ok.setLocation(Constantes.ANCHOFRAME/2 - 46, 280);
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
	
		background = new JLabel(View.getImagenes().get("configuracionBg"));
		background.setBorder(null);
		background.setLocation(Constantes.ANCHOFRAME/2 - 150, Constantes.ALTURAFRAME/2 - 97);
		background.setSize(background.getPreferredSize());
		add(background);
	}
}