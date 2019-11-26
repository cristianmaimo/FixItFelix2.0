package model.edificio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import controler.Constantes;
import model.Model;
import model.utilidades.Direccion;
import model.utilidades.Posicion;


	/**
 	* Clase que se encarga de la construcción, randomización, acceso y estructura de las <kbd>Ventanas</kbd>.<br>
 	* Es de tipo <kbd>protected</kbd>, su acceso es solo a travéz de {@link Edificio}.
 	* Utiliza la colección {@link HashMap} el cual siempre contiene, 15 ventanas como <i>values</i>
 	* y utilizando un objeto de tipo {@link Posicion} como <i>key</i>, este define la coordenada superior izquierda de cada {@link Ventana}
 	* en relacion con el origen de cada seccion (pixel superior izquierdo de la pantalla).
 	* La primera se encuentra en las coordenadas (#ANCHOVENTANA, #ALTURAVENTANA) (valores definidos en {@link Constantes}),
 	* y siguen un orden lógico de 3 filas por seccion y 5 ventanas por fila, sin ninguna separacion entre las mismas.<br>
 	* Por ultimo, tambien lleva un conteo de la cantidad de reparaciones restantes para completar la sección.
 	* 
 	* @author Cristian
 	* @see Seccion#Seccion
 	*/
class Seccion {
	/** Coleccion con la informacion de las ventanas de esta sección.*/
	private HashMap<Posicion, Ventana> ventanas; 
	private int roturasRestantes;
	private Random randomizador;
	
	
	// CONTRUCTOR
	/**
	 * Construye un {@link HashMap} con 15 intancias de {@link Ventana} como <i>values</i> utilizando un objeto tipo {@link Posicion} como <i>key</i>.
	 * El orden lógico para la construccion es el siguiente:<br>
	 * - Separa los obstaculos en 2 tipos, horizontales (<i>Hojas</i>) y verticales (<i>molduras</i> y <i>maceteros</i>) de manera aleatoria.<br>
	 * - Crea un array bidimencional (5x3) de tipo <kbd>String</kbd> con la informacion randomizada de la distribucion de los <i>tipos</i>
	 * de ventana ({@link Normal}, {@link Hoja}, {@link Semicircular}).<br>
	 * - Crea un segundo array bidimencianal pero de tipo int con la cantidad de obstaculos verticales a colocar.<br>
	 * - Utiliza ambos arrays para la correcta inicializacion del <kbd>HashMap</kbd> con las caracteristicas de cada ventana.<br>
	 * - Randomiza la cantidad de roturas de cada una (cantidad definida en el parametro recibido cantidadRoturas).
	 * 
	 * @param esPrimeraSeccion boolean utilizado para el chequeo de las ventanas semicirculares.
	 * @param cantidadRoturas int utilizado para la randomizacion de roturas
	 * @param cantidadObstaculos int utilizado para la randomizacion de ventanas con Hojas, <i>molduras</i> y <i>maceteros</i> 
	 * @see #randomizarTipos(int, boolean)
	 * @see #randomizarObstaculos(int, boolean)
	 * @see #randomizarRoturas(int)
	 * @see Edificio#Edificio(Dificultad)
	 */
	Seccion(boolean esPrimeraSeccion, int cantidadRoturas, int cantidadObstaculos) {
		randomizador = new Random();
		int cantidadObstaculosHorizontales = randomizador.nextInt(cantidadObstaculos);
		int cantidadObstaculosVerticales = cantidadObstaculos - cantidadObstaculosHorizontales;
		String[][] matrizDeTipos = randomizarTipos(cantidadObstaculosHorizontales, esPrimeraSeccion);
		int[][] matrizDeObstaculos = randomizarObstaculos(cantidadObstaculosVerticales, esPrimeraSeccion);
		ventanas = inicializarVentanas(matrizDeTipos, matrizDeObstaculos);
		roturasRestantes = 0;
		randomizarRoturas(cantidadRoturas);
	}
	
	// PROTECTED
	/**
	 * Repara una vez la ventana en la posicion indicada por el parametro.<br>
	 * Si la ventana esta completamente sana, no hace nada.<br>
	 * Si la ventana fue reparada exitosamente, es decir un panel cambio del estado "SEMIROTO" a "SANO",
	 * se suma 100 al puntaje actual.
	 * @param pos Posición de la ventana a intentar reparar.
	 * @return La cantidad de roturas restantes de la seccion.
	 * @see Ventana#reparar()
	 * @see Model#sumarPuntaje(int)
	 */
	int reparar(Posicion pos) {
		Ventana ventana = ventanas.get(pos);
		int seReparo = ventana.reparar();
		if (seReparo > 0) {
			roturasRestantes--;
			Model.getModel().cambioVentana(getInfoVentana(pos));
			if (seReparo == 2) {	//Panel completamente reparado.
				Model.getModel().sumarPuntaje(100);
			}
		}
		return roturasRestantes;
	}
	
	/**
	 * De ser posible (al menos 1 panel no esta <kbd>TODOROTO</kbd>), aumenta la cantidad de roturas en la {@link Ventana} ubicada
	 * en la {@link Posicion} indicada como parametro.<br>
	 * Se cambia el estado de un único {@link EstadoPanel} aleatorio de:
	 * <kbd>SANO</kbd> a <kbd>SEMIROTO</kbd>, o <kbd>SEMIROTO</kbd> a <kbd>TODOROTO</kbd>.<br>
	 *  Si la ventana esta completamente rota, no hace nada.
	 * @param pos Posición de la ventana a romper.
	 * @see	Ventana#romper()
	 * @see Ladrillo#romper()
	 */
	void romper(Posicion pos) {
		if(ventanas.get(pos).romper()) {
			roturasRestantes++;
		}	
	}
	
	/**
	 * Revisa si moverse de la <kbd>posición actual</kbd> a la posición en la {@link Direccion} indicada
	 * es un movimiento valido para {@link Felix} (no hay obstaculos, y no sale de los limites de la seccion).
	 * @param posicionActual Posicion desde la que se realiza el movimiento
	 * @param direccion Direccion del movimiento
	 * @return la {@link Posicion} de la {@link Ventana} en la direccion indicada o
	 * la misma posición recibida como parametro en caso de no ser un movimiento valido
	 * @see Felix#mover(Posicion)
	 */
	Posicion avanzar(Posicion posicionActual, Direccion direccion) {
		Posicion posicionFinal = new Posicion(posicionActual);
		if (ventanas.get(posicionFinal).hayObstaculo(direccion)) {
			return posicionFinal;
		}
		else {
			posicionFinal.avanzar(direccion);
			if ((ventanas.getOrDefault(posicionFinal, null) == null)){
				return new Posicion(posicionActual);
			} 
			else if (!(ventanas.get(posicionFinal).hayObstaculo(direccion.opuesta(direccion)))){
				return posicionFinal;
				}
			else {
				return new Posicion(posicionActual);
			}
		}
	}
	
	/**
	 * Recorre la coleccion de <kbd>ventanas</kbd> y devuelve la {@link Posicion}
	 * de una ventana abierta (panel inferior en estado <kbd>TODOROTO</kbd>).<br>
	 * En caso de haber mas de una, elije una aleatoria.<br>
	 * En caso de no haber ninguna devuelve la Posicion (0,0).
	 * 
	 * @return Posicion de una ventana abierta, Posicion (0,0) de no haber.
	 * @see Model#actualizarPastel()
	 */
	Posicion hayAbierta() {
		Set<Posicion> setOfKeys = ventanas.keySet();
		Iterator<Posicion> iterador = setOfKeys.iterator();
		ArrayList<Posicion> tempArray = new ArrayList<Posicion>(0);
		Posicion pos = null;
		while (iterador.hasNext()) {
			pos = iterador.next();
			if ((ventanas.get(pos).inferiorDespejado())) {
				tempArray.add(new Posicion(pos));
			}
		}
		pos = new Posicion(-1,-1);
		if (!(tempArray.isEmpty())) {
			iterador = tempArray.iterator();
			int iteraciones = randomizador.nextInt(tempArray.size());
			for (int i = 0; i < iteraciones ; i++) {
				pos = iterador.next();
			}
		}
		return new Posicion(pos);
	}
	
	ArrayList<InfoVentana> getInfoSeccion(){
		ArrayList<InfoVentana> infoSeccion = new ArrayList<InfoVentana>(Constantes.CANTIDADVENTANAS);
		
		
		for (int i = 0; i < Constantes.CANTIDADFILAS; i++) {
			for (int j = 0; j < Constantes.CANTIDADCOLUMNAS; j++) {
				Posicion key = new Posicion(j * Constantes.ANCHOVENTANA,
											i * Constantes.ALTURAVENTANA);
				InfoVentana info = ventanas.get(key).getInfoVentana();
				info.setPosicion(key);
				infoSeccion.add(info);
			}
		}
		return infoSeccion;
	}
	
	InfoVentana getInfoVentana(Posicion pos) {
		InfoVentana infoVentana = ventanas.get(pos).getInfoVentana();
		infoVentana.setPosicion(new Posicion(pos));
		return infoVentana;
	}
	
	// PRIVATE
	/**
	 * Crea y retorna un <kbd>array[5][3]</kbd> auxiliar con valores tipo
	 * <kbd>String</kbd> para la contruccion del <kbd>HashMap</kbd>.<br>
	 * Primero lo inicializa con el valor "Normal" en cada casilla (tipo mas comund
	 * de ventana).<br>
	 * Luego repite el siguiente proceso hasta que no haya mas <i>Hojas</i> que
	 * colocar:<br>
	 * - Elije aleatoriamente una casilla.<br>
	 * - Se asegura de que no haya sido reemplazada previamente y que no sea la
	 * posicion de una ventana semicircular (Que seimpre se encuentran en los
	 * casilleros [3][2] y [3][3].<br>
	 * - La reemplaza por el valor "Hoja".
	 * 
	 * @param hojasRestantes   Cantidad de ventanas tipo {@link Hoja} a colocar en
	 *                         el {@link HashMap}
	 * @param esPrimeraSeccion Boolean utilizado para el chequeo de ventanas tipo
	 *                         {@link Semicircular}
	 * @return <kbd>array[5][3]</kbd> de tipo String con los tipos de ventana a
	 *         crear en la seccion.
	 * @see Seccion#Seccion(boolean, int, int)
	 */
	private String[][] randomizarTipos(int hojasRestantes, boolean esPrimeraSeccion) {
		String[][] matrizDeTipos = new String[(Constantes.CANTIDADCOLUMNAS)][(Constantes.CANTIDADFILAS)];

		for (int i = 0; i < Constantes.CANTIDADFILAS; i++) {
			for (int j = 0; j < Constantes.CANTIDADCOLUMNAS; j++) {
				matrizDeTipos[j][i] = "Normal";
			}
		}
		while (hojasRestantes > 0) {
			int filaElejida = randomizador.nextInt(Constantes.CANTIDADCOLUMNAS);
			int columnaElejida = randomizador.nextInt(Constantes.CANTIDADFILAS);

			// Si es planta baja y la posicion de una circular saltea al siguiente loop
			if ((esPrimeraSeccion) && (columnaElejida == 2) && ((filaElejida == 1) || (filaElejida == 2))) {
				continue;
			} else {
				if (matrizDeTipos[filaElejida][columnaElejida] == "Normal") {
					matrizDeTipos[filaElejida][columnaElejida] = "Hoja";
					hojasRestantes--;
				}
			}
		}
		if (esPrimeraSeccion) {
			matrizDeTipos[2][2] = "Puerta";
			matrizDeTipos[2][1] = "PrimerPiso";
		}
		return matrizDeTipos;
	}

	/**
	 * Crea y retorna un <kbd>array[5][3]</kbd> auxiliar con valores tipo
	 * <i>int</i>, que representan la cantidad obstaculos verticales
	 * (<i>molduras</i> y <i>maceteros</i>) que posee la seccion actual.<br>
	 * Primero lo inicializa con el valor <i>0</i> en cada casilla. Luego repite el
	 * siguiente proceso hasta que no haya mas <i>obstaculos</i> que colocar:<br>
	 * - Elije aleatoriamente una casilla.<br>
	 * - Se asegura de que no haya mas de 2 obstaculos por ventana (solo puede haber
	 * 1 moldura, 1 macetero o ambos) - Incrementa el valor en 1.
	 * 
	 * @param obstaculosRestantes cantidad de obstaculos verticales a colocar.
	 * @param esPrimeraSeccion    Boolean utilizado para el chequeo de ventanas tipo
	 *                            {@link Semicircular}
	 * @return <kbd>array[5][3]</kbd> de tipo <i>int</i> con la cantidad obstaculos
	 *         verticales a colocar en la seccion.
	 * @see Seccion#Seccion(boolean, int, int)
	 */
	private int[][] randomizarObstaculos(int obstaculosRestantes, boolean esPrimeraSeccion) {
		int[][] matrizDeObstaculos = new int[(Constantes.CANTIDADCOLUMNAS)][(Constantes.CANTIDADFILAS)];

		while (obstaculosRestantes > 0) {
			int filaElejida = randomizador.nextInt(Constantes.CANTIDADCOLUMNAS);
			int columnaElejida = randomizador.nextInt(Constantes.CANTIDADFILAS);
			// Si es planta baja y la posicion de una circular saltea al siguiente loop
			if ((esPrimeraSeccion) && (columnaElejida == 2) && ((filaElejida == 2) || (filaElejida == 1))) {
				continue;
			} else {
				if (matrizDeObstaculos[filaElejida][columnaElejida] < 2) {
					matrizDeObstaculos[filaElejida][columnaElejida]++;
					obstaculosRestantes--;
				}
			}
		}
		return matrizDeObstaculos;
	}

	/**
	 * Utilizando las matrices axiliares recibidas como parametro, crea y retorna un
	 * <kbd>HashMap[Posicion,Ventana]</kbd> inicializando cada ventana con sus
	 * correctas características (tipo, y si tiene o no moludar y/o macetero), sin
	 * ningunra rotura y asegurandose de mantener el orden logico 3 filas, 5
	 * ventanas por fila.
	 * 
	 * @param matrizDeTipos
	 * @param matrizDeObstaculos
	 * @return HashMap[Posicion,Ventana] con las ventanas inicializadas y sin
	 *         roturas.
	 * @see Seccion#Seccion(boolean, int, int)
	 */
	private HashMap<Posicion, Ventana> inicializarVentanas(String[][] matrizDeTipos, int[][] matrizDeObstaculos) {
		Posicion key = null;
		Ventana value = null;
		HashMap<Posicion, Ventana> inicializarVentanas = new HashMap<Posicion, Ventana>(Constantes.CANTIDADVENTANAS);

		for (int i = 0; i < Constantes.CANTIDADFILAS; i++) {
			for (int j = 0; j < Constantes.CANTIDADCOLUMNAS; j++) {
				key = new Posicion((j * Constantes.ANCHOVENTANA), (i * Constantes.ALTURAVENTANA));

				switch (matrizDeTipos[j][i]) {
				case "Normal":
					value = new Normal(matrizDeObstaculos[j][i]);
					break;
				case "Hoja":
					value = new Hoja(matrizDeObstaculos[j][i]);
					break;
				case "Puerta":
					value = new Semicircular(4, 8);
					break;
				case "PrimerPiso":
					value = new Semicircular(8, 16);
					break;
				}
				inicializarVentanas.put(key, value);
			}
		}
		return inicializarVentanas;
	}

	/**
	 * Rompe aleatoriamente las ventanas del atributo {@link #ventanas} (previamente
	 * inicializado en {@link #inicializarVentanas(String[][], int[][])
	 * inicializarVentanas()}) la cantidad de veces indicada en el parametro
	 * <kbd>#cantidadARomper</kbd><br>
	 * <br>
	 * La lógica para la randomizacion es la siguiente: <br>
	 * - Crea un <i>HashMap</i> auxiliar (<kbd>roturasPorVentana</kbd>), con las
	 * <i>keys</i><br>
	 * correspondientes al atributo <kbd>#ventanas</kbd> pero con <i>values</i> de
	 * tipo <kbd>int</kbd> para llevar el conteo de roturas en cada ventana y
	 * asegurarse de que no se rompan demas.<br>
	 * - Remueve las posiciones correspondientes a ventanas de tipo Hoja ya que las
	 * misma no pueden romperse.<br>
	 * - Extrae un ArrayList de keys del HashMap axuliar para randomizar la
	 * iteracion utilizando un objeto tipo {@link Random}.<br>
	 * - Repite los siguientes 2 pasos la cantidad de veces inticada en el parametro
	 * <i>cantidadARomper</i><br>
	 * - Elije aleatoriamente (iterando sobre el HashMap auxiliar) una ventana y la
	 * rompe 1 vez<br>
	 * - Si la ventana ahora esta completamente rota, la remueve del HashMap
	 * auxiliar<br>
	 * 
	 * @param cantidadARomper
	 */
	private void randomizarRoturas(int cantidadARomper) {
		// Crea un HashMap auxiliar para facilitar la randomizacion
		HashMap<Posicion, Integer> roturasPorVentana = new HashMap<Posicion, Integer>();
		Posicion pos;

		// lo inicializa con las referencias a las ventanas que pueden romperse
		for (int i = 0; i < Constantes.CANTIDADFILAS; i++) {
			for (int j = 0; j < Constantes.CANTIDADCOLUMNAS; j++) {
				pos = new Posicion((j * Constantes.ANCHOVENTANA), (i * Constantes.ALTURAVENTANA));
				// Evita agregar referencias a las ventanas tipo Hoja ya que las misma no se
				// pueden romper
				if (!(ventanas.get(pos) instanceof Hoja)) {
					roturasPorVentana.put(pos, 0);
				}
			}
		}
		if (!(roturasPorVentana.isEmpty())) {
			// Extrae un ArrayList de keys del HashMap axuliar para randomizar la iteracion
			ArrayList<Posicion> keysAsArray = new ArrayList<Posicion>(roturasPorVentana.keySet());
			int indexElegido;
			while ((cantidadARomper > 0) && (keysAsArray.size() > 0)) {
				indexElegido = randomizador.nextInt(keysAsArray.size());
				pos = new Posicion(keysAsArray.get(indexElegido));
				// Rompe 1 vez la ventana en la posicion randomizada
				ventanas.get(pos).romper();
				roturasRestantes++;
				roturasPorVentana.put(pos, roturasPorVentana.get(pos) + 1);
				cantidadARomper--;
				// Remueve del set si esta completamente rota para no ser re-elejida en la
				// randomizacion
				if ((roturasPorVentana.get(pos)) == (ventanas.get(pos).getRoturasMaximas())) {
					keysAsArray.remove(indexElegido);
				}
			}
		}
	}
}