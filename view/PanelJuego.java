package view;

import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

import view.edificio.*;
import view.entidades.JFelix;
import view.entidades.JProyectil;
import view.entidades.JRalph;
import controler.Constantes;
import model.edificio.Edificio;

public class PanelJuego extends JPanel {
	private JFelix jfelix;
	private JRalph jralph;
	private ArrayList<JProyectil> jproyectiles;
	private JLabel pastel;
	private JEdificio jedificio;


	public PanelJuego(Dimension tamañoFrame) {
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setLocation(0,0);
		setSize(tamañoFrame);
		setBackground(Color.black);
		initComponents();
		setVisible(false);
	}

	private void initComponents() {
		jedificio = new JEdificio();
		jfelix = new JFelix();
		jralph = new JRalph();
//		jproyectiles = new ArrayList<JProyectil>();
//		jpastel = new JPastel();
		
		add(jfelix);
		add(jralph);
//		add(jproyectiles);
//		add(jpastel);
		add(jedificio);

	}

	public void actualizar() {
		jfelix.actualizar();
		jralph.actualizar();
//		jproyectiles.actualizar();
//		jpastel.actualizar();
		jedificio.actualizar();
		repaint();

	}
}
