package model.utilidades;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Objeto que lleva el conteo del el puntaje obtenido en una partida.
 */
public class Puntaje implements Serializable{
	private static final long serialVersionUID = 0L;
	

	private int puntaje;
	private String nombre;

	public Puntaje() {
		puntaje = -1;
		nombre = " ";
	}
	public Puntaje(int puntaje, String nombre) {
		this.puntaje = puntaje;
		this.nombre = nombre;
	}
	
	public void sumar(int puntaje) {this.puntaje += puntaje;}
	
	public int getPuntaje() {return puntaje;}
	public void setPuntaje(int puntaje) {this.puntaje = puntaje;}
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public static long getSerialversionuid() {return serialVersionUID;}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(puntaje);
		out.writeObject(nombre);
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		puntaje = (int) in.readObject();
		nombre = (String) in.readObject();
	}
}
