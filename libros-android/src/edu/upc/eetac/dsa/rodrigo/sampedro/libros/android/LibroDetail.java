package edu.upc.eetac.dsa.rodrigo.sampedro.libros.android;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import edu.upc.eetac.dsa.rodrigo.sampedro.libros.api.Libro;
import edu.upc.eetac.dsa.rodrigo.sampedro.libros.api.LibrosAPI;

public class LibroDetail extends Activity{
	
	public final static String Tag = LibroDetail.class.toString();
	private LibrosAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.libros_string_layout);
		api = new LibrosAPI();
		 Authenticator.setDefault(new Authenticator() {
	        	protected PasswordAuthentication getPasswordAuthentication() {
	        		return new PasswordAuthentication("alicia", "alicia"
	        				.toCharArray());
	        	}
	        });
		URL url = null;
		try {
			url = new URL((String) getIntent().getExtras().get("url"));
		} catch (MalformedURLException e) {
		}
		(new FetchStingTask()).execute(url);
	}
	
	private void loadSting(Libro libro) {
		TextView tvDetailTitulo = (TextView) findViewById(R.id.tvDetailTitulo);
		TextView tvDetailAutor = (TextView) findViewById(R.id.tvDetailAutor);
		TextView tvDetailLengua= (TextView) findViewById(R.id.tvDetailLengua);
		TextView tvDetailEdicion = (TextView) findViewById(R.id.tvDetailEdicion);		
		TextView tvDetailEditorial = (TextView) findViewById(R.id.tvDetailEditorial);
		TextView tvDetailUpdate = (TextView) findViewById(R.id.tvDetailUpdate);		
	 
		tvDetailTitulo.setText(libro.getTitulo());
		tvDetailAutor.setText(libro.getAutor());
		tvDetailLengua.setText(libro.getLengua());
		tvDetailEdicion.setText(libro.getEdicion());
		tvDetailEditorial.setText(libro.getEditorial());
		DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		//tvDetailUpdate.setText(fecha.format(libro.getLastUpdate()).toString());
		tvDetailUpdate.setText("akierror");
		
	}
	
	private class FetchStingTask extends AsyncTask<URL, Void, Libro> {
		private ProgressDialog pd;
	 
		@Override
		protected Libro doInBackground(URL... params) {
			Libro libro = api.getLibro(params[0]);
			return libro;
		}
	 
		@Override
		protected void onPostExecute(Libro result) {
			loadSting(result);
			if (pd != null) {
				pd.dismiss();
			}
		}
	 
		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(LibroDetail.this);
			pd.setTitle("Loading...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
	 
	}
	

}
