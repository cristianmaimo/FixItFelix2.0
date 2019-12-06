package view.paneles.utilidades;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controler.Controler;
import view.View;

public class PedirNombre extends JLabel{
	private JButton ok;
	private JTextField pedirNombre;
	private String nuevoNombre;
	
	public PedirNombre (){
		setIcon(View.getImagenes().get("newScore"));
		setLayout(null);
		setLocation(185,112);
		setSize(getPreferredSize());
		setVisible(false);

		iniciarBoton();
		iniciarnuevoNombre();

	}

	private void iniciarnuevoNombre() {
		pedirNombre = new JTextField();			
		pedirNombre.setOpaque(true);
		pedirNombre.setForeground(Color.black);
		pedirNombre.setLocation(120, 130);
		pedirNombre.setSize(130,20);
		add(pedirNombre);
	}

	private void iniciarBoton() {
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
				nuevoNombre = pedirNombre.getText();
				if (verificarNombre()) {
					setVisible(false);
					Controler.getControler().actualizarPuntajes(nuevoNombre);
				}
			}
		});
		add(ok);
	}
	private boolean verificarNombre() {
		if (nuevoNombre.length() < 4 || nuevoNombre.length() > 20) return false;
		else return true;
		
	}
}
