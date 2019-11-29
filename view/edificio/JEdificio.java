package view.edificio;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JLabel;

import controler.Constantes;
import model.Model;
import model.edificio.Edificio;
import model.edificio.InfoVentana;
import model.utilidades.Posicion;
import view.View;

public class JEdificio extends JLabel{
	JSeccion[] jsecciones;
	
	public JEdificio() {
		super(View.getImagenes().get("edificio"));
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setLocation(Constantes.OFFSETXEDIFICIO, -(Constantes.ALTURAEDIFICIO - Constantes.HEADER + Constantes.ALTURAVENTANA*4));

		setSize(getPreferredSize());
		
		jsecciones = new JSeccion[3];
		for (int i = 0 ; i < Constantes.CANTIDADSECCIONES; i++) {
			jsecciones[i] = new JSeccion(Model.getModel().infoSeccion(i));
			add(jsecciones[i]);
		}
		jsecciones[0].setLocation(Constantes.OFFSETXSECCION, Constantes.OFFSETYSECCION + (Constantes.ALTURASECCION * 2));
		jsecciones[1].setLocation(Constantes.OFFSETXSECCION, Constantes.OFFSETYSECCION + Constantes.ALTURASECCION);
		jsecciones[2].setLocation(Constantes.OFFSETXSECCION, Constantes.OFFSETYSECCION);
	}

	public Container getSeccion(int seccionActual) {
		return jsecciones[seccionActual];
	}

	public void actualizar() {
		int seccionActual = Edificio.getEdificio().getSeccionActual();
		switch (seccionActual){
		case 0:
			setLocation(Constantes.OFFSETXEDIFICIO, -(Constantes.ALTURAEDIFICIO - Constantes.HEADER - Constantes.ALTURAVENTANA*4));
			break;
		case 1:
			setLocation(Constantes.OFFSETXEDIFICIO, -(Constantes.ALTURAEDIFICIO - Constantes.HEADER - Constantes.ALTURAVENTANA*7));
			break;
		case 2:
			setLocation(Constantes.OFFSETXEDIFICIO, -(Constantes.ALTURAEDIFICIO - Constantes.HEADER - Constantes.ALTURAVENTANA*10));
			break;
		}
		ArrayList<InfoVentana> seccion = Edificio.getEdificio().infoSeccion(seccionActual);
		ArrayList<JVentana> jseccionActual = jsecciones[Edificio.getEdificio().getSeccionActual()].jventanas;
		for (InfoVentana ventanaActual : seccion) {
			for (JVentana jventanaActual : jseccionActual)
				if ((ventanaActual.getPosicion().getCoordenadaX() == jventanaActual.getLocation().x) &&
					(ventanaActual.getPosicion().getCoordenadaY() == jventanaActual.getLocation().y)) {
					jventanaActual.actualizarPaneles(ventanaActual);
				}
		}
	}

	public void actualizarMarcos() {
		for (int i = 0; i < Constantes.CANTIDADSECCIONES; i++) {
			jsecciones[i].actualizarMarcos(Edificio.getEdificio().infoSeccion(i));
		}
	}
}