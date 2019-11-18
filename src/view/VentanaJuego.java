package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controler.Constantes;

import model.edificio.Edificio;
import model.edificio.Hoja;
import model.edificio.Normal;
import model.edificio.Seccion;
import model.edificio.Semicircular;
import model.edificio.Ventana;
import model.Model;
import model.entidades.AccionFelix;
import model.entidades.AccionRalph;
import model.entidades.Felix;
import model.utilidades.Posicion;
import model.entidades.Pastel;
import model.entidades.Proyectil;
import model.entidades.Ralph;

public class VentanaJuego extends JFrame {
	private JuegoPanel juegoPanel;

	public VentanaJuego() {
		setTitle("JUEGO");
		setSize(Constantes.ANCHOFRAME, Constantes.ALTURAFRAME);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setAlwaysOnTop(true);

		juegoPanel = new JuegoPanel();
		setContentPane(juegoPanel);
		getContentPane().setBackground(Color.CYAN);
//		pack();

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (Felix.getFelix().getOcupado() == 0) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						Felix.getFelix().setAccion(AccionFelix.MOVIENDO_IZQ);
						Felix.getFelix().setOcupado(1);
						break;
					case KeyEvent.VK_RIGHT:
						Felix.getFelix().setAccion(AccionFelix.MOVIENDO_DER);
						Felix.getFelix().setOcupado(1);
						break;
					case KeyEvent.VK_UP:
						Felix.getFelix().setAccion(AccionFelix.MOVIENDO_ARR);
						Felix.getFelix().setOcupado(1);
						break;
					case KeyEvent.VK_DOWN:
						Felix.getFelix().setAccion(AccionFelix.MOVIENDO_ABA);
						Felix.getFelix().setOcupado(1);
						break;
					case KeyEvent.VK_SPACE:
						Felix.getFelix().setAccion(AccionFelix.ARREGLANDO);
						Felix.getFelix().setOcupado(4);
						break;
					}
				}
			}
		});
		setVisible(true);
	}

	//@Override
	public void paint(Graphics g) {
		super.paint(g);
		juegoPanel.paint(g);
	}

	class JuegoPanel extends JPanel {
		private HashMap<String, BufferedImage> imagenes;
		private int cont = 0;
		
		public JuegoPanel() {
			setLocation(0, 0);
			imagenes = new HashMap<>();
			cargarImagenes();
		}

		private void cargarImagenes() {
			try {
				String temp;
				BufferedImage imagen;
				//felix
				String[] felixs = {"felix", "felixMoviendo", "felixArreglando1", "felixArreglando2", "felixArreglando3", "felixArreglando4"};
				for (String felix : felixs) {
					imagen = ImageIO.read(getClass().getResource(Constantes.PATHIMAGES + "felix/" + felix + ".png"));
					imagenes.put(felix, imagen);
				}

				//edificio
				String[] secciones = { "seccion0", "seccion1", "seccion2" };
				for (String seccion : secciones) {
					temp = Constantes.PATHIMAGES + "edificio/" + seccion + ".png";
					imagen = ImageIO.read(getClass().getResource(temp));
					imagenes.put(seccion, imagen);
				}

				String[] ventanas = { "Normal", "PanelCerrada", "PanelDer", "PanelIzq", "Puerta", "PrimerPiso" };
				for (String ventana : ventanas) {
					temp = Constantes.PATHIMAGES + "edificio/ventanas/ventana" + ventana + ".png";
					imagen = ImageIO.read(getClass().getResource(temp));
					imagenes.put("ventana" + ventana, imagen);
				}

				String[] paneles = { "NormalSano", "NormalSemiRoto0", "NormalSemiRoto1", "NormalSemiRoto2",
						"NormalSemiRoto3", "NormalTodoRoto", "PrimerPisoSano", "PrimerPisoSemiRoto0",
						"PrimerPisoSemiRoto1", "PrimerPisoSemiRoto2", "PrimerPisoSemiRoto3", "PrimerPisoTodoRoto",
						"PuertaSano", "PuertaSemiRoto0", "PuertaSemiRoto1", "PuertaSemiRoto2", "PuertaSemiRoto3",
						"PuertaTodoRoto" };
				for (String panel : paneles) {
					temp = Constantes.PATHIMAGES + "edificio/ventanas/panel" + panel + ".png";
					imagen = ImageIO.read(getClass().getResource(temp));
					imagenes.put("panel" + panel, imagen);
				}

				String macetero = Constantes.PATHIMAGES + "edificio/ventanas/macetero.png";
				imagen = ImageIO.read(getClass().getResource(macetero));
				imagenes.put("macetero", imagen);
				String moldura = Constantes.PATHIMAGES + "edificio/ventanas/moldura.png";
				imagen = ImageIO.read(getClass().getResource(Constantes.PATHIMAGES + "edificio/ventanas/moldura.png"));
				imagenes.put("moldura", imagen);
				
				//pastel
				imagenes.put("pastel1", ImageIO.read(getClass().getResource(Constantes.PATHIMAGES + "misc/pastel1.png")));
				imagenes.put("pastel0", ImageIO.read(getClass().getResource(Constantes.PATHIMAGES + "misc/pastel0.png")));
				
				//proyectiles
				imagenes.put("ladrillo1", ImageIO.read(getClass().getResource(Constantes.PATHIMAGES + "proyectiles/ladrillo1.png")));
				imagenes.put("ladrillo0", ImageIO.read(getClass().getResource(Constantes.PATHIMAGES + "proyectiles/ladrillo0.png")));
				imagenes.put("pajaroRight1", ImageIO.read(getClass().getResource(Constantes.PATHIMAGES + "proyectiles/pajaroRight1.png")));
				imagenes.put("pajaroRight0", ImageIO.read(getClass().getResource(Constantes.PATHIMAGES + "proyectiles/pajaroRight0.png")));
				imagenes.put("pajaroLeft1", ImageIO.read(getClass().getResource(Constantes.PATHIMAGES + "proyectiles/pajaroLeft1.png")));
				imagenes.put("pajaroLeft0", ImageIO.read(getClass().getResource(Constantes.PATHIMAGES + "proyectiles/pajaroLeft0.png")));
				
				//ralph
				String[] ralphs = {"ralphGolpeando1", "ralphGolpeando2", "ralphGolpeando3", "ralphGolpeando4", "ralphMoviendoLeft0", "ralphMoviendoLeft1", "ralphMoviendoRight0", "ralphMoviendoRight1", "ralphParado0"};
				for (String ralph : ralphs) {
					imagen = ImageIO.read(getClass().getResource(Constantes.PATHIMAGES + "ralph/" + ralph + ".png"));
					imagenes.put(ralph, imagen);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			dibujarEdificio(g);
			dibujarPastel(g);
			dibujarRalph(g);
			dibujarFelix(g);
			dibujarProyectiles(g);
			
			g.dispose();
			cont++;
		}

		private void dibujarProyectiles(Graphics g) {
			for (Proyectil pro : Model.getModel().getProyectiles()) {
				switch (pro.getClass().getSimpleName()) {
				case "Ladrillo":
					g.drawImage(imagenes.get("ladrillo" + cont%2),
							pro.getPosicion().getCoordenadaX() + Constantes.OFFSETXSECCION,
							pro.getPosicion().getCoordenadaY() + Constantes.OFFSETYSECCION, null);
					break;
				case "Pajaro":
					if (pro.getVelocidad() > 0)
						g.drawImage(imagenes.get("pajaroRight" + cont%2),
								pro.getPosicion().getCoordenadaX(),
								pro.getPosicion().getCoordenadaY() + Constantes.OFFSETYSECCION, null);
					else
						g.drawImage(imagenes.get("pajaroLeft" + cont%2),
								pro.getPosicion().getCoordenadaX(),
								pro.getPosicion().getCoordenadaY() + Constantes.OFFSETYSECCION, null);
					break;
				}
			}
		}

		private void dibujarFelix(Graphics g) {
			switch (Felix.getFelix().getAccion()) {
			case ARREGLANDO:
				g.drawImage(imagenes.get("felixArreglando" + Felix.getFelix().getOcupado()),
						Felix.getFelix().getPosicion().getCoordenadaX() + Constantes.OFFSETXSECCION,
						Felix.getFelix().getPosicion().getCoordenadaY() + Constantes.OFFSETYSECCION, this);
				break;
			case MOVIENDO_ABA:
			case MOVIENDO_ARR:
			case MOVIENDO_DER:
			case MOVIENDO_IZQ:
				g.drawImage(imagenes.get("felixMoviendo"), 
						Felix.getFelix().getPosicion().getCoordenadaX() + Constantes.OFFSETXSECCION,
						Felix.getFelix().getPosicion().getCoordenadaY() + Constantes.OFFSETYSECCION, this);
				break;
			case MURIENDO:
				break;
			case PARADO:
				g.drawImage(imagenes.get("felix"), 
						Felix.getFelix().getPosicion().getCoordenadaX() + Constantes.OFFSETXSECCION,
						Felix.getFelix().getPosicion().getCoordenadaY() + Constantes.OFFSETYSECCION, this);
				break;
			case PASANDO:
				break;
			default:
				break;

			}
		}

		private void dibujarRalph(Graphics g) {
			switch (Ralph.getRalph().getAccion()) {
			case GOLPEANDO:
				g.drawImage(imagenes.get("ralphGolpeando" + cont%4),
						Ralph.getRalph().getPosicion().getCoordenadaX() + Constantes.OFFSETXSECCION,
						Ralph.getRalph().getPosicion().getCoordenadaY() + Constantes.OFFSETYSECCION, this);
				break;
			case MOVIENDO:
				if (Ralph.getRalph().getPosicionDestino() < Ralph.getRalph().getPosicion().getCoordenadaX())
					g.drawImage(imagenes.get("ralphMoviendo" + cont%2),
							Ralph.getRalph().getPosicion().getCoordenadaX() + Constantes.OFFSETXSECCION,
							Ralph.getRalph().getPosicion().getCoordenadaY() + Constantes.OFFSETYSECCION, this);
				else
					g.drawImage(imagenes.get("ralphGolpeando" + cont%2),
							Ralph.getRalph().getPosicion().getCoordenadaX() + Constantes.OFFSETXSECCION,
							Ralph.getRalph().getPosicion().getCoordenadaY() + Constantes.OFFSETYSECCION, this);
				break;
			case PARADO:
				g.drawImage(imagenes.get("ralphParado0"),
						Ralph.getRalph().getPosicion().getCoordenadaX() + Constantes.OFFSETXSECCION,
						Ralph.getRalph().getPosicion().getCoordenadaY() + Constantes.OFFSETYSECCION, this);
				break;
			default:
				break;
			}
		}

		private void dibujarPastel(Graphics g) {
			Pastel pastel;
			pastel = Model.getModel().getPastel();
			if (pastel != null)
				g.drawImage(imagenes.get("pastel" + cont%2),
						pastel.getPosicion().getCoordenadaX() + Constantes.OFFSETXSECCION, 
						pastel.getPosicion().getCoordenadaY() + Constantes.OFFSETYSECCION, null);
		}

		private void dibujarEdificio(Graphics g) {
			Seccion seccion = Edificio.getEdificio().getSeccion();
			String actual = "seccion" + Edificio.getEdificio().getSeccionActual();
			g.drawImage(imagenes.get(actual), 0, Constantes.HEADER, this);

			Set<Posicion> set = seccion.ventanas.keySet();
			Iterator<Posicion> it = set.iterator();
			Posicion pos;
			for (int i = 0; i < 15; i++) {
				pos = it.next();
				dibujarVentana(g, pos, seccion.ventanas.get(pos));
			}
		}

		private void dibujarVentana(Graphics g, Posicion pos, Ventana ventana) {
			if (ventana instanceof Normal) {
				dibujarNormal(g,pos,ventana);
			} 
			else if (ventana instanceof Hoja) {
				dibujarHoja(g, pos, ventana);
			} 
			else if (ventana instanceof Semicircular) {
				if (ventana.roturasMaximas == 16)
					dibujarPrimerPiso(g, pos, ventana);
				else if (ventana.roturasMaximas == 8)
					dibujarPuerta(g, pos, ventana);

			}
		}

		private void dibujarNormal(Graphics g, Posicion pos, Ventana ventana) {
			String temp;
			int coordX = pos.getCoordenadaX() + Constantes.OFFSETXSECCION + 22;
			int coordY = pos.getCoordenadaY() + Constantes.OFFSETYSECCION + 70;

			switch (ventana.paneles.get(1)) {
			case SANO:
				g.drawImage(imagenes.get("panelNormalSano"), coordX, coordY, this);
				break;
			case SEMIROTO:
				g.drawImage(imagenes.get("panelNormalSemiRoto"+ ventana.semirotoIndex[0]), coordX, coordY, this);
				break;
			case TODOROTO:
				g.drawImage(imagenes.get("panelNormalTodoRoto"), coordX, coordY, this);
				break;
			default:
				break;
			}
			coordY -= 26;
			switch (ventana.paneles.get(0)) {
			case SANO:
				g.drawImage(imagenes.get("panelNormalSano"), coordX, coordY, this);
				break;
			case SEMIROTO:
				g.drawImage(imagenes.get("panelNormalSemiRoto" + ventana.semirotoIndex[0]), coordX, coordY, this);
				break;
			case TODOROTO:
				g.drawImage(imagenes.get("panelNormalTodoRoto"), coordX, coordY, this);
				break;
			default:
				break;
			}
			coordX -= 22;
			coordY -= 44;
			g.drawImage(imagenes.get("ventanaNormal"), coordX, coordY, this);
			
			if (ventana.hayMacetero) {
				g.drawImage(imagenes.get("macetero"), coordX + 11, coordY + 80, this);
			}
			if (ventana.hayMoldura) {
				g.drawImage(imagenes.get("moldura"), coordX + 9, coordY + 16, this);
			}
		}

		private void dibujarHoja(Graphics g, Posicion pos, Ventana ventana) {
			String temp;
			int coordX = pos.getCoordenadaX() + Constantes.OFFSETXSECCION + 22;
			int coordY = pos.getCoordenadaY() + Constantes.OFFSETYSECCION + 70;

			switch (ventana.paneles.get(1)) {
			case SANO:
				g.drawImage(imagenes.get("panelNormalSano"), coordX, coordY, this);
				break;
			case SEMIROTO:
				g.drawImage(imagenes.get("panelNormalSemiRoto" + ventana.semirotoIndex[0]), coordX, coordY, this);
				break;
			case TODOROTO:
				g.drawImage(imagenes.get("panelNormalTodoRoto"), coordX, coordY, this);
				break;
			default:
				break;
			}
			coordY -= 26;
			switch (ventana.paneles.get(0)) {
			case SANO:
				g.drawImage(imagenes.get("panelNormalSano"), coordX, coordY, this);
				break;
			case SEMIROTO:
				g.drawImage(imagenes.get("panelNormalSemiRoto" + ventana.semirotoIndex[0]), coordX, coordY, this);
				break;
			case TODOROTO:
				g.drawImage(imagenes.get("panelNormalTodoRoto"), coordX, coordY, this);
				break;
			default:
				break;
			}
			coordX -= 22;
			coordY -= 44;
			String hoja = "";
			switch (((Hoja) ventana).estado) {
			case ABIERTA_DERECHA:
				hoja = "ventanaPanelDer";
				break;
			case ABIERTA_IZQUIERDA:
				hoja = "ventanaPanelIzq";
				break;
			case CERRADA:
				hoja = "ventanaPanelCerrada";
				break;
			default:
				break;
			}
			g.drawImage(imagenes.get(hoja), coordX, coordY, this);
			
			if (ventana.hayMacetero) {
				g.drawImage(imagenes.get("macetero"), coordX + 11, coordY + 80, this);
			}
			if (ventana.hayMoldura) {
				g.drawImage(imagenes.get("moldura"), coordX + 9, coordY + 16, this);
			}
		} 
		
		private void dibujarPrimerPiso(Graphics g, Posicion pos, Ventana ventana) {
			String temp;
			int coordX = pos.getCoordenadaX() + Constantes.OFFSETXSECCION - 6;
			int coordY = pos.getCoordenadaY() + Constantes.OFFSETYSECCION;
			g.drawImage(imagenes.get("ventanaPrimerPiso"), coordX, coordY, this);
		}

		private void dibujarPuerta(Graphics g, Posicion pos, Ventana ventana) {
			String temp;
			int coordX = pos.getCoordenadaX() + Constantes.OFFSETXSECCION - 5 + 44;
			int coordY = pos.getCoordenadaY() + Constantes.OFFSETYSECCION - 17 + 92;
			int index = 3;
			for (int i = 1; i >= 0; i--) {
				int coordX2 = coordX;
				for(int j = 1; j >= 0; j--) {
					switch (ventana.paneles.get(index)) {
					case SANO:
						g.drawImage(imagenes.get("panelPuertaSano"), coordX2, coordY, this);
						break;
					case SEMIROTO:
						g.drawImage(imagenes.get("panelPuertaSemiRoto" + ventana.semirotoIndex[index]), coordX2, coordY, this);
						break;
					case TODOROTO:
						g.drawImage(imagenes.get("panelPuertaSano"), coordX2, coordY, this);
						break;
					default:
						break;
					}
					index--;
					coordX2 -= 26;
				}
				coordY -= 24;
			}
			
			coordX -= 44;
			coordY -= 44;
			g.drawImage(imagenes.get("ventanaPuerta"), coordX, coordY, this);

		}
	}
}
