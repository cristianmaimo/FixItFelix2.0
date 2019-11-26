package model.utilidades;

/**
 * Valores que son constantes en el transcurso de cada nivel y affectan la dificultad del mismo
 */
public class Dificultad {
	public final int VENTANASROTAS;
	public final int CHANCEGOLPEAR; 
	public final int VELOCIDADLADRILLOS; 
	public final int VELOCIDADPAJAROS; 
	public final int CANTIDADOBSTACULOS;
	public final int CANTIDADPAJAROS;
	// Todavia no implementado
	//public final int TIEMPOLIMITE;
	
	public Dificultad(){
		VENTANASROTAS = 12;
		CHANCEGOLPEAR = 2;
		VELOCIDADLADRILLOS = 2; // Pixeles por loop
		VELOCIDADPAJAROS = 2; // Pixeles por loop
		CANTIDADOBSTACULOS = 3; // Total de obstaculos por nivel
		// Todavia no implementado
		//TIEMPOLIMITE = 0;
		CANTIDADPAJAROS = 1;
	}
	
	public Dificultad(Dificultad difBase, int nivel){
		float modificador = 1 + (nivel-1 * 0.1f); //aumenta un 10% en cada nivel.
		VENTANASROTAS = Math.round(difBase.VENTANASROTAS * modificador);
		CHANCEGOLPEAR = Math.round(difBase.CHANCEGOLPEAR * modificador);
		VELOCIDADLADRILLOS = Math.round(difBase.VELOCIDADLADRILLOS * modificador);
		VELOCIDADPAJAROS = Math.round(difBase.VELOCIDADPAJAROS * modificador);
		CANTIDADOBSTACULOS = Math.round(difBase.CANTIDADOBSTACULOS * modificador);
		// Todavia no implementado
		// TIEMPOLIMITE = Math.round(difBase.TIEMPOLIMITE * modificador);
		switch (nivel) {
		case 4:
		case 5:
		case 6:
		case 7:
			CANTIDADPAJAROS = 2;
			break;
		case 8:
		case 9:
		case 10:
			CANTIDADPAJAROS = 3;
			break;
		default: CANTIDADPAJAROS = 1;
		}
	}
}
