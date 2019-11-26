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
//		setLocation(Constantes.OFFSETXEDIFICIO, 0);
//		setLocation(Constantes.OFFSETXEDIFICIO,  -450);
		setLocation(Constantes.OFFSETXEDIFICIO, -815);
//		setSize(420,1280);
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
		ArrayList<InfoVentana> cambios = Model.getModel().cambiosVentanas();
		if (cambios != null) {
			ArrayList<JVentana> jseccionActual = jsecciones[Edificio.getEdificio().getSeccionActual()].jventanas;
			for (InfoVentana ventanaActual : cambios) {
				for (JVentana jventanaActual : jseccionActual)
					if ((ventanaActual.getPosicion().getCoordenadaX() == jventanaActual.getLocation().x) &&
						(ventanaActual.getPosicion().getCoordenadaY() == jventanaActual.getLocation().y)) {
						jventanaActual.actualizarPaneles(ventanaActual);
					}
			}
		}
		
	}
}