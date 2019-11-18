package model.utilidades;

/**
 * Enumerativo que representa las 4 direcciones en que puede moverse Felix
 */
public enum Direccion {
	IZQUIERDA, DERECHA, ARRIBA, ABAJO;

	/**
	 * Invierte la direccion pasada como parametro
	 */
	public Direccion opuesta(Direccion direccion) {
		Direccion opuesta = null;
		switch (direccion) {
		case IZQUIERDA:
			opuesta = Direccion.DERECHA;
			break;
		case DERECHA:
			opuesta = Direccion.IZQUIERDA;
			break;
		case ARRIBA:
			opuesta = Direccion.ABAJO;
			break;
		case ABAJO:
			opuesta = Direccion.ARRIBA;
			break;
		}
		return opuesta;
	}
}
