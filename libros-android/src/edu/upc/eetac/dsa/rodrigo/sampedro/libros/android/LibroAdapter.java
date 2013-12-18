package edu.upc.eetac.dsa.rodrigo.sampedro.libros.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.upc.eetac.dsa.rodrigo.sampedro.libros.api.Libro;

public class LibroAdapter extends BaseAdapter {

	ArrayList<Libro> data;
	LayoutInflater inflater;
	
	//mapea lso widgets creados en el layout
	private static class ViewHolder {
		TextView tvLibro;
		TextView tvAutor;
		TextView tvEditorial;
	}
	
	//contxt es por odnde se muebe la aplicacion, es donde esta la aplicaicon hay que arrastrarlo
	public LibroAdapter(Context context, ArrayList<Libro> data) {
		super();
		inflater = LayoutInflater.from(context);
		this.data = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int posicion) {
		// TODO Auto-generated method stub
		return data.get(posicion);
	}

	
	@Override
	public long getItemId(int position) {
		return Long.parseLong(String.valueOf(((Libro)getItem(position)).getLibroid()));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//es el reciclaje de vistas
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.libros_row_layout, null);
			
			viewHolder = new ViewHolder();
			//referenciowidgets de layout con viewholder
			viewHolder.tvLibro = (TextView) convertView
					.findViewById(R.id.tvLibro);
			viewHolder.tvAutor= (TextView) convertView
					.findViewById(R.id.tvAutor);
			viewHolder.tvEditorial = (TextView) convertView
					.findViewById(R.id.tvEditorial);
			//lo guardo en el ConvertView
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String Libro = data.get(position).getTitulo();
		String Autor = data.get(position).getAutor();
		String Editorial = data.get(position).getEditorial();
		viewHolder.tvLibro.setText(Libro);
		viewHolder.tvAutor.setText(Autor);
		viewHolder.tvEditorial.setText(Editorial);
		return convertView;
	}

}
