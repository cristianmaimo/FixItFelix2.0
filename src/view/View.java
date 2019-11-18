package view;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import controler.Constantes;

public class View extends JFrame{
	private static View instancia;
	Dimension tama�oPantalla;
	Dimension tama�oVentana;
	
	public JPanel mainPanel;
	public MainMenu mainMenu;
	public Config config;
	public Reglas reglas;
	public TopScores top5;
	
	public static View getView(){
		return instancia;
	}
	public static void iniciarView() {
		instancia = new View();
	}
	
	private View() {
		tama�oPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		tama�oVentana = new Dimension(Constantes.ANCHOFRAME, Constantes.ALTURAFRAME);
		setTitle("Fix it Felix jr.");
		setLocationRelativeTo(null);
		setLocation((tama�oPantalla.width/2) - (Constantes.ANCHOFRAME/2), 0);
		setResizable(false);
		getContentPane().setBackground(Color.black);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
//		setFocusable(true);
//		requestFocusInWindow();
//		setFocusTraversalKeysEnabled(false);
		
		mainPanel = new JPanel(null);
		mainPanel.setBorder(null);
		mainPanel.setLocation(0,0);
		mainPanel.setPreferredSize(tama�oVentana);
		getContentPane().add(mainPanel);
		pack();
		
		mainMenu = new MainMenu(tama�oVentana);
		config = new Config(tama�oVentana);
		reglas = new Reglas(tama�oVentana);
		top5 = new TopScores(tama�oVentana);
		
		mainPanel.add(mainMenu);
		mainPanel.add(config);
		mainPanel.add(reglas);
		mainPanel.add(top5);

		setVisible(true);
	}

	public Dimension getTama�oVentana() {
		return tama�oVentana;
	}
}