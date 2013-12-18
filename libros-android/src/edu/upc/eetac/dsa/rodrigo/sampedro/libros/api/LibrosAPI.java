package edu.upc.eetac.dsa.rodrigo.sampedro.libros.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.util.Log;

public class LibrosAPI {

	private final static String TAG = LibrosAPI.class.toString();
	private final static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public LibroCollection getLibros(URL url) {
		LibroCollection libros = new LibroCollection();

		HttpURLConnection urlConnection = null;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setRequestProperty("Accept",
					MediaType.LIBROS_API_LIBRO_COLLECTION);
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			JSONObject jsonObject = new JSONObject(sb.toString());
			JSONArray jsonLinks = jsonObject.getJSONArray("links");
			parseLinks(jsonLinks, libros.getLinks());

			JSONArray jsonStings = jsonObject.getJSONArray("libros");
			for (int i = 0; i < jsonStings.length(); i++) {
				JSONObject jsonLibro= jsonStings.getJSONObject(i);
				Libro libro = parseLibro(jsonLibro);

				libros.add(libro);
			}
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}

		return libros;
	}

	private void parseLinks(JSONArray source, List<Link> links)
			throws JSONException {
		for (int i = 0; i < source.length(); i++) {
			JSONObject jsonLink = source.getJSONObject(i);
			Link link = new Link();
			link.setRel(jsonLink.getString("rel"));
			link.setTitle(jsonLink.getString("title"));
			link.setType(jsonLink.getString("type"));
			link.setUri(jsonLink.getString("uri"));
			links.add(link);
		}
	}

	private Libro parseLibro(JSONObject source) throws JSONException,
			ParseException {
		Libro libro = new Libro();
		libro.setLibroid(source.getInt("libroid"));
		libro.setTitulo(source.getString("titulo"));
		libro.setAutor(source.getString("author"));
		libro.setLengua(source.getString("lengua"));
		libro.setEdicion(source.getString("edicion"));

		// libro.setFecha_edicion();
		// libro.setFecha_impresion();
		libro.setEditorial(source.getString("editorial"));

		String tsLastModified = source.getString("lastUpdate")
				.replace("T", " ");

		//libro.setLastUpdate(sdf.parse(tsLastModified));
		
		
		JSONArray jsonStingLinks = source.getJSONArray("links");
		parseLinks(jsonStingLinks, libro.getLinks());
		return libro;
	}

}
