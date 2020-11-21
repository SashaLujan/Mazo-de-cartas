package visorMazo;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import juego.Mazo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
/*
import juego.Atributo;
import juego.Carta;
import juego.Juego;
import juego.Jugador;
import juego.Mazo;*/

public class VisorMazo {

	public static Mazo mostrarMazo(String jsonFile) {
		Mazo mazo = new Mazo(jsonFile);
		// URL url = getClass().getResource(jsonFile);
		File jsonInputFile = new File(jsonFile);
		InputStream is;
		try {
			is = new FileInputStream(jsonInputFile);
			// Creo el objeto JsonReader de Json.
			JsonReader reader = Json.createReader(is);
			// Obtenemos el JsonObject a partir del JsonReader.
			JsonArray cartas = (JsonArray) reader.readObject().getJsonArray("cartas");
			for (JsonObject carta : cartas.getValuesAs(JsonObject.class)) {
				String nombreCarta = carta.getString("nombre");
				JsonObject atributos = (JsonObject) carta.getJsonObject("atributos");
				String atributosStr = "";
				for (String nombreAtributo : atributos.keySet())
					atributosStr = atributosStr + nombreAtributo + ": " + atributos.getInt(nombreAtributo) + "; ";
				System.out.println(nombreCarta + "\t\t\t" + atributosStr);
			}

			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mazo;
	}

	public static void main(String[] args) {
		String rutaJson = "./superheroes.json";
		//String mazoPath = "./autos.json";
		Mazo m1 = new Mazo(rutaJson);
		m1 = VisorMazo.mostrarMazo(rutaJson);
		/*
		 * Jugador j1 = new Jugador("Joselina"); Jugador j2 = new Jugador("Sasha");
		 * Juego juego = new Juego(m1, 50, j1, j2); System.out.println(m1);
		 * jugarPartida.jugar();
		 */
	}
}
