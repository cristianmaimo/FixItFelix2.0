package model.edificio;

import java.util.ArrayList;

import model.utilidades.Direccion;

/**
 * Las ventanas del tipo <kbd>Semicircular</kbd> se diferencian en que pueden tener 4 u 8 paneles,
 * incrementando la candidad maxima de roturas. Y ademas no tienen ningun tipo de obstaculo.
 * @author Cristian
 * @see Ventana
 * @see Normal
 * @see Hoja
 */
public class Semicircular extends Ventana {

	/**
	 * Inicializa una {@link Ventana} con 4 u 8 paneles (segun indicado por los parametros), y sin obstaculos.
	 * @param cantidadPaneles
	 * @param roturasMaximas
	 * @see Ventana
	 * @see Normal
	 * @see Hoja
	 */
	Semicircular(int cantidadPaneles, int roturasMaximas) {
		paneles = new ArrayList<Panel>(cantidadPaneles);
		for (int i = 0; i < cantidadPaneles; i++) {
			paneles.add(i, Panel.SANO);
		}
		hayMoldura = false; // Las semicirculares nunca tienen moldura ni macetero
		hayMacetero = false;
		reparacionesRestantes = 0;
		this.roturasMaximas = roturasMaximas;
	}

	/**
	 * Se cobreescrió el método ya que no pueden salir habitantes en las <kbd>Semicirculares</kbd>
	 * @return <i>False</i>
	 * @see Ventana
	 * @see Normal
	 * @see Hoja
	 */
	boolean inferiorDespejado() {
		return false;
	}
	
	/**
	 * Se cobreescrió el método ya que <kbd>Semicirculares</kbd> mo tienen obstaculos
	 * @return <i>False</i>
	 * @see Ventana
	 * @see Normal
	 * @see Hoja
	 */
	boolean hayObstaculo(Direccion direccion) {
		return false;
	}
}
