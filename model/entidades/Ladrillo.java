package model.entidades;

import java.util.Random;

import model.edificio.Edificio;
import model.utilidades.ChoqueLadrilloException;
import model.utilidades.Posicion;
import controler.Constantes;

/**
 * La clase Ladrillo describe los ladrillos que tira Ralph para golpear a Felix.
 * 
 * @author Edelmiro
 *
 */
public class Ladrillo extends Proyectil {
	private Random randomizador = new Random();
	
	//CONSTRUCTOR
	/**
	 * Un ladrillo se construye recibiendo un int que reprecenta la velocidad a la que cae y una Posicion inicial.
	 * Su hitbox está predefinida en Config.LADRILLOHITBOX.
	 */
	public Ladrillo(int vel, Posicion pos) {
		velocidad = vel;
		posicion = pos;
		indexAnim = 0;
		hitbox = Constantes.LADRILLOHITBOX;
	}
	
	//PUBLIC
	/**
	 * Cada vez que se llame al actualizar de un ladrillo este va comenzar moviéndose, luego si está dentro del edificio tiene un chance de romper.
	 * Se grafica y finalmente checkea si está colicionando con felix y de ser así le quita una vida y reinicia el nivel.
	 * @throws ChoqueLadrilloException 
	 */
	@Override
	public void actualizar() throws ChoqueLadrilloException {
		mover();
		if (randomizador.nextInt(101) < Constantes.CHANCEROMPER) {
			if (posicion.dentroDe(new Posicion(0,0), new Posicion(Constantes.ANCHOSECCION, Constantes.ALTURASECCION))) {
				romper();
			}
		}
		if (hayColicion()) {
			throw new ChoqueLadrilloException();
		}
	}
	
	/**
	 * El movimiento del ladrillo es simplemente "caer" avanzando en Y la cantidad de pixeles definida como velocidad.
	 */
	@Override
	public void mover() {
		posicion.addY(velocidad);
		if (indexAnim > (Constantes.LADRILLOFRAMES*2)) {
			indexAnim = 0;
		}
		else indexAnim++;
	}
	
	/**
	 * Llama al método romper del Edificio indicandole sobre qué ventana está parado.
	 */
	public void romper() {
		Edificio.getEdificio().romper(posicion.aVentana());
	}
	
	//GETERS Y SETTERS
	public String getNombreImagen() {
		String name = "ladrillo";
		
		if (indexAnim > Constantes.LADRILLOFRAMES) {
			name += "1";
		}
		else name += "0";
		return name;
	}
}
