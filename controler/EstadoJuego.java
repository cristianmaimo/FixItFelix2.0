package controler;

public class EstadoJuego {
	Escena escena;
	int frame;
	
	EstadoJuego(){
		escena = Escena.INICIANDO;
		frame = 0;
	}
	
	void setEscena(Escena escena){
		this.escena = escena;
		frame = 0;
	}
}

enum Escena{
	INICIANDO, JUGANDO, SUBIENDO, PASANDO, TERMINANDO, REINICIANDO;
}