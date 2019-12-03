package model.edificio;

import java.util.ArrayList;

import model.utilidades.Direccion;
import model.utilidades.Posicion;

/**
 * Las ventanas del tipo <kbd>Semicircular</kbd> se diferencian en que pueden tener 4 u 8 paneles,
 * incrementando la candidad maxima de roturas. Y ademas no tienen ningun tipo de obstaculo.
 * @author Cristian
 * @see Ventana
 * @see Normal
 * @see Hoja
 */
class Semicircular extends Ventana {

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
			paneles.add(i, new Panel(EstadoPanel.SANO));
		}
		hayMoldura = false; // Las semicirculares nunca tienen moldura ni macetero
		hayMacetero = false;
		reparacionesRestantes = 0;
		this.roturasMaximas = roturasMaximas;
	}

	/**
	 * Se sobreescrió el método ya que no pueden salir habitantes en las <kbd>Semicirculares</kbd>
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

	@Override
	InfoVentana getInfoVentana() {
		InfoVentana info = new InfoVentana();
		if (paneles.size() == 4) info = getInfoPuerta();
		else info = getInfoPrimerPiso();
		return info;
	}

	private InfoVentana getInfoPrimerPiso() {
		InfoVentana info = new InfoVentana();

		info.setImgMarco("ventanaPrimerPiso");
		
		String[] nombres= new String[8];
		for (int i = 0; i < paneles.size(); i++) {
			String panel = "panelPrimerPiso";
			switch (paneles.get(i).estado) {
			case SANO:
				panel += "Sano";
				break;
			case SEMIROTO:
				panel += "SemiRoto";
				panel += paneles.get(i).indexImagen;
				break;
			case TODOROTO:
				panel += "TodoRoto";
				break;
			}

			nombres[i] = panel;
		}
		info.setImgPaneles(nombres);
		
		ArrayList<Posicion> offsets = new ArrayList<Posicion>(8); 
		offsets.add(new Posicion(8, 75));
		offsets.add(new Posicion(20, 75));
		offsets.add(new Posicion(38, 75));
		offsets.add(new Posicion(50, 75));
		offsets.add(new Posicion(8, 58));
		offsets.add(new Posicion(20, 58));
		offsets.add(new Posicion(38, 58));
		offsets.add(new Posicion(50, 58));
		info.setPosicionPaneles(offsets);
		
		info.setMacetero(hayMacetero);
		info.setMoldura(hayMoldura);
		
		return info;
	}

	private InfoVentana getInfoPuerta() {
		InfoVentana info = new InfoVentana();

		info.setImgMarco("ventanaPuerta");
		
		String[] nombres= new String[4];
		for (int i = 0; i < paneles.size(); i++) {
			String panel = "panelPuerta";
			switch (paneles.get(i).estado) {
			case SANO:
				panel += "Sano";
				break;
			case SEMIROTO:
				panel += "SemiRoto";
				panel += paneles.get(i).indexImagen;
				break;
			case TODOROTO:
				panel += "TodoRoto";
				break;
			}

			nombres[i] = panel;
		}
		info.setImgPaneles(nombres);
		
		ArrayList<Posicion> offsets = new ArrayList<Posicion>(4); 
		offsets.add(new Posicion(13, 76));
		offsets.add(new Posicion(39, 76));
		offsets.add(new Posicion(13, 52));
		offsets.add(new Posicion(39, 52));
		info.setPosicionPaneles(offsets);
		
		info.setMacetero(hayMacetero);
		info.setMoldura(hayMoldura);
		
		return info;
	}
}
