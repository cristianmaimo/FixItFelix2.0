package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import controler.Constantes;
import model.edificio.Edificio;
import model.edificio.InfoVentana;
import model.entidades.*;
import model.utilidades.*;

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
	ObjectOutputStream salida = null;
	ObjectInputStream entrada = null;
	private static Model instancia;
	private ArrayList<Puntaje> puntajesMaximos;
	private Puntaje puntajeActual;
	private Dificultad dificultadBase;
	private Dificultad dificultadActual;
	private int nivelActual;
	private ArrayList<Proyectil> proyectiles;
	private Pastel pastel;
	private Random randomizador = new Random();
	
	private ArrayList<InfoVentana> cambiosVentanas;
	
	//CONSTRUCTOR
	/**
	 * Model es un singleton que se contruye de modo estático.<br>
	 * Inicializa los valores para la creacion del primer nivel.
	 *
	 */
	private Model(){
//		puntajesMaximos = cargarPuntajes();
		puntajeActual = new Puntaje(0, "");
		nivelActual = 1;
		dificultadBase = new Dificultad();
		dificultadActual = new Dificultad(dificultadBase, nivelActual);
		
		Edificio.nuevoNivel(dificultadActual);
		Ralph.iniciarRalph();
		Felix.iniciarFelix();
		proyectiles = new ArrayList<Proyectil>(0);
		pastel = new Pastel();
		cambiosVentanas = null;
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
	 */
	public void actualizar()throws FinDeNivelException, FinDeSeccionException{
		cambiosVentanas = null;
		Ralph.getRalph().actualizar();
		pastel.actualizar();
		try {
			actualizarProyectiles();
		}
		catch (ChoquePajaroException e) {
			Felix.getFelix().colicion(false);
		}
		catch (ChoqueLadrilloException e) {
			Felix.getFelix().colicion(true);
		}
		finally	{
			Felix.getFelix().actualizar();
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
		for (int i = 0; i < proyectiles.size(); i++){
			it.next().actualizar();
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
		Felix.getFelix().setPosicion(Constantes.FELIXSTART2);
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
	 * Compara el puntaje actual con la lista de {@link #puntajesMaximos} agrega de de manera ordenada en caso
	 * de ser un nuevo Puntaje Maximo.
	 */
	public void verificarPuntaje(String nombre) {
		if ((puntajesMaximos.size() < 5) || (puntajeActual.getPuntaje() > puntajesMaximos.get(4).getPuntaje())) {
			Iterator<Puntaje> it = puntajesMaximos.iterator();
			int i;
			for (i = 0 ; i < puntajesMaximos.size() ; i++) {
				if (it.hasNext()) {
					if (puntajeActual.getPuntaje() > puntajesMaximos.get(i).getPuntaje()) {
						continue;
					}
				}
			}
			puntajeActual.setNombre(nombre);
			puntajesMaximos.add(--i, puntajeActual);
			System.out.println(" Nuevo Puntaje Maximo ");
		}
		if (puntajesMaximos.size() > 5) puntajesMaximos.remove(5);
	}

	public void cambioVentana(InfoVentana infoVentana) {
		if (cambiosVentanas == null) cambiosVentanas = new ArrayList<InfoVentana>();
		cambiosVentanas.add(infoVentana);
	}
	
	//GETTERS Y SETTERS
	public Dificultad getDificultadActual() {
		return dificultadActual;
	}
	public ArrayList<Puntaje> getPuntajesMaximos() {
		return puntajesMaximos;
	}
	public ArrayList<Proyectil> getProyectiles() {
		return this.proyectiles;
	}
	public ArrayList<InfoVentana> infoSeccion(int seccion){
		return Edificio.getEdificio().infoSeccion(seccion);
	}
	public void setNivelActual(int i) {
		nivelActual = i;
		dificultadActual = new Dificultad(dificultadBase, nivelActual);
		Edificio.nuevoNivel(dificultadActual);
	}
	public ArrayList<InfoVentana> cambiosVentanas(){
		return cambiosVentanas;
	}
	
	//PRIVATE
	private ArrayList<Puntaje> cargarPuntajes(){
		try {
			FileInputStream file = new FileInputStream(
					new File(getClass().getResource(Constantes.PATHSAVES + "save.dat").toURI()));
			ObjectInputStream lectorObjetos = new ObjectInputStream(file);
			Puntaje pun = (Puntaje) lectorObjetos.readObject();
			for (int i = 0; i < 5; i++) {
				puntajesMaximos.add((Puntaje) pun);
				pun = (Puntaje) lectorObjetos.readObject();
			}
			lectorObjetos.close();
		} catch (Exception e) {
		}
		if (puntajesMaximos.isEmpty()) {
			crearPuntajes();
			escribirPuntajes(puntajesMaximos);
		}
		return null;
	}
	private void escribirPuntajes(ArrayList<Puntaje> puntajesMaximos2) {
		try {
			FileOutputStream file = new FileOutputStream(new File(getClass().getResource(Constantes.PATHSAVES + "saves.dat").toURI()));
			ObjectOutputStream grabadorObjetos = new ObjectOutputStream(file);
			for (Puntaje pun : puntajesMaximos2) {
				grabadorObjetos.writeObject(pun);
			}
			grabadorObjetos.close();
		} catch (Exception e) {}
	}
	private void crearPuntajes() {
		puntajesMaximos.add(new Puntaje(12300, "Pepito"));
		puntajesMaximos.add(new Puntaje(9700, "Juanita"));
		puntajesMaximos.add(new Puntaje(5500, "Ramon"));
		puntajesMaximos.add(new Puntaje(3600, "Pedro"));
		puntajesMaximos.add(new Puntaje(1000, "Pedro"));
	}



}
