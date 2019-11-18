package model.entidades;

import model.edificio.Edificio;
import model.utilidades.*;

import java.util.Random;

import controler.Constantes;

/**
 * La clase Pajaro describe los pajaros que pasan en las secciones 2 y 3 y que pueden golpear a Felix quitandole una vida y reiniciando la sección.
 * 
 * @author Edelmiro
 *
 */
public class Pajaro extends Proyectil {
	Random randomizador = new Random();
	Boolean anim = true;
	
	/**
	 * El movimiento de los pajaros es horizontal sumando a la coordenadaX el valor en velocidad que puede ser positivo o negativo si está llendo hacia la derecha o izaquierda respectivamente.
	 */
	@Override
	public void mover() {
		setPosicion(posicion.getCoordenadaX() + velocidad, posicion.getCoordenadaY());
	}
	
	
	/**
	 * Cuando se actualiza un Pajaro empieza moviendose, luego si ya salió de la pantalla gira para ir hacia el otro lado, 
	 * se grafica y finalmente finalmente checkea si está colicionando con felix y de ser así le quita una vida y reinicia la sección.
	 * @throws ChoquePajaroException 
	 */
	@Override
	public void actualizar() throws ChoquePajaroException {
		mover();
		if (posicion.getCoordenadaX() < -hitbox.getAncho() - Constantes.OFFSETXSECCION || posicion.getCoordenadaX() > Constantes.ANCHOFRAME + hitbox.getAncho() + Constantes.OFFSETXSECCION) {
			girar();
		}
		if (hayColicion()) {
			throw new ChoquePajaroException();
		}
	}
	
	
	/**
	 * El método girar multiplica la velocidad por -1 para invertir la dirección del movimiento.
	 */
	public void girar() {
		velocidad *= -1;
	}
	
	/**
	 * Cuando se instancia un pajaro obtiene su velocidad y posicion inicial como parámetros y busca su hitbox de la clase de configuraciones. 
	 */
	public Pajaro(int vel, Posicion pos) {
		velocidad = vel;
		hitbox = Constantes.PAJAROHITBOX;
		posicion = pos;
	}
}
