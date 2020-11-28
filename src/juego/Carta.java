package juego;

import java.util.HashMap; //cambie el ArrayList por el HashMap

import pociones.Pocion; //ahora importa la clase pocion y no elemento abstracto no es necesario

public class Carta {

	private HashMap<String, Integer> atributos; // cambie el ArrayList por HashMap
	private String nombreDePersonaje;
	private Pocion pocion;

	public Carta(String nombreDePersonaje) {
		this.nombreDePersonaje = nombreDePersonaje;
		this.pocion = null;
		this.atributos = new HashMap<>();
	}

	public String getNombreDePersonaje() {
		return nombreDePersonaje;
	}

	public void setNombre(String nombreDePersonaje) {
		this.nombreDePersonaje = nombreDePersonaje;
	}

	public void addAtributo(String nombre, int num) { // modifique el add atributo acorde a HashMap
		if (this.atributos.get(nombre) == null) {
			this.atributos.put(nombre, num);
		}
	}

	public int CantAtributos() {
		return this.atributos.size();
	}

	public HashMap<String, Integer> getAtributos() {
		return new HashMap<>(this.atributos);
	}

	public int getAtributo(String nombre) {
		return this.atributos.get(nombre);
	}

	public int getValor(String nombre) {
		return this.atributos.get(nombre);
	}

	// hice un get y set de pocion eliminando getP1 y setP1
	public Pocion getPocion() {
		return this.pocion;
	}

	public void setPocion(Pocion p) {
		this.pocion = p;
	}

}
