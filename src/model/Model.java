package model;

import java.awt.Dimension;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import controler.Constantes;
import model.edificio.Edificio;
import model.edificio.InfoVentana;
import model.entidades.Felix;
import model.entidades.Ladrillo;
import model.entidades.Pajaro;
import model.entidades.Pastel;
import model.entidades.Proyectil;
import model.entidades.Ralph;
import model.utilidades.ChoqueLadrilloException;
import model.utilidades.ChoquePajaroException;
import model.utilidades.Dificultad;
import model.utilidades.FinDeJuegoException;
import model.utilidades.FinDeSeccionException;
import model.utilidades.Posicion;
import model.utilidades.Puntaje;
import model.utilidades.Temporizador;
import view.View;

/**
 * La clase Model se encarga de la lógica general del juego. Inicio y fin de nivel y partida, dificultad, conteo de puntajes,
 * retiene informacion de {@link Ralph} y de las Entidades que interactúan {@link Felix},
 * asi como la creacion y destruccion de las mismas.
 * 
 * @see Ralph
 * @see Felix
 * @see Edificio
 * @see Entidad
 */
public class Model{
	private static Model instancia;
	private ArrayList<Puntaje> puntajesMaximos;
	private Puntaje puntajeActual;
	private Dificultad dificultadBase;
	private Dificultad dificultadActual;
	private int nivelActual;
	private ArrayList<Proyectil> proyectiles;
	private Pastel pastel;
	private Temporizador temporizador;
	Dimension a,v;
	
	private Random randomizador = new Random();
	
	//CONSTRUCTOR
	/**
	 * Model es un singleton que se contruye de modo estático.<br>
	 * Inicializa los valores para la creacion del primer nivel.
	 *
	 */
	private Model(){
		puntajesMaximos = new ArrayList<Puntaje>(5);
		try {
			cargarPuntajes();
		} catch(IOException e) {
			System.out.println("Problema al leer archivo highscores.dat");
		}
		puntajeActual = new Puntaje(0, "Desconocido");
		nivelActual = 1;
		dificultadBase = new Dificultad();
		dificultadActual = new Dificultad(dificultadBase, nivelActual);
		
		Edificio.nuevoNivel(dificultadActual);
		Ralph.iniciarRalph();
		Felix.iniciarFelix();
		proyectiles = new ArrayList<Proyectil>(0);
		pastel = new Pastel();
		temporizador = new Temporizador(dificultadActual.TIEMPOLIMITE);
	}	
	
	//PUBLIC
	public static Model getModel() {
		return instancia;
	}	
	
	/**
	 * Metodo llamado para el comienzo de la partida. 
	 * @param movimientos String que representa las acciones de Felix
	 */
	public static void iniciarModel() {
		instancia = new Model();
	}
 	
	/**
	 * Metodo <b>principal</b>, see encarga de actualizar a Ralph y a todas las entidades para
	 * su correcta interaccion.
	 * El juego sale de este metodo solo cuando Felix se queda sin vidas o completa todos los niveles. 
	 * @throws FinDeSeccionException 
	 * @throws FinDeNivelException 
	 * @throws ChoqueLadrilloException 
	 * @throws ChoquePajaroException 
	 * @throws FinDeJuegoException 
	 */
	public void actualizar()throws FinDeSeccionException, ChoquePajaroException, ChoqueLadrilloException, FinDeJuegoException{
		temporizador.actualizar();
		Ralph.getRalph().actualizar();
		Felix.getFelix().actualizar();
		pastel.actualizar();
		try {
			actualizarProyectiles();
			} catch (ChoquePajaroException e) {
				Felix.getFelix().colicion(false);
			} catch (ChoqueLadrilloException e) {
				Felix.getFelix().colicion(true);
			}
	}
	
	/**
	 * Se encarga de actualizar la posicione de los proyectiles
	 * @throws ChoqueLadrilloException 
	 * @throws ChoquePajaroException 
	 * @see Proyectil
	 */
	private void actualizarProyectiles() throws ChoquePajaroException, ChoqueLadrilloException {
		Iterator<Proyectil> it = proyectiles.iterator();
		Proyectil actual;
		while (it.hasNext()) {
			actual = it.next();
			actual.actualizar();
			if (actual.getPosicion().getCoordenadaY() > Constantes.ALTURAVENTANA *3) {
				it.remove();
			}
		}
	}
		
	/**
	 * Crea un ladrillo en la posicion recibida como parametro y lo agrega a la coleccion de proyectiles.
	 * @param pos Objeto de tipo {@link Posicion}
	 */
	public void agregarLadrillo(Posicion pos) {
		proyectiles.add(new Ladrillo(dificultadActual.VELOCIDADLADRILLOS, pos));
	}
	
	/**
	 * Crea la cantidad adecuada de pajaros según el nivel actual y los agrega a la coleccion de proyectiles
	 * @see Pajaro
	 * @see Dificultad
	 */
	public void agregarPajaros() {
		int coordY1 = Constantes.PAJAROOFFSET + (Constantes.ALTURAVENTANA * randomizador.nextInt(3));
		int rand = randomizador.nextInt(2);
		if (dificultadActual.CANTIDADPAJAROS == 1) {
			if (rand == 0)
				proyectiles.add(new Pajaro(dificultadActual.VELOCIDADPAJAROS, coordY1));
		} else if (dificultadActual.CANTIDADPAJAROS == 2) {
			int coordY2 = Constantes.PAJAROOFFSET + (Constantes.ALTURAVENTANA * randomizador.nextInt(3));
			while (coordY1 == coordY2)
				coordY2 = Constantes.PAJAROOFFSET + (Constantes.ALTURAVENTANA * randomizador.nextInt(3));
			proyectiles.add(new Pajaro(dificultadActual.VELOCIDADPAJAROS,coordY1));
			proyectiles.add(new Pajaro(dificultadActual.VELOCIDADPAJAROS,coordY2));
		} else if (dificultadActual.CANTIDADPAJAROS == 3) {
			proyectiles.add(new Pajaro(dificultadActual.VELOCIDADPAJAROS, Constantes.PAJAROOFFSET + Constantes.ALTURAVENTANA * 2));
			proyectiles.add(new Pajaro(dificultadActual.VELOCIDADPAJAROS, Constantes.PAJAROOFFSET + Constantes.ALTURAVENTANA));
			proyectiles.add(new Pajaro(dificultadActual.VELOCIDADPAJAROS, Constantes.PAJAROOFFSET));
		}		
	}

	/**
	 * Comienza el nivel desde su estado inicial
	 * @see Ladrillo#hayColicion()
	 * @see Edificio#nuevoNivel(Dificultad)
	 * @see #reiniciarEntidades()
	 */
	public void reiniciarNivel() {
		Edificio.nuevoNivel(dificultadActual);
		reiniciarEntidades();
	}	
	
	/**
	 * Crea el siguiente nivel, con una dificultad mayor. Si no quedan mas niveles finaliza la partida y reinicia los estados del
	 * juego a los iniciales.
	 * @throws FinDeJuegoException 
	 * 
	 * @see Edificio#avanzarSeccion()
	 */
	public void avanzarNivel() throws FinDeJuegoException {
		nivelActual++;
		if (nivelActual <= 10) {
			dificultadActual = new Dificultad(dificultadBase, nivelActual);
			Edificio.nuevoNivel(dificultadActual);
			reiniciarEntidades();
			Ralph.getRalph().reiniciar();
			temporizador = new Temporizador(dificultadActual.TIEMPOLIMITE);
		}
		else {
			throw new FinDeJuegoException();
		}
	}
	
	/**
	 * Borra los elementos de la coleccion de proyectiles, y reposiciona a {@link Felix}
	 * en la ventana central de la fila inferior
	 */
	public void reiniciarEntidades() {
		Ralph.getRalph().reiniciar(Constantes.CANTLADRILLOS);
		pastel = new Pastel();
		proyectiles.clear();
		if (Edificio.getEdificio().getSeccionActual() == 0) {
			Felix.getFelix().setPosicion(Constantes.FELIXSTART);
		} else {
			agregarPajaros();
			Felix.getFelix().setPosicion(Constantes.FELIXSTART2);
		}
	}

	/**
	 * Suma al puntaje actual el valor recibido como parametro
	 * @param valor Tipo <kbd>int</kbd>
	 */
	public void sumarPuntaje(int valor) {
		puntajeActual.sumar(valor);
	}
	
	/**
	 * Compara el puntaje actual con la lista de {@link #puntajesMaximos}
	 * retorna el indice donde agregar el nuevo puntaje maximo, o -1 en caso de no serlo
	 */
	public int verificarPuntaje() {
		int i;
		if ((puntajeActual.getPuntaje() > puntajesMaximos.get(4).getPuntaje())) {
			for (i = 0; i < puntajesMaximos.size(); i++) {
				if (puntajeActual.getPuntaje() > puntajesMaximos.get(i).getPuntaje()) {
					break;
				}
			}
		} else i = -1;
		return i;
	}

	public void actualizarPuntajes(int indexPuntaje) {
		puntajesMaximos.add(indexPuntaje, puntajeActual);
		puntajesMaximos.remove(5);
		try {
		escribirPuntajes();
		} catch(IOException e) {
			System.out.println("Problema al escribir archivo highscores.dat");
		}
	}

	
	//GETTERS Y SETTERS
	public Dificultad getDificultadActual() {
		return dificultadActual;
	}
	public ArrayList<Puntaje> getPuntajesMaximos() {
		return puntajesMaximos;
	}
	public ArrayList<Proyectil> getProyectiles() {
		return proyectiles;
	}
	public ArrayList<InfoVentana> infoSeccion(int seccion){
		return Edificio.getEdificio().infoSeccion(seccion);
	}
	public void setNivelInicial(int nivelInicial) {
		nivelActual = nivelInicial;
		dificultadActual = new Dificultad(dificultadBase, nivelActual);
		Edificio.nuevoNivel(dificultadActual);
		temporizador = new Temporizador(dificultadActual.TIEMPOLIMITE);
		View.getView().panelJuego.actualizarMarcos();
	}
	public int getPuntajeActual() {
		return puntajeActual.getPuntaje();
	}
	public void setNombre(String nuevoNombre) {
		puntajeActual.setNombre(nuevoNombre);
	}
	public Pastel getPastel() {
		return pastel;
	}
	public int getNivelActual() {
		return nivelActual;
	}
	public Temporizador getTemporizador() {
		return temporizador;
	}
	//PRIVATE
	private ArrayList<Puntaje> cargarPuntajes() throws IOException  {
		File highscores = new File(Paths.get(Constantes.PATHPUNTAJES).toAbsolutePath().toString());
		ObjectInputStream lectorObjetos = null;
		try {
			FileInputStream file = new FileInputStream(highscores);
			lectorObjetos = new ObjectInputStream(file);
			Puntaje pun;
			for (int i = 0; i < 5; i++) {
				pun = (Puntaje) lectorObjetos.readObject();
				puntajesMaximos.add(pun);
			}
		} catch (EOFException e) {
			// Archivo alterado
			for (int i = 0; i < 5; i++)	puntajesMaximos.add(new Puntaje());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			//Archivo inexistente
			for (int i = 0; i < 5; i++)	puntajesMaximos.add(new Puntaje());
		} finally {
			if (lectorObjetos != null) lectorObjetos.close();
			escribirPuntajes();
		}
		return null;
	}
	
	private void escribirPuntajes() throws IOException {
		Path path = Paths.get(Constantes.PATHPUNTAJES);
		Files.deleteIfExists(path);
		File highscores = new File(path.toAbsolutePath().toString());
		ObjectOutputStream grabadorObjetos = null;
		try {
			highscores.createNewFile();
			FileOutputStream file = new FileOutputStream(highscores);
			grabadorObjetos = new ObjectOutputStream(file);
			for (Puntaje actual : puntajesMaximos) {
				grabadorObjetos.writeObject(actual);
			}
			grabadorObjetos.close();
		} catch (IOException e) {
			throw e;
		}
	}

	public void reiniciarProyectiles() {
		proyectiles.clear();
	}
}
