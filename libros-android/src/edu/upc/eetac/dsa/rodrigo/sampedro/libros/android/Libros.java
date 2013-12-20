package edu.upc.eetac.dsa.rodrigo.sampedro.libros.android;

import java.io.IOException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import edu.upc.eetac.dsa.rodrigo.sampedro.libros.api.Libro;
import edu.upc.eetac.dsa.rodrigo.sampedro.libros.api.LibroCollection;
import edu.upc.eetac.dsa.rodrigo.sampedro.libros.api.LibrosAPI;

public class Libros extends ListActivity
{
	private final static String TAG = Libros.class.getName();
    /** Called when the activity is first created. */
//	private static final String[] items = { "lorem", "ipsum", "dolor", "sit",
//		"amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
//		"ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
//		"erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
//		"augue", "purus" };
	
	//private ArrayAdapter<String> adapter;
	private LibroAdapter adapter;
	String serverAddress;
	String serverPort;
	LibrosAPI api;
	ProgressDialog pd;
	ArrayList<Libro> librosList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        librosList = new ArrayList<Libro>();
        Authenticator.setDefault(new Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication("alicia", "alicia"
        				.toCharArray());
        	}
        });
        
    	AssetManager assetManager = getAssets();
    	Properties config = new Properties();
    	try {
    		config.load(assetManager.open("config.properties"));
    		serverAddress = config.getProperty("server.address");
    		serverPort = config.getProperty("server.port");
     
    		Log.d(TAG, "Configured server " + serverAddress + ":" + serverPort);
    	} catch (IOException e) {
    		Log.e(TAG, e.getMessage(), e);
    		finish();
    	}
    	api = new LibrosAPI();
    	URL url = null;
    	try {
    		url = new URL("http://" + serverAddress + ":" + serverPort
    				+ "/libros-api/libros");
    	} catch (MalformedURLException e) {
    		Log.d(TAG, e.getMessage(), e);
    		finish();
    	}
    	(new FetchStingsTask()).execute(url);
     
    	setContentView(R.layout.relative);
//    	adapter = new ArrayAdapter<String>(this,
//    			android.R.layout.simple_list_item_1, items);
    	adapter = new LibroAdapter(this, librosList);
    	setListAdapter(adapter);
    }
    private void addLibros(LibroCollection libros){
    	librosList.addAll(libros.getLibros());
    	adapter.notifyDataSetChanged();
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Libro libro = librosList.get(position);
     
    	// HATEOAS version
    	URL url = null;
    	try {
    		url = new URL(libro.getLinks().get(1).getUri());
    	} catch (MalformedURLException e) {
    		return;
    	}
     
    	// No HATEOAS
    	// URL url = null;
    	// try {
    	// url = new URL("http://" + serverAddress + ":" + serverPort
    	// + "/beeter-api/stings/" + id);
    	// } catch (MalformedURLException e) {
    	// return;
    	// }
    	Log.d(TAG, url.toString());
    	
    	Intent intent = new Intent(this, LibroDetail.class);
    	intent.putExtra("url", url.toString());
    	startActivity(intent);
    }
    
    private class FetchStingsTask extends AsyncTask<URL, Void, LibroCollection> {
    	private ProgressDialog pd;
     
    	@Override
    	protected LibroCollection doInBackground(URL... params) {
    		//parametros pasados a la funcion del tipo en este acaos url
    		LibroCollection libros = api.getLibros(params[0]);
    		return libros;
    	}
     
    	@Override
    	protected void onPostExecute(LibroCollection result) {
    		ArrayList<Libro> libros = new ArrayList<Libro>(result.getLibros());
    		for (Libro s : libros) {
    			Log.d(TAG, s.getLibroid() + "-" + s.getAutor());
    		}
    		addLibros(result);
    		if (pd != null) {
    			pd.dismiss();
    		}
    	}
     
    	@Override
    	protected void onPreExecute() {
    		pd = new ProgressDialog(Libros.this);
    		pd.setTitle("Searching...");
    		pd.setCancelable(false);
    		pd.setIndeterminate(true);
    		pd.show();
    	}
     
    }
    
}
