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
 
	public LibroCollection getStings(URL url) {
		LibroCollection stings = new LibroCollection();
 
		HttpURLConnection urlConnection = null;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();
 
			urlConnection.setRequestProperty("Accept",
					MediaType.BEETER_API_STING_COLLECTION);
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
			parseLinks(jsonLinks, stings.getLinks());
 
			JSONArray jsonStings = jsonObject.getJSONArray("stings");
			for (int i = 0; i < jsonStings.length(); i++) {
				JSONObject jsonSting = jsonStings.getJSONObject(i);
				Libro sting = parseSting(jsonSting);
 
				stings.add(sting);
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
 
		return stings;
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
 
	private Libro parseSting(JSONObject source) throws JSONException,
			ParseException {
		Libro sting = new Libro();
		sting.setAuthor(source.getString("author"));
		if (source.has("content"))
			sting.setContent(source.getString("content"));
		String tsLastModified = source.getString("lastModified").replace("T",
				" ");
		sting.setLastModified(sdf.parse(tsLastModified));
		sting.setStingid(source.getString("stingid"));
		sting.setSubject(source.getString("subject"));
		sting.setUsername(source.getString("username"));
 
		JSONArray jsonStingLinks = source.getJSONArray("links");
		parseLinks(jsonStingLinks, sting.getLinks());
		return sting;
	}

}
