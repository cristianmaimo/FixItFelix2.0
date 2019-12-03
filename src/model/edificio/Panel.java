package model.edificio;

class Panel {
	EstadoPanel estado;
	int indexImagen;
	
	Panel(EstadoPanel estado) {
		this.estado = estado;
		indexImagen = 0;
	}
	
	Panel(EstadoPanel estado, int indexImagen) {
		this.estado = estado;
		this.indexImagen = indexImagen;
	}
	
	EstadoPanel getEstado(){
		return estado;
	}
}
