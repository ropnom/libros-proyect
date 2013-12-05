package eetac.upc.edu.dsa.rodrigo.libros.api.model;

import java.util.ArrayList;
import java.util.List;

import eetac.upc.edu.dsa.rodrigo.libros.api.links.Link;

public class LibroCollection {

	private List<Libro> libros = new ArrayList<Libro>();
	private List<Link> links = new ArrayList<Link>();

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void add(Libro libro) {
		libros.add(libro);
	}

	public void addLink(Link link) {
		links.add(link);
	}

}
