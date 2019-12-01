package view.paneles.utilidades;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import model.Model;
import model.utilidades.Temporizador;
import view.paneles.Header;

public class JTiempo extends JComponent {
	private ArrayList<JLabel> minutos;
	private ArrayList<JLabel> segundos;
	
	public JTiempo (){
		setLayout(null);
		setBorder(null);
		setOpaque(true);
		setSize(66, 16);
		setLocation(107, 8);
		
		minutos = new ArrayList<JLabel>(2);
		JLabel actual;
		for (int i = 0; i < 2; i++) {
			actual = new JLabel();
			actual.setLayout(null);
			actual.setBorder(null);
			actual.setOpaque(false);
			actual.setSize(32, 16);
			minutos.add(i, actual);
			add(minutos.get(i));
		}
		minutos.get(0).setLocation(0, 0);
		minutos.get(1).setLocation(16, 0);
		
		segundos = new ArrayList<JLabel>(2);
		for (int i = 0; i < 2; i++) {
			actual = new JLabel();
			actual.setLayout(null);
			actual.setBorder(null);
			actual.setOpaque(false);
			actual.setSize(32, 16);
			segundos.add(i, actual);
			add(segundos.get(i));
		}
		segundos.get(0).setLocation(36, 0);
		segundos.get(1).setLocation(52, 0);
		
	}
	
	public void actualizar(){
		Temporizador temporizador = Model.getModel().getTemporizador();
		ArrayList<ImageIcon> imagen;
		
		imagen = Header.numeroAImagenes(temporizador.minutosRestantes(), 2);
		minutos.get(0).setIcon(imagen.get(0));
		minutos.get(1).setIcon(imagen.get(1));
		
		imagen = Header.numeroAImagenes(temporizador.segundosRestantes(), 2);
		segundos.get(0).setIcon(imagen.get(0));
		segundos.get(1).setIcon(imagen.get(1));
		}
}

