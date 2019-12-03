package model.edificio;

import java.util.ArrayList;

import controler.Constantes;
import model.Model;
import model.entidades.Felix;
import model.entidades.Ladrillo;
import model.entidades.Pajaro;
import model.utilidades.Dificultad;
import model.utilidades.Direccion;
import model.utilidades.FinDeNivelException;
import model.utilidades.FinDeSeccionException;
import model.utilidades.Posicion;

/**
 * Clase encargada de la estructura lógica del nivel.
 * 
 * Implementa el patrón singleton para facilitar la comunicación tanto con las distintas <kbd>Entidades</kbd> 
 * como con {@link Model}. Todo acceso o modificación de estados en las clases {@link Seccion} y {@link Ventana}
 * se realiza a travéz de la única instancia de edificio. Ademas mantiene el estado de las 3
 * secciones en un array fijo, y un valor <i>seccionActual</i> para saber en cual de ellas ocurren los distintos
 * eventos actuales.
 * 
 * @author Cristian
 * @see Pajaro
 * @see Ladrillo
 * @see Felix
 */
public class Edificio {
	private static Edificio instancia;
	private Seccion[] secciones;
	private int seccionActual;
	
	// CONTRUCTORS
	/**
	 * Constructor privado que genera la única instancia de <kbd>Edificio</kbd>,
	 * utilizado al comienzo del juego y al finalizar un nivel.
	 * Toma los valores de las las constantes <i>VENTANASROTAS</i> y <i>CANTIDADOBSTACULOS</i>
	 * y los distribuye equitativamente atravez de las 3 secciones,
	 * si los valor no son divisibles por 3, los separa de tal manera que el <i>resto</i>
	 * esté en las secciones superiores, haciendolas levemente mas difíciles.
	 * 
	 * @param dif Utiliza los valores de la dificultad para la correcta randomizacion del nivel.
	 * @see Dificultad
	 * @see Seccion#Seccion(boolean esPrimeraSeccion, int cantidadRoturas, int cantidadObstaculos)
	 */
	private Edificio(Dificultad dif) {
		secciones = new Seccion[(Constantes.CANTIDADSECCIONES)];
		secciones[0] = new Seccion(true, (Math.floorDiv(dif.VENTANASROTAS,3)), (Math.floorDiv(dif.CANTIDADOBSTACULOS,3)));
		secciones[1] = new Seccion(false, (Math.round(dif.VENTANASROTAS/3)), (Math.round(dif.CANTIDADOBSTACULOS/3)));
		secciones[2] = new Seccion(false, (int) (Math.ceil(dif.VENTANASROTAS/3)), (int) (Math.ceil(dif.CANTIDADOBSTACULOS/3)));
		seccionActual = 0;
	}

	// PUBLIC
	/**	
	 * Acceso público a la instancia.
	 * 
	 * @return La unica instancia de Edificio.
	 */
	public static Edificio getEdificio(){
		return instancia;
		}
	
	/**
	 * Método público para la creacción de un nuevo nivel. Es utilizado exclusivamente por {@link Model}
	 * 
	 * @param nuevaDificultad Objeto que contiene los valores necesarios para la randomización de ventanas
	 * @see Dificultad
	 */	
	public static void nuevoNivel(Dificultad nuevaDificultad) {
		instancia = new Edificio(nuevaDificultad);
	}

	/**
	 * Modifica el estado de un <kbd>Panel</kbd> aleatorio en la {@link Ventana} correspondiente a la posicion
	 * recibida como parametro (según su estado actual, de <i>SANO</i> a <i>SEMIRROTO</i>, o de <i>SEMIROTO</i> A <i>TODOROTO</i>)<br>
	 * Además incrementa en 1 la cantidad de reparaciones necesarias en esa <kbd>Ventana</kbd>.<br>
	 * Si el estado de todos los paneles es <i>TODOROTO</i>, no hace nada.
	 * 
	 * @param pos Posicion de la ventana a romper
	 * @see Seccion#romper(Posicion)
	 * @see Ventana#romper()
	 * @see Ladrillo#romper()
	 */
	public void romper(Posicion pos){
		secciones[seccionActual].romper(pos);
	}

	/**
	 * Cambia el estado del primer <kbd>Panel</kbd> que no se encuentre <kbd>SANO</kbd> (empezando desde el inferior).
	 * Ademas reduce en 1 la cantidad de reparaciones necesarias en la {@link Ventana} 
	 * ubicada en la posición recibida como parametro.
	 * Si la reparacion fue exitosa llama a {@link Model#sumarPuntaje(int)} con el valor 100. Si es la última ventana
	 * de la sección, suma 400 extra y llama a {@link #avanzarSeccion()}.
	 * Si todos los paneles estan sanos, no hace nada.
	 * 
	 * @param pos Posicion a reparar.
	 * @throws FinDeNivelException 
	 * @throws FinDeSeccionException 
	 * @see Seccion#reparar(Posicion)
	 * @see Ventana#reparar()
	 * @see Felix#reparar()
	 * @see Model#sumarPuntaje(int)
	 */
	public void reparar(Posicion pos) throws FinDeSeccionException{
		if (secciones[seccionActual].reparar(pos) == 0) {
			Model.getModel().sumarPuntaje(400);
			throw new FinDeSeccionException();
		}
	}
	
	/**
	 *Si es la última sección llama a {@link Model#nivelCompletado()}, en caso contrario
	 *avanza la seccion actual, hace un llamado a {@link Model#reiniciarEntidades()}
	 *y reposiciona a {@link Felix} en el centro de la fila inferior.
	 * @throws FinDeNivelException 
	 * @throws FinDeSeccionException 
	 */	
	public void avanzarSeccion() throws FinDeNivelException {
		seccionActual++;
		Model.getModel().reiniciarEntidades();
		if (seccionActual >= Constantes.CANTIDADSECCIONES) {
			throw new FinDeNivelException();
		}
	}

	/**
	 * Pide a Model la dificultad actual para la re-randomización de la Sección, 
	 * ademas hace un llamado a {@link Model#reiniciarEntidades()}
	 * @see Pajaro#hayColicion()
	 * @see Seccion#Seccion(boolean esPrimeraSeccion, int cantidadRoturas, int cantidadObstaculos)
	 */
	public void reiniciarSeccion() {
		Model.getModel().reiniciarEntidades();
		Dificultad dif = Model.getModel().getDificultadActual();
		switch (seccionActual) {
		case 1:
			secciones[1] = new Seccion(false, (Math.round(dif.VENTANASROTAS/3)), (Math.round(dif.CANTIDADOBSTACULOS/3)));
			break;
		case 2:
			secciones[2] = new Seccion(false, (int) (Math.ceil(dif.VENTANASROTAS/3)), (int) (Math.ceil(dif.CANTIDADOBSTACULOS/3)));
			break;
		}
	}

	/**
	 * Realiza los chequeos necesarios para corroborar que el movimiento que intenta hacer {@link Felix} es válido.
	 * (Que no haya obstaculos y que no intente salirse de los limites de la seccion).
	 * 
	 * @param posActual Posicion actual de Felix
	 * @param dir Dirección en la cual Felix intenta moverse
	 * @return La nueva {@link Posicion} de {@link Felix}, o la misma en caso de haber un obstáculo
	 * 			o ser un movimiento no permitido
	 * @see Seccion#avanzar(Posicion, Direccion)
	 * @see Ventana#hayObstaculo(Direccion)
	 */
	public Posicion avanzar(Posicion posActual, Direccion dir) {
		Posicion posNueva = secciones[seccionActual].avanzar(posActual, dir);
		return posNueva;	
	}

	/**
	 * Busca y devuelve la {@link Posicion} una {@link Ventana}
	 * con el {@link EstadoPanel} inferior <kbd>TODOROTO</kbd>, En caso de haber mas de una, elije una aleatoria.
	 * Y si no la hay, devuelve null;
	 * @return La {@link Posicion} de una ventana en la que pueda aparecer un pastel.
	 * @see Seccion#hayAbierta()
	 * @see Ventana#inferiorDespejado()
	 * @see Model#actualizarPastel()
	 */
	public Posicion abierta() {
		Posicion pos = secciones[seccionActual].hayAbierta();
		return  pos;
		}

	public ArrayList<InfoVentana> infoSeccion(int seccion) {
		return secciones[seccion].getInfoSeccion();
	}
		
	//GETTERS y SETTERS
	public int getSeccionActual() {
		return seccionActual;
	}
	
}
