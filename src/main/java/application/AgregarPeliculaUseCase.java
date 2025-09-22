package application;

import domain.entities.Pelicula;
import domain.services.ServicioPeliculas;

public class AgregarPeliculaUseCase {
    private final ServicioPeliculas servicioPelicula;

    public AgregarPeliculaUseCase(ServicioPeliculas servicioPelicula) {
        this.servicioPelicula = servicioPelicula;
    }

    public void execute(String nombre, String director, int anno, int duracion, String genero) {
        // 1. generar la pelicula con los datos ingresados por el usuario
        Pelicula pelicula = this.servicioPelicula.generarPelicula(nombre, director, anno, duracion, genero);

        // 2. agrega la película al repositorio
        this.servicioPelicula.agregarPelicula(pelicula);
    }
}
