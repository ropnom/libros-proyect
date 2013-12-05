package eetac.upc.edu.dsa.rodrigo.libros.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eetac.upc.edu.dsa.rodrigo.libros.api.links.Link;

public class Resena {

	private int resenaid;
	private int libroid;
	private String username;
	private Date lasupdate;
	private String content;
	private List<Link> links = new ArrayList<Link>();
	
	public int getResenaid() {
		return resenaid;
	}
	public void setResenaid(int resenaid) {
		this.resenaid = resenaid;
	}
	public int getLibroid() {
		return libroid;
	}
	public void setLibroid(int libroid) {
		this.libroid = libroid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getLasupdate() {
		return lasupdate;
	}
	public void setLasupdate(Date lasupdate) {
		this.lasupdate = lasupdate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
}
