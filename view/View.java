package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controler.Constantes;
import view.paneles.Config;
import view.paneles.MainMenu;
import view.paneles.PanelJuego;
import view.paneles.Reglas;
import view.paneles.TopScores;

public class View extends JFrame{
	private static View instancia;
	private Dimension tamañoPantalla;
	private Dimension tamañoFrame;
	private static HashMap<String, ImageIcon> imagenes;
	
	public JPanel mainPanel;
	public MainMenu mainMenu;
	public Config config;
	public Reglas reglas;
	public TopScores top5;
	public PanelJuego panelJuego;
	
	public static View getView(){
		return instancia;
	}
	public static void iniciarView() {
		if (instancia == null) {
			imagenes = new HashMap<String, ImageIcon>();
			cargarImagenes();
			instancia = new View();
		}
	}
	
	public static HashMap<String, ImageIcon> getImagenes(){
		return imagenes;
	}
	
	private View() {
		tamañoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		tamañoFrame = new Dimension(Constantes.ANCHOFRAME, Constantes.ALTURAFRAME);
		setTitle("Fix it Felix jr.");
		setLayout(null);
		setLocation((tamañoPantalla.width/2) - (Constantes.ANCHOFRAME/2), 0);
		setResizable(false);
		getContentPane().setBackground(Color.black);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		getContentPane().setLocation(0, 0);
		getContentPane().setPreferredSize(tamañoFrame);

		mainMenu = new MainMenu(tamañoFrame);
		config = new Config(tamañoFrame);
		reglas = new Reglas(tamañoFrame);
		top5 = new TopScores(tamañoFrame);
		panelJuego = new PanelJuego(tamañoFrame);
		
		getContentPane().add(mainMenu);
		getContentPane().add(config);
		getContentPane().add(reglas);
		getContentPane().add(top5);
		getContentPane().add(panelJuego);
		setVisible(true);
		pack();
	}

	private static void cargarImagenes(){
		imagenesMenu();
		imagenesFelix();
		imagenesVentana();
		imagenesEntidades();
		imagenesHeader();
		// Edificio
		imagenes.put("edificio", new ImageIcon(Constantes.PATHIMAGES + "edificio/edificio.png"));
		//Placeholder
		imagenes.put("placeholder", new ImageIcon(Constantes.PATHIMAGES + "placeholder.png"));
	}
	private static void imagenesMenu() {
		ImageIcon imagen;
		
		String[] menues = {"CerrarBt1", "CerrarBt2", "CloseBt", "CloseBt1", "CloseBt2", "CloseBtt2", "configBt1", "configBt2",
							"Configuracionbg", "exitBt1", "exitBt2", "HowTo", "JugarBt1", "JugarBt2", "logo", "MasBt1", "MasBt2",
							"OkBt1", "OkBt2", "rulesBt1", "rulesBt2", "Top5", "topBt1", "topBt2"};
		for (String actual : menues) {
			imagen = new ImageIcon(Constantes.PATHIMAGES + "mainmenu/" + actual + ".png");
			imagenes.put(actual, imagen);
		}
	}
	private static void imagenesHeader() {
		ImageIcon imagen;
		
		imagenes.put("headerbg", new ImageIcon(Constantes.PATHIMAGES + "header/headerbg.png"));
		imagenes.put("vida", new ImageIcon(Constantes.PATHIMAGES + "header/vida.png"));
		
		for (int i = 0; i <= 9; i++) {
				imagen = new ImageIcon(Constantes.PATHIMAGES + "header/numeros/"+ i + ".png");
				imagenes.put(Integer.toString(i), imagen);
			}
	}
		
	private static void imagenesEntidades() {
		// Pastel
		imagenes.put("pastel1", new ImageIcon(Constantes.PATHIMAGES + "pastel/pastel1.png"));
		imagenes.put("pastel0", new ImageIcon(Constantes.PATHIMAGES + "pastel/pastel0.png"));
		imagenes.put("nicelander0", new ImageIcon(Constantes.PATHIMAGES + "pastel/nicelander0.png"));
		imagenes.put("nicelander1", new ImageIcon(Constantes.PATHIMAGES + "pastel/nicelander1.png"));

		// Proyectiles
		imagenes.put("ladrillo0", new ImageIcon(Constantes.PATHIMAGES + "proyectiles/ladrillo0.png"));
		imagenes.put("ladrillo1", new ImageIcon(Constantes.PATHIMAGES + "proyectiles/ladrillo1.png"));
		imagenes.put("pajaroRight0", new ImageIcon(Constantes.PATHIMAGES + "proyectiles/pajaroRight0.png"));
		imagenes.put("pajaroRight1", new ImageIcon(Constantes.PATHIMAGES + "proyectiles/pajaroRight1.png"));
		imagenes.put("pajaroLeft0", new ImageIcon(Constantes.PATHIMAGES + "proyectiles/pajaroLeft0.png"));
		imagenes.put("pajaroLeft1", new ImageIcon(Constantes.PATHIMAGES + "proyectiles/pajaroLeft1.png"));

		// Ralph
		imagenes.put("ralphParado", new ImageIcon(Constantes.PATHIMAGES + "ralph/ralphParado.png"));
		imagenes.put("ralphMoviendoLeft0", new ImageIcon(Constantes.PATHIMAGES + "ralph/ralphMoviendoLeft0.png"));
		imagenes.put("ralphMoviendoLeft1", new ImageIcon(Constantes.PATHIMAGES + "ralph/ralphMoviendoLeft1.png"));
		imagenes.put("ralphMoviendoRight0", new ImageIcon(Constantes.PATHIMAGES + "ralph/ralphMoviendoRight0.png"));
		imagenes.put("ralphMoviendoRight1", new ImageIcon(Constantes.PATHIMAGES + "ralph/ralphMoviendoRight1.png"));
		imagenes.put("ralphGolpeando0", new ImageIcon(Constantes.PATHIMAGES + "ralph/ralphGolpeando0.png"));
		imagenes.put("ralphGolpeando1", new ImageIcon(Constantes.PATHIMAGES + "ralph/ralphGolpeando1.png"));
		imagenes.put("ralphGolpeando2", new ImageIcon(Constantes.PATHIMAGES + "ralph/ralphGolpeando2.png"));
		imagenes.put("ralphGolpeando3", new ImageIcon(Constantes.PATHIMAGES + "ralph/ralphGolpeando3.png"));
	}
	private static void imagenesVentana() {
		String temp;
		ImageIcon imagen;
		// Ventanas
		imagenes.put("ventanaNormal", new ImageIcon(Constantes.PATHIMAGES + "edificio/ventanas/ventanaNormal.png"));
		imagenes.put("ventanaHojaCerrada", new ImageIcon(Constantes.PATHIMAGES + "edificio/ventanas/ventanaHojaCerrada.png"));
		imagenes.put("ventanaHojaIzquierda", new ImageIcon(Constantes.PATHIMAGES + "edificio/ventanas/ventanaHojaIzquierda.png"));
		imagenes.put("ventanaHojaDerecha", new ImageIcon(Constantes.PATHIMAGES + "edificio/ventanas/ventanaHojaDerecha.png"));
		imagenes.put("ventanaPuerta", new ImageIcon(Constantes.PATHIMAGES + "edificio/ventanas/ventanaPuerta.png"));
		imagenes.put("ventanaPrimerPiso", new ImageIcon(Constantes.PATHIMAGES + "edificio/ventanas/ventanaPrimerPiso.png"));

		imagenes.put("macetero", new ImageIcon(Constantes.PATHIMAGES + "edificio/ventanas/macetero.png"));
		imagenes.put("moldura", new ImageIcon(Constantes.PATHIMAGES + "edificio/ventanas/moldura.png"));
		
		//Paneles
		String[] paneles = { "NormalSano", "NormalSemiRoto0", "NormalSemiRoto1", "NormalSemiRoto2", "NormalSemiRoto3",
				"NormalTodoRoto", "PrimerPisoSano", "PrimerPisoSemiRoto0", "PrimerPisoSemiRoto1", "PrimerPisoSemiRoto2",
				"PrimerPisoSemiRoto3", "PrimerPisoTodoRoto", "PuertaSano", "PuertaSemiRoto0", "PuertaSemiRoto1",
				"PuertaSemiRoto2", "PuertaSemiRoto3", "PuertaTodoRoto" };
		for (String panel : paneles) {
			temp = Constantes.PATHIMAGES + "edificio/ventanas/panel" + panel + ".png";
			imagen = new ImageIcon(temp);
			imagenes.put("panel" + panel, imagen);
		}
	}
	
	private static void imagenesFelix() {
		String temp;
		ImageIcon imagen;
		String[] estados = { "Comiendo", "Muriendo", "Reparando" };
		for (String actual : estados) {
			for (int i = 0; i < 4; i++) {
				temp = "felix" + actual + i;
				imagen = new ImageIcon(Constantes.PATHIMAGES + "felix/" + temp + ".png");
				imagenes.put(temp, imagen);
			}
		}
		imagenes.put("felixMuriendo4", new ImageIcon(Constantes.PATHIMAGES + "felix/felixMuriendo4.png"));

		for (int i = 0; i < 4; i++) {
			temp = "felixReparando" + i + "I";
			imagen = new ImageIcon(Constantes.PATHIMAGES + "felix/" + temp + ".png");
			imagenes.put(temp, imagen);
		}

		imagenes.put("felixParado", new ImageIcon(Constantes.PATHIMAGES + "felix/felixParado.png"));
		imagenes.put("felixParadoI", new ImageIcon(Constantes.PATHIMAGES + "felix/felixParadoI.png"));
		imagenes.put("felixMoviendo", new ImageIcon(Constantes.PATHIMAGES + "felix/felixMoviendo.png"));
		imagenes.put("felixMoviendoI", new ImageIcon(Constantes.PATHIMAGES + "felix/felixMoviendoI.png"));
	}
	public void reiniciarPanelJuego() {
		remove(panelJuego);
		panelJuego = new PanelJuego(tamañoFrame);
		add(panelJuego);
	}
}