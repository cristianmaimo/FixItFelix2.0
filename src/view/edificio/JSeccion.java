package view.edificio;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;

import controler.Constantes;
import model.edificio.InfoVentana;

class JSeccion extends JComponent {
	ArrayList<JVentana> jventanas;

	JSeccion (ArrayList<InfoVentana> infoseccion) {
		setLayout(null);
		setBorder(null);
		setOpaque(false);;
		setSize(Constantes.ANCHOSECCION, Constantes.ALTURASECCION);
		
		jventanas = new ArrayList<JVentana>(Constantes.CANTIDADVENTANAS);

		JVentana jventana;
		Iterator<InfoVentana> it = infoseccion.iterator();
		for (int i = 0; i < Constantes.CANTIDADVENTANAS; i++) {
			jventana = new JVentana(it.next());
			jventanas.add(jventana);
			add(jventanas.get(i));
		}
	}

	public void actualizarMarcos(ArrayList<InfoVentana> infoSeccion) {
		for (int i = 0; i < Constantes.CANTIDADVENTANAS; i++)
			jventanas.get(i).actualizarMarco(infoSeccion.get(i));
	}
}
