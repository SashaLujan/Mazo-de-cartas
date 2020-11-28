package juego;

import java.util.ArrayList;

public class Juego {

	private int cantidadRondas;
	private Jugador j1;
	private Jugador j2;
	private Mazo mazo;

	// agregue nuevos atributos
	// va cambiando de valor en cada ronda que se juega
	private ArrayList<String> resultados;
	private int numRonda;
	private Jugador ganadorRondaAnt;
	private Carta cartaJ1;
	private Carta cartaJ2;
	private String nombreAtributo;
	private int valorAtributoJ1;
	private int valorAtributoJ2;

	public Juego(int cantidadRondas, Jugador j1, Jugador j2, Mazo mazo) {
		this.cantidadRondas = cantidadRondas;
		this.j1 = j1;
		this.j2 = j2;
		this.mazo = mazo;
		this.resultados = new ArrayList<>();
		this.numRonda = 1;
		this.ganadorRondaAnt = j1;
		this.cartaJ1 = null;
		this.cartaJ2 = null;
		this.nombreAtributo = "";
		this.valorAtributoJ1 = -1;
		this.valorAtributoJ2 = -1;
	}

	public Jugador getJugador1() {
		return this.j1;
	}

	public Jugador getJugador2() {
		return this.j2;
	}

	public int getCantidadRondas() {
		return cantidadRondas;
	}

	public void setCantidadRondas(int cant) {
		this.cantidadRondas = cant;
	}

	public Mazo getMazo() {
		return this.mazo;
	}

	public ArrayList<String> getResultados() {
		return new ArrayList<>(this.resultados);
	}

	private void setRonda(int num) {
		numRonda = num;
	}

	private void setNombreAtributo(String nombre) {
		nombreAtributo = nombre;
	}

	private void setAtributos(int a1, int a2) {
		valorAtributoJ1 = a1;
		valorAtributoJ2 = a2;
	}

	private void setCartas() {
		this.cartaJ1 = this.j1.getPrimerCarta();
		this.cartaJ2 = this.j2.getPrimerCarta();
	}

	private void repartirMazo() { // modifique el metodo de repartir
		boolean aux = false;
		for (Carta carta : this.mazo.mezclarMazo()) {
			if (aux == false) {
				this.j1.addCartas(carta);
				aux = true;
			} else {
				this.j2.addCartas(carta);
				aux = false;
			}
		}
	}

	private Jugador turnoJugador() { // nuevo metodo
		if (ganadorRondaAnt.equals(this.j1)) {
			return this.j1;
		} else
			return this.j2;
	}

	private void seleccionarAtributo() {
		this.setNombreAtributo(this.turnoJugador().selecAtributo());
	}

	private void iniciarJugada() { // inicia la jugada , selecciona el atributo y
									// llama al metodo comparar
		this.seleccionarAtributo();
		compararAtributos();
	}

	private int getValorAtributo(Jugador j) {
		Carta c = j.getPrimerCarta();
		if (c.getPocion() == null) {
			return c.getAtributo(this.nombreAtributo);
		} else {
			return c.getPocion().aplicar(c.getAtributo(this.nombreAtributo));
		}
	}

	private void compararAtributos() { // compara las carta de los jugadores

		int valorJ1 = this.j1.getPrimerCarta().getAtributo(this.nombreAtributo);
		int valorJ2 = this.j2.getPrimerCarta().getAtributo(this.nombreAtributo);
		setAtributos(valorJ1, valorJ2);
		setCartas();
		if (valorJ1 > valorJ2) {
			moverCartas(this.j1, this.j2);
			ganadorRondaAnt = this.j1;
		} else if (valorJ1 < valorJ2) {
			moverCartas(this.j2, this.j1);
			ganadorRondaAnt = this.j2;
		} else {
			this.j1.moverCartaAlFinal();
			this.j2.moverCartaAlFinal();
		}
	}

	private void moverCartas(Jugador j1, Jugador j2) {
		Carta cartaPerdida = null;

		j1.moverCartaAlFinal();
		cartaPerdida = j2.getPrimerCarta();
		j2.eliminarCarta();
		j1.addCartas(cartaPerdida);
	}

	private boolean tieneCartas(Jugador jugador) {
		if (jugador.cantCartas() == 0) {
			return false;
		}
		return true;
	}

	private boolean ambosTienenCartas() {
		return this.tieneCartas(this.j1) && this.tieneCartas(j2);
	}

	private boolean terminoRondas() {
		return numRonda <= this.cantidadRondas;
	}

	public String getGanador() {
		if (!this.tieneCartas(this.j1)) {
			return this.j2.getNombre();
		} else if (!this.tieneCartas(this.j2)) {
			return this.j1.getNombre();
		} else {
			if (this.j1.cantCartas() > this.j2.cantCartas()) {
				return this.j1.getNombre();
			} else if (this.j1.cantCartas() < this.j2.cantCartas()) {
				return this.j2.getNombre();
			} else {
				return "Empate";
			}
		}
	}

	public void jugar() {

		this.mazo.eliminarCartasInvalidas();
		this.mazo.addPocionACarta();
		this.repartirMazo();

		while (this.ambosTienenCartas() && this.terminoRondas()) {

			this.iniciarJugada();

			this.resultados.add(this.toString());

			this.setRonda(numRonda + 1);

		}
		if (this.getGanador() != "Empate") {
			this.resultados.add("\nEl ganador del juego es: " + this.getGanador());
		} else {
			this.resultados.add("\n" + this.getGanador());
		}
	}

	@Override
	public String toString() {

		return "\nRonda N�: " + numRonda + "\nAtributo Seleccionado: " + this.nombreAtributo + "\nCarta J1: "
				+ this.cartaJ1.getNombreDePersonaje() + "\nCarta J2: " + this.cartaJ2.getNombreDePersonaje()
				+ "\nPocion j1: " + this.cartaJ1.getPocion() + "\nPocion j2: " + this.cartaJ2.getPocion()
				+ "\nValor J1: " + this.valorAtributoJ1 + "\nValor J2: " + this.valorAtributoJ2 + "\nGanador ronda: "
				+ ganadorRondaAnt.getNombre() + "\nCantidad cartas " + this.j1.getNombre() + ": " + j1.cantCartas()
				+ "\nCantidad cartas " + this.j2.getNombre() + ": " + j2.cantCartas() + "\n";
	}
}
