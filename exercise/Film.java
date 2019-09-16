package com.example.exercise;

import java.io.Serializable;

public class Film implements Serializable {
	private String filmName;
	private String rating;
	private String actors;
	private String image;
	
	public Film(String filmName, String rating, String actors, String image) {
		super();
		this.filmName = filmName;
		this.rating = rating;
		this.actors = actors;
		this.image=image;
	}

	public String getFilmName() {
		return filmName;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String image) {
		this.actors = actors;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	

	@Override
	public String toString() {
		return "Film :" + filmName + ", rating " + rating + ",the actors are" + actors +".";
	}
	
	

}