package pociones;

public abstract class Pocion {

	private String nombre;
	
	public Pocion (String nombre) {
		this.nombre = nombre;
	}
	
	
	public String getNombre() {
		return nombre;
	}


	public abstract int aplicar (int valor);


	@Override
	public String toString() {
		return this.nombre;
	} 
	
	
}
