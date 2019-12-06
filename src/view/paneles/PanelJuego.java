package view.paneles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;

import view.View;
import view.edificio.JEdificio;
import view.entidades.JFelix;
import view.entidades.JPastel;
import view.entidades.JProyectil;
import view.entidades.JRalph;
import view.paneles.utilidades.FinDeJuego;
import view.paneles.utilidades.NivelCompletado;
import view.paneles.utilidades.Pausa;
import view.paneles.utilidades.PedirNombre;
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
	private JLabel background;
	
	private Pausa pausa;
	private NivelCompletado nivelCompletado;
	private FinDeJuego finDeJuego;
	private PedirNombre pedirNombre;

	public PanelJuego() {
		setLayout(null);
		setBorder(null);
		setOpaque(false);
		setLocation(0,0);
		setSize(Constantes.TAMANOFRAME);
		initComponents();
		setVisible(false);
	}

	private void initComponents() {
		pausa = new Pausa();
		nivelCompletado = new NivelCompletado();
		finDeJuego = new FinDeJuego();
		pedirNombre = new PedirNombre();
		header = new Header();
		jproyectiles = new ArrayList<JProyectil>(Constantes.CANTIDADPROYECTILES);
		jedificio = new JEdificio();
		jfelix = new JFelix();
		jralph = new JRalph();
		jpastel = new JPastel();
		
		background = new JLabel();
		background.setIcon(View.getImagenes().get("background"));
		background.setLayout(null);
		background.setBorder(null);
		background.setOpaque(false);
		background.setSize(getPreferredSize());
		background.setLocation(0, -(Constantes.ALTURAEDIFICIO - Constantes.HEADER - Constantes.ALTURAVENTANA*4));
		
		add(pausa);
		add(nivelCompletado);
		add(finDeJuego);
		add(pedirNombre);
		add(header);
		iniciarJProyectiles();
		add(jfelix);
		add(jralph);
		add(jpastel);
		add(jedificio);
		add(background);
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
	
	public void finDeJuego() {
		if(Model.getModel().getNivelActual() > 10) finDeJuego.setIcon(View.getImagenes().get("victoria"));
		finDeJuego.setVisible(true);
	}

	public void perdirNombre() {
		finDeJuego.setVisible(false);
		pedirNombre.setVisible(true);
	}
	
	public void pausa() {
		pausa.setVisible(true);
	}

	public void subiendo(int frame) {
		switch (frame) {
		case 0:
			jfelix.setIcon(View.getImagenes().get("felixParado"));
			jralph.setIcon(View.getImagenes().get("ralphEnojado0"));
			break;
		case 20:
			jralph.setIcon(View.getImagenes().get("ralphEnojado1"));
			break;
		case 40:
			jralph.setIcon(View.getImagenes().get("ralphEnojado0"));
			break;
		case 60:
			jralph.setIcon(View.getImagenes().get("ralphEnojado1"));
			break;
		case 80:
			jralph.setIcon(View.getImagenes().get("ralphSubiendo0"));
			break;
		case 100:
			jralph.setIcon(View.getImagenes().get("ralphSubiendo1"));
			break;
		case 120:
			jralph.setIcon(View.getImagenes().get("ralphSubiendo0"));
			break;
		case 140:
			jralph.setIcon(View.getImagenes().get("ralphSubiendo1"));
			break;
		default:
			if (frame > 80) jralph.setLocation(jralph.getLocation().x, jralph.getLocation().y - 2);
			break;
		}
	}

	public void pasando() {
		nivelCompletado.setVisible(true);
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		nivelCompletado.setVisible(false);
	}
}
