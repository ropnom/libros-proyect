package eetac.upc.edu.dsa.rodrigo.libros.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eetac.upc.edu.dsa.rodrigo.libros.links.Link;

public class Sting {
	
	private String stingid;
	private String username;
	private String author;
	private String subject;
	private String content;
	private Date lastModified;
	private List<Link> links = new ArrayList<Link>();
	
	public String getStingid() {
		return stingid;
	}
	public void setStingid(String id) {
		this.stingid = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subjet) {
		this.subject = subjet;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date timestamp) {
		lastModified = timestamp;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> rw) {
		this.links = rw;
	}
	public void addLink(Link link) {
		links.add(link);
	}
	

}
