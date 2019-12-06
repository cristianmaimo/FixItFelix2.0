package view.paneles;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controler.Constantes;
import view.View;
import view.paneles.utilidades.JNivel;
import view.paneles.utilidades.JPuntaje;
import view.paneles.utilidades.JTiempo;
import view.paneles.utilidades.JVidas;

public class Header extends JLabel {
	private JPuntaje puntaje;
	private JVidas vidas;
	private JNivel nivel;
	private JTiempo tiempo;
	
	
	Header(){
		setIcon(View.getImagenes().get("headerBg"));
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setSize(Constantes.ANCHOFRAME, Constantes.HEADER);
		setLocation(0,0);
		
		tiempo = new JTiempo();
		vidas = new JVidas();
		nivel = new JNivel();
		puntaje = new JPuntaje();
		
		add(tiempo);
		add(vidas);
		add(nivel);
		add(puntaje);
	}
	
	void actualizar(){
		tiempo.actualizar();
		vidas.actualizar();
		nivel.actualizar();
		puntaje.actualizar();
	}

	public static ArrayList<ImageIcon> numeroAImagenes(int numeros, int tamanoArray) {
		ArrayList<ImageIcon> imagenesNumeros = new ArrayList<ImageIcon>(tamanoArray);
		String stringNumeros = Integer.toString(numeros);
		String temp = "";
		for (int i = 0; i < (tamanoArray - stringNumeros.length()); i++){
			temp += "0";
		}
		stringNumeros = temp + stringNumeros;
		
		for (int i = 0; i < tamanoArray; i++) {
			imagenesNumeros.add(i, View.getImagenes().get("" + stringNumeros.charAt(i)));
		}
		
		return imagenesNumeros;
	}
}
