package view.paneles;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controler.Constantes;
import view.View;
import view.paneles.utilidades.JPuntaje;
import view.paneles.utilidades.JVidas;

public class Header extends JLabel {
	private JPuntaje puntaje;
	private JVidas vidas;
	
	
	Header(){
		setIcon(View.getImagenes().get("headerbg"));
		setLayout(null);
		setBorder(null);
		setOpaque(false);;
		setSize(Constantes.ANCHOFRAME, Constantes.HEADER);
		setLocation(0,0);
		
		puntaje = new JPuntaje();
		vidas = new JVidas();
		
		add(puntaje);
		add(vidas);
	}
	
	void actualizar(){
		puntaje.actualizar();
		vidas.actualizar();
	}

	public static ArrayList<ImageIcon> numeroAImagenes(int numeros, int tamañoArray) {
		ArrayList<ImageIcon> imagenesNumeros = new ArrayList<ImageIcon>(tamañoArray);
		String stringNumeros = Integer.toString(numeros);
		String temp = "";
		for (int i = 0; i < (tamañoArray - stringNumeros.length()); i++){
			temp += "0";
		}
		stringNumeros = temp + stringNumeros;
		
		for (int i = 0; i < tamañoArray; i++) {
			imagenesNumeros.add(i, View.getImagenes().get("" + stringNumeros.charAt(i)));
		}
		
		return imagenesNumeros;
	}
}
