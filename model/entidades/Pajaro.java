package model.entidades;
import java.util.Random;

import controler.Constantes;
import model.utilidades.ChoquePajaroException;
import model.utilidades.Posicion;

/**
 * La clase Pajaro describe los pajaros que pasan en las secciones 2 y 3 y que pueden golpear a Felix quitandole una vida y reiniciando la secci�n.
 * 
 * @author Edelmiro
 *
 */
public class Pajaro extends Proyectil {
	Random randomizador = new Random();
	
	//CONSTRUCTORS
	/**
	 * Cuando se instancia un pajaro obtiene su velocidad y coordenadaY como parametro, randomiza su posicion en X y busca su hitbox de la clase Constantes. 
	 */
	public Pajaro(int vel, int coordY) {
		velocidad = vel;
		int coordX;
		if (randomizador.nextInt(2) == 0) coordX = -Constantes.PAJAROHITBOX.getAncho() - Constantes.OFFSETXSECCION;
		else coordX = Constantes.ANCHOSECCION + Constantes.OFFSETXSECCION;
		posicion = new Posicion(coordX, coordY);
		indexAnim = 0;
		hitbox = Constantes.PAJAROHITBOX;
	}
	
	//PUBLIC
	/**
	 * El movimiento de los pajaros es horizontal sumando a la coordenadaX el valor en velocidad que puede ser positivo o negativo si est� llendo hacia la derecha o izaquierda respectivamente.
	 */
	@Override
	public void mover() {
		posicion.addX(velocidad);
		if (indexAnim > (Constantes.PAJAROFRAMES*2)) {
			indexAnim = 0;
		}
		else indexAnim++;
	}
	/**
	 * Cuando se actualiza un Pajaro empieza moviendose, luego si ya sali� de la pantalla gira para ir hacia el otro lado, 
	 * se grafica y finalmente finalmente checkea si est� colicionando con felix y de ser as� le quita una vida y reinicia la secci�n.
	 * @throws ChoquePajaroException 
	 */
	@Override
	public void actualizar() throws ChoquePajaroException {
		mover();
		if ((posicion.getCoordenadaX() < (-hitbox.getAncho() - Constantes.OFFSETXSECCION)) ||
			(posicion.getCoordenadaX() > (Constantes.ANCHOSECCION + Constantes.OFFSETXSECCION))) {
			girar();
		}
		if (hayColicion()) {
			throw new ChoquePajaroException();
		}
	}
	/**
	 * El m�todo girar multiplica la velocidad por -1 para invertir la direcci�n del movimiento.
	 */
	public void girar() {
		velocidad *= -1;
	}

	//GETTERS Y SETTERS
	public String getNombreImagen() {
		String name = "";
		if (velocidad > 0) {
			name += "pajaroRight";
		}
		else name += "pajaroLeft";
		
		if (indexAnim > Constantes.PAJAROFRAMES) {
			name += "1";
		}
		else name += "0";
		return name;
	}
}
