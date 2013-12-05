package eetac.upc.edu.dsa.rodrigo.libros.model;

import java.util.ArrayList;
import java.util.List;

import eetac.upc.edu.dsa.rodrigo.libros.links.Link;


public class StingCollection {
	
	private List<Sting> stings = new ArrayList<Sting>();
	private List<Link> links = new ArrayList<Link>();
	
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> rw) {
		this.links = rw;
	}
	public void addSting(Sting sting)
	{
		stings.add(sting);
	}
	public void addLink(Link link) {
		links.add(link);
	}

	public List<Sting> getStings() {
		return stings;
	}

	public void setStings(List<Sting> stings) {
		this.stings = stings;
	}

}
