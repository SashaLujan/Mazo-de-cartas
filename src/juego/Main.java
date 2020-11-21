package juego;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import estrategia.Estrategia;
import estrategia.EstrategiaAmbicioso;
import estrategia.EstrategiaObstinado;
import estrategia.EstrategiaTimbero;

//cambie el nombre de las pocimas por las que nos habian dado en el trabajo como ejemplo
import pociones.Pocion;
import pociones.PocionKriptonita;
import pociones.PocionValorFijo;

public class Main {

	public static void main(String[] args) {

		int cantRondas = 50;
		String nomMazo = "Mazo super heroes";
		String rutaJson = "./superheroes.json";
		Estrategia timbero = new EstrategiaTimbero();
		Estrategia ambicioso = new EstrategiaAmbicioso();
		Estrategia obstinado = new EstrategiaObstinado();

		Jugador j1 = new Jugador("Joselina", ambicioso);
		Jugador j2 = new Jugador("Sasha", timbero);
		Mazo mazoSuperHeroes = new Mazo(nomMazo);

		Pocion p1 = new PocionKriptonita("Kriptonita", 1000);
		Pocion p2 = new PocionValorFijo("Valor fijo", 20000);

		mazoSuperHeroes.addCartas(crearMazo(rutaJson));
		mazoSuperHeroes.addPocion(p1);
		mazoSuperHeroes.addPocion(p2);

		Juego juego = new Juego(cantRondas, j1, j2, mazoSuperHeroes);

		juego.jugar();

		System.out.println(juego.getResultados());
	}

	// agrgue el metodo para crear el mazo
	public static ArrayList<Carta> crearMazo(String jsonFile) {

		File jsonInputFile = new File(jsonFile);
		InputStream is;

		ArrayList<Carta> mazo = new ArrayList<>();
		try {
			is = new FileInputStream(jsonInputFile);

			JsonReader reader = Json.createReader(is);

			JsonArray cartas = (JsonArray) reader.readObject().getJsonArray("cartas");
			for (JsonObject carta : cartas.getValuesAs(JsonObject.class)) {
				String nombreCarta = carta.getString("nombre");

				Carta c = new Carta(nombreCarta);
				JsonObject atributos = (JsonObject) carta.getJsonObject("atributos");

				for (String nombreAtributo : atributos.keySet()) {
					c.addAtributo(nombreAtributo, atributos.getInt(nombreAtributo));
				}
				mazo.add(c);
			}
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return mazo;
	}
}
