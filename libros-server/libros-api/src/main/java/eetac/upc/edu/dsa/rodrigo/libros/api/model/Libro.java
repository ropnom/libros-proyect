package eetac.upc.edu.dsa.rodrigo.libros.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eetac.upc.edu.dsa.rodrigo.libros.api.links.Link;

public class Libro {

	private int libroid;
	private String titulo;
	private String autor;
	private String lengua;
	private String edicion;
	private Date fecha_edicion;
	private Date fecha_impresion;
	private String editorial;
	private Date lastUpdate;
	private List<Link> links = new ArrayList<Link>();

	public int getLibroid() {
		return libroid;
	}

	public void setLibroid(int libroid) {
		this.libroid = libroid;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getLengua() {
		return lengua;
	}

	public void setLengua(String lengua) {
		this.lengua = lengua;
	}

	public String getEdicion() {
		return edicion;
	}

	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}

	public Date getFecha_edicion() {
		return fecha_edicion;
	}

	public void setFecha_edicion(Date fecha_edicion) {
		this.fecha_edicion = fecha_edicion;
	}

	public Date getFecha_impresion() {
		return fecha_impresion;
	}

	public void setFecha_impresion(Date fecha_impresion) {
		this.fecha_impresion = fecha_impresion;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void addLink(Link link) {
		links.add(link);
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
