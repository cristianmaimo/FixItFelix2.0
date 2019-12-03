package model.edificio;

import java.util.ArrayList;

import model.utilidades.Posicion;

public class InfoVentana {
	private Posicion posicion;
	private String imgMarco;
	private String[] imgPaneles;
	private ArrayList<Posicion> offsetPaneles;
	private boolean moldura;
	private boolean macetero;
	
	public InfoVentana() {
	}
	//GETTERS Y SETTERS 
	public Posicion getPosicion() {return posicion;}
	public void setPosicion(Posicion posicion) {this.posicion = posicion;}
	
	public String getImgMarco() {return imgMarco;}
	public void setImgMarco(String tipo) {this.imgMarco = tipo;}
	
	public String[] getImgPaneles() {return imgPaneles;}
	public void setImgPaneles(String[] paneles) {this.imgPaneles = paneles;}
	
	public ArrayList<Posicion> getPosicionPaneles() {return offsetPaneles;}
	public void setPosicionPaneles(ArrayList<Posicion> posicionPaneles) {this.offsetPaneles = posicionPaneles;}
	
	public boolean hayMoldura() {return moldura;}
	public void setMoldura(boolean moldura) {this.moldura = moldura;}
	
	public boolean hayMacetero() {return macetero;}
	public void setMacetero(boolean macetero) {this.macetero = macetero;}
	
}
