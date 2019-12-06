package view;

import java.awt.Color;
import java.net.URL;
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
		setTitle("Fix it Felix jr.");
		setIconImage(imagenes.get("felixReparando2").getImage());
		setLayout(null);
		setResizable(false);
		getContentPane().setBackground(Color.black);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		getContentPane().setLocation(0, 0);
		getContentPane().setPreferredSize(Constantes.TAMANOFRAME);

		mainMenu = new MainMenu();
		config = new Config();
		reglas = new Reglas();
		top5 = new TopScores();
		panelJuego = new PanelJuego();
		
		getContentPane().add(mainMenu);
		getContentPane().add(config);
		getContentPane().add(reglas);
		getContentPane().add(top5);
		getContentPane().add(panelJuego);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}

	private static void cargarImagenes(){
		imagenesMenu();
		imagenesFelix();
		imagenesVentana();
		imagenesEntidades();
		imagenesHeader();
		// Edificio
		
		imagenes.put("edificio", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
						Constantes.PATHIMAGES + "edificio/edificio.png")));
		// Placeholder
		imagenes.put("placeholder", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
						Constantes.PATHIMAGES + "placeholder.png")));
		// Backgorund
		imagenes.put("background", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
						Constantes.PATHIMAGES + "background.png")));
	}
	private static void imagenesMenu() {
		URL url;
		String path;
		String[] menues = {"cerrarBt1", "cerrarBt2", "cerrarBt3", "cerrarBt4", "configBt1", "configBt2",
							"configuracionBg", "exitBt1", "exitBt2", "howTo", "jugarBt1", "jugarBt2", "logo",
							"okBt1", "okBt2", "rulesBt1", "rulesBt2", "top5", "topBt1", "topBt2", "victoria", "derrota",
							"newScore", "pausa", "continuar1", "continuar2", "nivelCompletado"};
		for (String actual : menues) {
			path = Constantes.PATHIMAGES + "mainmenu/" + actual + ".png";
			url = Thread.currentThread().getContextClassLoader().getResource(path);
			imagenes.put(actual, new ImageIcon(url));
		}
	}
	private static void imagenesHeader() {
		String path;
		URL url;
		
		imagenes.put("headerBg", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "header/headerBg.png")));
		imagenes.put("vida", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "header/vida.png")));
		
		for (int i = 0; i <= 9; i++) {
				path = Constantes.PATHIMAGES + "header/numeros/"+ i + ".png";
				url = Thread.currentThread().getContextClassLoader().getResource(path);
				imagenes.put(Integer.toString(i), new ImageIcon(url));
			}
	}
	private static void imagenesEntidades() {
		// Pastel
		imagenes.put("pastel1", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "pastel/pastel1.png")));
		imagenes.put("pastel0", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "pastel/pastel0.png")));
		imagenes.put("nicelander0", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "pastel/nicelander0.png")));
		imagenes.put("nicelander1", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "pastel/nicelander1.png")));

		// Proyectiles
		imagenes.put("ladrillo0", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "proyectiles/ladrillo0.png")));
		imagenes.put("ladrillo1", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "proyectiles/ladrillo1.png")));
		imagenes.put("pajaroRight0", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "proyectiles/pajaroRight0.png")));
		imagenes.put("pajaroRight1", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "proyectiles/pajaroRight1.png")));
		imagenes.put("pajaroLeft0", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "proyectiles/pajaroLeft0.png")));
		imagenes.put("pajaroLeft1", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "proyectiles/pajaroLeft1.png")));

		// Ralph
		imagenes.put("ralphParado", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphParado.png")));
		imagenes.put("ralphMoviendoLeft0", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphMoviendoLeft0.png")));
		imagenes.put("ralphMoviendoLeft1", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphMoviendoLeft1.png")));
		imagenes.put("ralphMoviendoRight0", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphMoviendoRight0.png")));
		imagenes.put("ralphMoviendoRight1", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphMoviendoRight1.png")));
		imagenes.put("ralphGolpeando0", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphGolpeando0.png")));
		imagenes.put("ralphGolpeando1", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphGolpeando1.png")));
		imagenes.put("ralphGolpeando2", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphGolpeando2.png")));
		imagenes.put("ralphGolpeando3", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphGolpeando3.png")));
		imagenes.put("ralphEnojado0", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphEnojado0.png")));
		imagenes.put("ralphEnojado1", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphEnojado1.png")));
		imagenes.put("ralphSubiendo0", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphSubiendo0.png")));
		imagenes.put("ralphSubiendo1", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "ralph/ralphSubiendo1.png")));
	}
	private static void imagenesVentana() {
		String path;
		URL url;
		// Ventanas
		imagenes.put("ventanaNormal", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(Constantes.PATHIMAGES + "edificio/ventanas/ventanaNormal.png")));
		imagenes.put("ventanaHojaCerrada", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(Constantes.PATHIMAGES + "edificio/ventanas/ventanaHojaCerrada.png")));
		imagenes.put("ventanaHojaIzquierda", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(Constantes.PATHIMAGES + "edificio/ventanas/ventanaHojaIzquierda.png")));
		imagenes.put("ventanaHojaDerecha", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(Constantes.PATHIMAGES + "edificio/ventanas/ventanaHojaDerecha.png")));
		imagenes.put("ventanaPuerta", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(Constantes.PATHIMAGES + "edificio/ventanas/ventanaPuerta.png")));
		imagenes.put("ventanaPrimerPiso", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(Constantes.PATHIMAGES + "edificio/ventanas/ventanaPrimerPiso.png")));

		imagenes.put("macetero", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(Constantes.PATHIMAGES + "edificio/ventanas/macetero.png")));
		imagenes.put("moldura", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(Constantes.PATHIMAGES + "edificio/ventanas/moldura.png")));
		
		//Paneles
		String[] paneles = { "NormalSano", "NormalSemiRoto0", "NormalSemiRoto1", "NormalSemiRoto2", "NormalSemiRoto3",
				"NormalTodoRoto", "PrimerPisoSano", "PrimerPisoSemiRoto0", "PrimerPisoSemiRoto1", "PrimerPisoSemiRoto2",
				"PrimerPisoSemiRoto3", "PrimerPisoTodoRoto", "PuertaSano", "PuertaSemiRoto0", "PuertaSemiRoto1",
				"PuertaSemiRoto2", "PuertaSemiRoto3", "PuertaTodoRoto" };
		for (String panel : paneles) {
			path = Constantes.PATHIMAGES + "edificio/ventanas/panel" + panel + ".png";
			url = Thread.currentThread().getContextClassLoader().getResource(path);
			imagenes.put("panel" + panel, new ImageIcon(url));
		}
	}
	private static void imagenesFelix() {
		String path, name;
		URL url;
		String[] estados = { "Comiendo", "Muriendo", "Reparando" };
		for (String actual : estados) {
			for (int i = 0; i < 4; i++) {
				name = "felix" + actual + i;
				path = Constantes.PATHIMAGES + "felix/" + name + ".png";
				url = Thread.currentThread().getContextClassLoader().getResource(path);
				imagenes.put(name, new ImageIcon(url));
			}
		}
		imagenes.put("felixMuriendo4", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "felix/felixMuriendo4.png")));

		for (int i = 0; i < 4; i++) {
			name = "felixReparando" + i + "I";
			path = Constantes.PATHIMAGES + "felix/" + name + ".png";
			url = Thread.currentThread().getContextClassLoader().getResource(path);
			imagenes.put(name, new ImageIcon(url));
		}

		imagenes.put("felixParado", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "felix/felixParado.png")));
		imagenes.put("felixParadoI", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "felix/felixParadoI.png")));
		imagenes.put("felixMoviendo", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "felix/felixMoviendo.png")));
		imagenes.put("felixMoviendoI", new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				Constantes.PATHIMAGES + "felix/felixMoviendoI.png")));
	}
	public void reiniciarPanelJuego() {
		remove(panelJuego);
		panelJuego = new PanelJuego();
		add(panelJuego);
	}

	public void pasando() {
		// TODO Auto-generated method stub
		
	}
}