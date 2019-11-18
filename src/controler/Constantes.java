package controler;

import model.entidades.Ralph;
import model.utilidades.Hitbox;
import model.utilidades.Posicion;

/**
 * Coleccion de <kbd>CONSTANES</kbd> de valores que afectan distintas dinamicas del juego.
 * Las mismas son utilisadas en diversas partes del código.
 * 
 */
public class Constantes {
	public static final int PAUSALOOP = 300; // Milisegundos
	// Ventana
	public static final int ANCHOVENTANA = 68; // Pixeles
	public static final int ALTURAVENTANA = 112; // Pixeles
	public static final int CHANCECERRADA = 20; // Porcentaje
	
	// Seccion
	public static final int CANTIDADFILAS = 3;
	public static final int VENTANASPORFILA = 5;
	public static final int CANTIDADSECCIONES = 3;
	public static final int ANCHOSECCION = ALTURAVENTANA * CANTIDADFILAS; // Pixeles
	public static final int ALTURASECCION = ALTURAVENTANA * CANTIDADFILAS; // Pixeles
	public static final int OFFSETXSECCION = 40;
	public static final int OFFSETYSECCION = 129; // Techo
	
	// Edificio
	public static final int ANCHOFRAME = 420; // Pixeles
	public static final int ALTURAFRAME = 465; // Pixeles
	public static final int HEADER = 17;
	
	// Felix
	public static final int VIDAS = 5;
	public static final int FELIXOFFSETX = 20; // Pixeles
	public static final int FELIXOFFSETY = 40; // Pixeles
	public static final int FELIXOFFSETYPUERTA = 10; // Pixeles
	public static final int TIEMPOINMUNE = 8000; // Milisegundos
	public static final Hitbox FELIXHITBOX = new Hitbox(20, 50); // Pixeles
	
	public static final Posicion FELIXSTART = new Posicion(ANCHOVENTANA*2 + FELIXOFFSETX, ALTURAVENTANA * 2 + FELIXOFFSETY + FELIXOFFSETYPUERTA);
	public static final Posicion FELIXSTART2 = new Posicion(ANCHOVENTANA*2 + FELIXOFFSETX, ALTURAVENTANA * 2 + FELIXOFFSETY);
	public static final Posicion FELIXOFFSET = new Posicion(FELIXOFFSETX, FELIXOFFSETY);
	public static final Posicion FELIXOFFSETPUERTA = new Posicion(FELIXOFFSETX, FELIXOFFSETYPUERTA);
	
	// Ralph
	public static final int RALPHANCHO = 90;
	public static final int RALPHALTO = 108;
	public static final int CANTLADRILLOS = 40;
	public static final Posicion RALPHSTART = new Posicion(ANCHOVENTANA*2 , - RALPHALTO);
	public static final long TIEMPOMOVERRALPH = 5000;
	public static final int VELOCIDADRALPH = 5; // Pixeles por loop
	public static final int	CHANCEMOVER = 15; // Porcentaje
	public static final int CHANCEGOLPEAR = 5; // Porcentaje
	public static final long TIEMPOGOLPEANDO = 3000; // Milisegundos
		

	// Entidades
	public static final int CHANCEROMPER = 1;
	public static final Hitbox LADRILLOHITBOX = new Hitbox(20, 10);
	public static final Hitbox PAJAROHITBOX = new Hitbox(20, 10);
	public static final int PAJAROOFFSET = (int)(ALTURAVENTANA * 0.6);
	
	// Pastel
	public static final int PASTELOFFSETX = (int)(ANCHOVENTANA / 2); // Pixeles
	public static final int PASTELOFFSETY = (int)(ALTURAVENTANA * 0.7); // Pixeles
	public static final int CHANCEPASTEL = 40; // Porcentaje
	public static final int CHEQUEOPASTEL = 10000; // Milisegundos

	public static final String PATHIMAGES = "/assets/images/";
	public static final String PATHSAVES = "/assets/saves/";

}