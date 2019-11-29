package view.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import view.View;
import view.edificio.JEdificio;
import view.entidades.JFelix;
import view.entidades.JPastel;
import view.entidades.JProyectil;
import view.entidades.JRalph;
import controler.Constantes;
import model.Model;
import model.entidades.Proyectil;

public class PanelJuego extends JPanel {
	private Header header;
	private JFelix jfelix;
	private JRalph jralph;
	private ArrayList<JProyectil> jproyectiles;
	private JPastel jpastel;
	private JEdificio jedificio;


	public PanelJuego(Dimension tamañoFrame) {
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setLocation(0,0);
		setSize(tamañoFrame);
		setBackground(Color.cyan);
		initComponents();
		setVisible(false);
	}

	private void initComponents() {
		header = new Header();
		jproyectiles = new ArrayList<JProyectil>(Constantes.CANTIDADPROYECTILES);
		jedificio = new JEdificio();
		jfelix = new JFelix();
		jralph = new JRalph();
		jpastel = new JPastel();
		
		add(header);
		iniciarJProyectiles();
		add(jfelix);
		add(jralph);
		add(jpastel);
		add(jedificio);

	}

	private void iniciarJProyectiles() {
		for(int i = 0; i < Constantes.CANTIDADPROYECTILES; i++) {
			jproyectiles.add(new JProyectil());
			add(jproyectiles.get(i));
		}
	}

	public void actualizar() {
		header.actualizar();
		actualizarProyectiles();
		jpastel.actualizar();
		jfelix.actualizar();
		jralph.actualizar();
		jedificio.actualizar();
		repaint();

	}

	private void actualizarProyectiles() {
		ArrayList<Proyectil> proyectiles = Model.getModel().getProyectiles();
		Iterator<Proyectil> it = proyectiles.iterator();
		Proyectil proyectilActual;
		int x,y;
		for (JProyectil jproyectilActual : jproyectiles) {
			if (it.hasNext()) {
				proyectilActual = it.next();
				jproyectilActual.setIcon(View.getImagenes().get(proyectilActual.getNombreImagen()));
				x = proyectilActual.getPosicion().getCoordenadaX() + Constantes.OFFSETXVISUAL;
				y = proyectilActual.getPosicion().getCoordenadaY() + Constantes.OFFSETYVISUAL;
				jproyectilActual.setLocation(x, y);
			} else {
				jproyectilActual.setLocation(-100, -100);
			}
			
		}
	}
	
	public void actualizarMarcos() {
		jedificio.actualizarMarcos();
	}
}
