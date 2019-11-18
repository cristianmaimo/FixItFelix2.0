package model.edificio;

import model.entidades.Felix;
import model.utilidades.Direccion;

/**
 * Subclase mas utilizada de {@link Ventana}, Solo tiene 2 paneles, puede tener una moldura y/o un macetero.
 * y la cantidad de roturas maximas es 4.
 * @author Cristian
 * @see Hoja
 * @see Semicircular
 */

public class Normal extends Ventana {
	
	/**
	 * Inicializa una ventana con 2 paneles sanos y randomiza si hay moldura y/o macetero según la cantidad
	 * indicada en el parametro recibído.
	 * @param obstaculosVerticales Solo acepta 0, 1 o 2.
	 * @see #randomizarObstaculosVerticales(int)
	 * @see	Ventana
	 * @see Hoja
	 * @see Semicircular
	 */
	Normal(int obstaculosVerticales) {
		super();
		roturasMaximas = 4;
		randomizarObstaculosVerticales(obstaculosVerticales);
	}
	
	/**
	 * Revisa si el panel inferior esta <kbd>TODOROTO</kbd> y retorna <kbd>True</kbd> o <kbd>False</kbd> adecuadamente.
	 * @see model.Model#actualizarPastel()
	 */
	@Override
	boolean inferiorDespejado() {
		if (paneles.get(0) == Panel.TODOROTO)
			return true;
		return false;
	}
	
	/**
	 * Retorna <kbd>True</kbd> si hay un obstaculo en la {@link Direccion} indicada por el parametro, <kbd>False</kbd> en caso contrario<br>
	 * Como solo puede tener moldura o macetero solo revisa hacia arriba o hacia abajo.
	 * @return <kbd>True</kbd> si hay un obstaculo en la direccion indicada por el parametro, <kbd>False</kbd> en caso contrario.<br>
	 * @see model.Model#actualizarFelix()
	 * @see Edificio#nuevaPosicion(Posicion, Direccion)
	 * @see Felix#mover(model.utilidades.Posicion)
	 */
	boolean hayObstaculo(Direccion direccion) {
		boolean obstaculo = false;
		switch (direccion) {
		case ARRIBA:
			if (hayMoldura)
				obstaculo = true;
			break;
		case ABAJO:
			if (hayMacetero)
				obstaculo = true;
			break;
		default:
			obstaculo = false;
			break;
		}
		return obstaculo;
	}
}