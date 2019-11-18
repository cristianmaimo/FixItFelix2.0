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
	/**
	 * Lista con los 5 Puntajes Maximos y sus correspondientes autores.
	 */
	private ArrayList<Puntaje> puntajesMaximos;
	private Puntaje puntajeActual;
	
	private Dificultad dificultadBase;
	private Dificultad dificultadActual;
	private int nivelActual;
	
	private ArrayList<Proyectil> proyectiles;
	private Pastel pastel;
	private long delayPastel;

	Random randomizador = new Random();
	
	/**
	 * Model es un singleton que se contruye de modo estático.<br>
	 * Inicializa los valores para la creacion del primer nivel.
	 *
	 */
	private Model(){
		puntajesMaximos = new ArrayList<Puntaje>();
		try {
			FileInputStream file = new FileInputStream(new File(getClass().getResource(Constantes.PATHSAVES + "save.dat").toURI()));
			ObjectInputStream lectorObjetos = new ObjectInputStream(file);
			Puntaje pun = (Puntaje) lectorObjetos.readObject();
			for(int i=0; i<5; i++) {
				puntajesMaximos.add( (Puntaje) pun);
			    pun = (Puntaje) lectorObjetos.readObject();
			}
			lectorObjetos.close();
		}
		catch (Exception e){}
			if(puntajesMaximos.isEmpty()) {
				crearPuntajes();
				escribirPuntajes(puntajesMaximos);
		}
		puntajeActual = new Puntaje(0, "Desconocido");
		
		nivelActual = 1;
		dificultadBase = new Dificultad();
		dificultadActual = new Dificultad(dificultadBase, nivelActual);
		Edificio.nuevoNivel(dificultadActual);
		
		proyectiles = new ArrayList<Proyectil>(0);
		pastel = null;
		delayPastel = System.currentTimeMillis();
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
	public void actualizar()
			throws FinDeNivelException, FinDeSeccionException, ChoquePajaroException, ChoqueLadrilloException {

		Felix.getFelix().actualizar();
		Ralph.getRalph().actualizar();
		actualizarProyectiles();
		actualizarPastel();
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
	 * Verifica el estado y posicion actual del {@link Pastel}, en caso de no haber
	 * revisa si es posible su creación.
	 */
	private void actualizarPastel() {
		if ((pastel == null) && (!(Felix.getFelix().isInmune()))) {
			if ((System.currentTimeMillis() - delayPastel) >= Constantes.CHEQUEOPASTEL) {
				Posicion pos = new Posicion(Edificio.getEdificio().abierta());
				// en caso de null el constructor genera la posicion -1,-1
				if (!((pos.getCoordenadaX() == -1) && (pos.getCoordenadaY() == -1))) {
					if (randomizador.nextInt(101) < Constantes.CHANCEPASTEL) {
						pastel = new Pastel(pos);
						System.out.println(" Pastel Creado ");
					}
				}
			}
		}
		else if (pastel != null){
				if (pastel.esAlcanzado()) {
					pastel = null;
				}
			}
	}

	
	public Dificultad dificultadActual() {
		return dificultadActual;
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
		if (dificultadActual.CANTIDADPAJAROS == 1) {
			proyectiles.add(new Pajaro(dificultadActual.VELOCIDADLADRILLOS,
							new Posicion(-Constantes.PAJAROHITBOX.getAncho() - Constantes.OFFSETXSECCION + randomizador.nextInt(2) * (Constantes.ANCHOFRAME + Constantes.PAJAROHITBOX.getAncho() * 2 + Constantes.OFFSETXSECCION),
										  Constantes.PAJAROOFFSET + Constantes.ALTURAVENTANA * randomizador.nextInt(3))));
		} else if (dificultadActual.CANTIDADPAJAROS == 2) {
			int pos1 = Constantes.PAJAROOFFSET + Constantes.ALTURAVENTANA * randomizador.nextInt(3);
			int pos2 = Constantes.PAJAROOFFSET + Constantes.ALTURAVENTANA * randomizador.nextInt(3);
			while (pos1 == pos2)
				pos2 = Constantes.PAJAROOFFSET + Constantes.ANCHOVENTANA * randomizador.nextInt(3);
			proyectiles.add(new Pajaro(dificultadActual.VELOCIDADLADRILLOS,
					new Posicion(-Constantes.PAJAROHITBOX.getAncho() - Constantes.OFFSETXSECCION + randomizador.nextInt(2) * (Constantes.ANCHOFRAME + Constantes.PAJAROHITBOX.getAncho() * 2 + Constantes.OFFSETXSECCION), pos1)));
			proyectiles.add(new Pajaro(dificultadActual.VELOCIDADLADRILLOS,
					new Posicion(-Constantes.PAJAROHITBOX.getAncho() - Constantes.OFFSETXSECCION + randomizador.nextInt(2) * (Constantes.ANCHOFRAME + Constantes.PAJAROHITBOX.getAncho() * 2 + Constantes.OFFSETXSECCION), pos2)));
		} else if (dificultadActual.CANTIDADPAJAROS == 3) {
			proyectiles.add(new Pajaro(dificultadActual.VELOCIDADLADRILLOS,
					new Posicion(-Constantes.PAJAROHITBOX.getAncho() - Constantes.OFFSETXSECCION + randomizador.nextInt(2) * (Constantes.ANCHOFRAME + Constantes.PAJAROHITBOX.getAncho() * 2 + Constantes.OFFSETXSECCION), Constantes.PAJAROOFFSET + Constantes.ALTURAVENTANA * 2)));
			proyectiles.add(new Pajaro(dificultadActual.VELOCIDADLADRILLOS,
					new Posicion(-Constantes.PAJAROHITBOX.getAncho() - Constantes.OFFSETXSECCION + randomizador.nextInt(2) * (Constantes.ANCHOFRAME + Constantes.PAJAROHITBOX.getAncho() * 2 + Constantes.OFFSETXSECCION), Constantes.PAJAROOFFSET + Constantes.ALTURAVENTANA)));
			proyectiles.add(new Pajaro(dificultadActual.VELOCIDADLADRILLOS,
					new Posicion(-Constantes.PAJAROHITBOX.getAncho() - Constantes.OFFSETXSECCION + randomizador.nextInt(2) * (Constantes.ANCHOFRAME + Constantes.PAJAROHITBOX.getAncho() * 2 + Constantes.OFFSETXSECCION), Constantes.PAJAROOFFSET)));
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
	 * Comienza el nivel desde su estado inicial
	 * @see Ladrillo#hayColicion()
	 * @see Edificio#nuevoNivel(Dificultad)
	 * @see #reiniciarEntidades()
	 */
	public void reiniciarNivel() {
		Ralph.getRalph().reiniciar(Constantes.CANTLADRILLOS);
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
			Felix.getFelix().setPosicion(Constantes.FELIXSTART);
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
		proyectiles.clear();
		pastel = null;
		Ralph.getRalph().reiniciar();
		delayPastel = System.currentTimeMillis();
		if (Edificio.getEdificio().getSeccionActual() == 0) {
			Felix.getFelix().setPosicion(Constantes.FELIXSTART);
		} else {
			agregarPajaros();
			Felix.getFelix().setPosicion(Constantes.FELIXSTART2);
		}
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
	
	/**
	 * Muestra la pantalla de derrota si {@link Felix} perdio todas sus vida, o la de victoria si
	 * completo los 10 niveles.
	 */
	public void finalizarPartida() throws InterruptedException {
		TimeUnit.SECONDS.sleep(5);
	}

	public void setNivelActual(int i) {
		nivelActual = i;
	}

	public ArrayList<Puntaje> getPuntajesMaximos() {
		return puntajesMaximos;
	}

	public ArrayList<Proyectil> getProyectiles() {
		return this.proyectiles;
	}

	public Pastel getPastel() {
		return this.pastel;
	}
}
