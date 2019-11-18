package model.edificio;

import java.util.Random;

import controler.Constantes;
import model.utilidades.Direccion;

/**
 * Las ventanas del tipo <kbd>Hoja</kbd> nunca se rompen, pero a diferencia de las {@link Normal} y {@link Semicircular}
 * poseen una hoja que puede estar en estado <i>ABIERTA_IZQUIERDA</i> <i>ABIERTA_DERECHA</i>, impidiendo el paso en esa direccion.
 * O pueden estar cerradas, lo si cual permite avanzar.
 * * 
 * @author Cristian
 * 

 */
public class Hoja extends Ventana {
	public Estado estado;

	/**
	 * Inicializa una ventana con 2 Paneles <i>SANOS</i>, <i>roturasMaximas</i> en 0. Y
	 * utiliza el valor indicado como parametro para randomizar si tiene macetero y/o ventana<br>
	 * Por ultimo randomiza el estado de la hoja, con la misma probabilidad de ser IZQUIERDA o DERECHA,
	 * y una pequeña chance de estar CERRADA.
	 * @see Ventana
	 * @see Normal
	 * @see Semicircular
	 */
	Hoja(int obstaculosVerticales) {
		super();
		roturasMaximas = 0;
		randomizarObstaculosVerticales(obstaculosVerticales);
		Random randomizador = new Random();
		if (randomizador.nextInt(101) < Constantes.CHANCECERRADA) estado = Estado.CERRADA;
		else {
			switch (randomizador.nextInt(2)) {
			case 0:
				estado = Estado.ABIERTA_IZQUIERDA;
				break;
			case 1:
				estado = Estado.ABIERTA_DERECHA;
				break;
			}
		}
	}

	/**
	 * Sobreescritura de metodo, siempre retorna False ya que las ventanas con hojas siempre estan sanas.
	 * @return False
	 */
	@Override
	boolean reparar() {
		return false;
	}
	
	/**
	 * Sobreescritura de metodo, siempre retorna False ya que las ventanas con hojas no pueden romperse.
	 * @return False
	 */
	boolean romper() {
		return false;
	}
	
	/**
	 * Sobreescritura de metodo, siempre retorna False ya que las ventanas con hojas no pueden romperse.
	 * @return False
	 */
	boolean inferiorDespejado() {
		return false;
	}
	
	/**
	 * Se sobreescribió este método ya que las Hojas abiertas tambien pueden impidir el movimiento horizontal.
	 * @see Ventana#hayObstaculo(Direccion)
	 */
	@Override
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
		case IZQUIERDA:
			if (estado == Estado.ABIERTA_IZQUIERDA)
				obstaculo = true;
			break;
		case DERECHA:
			if (estado == Estado.ABIERTA_DERECHA)
				obstaculo = true;
			break;
		default:
			obstaculo = false;
			break;
		}
		return obstaculo;
	}


	/**
	 *  Metodo auxiliar previo a la implementacion de la interfaz gráfica.
	 *  @see Graficador
	 */
	public String toString() {
			String representacion = "";
		
			switch (estado) {
			case ABIERTA_IZQUIERDA:
				representacion += "I";
				break;
			case ABIERTA_DERECHA:
				representacion += "D";
				break;
			case CERRADA:
				representacion += "C";
				break;
			}	
			
			if (hayMoldura) {representacion += "T";}
			else {representacion += "F";		}
			if (hayMacetero) {representacion += "T";}
			else {representacion += "F";}
			representacion += "0" + Integer.toString(this.reparacionesRestantes);
			representacion += "0" + Integer.toString(this.roturasMaximas);
			return representacion;
		}	
}
