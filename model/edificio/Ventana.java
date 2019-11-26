package model.edificio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import model.entidades.Felix;
import model.entidades.Ladrillo;
import model.utilidades.Direccion;

/**
 * Clase abstracta que representa la estructura lógica mas basica del de cada nivel.<br>
 * Utiliza una colección del tipo <kbd>ArrayList</kbd> de tipo {@link EstadoPanel}], su tamaño depende
 * de la implementación de la subclase<br>
 * Los atributos <kbd>hayMoldura</kbd> y <kbd>hayMacetero</kbd> se utilizan para
 *  el chequeo de obstaculos cuando {@link Felix} se mueve.<br>
 * Y los atributos <kbd>reparacionesRestantes</kbd> y <kbd>roturasMaximas</kbd> para facilitar el conteo de roturas.
 * Las posibles sub clases son {@link Normal}, {@link Hoja}y {@link Semicircular} 
 * @author Cristian
 * @see Seccion
 * @see Felix#reparar()
 * @see Ladrillo#romper()
 */
abstract class Ventana{
	public ArrayList<Panel> paneles;
	public boolean hayMoldura;
	public boolean hayMacetero;
	protected int reparacionesRestantes;
	public int roturasMaximas;


	// CONSTRUCTOR
	/**
	 * Inicializa una ventana sin obstaculos y con 2 paneles, ambos sanos. (Caso mas básico)
	 */
	protected Ventana() {
		paneles = new ArrayList<Panel>(2);
		paneles.add(0, new Panel(EstadoPanel.SANO));
		paneles.add(1, new Panel(EstadoPanel.SANO));
		reparacionesRestantes = 0;
	}
	
	// ABSTRACT
	/**
	 * Las subclases deben implementar el chequedo de un espacio adecuado para la creacion de del pastel.
	 * @return True si la ventana es apta para la creacion del pastel
	 * @see Normal
	 * @see Hoja
	 * @see Seccion
	 */
	abstract boolean inferiorDespejado();
	abstract InfoVentana getInfoVentana();
	
	/**
	 * Las subclases deben implementar el chequeo de obstaculos
	 * @param direccion
	 * @return Devuelve <i>True</i> si la ventana no posee un obstaculo en la {@link Direccion} indicada.
	 * <i>False</i> en caso contrario.
	 * @see Normal
	 * @see Hoja
	 * @see Seccion
	 */
	abstract boolean hayObstaculo(Direccion direccion);
	
	/**
	 * Utilizado durante la inicializacion de la ventana, actualiza aleatoriamente
	 * los valores de <kbd>hayMoldura</kbd> y <kbd>hayMacetero</kbd> según indicado por el parametro recibído.
	 * El mismo solo puede ser 0, 1 o 2.
	 * 
	 * @param obstaculosVerticales
	 * @see Seccion#Seccion(boolean,int,int)
	 */
	void randomizarObstaculosVerticales(int obstaculosVerticales) {
		switch (obstaculosVerticales) {
		case 0:
			hayMoldura = false;
			hayMacetero = false;
			break;
		case 1:
			Random randomizador = new Random();
			if (randomizador.nextInt(2) == 0) {
				hayMoldura = true;
				hayMacetero = false;
			} else {
				hayMoldura = false;
				hayMacetero = true;
			}
			break;
		case 2:
			hayMoldura = true;
			hayMacetero = true;
			break;
		}
	}

	/**
	 * Elije aleatoriamente un {@link EstadoPanel} <kbd>SANO</kbd> o <kbd>SEMIROTO</kbd>, y lo modifica
	 * a <kbd>SEMIROTO</kbd> o <kbd>TODOROTO</kbd> respectivamente. Y retorna True.<br>
	 * 
	 * En caso de que todos los paneles esten en el estado , retorna FALSE.
	 * @return True si se rompio un panel, False en caso contrario.
	 * @see Seccion#randomizarRoturas(int)
	 * @see Ladrillo#romper()
	 */
	boolean romper() {
		boolean seRompio = false;
		// si la ventana esta completamente rota, directamente devuelve falso
		if (reparacionesRestantes < roturasMaximas) {
			// Crea una lista con los inices de los paneles que pueden romperse
			LinkedList<Integer> tempList = new LinkedList<Integer>();
			for (int i = 0; i < paneles.size(); i++) {
				if (paneles.get(i).getEstado() != EstadoPanel.TODOROTO) {
					tempList.add(i);
				}
			}
			// Itera una cantidad de veces aleatoria sobre la lista y rompe
			// el panel correspondiente en el atributo "paneles"
			Random rand = new Random();
			Iterator<Integer> it = tempList.iterator();
			int panelElejido = 0;
			int iteracionesElejidas = rand.nextInt(tempList.size())+1;
			for (int i=0; i < iteracionesElejidas; i++) {
				panelElejido = it.next();
			}
			switch (paneles.get(panelElejido).getEstado()) {
				case SANO:
					paneles.set(panelElejido, new Panel(EstadoPanel.SEMIROTO,rand.nextInt(4)));
					seRompio = true;
					reparacionesRestantes++;
					break;
				case SEMIROTO:
					paneles.set(panelElejido, new Panel(EstadoPanel.TODOROTO));
					seRompio = true;
					reparacionesRestantes++;
					break;
				case TODOROTO: // Logicamente inalcanzable, agregado para evitar error de compilación.
					break;
			}
		}
		return seRompio;
	}
	
	/**
	 * Busca el siguiente {@link EstadoPanel} con estado <kbd>SEMIROTO</kbd> o <kbd>TODOROTO</kbd>
	 * (comenzando del inferior y buscando hacia arriba). Y cambia su estado a <kbd>SANO</kbd> o 
	 * <kbd>SEMIROTO</kbd> respectivamente, y retorna True.<br>
	 * Retorna False en caso de estar todos en estado <kbd>SANO</kbd>
	 * 
	 * @return True si se reparo un panel, False en caso contrario.
	 * @see Felix#reparar()
	 */
	int reparar() {
		int seReparo = 0;
		int i = 0;
		while ((i < paneles.size()) && (seReparo == 0)) {
			switch (paneles.get(i).getEstado()) {
			case SANO:
				i++;
				break;
			case SEMIROTO:
				paneles.set(i, new Panel(EstadoPanel.SANO));
				reparacionesRestantes--;
				seReparo = 2;
				break;
			case TODOROTO:
				Random rand = new Random();
				paneles.set(i,new Panel(EstadoPanel.SEMIROTO, rand.nextInt(4)));
				reparacionesRestantes--;
				seReparo = 1;
				break;
			}
		}
		return seReparo;
	}
	
	// GETTERS Y SETTERS
	int getRoturasMaximas() {
		return roturasMaximas;
	}

}
