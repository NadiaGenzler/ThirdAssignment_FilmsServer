package com.example.exercise;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebServiceForFilms {
	
	static List<Film> listOfFilms=new ArrayList<Film>();
	
	static {
		listOfFilms.add(new Film("The Goldfinch","75%"," Finn Wolfhard, Nicole Kidman, Sarah Paulson",
				"https://m.media-amazon.com/images/M/MV5BY2ZmNmUzNTctYTA3Mi00ZTg3LWFmMWMtYzU2ZjA3NDRmODIzXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_UX182_CR0,0,182,268_AL_.jpg"));
		listOfFilms.add(new Film("Once Upon a Time in Hollywood","60%","Leonardo DiCaprio ,Brad Pitt ,Margot Robbie ,Emile Hirsch ,Margaret Qualley ,Timothy Olyphant",
				"https://m.media-amazon.com/images/M/MV5BOTg4ZTNkZmUtMzNlZi00YmFjLTk1MmUtNWQwNTM0YjcyNTNkXkEyXkFqcGdeQXVyNjg2NjQwMDQ@._V1_UX182_CR0,0,182,268_AL_.jpg"));
		listOfFilms.add(new Film("Joker","80%","Joaquin Phoenix, Zazie Beetz,Robert De Niro,Jolie Chan,Marc Maron",
				"https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_UX182_CR0,0,182,268_AL_.jpg"));
		listOfFilms.add(new Film("Hustlers","90%","Jennifer Lopez, Keke Palmer, Constance Wu, Lili Reinhart, Lizzo",
				"https://m.media-amazon.com/images/M/MV5BNjM5ZTNiNGMtMDA2OC00MDYyLWEyNzAtOWZmMzFlY2VmOWM4XkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_UX182_CR0,0,182,268_AL_.jpg"));
	}
	
	@RequestMapping(value = "/film_list", method = RequestMethod.GET)
	public List<Film> doGetFilms(){
		
		return listOfFilms;
		
	}
	
	@RequestMapping(value="/film_list/{filmName}",method=RequestMethod.GET)
	public Film doGetFilmByName(@PathVariable("filmName") String filmName) {
		for(Film film:listOfFilms) {
			if(film.getFilmName().equals(filmName)) {
				return film;}
		}
		return null;
	}
	
	@RequestMapping(value="/film_list",method=RequestMethod.POST)
	public String doPostFilm(@RequestBody Film film) {
		listOfFilms.add(film);
		return "Success!";
	}
	
	@RequestMapping(value="/film_list/{filmName}",method=RequestMethod.PUT)
	public String doPutFilm(@RequestBody Film sent, @PathVariable("filmName") String filmName) {
		for(Film film:listOfFilms) {
			if(film.getFilmName().equals(filmName)) {
				film.setActors(sent.getActors());
				film.setFilmName(sent.getFilmName());
				film.setRating(sent.getRating());;
				film.setImage(sent.getImage());
				return "Updated";
			}
		}
		return "Film not Found";
	}
	
	
	@RequestMapping(value="/film_list/{filmName}", method=RequestMethod.DELETE)
	public String doDeleteFilmByName(@PathVariable("filmName") String filmName) {
		
		Film del=null;
		for(Film film:listOfFilms) {
			if(film.getFilmName().equals(filmName)) {
				del=film;
			}
		}
		if(del!=null) {
			listOfFilms.remove(del);
			return "Film Was Deleted From Favorits";
		}
		return "Film not Found";
	}
	
	

	
	
	
}
