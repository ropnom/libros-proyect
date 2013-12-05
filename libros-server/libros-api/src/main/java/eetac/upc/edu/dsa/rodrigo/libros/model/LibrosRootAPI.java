package eetac.upc.edu.dsa.rodrigo.libros.model;

import java.util.ArrayList;
import java.util.List;

import eetac.upc.edu.dsa.rodrigo.libros.links.Link;


public class LibrosRootAPI {

	private List<Link> rw;

	public LibrosRootAPI() {
		rw = new ArrayList<Link>();
	}

	public void addLink(Link link) {
		rw.add(link);
	}

	public void setRw(List<Link> rw) {
		this.rw = rw;
	}

	public List<Link> getRw() {
		return rw;
	}

}
