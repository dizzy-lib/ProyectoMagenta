package domain.services;

import domain.entities.Genero;
import domain.entities.Pelicula;
import domain.entities.PeliculaBase;
import infraestructure.CarteleraRepository;
import shared.exceptions.IdDuplicadoException;

public class ServicioPeliculas {
    private final CarteleraRepository carteleraRepository;
    private static final int ID_INICIAL = 1; // ID que se asigna cuando no hay películas

    public ServicioPeliculas(CarteleraRepository carteleraRepository) {
        this.carteleraRepository = carteleraRepository;
    }

    public Pelicula generarPelicula(
            String nombre,
            String director,
            int anno,
            int duracion,
            String genero
    ) throws IllegalArgumentException {
        // Obtiene el siguiente ID disponible de forma segura
        int nuevoId = obtenerSiguienteId();

        // Crea la instancia de película
        return new PeliculaBase(
                nuevoId,
                nombre,
                director,
                anno,
                duracion,
                Genero.valueOf(genero)
        );
    }

    /**
     * Obtiene el siguiente ID disponible para una nueva película
     * @return el siguiente ID disponible (1 si no hay películas, último ID + 1 si existen)
     */
    private int obtenerSiguienteId() {
        try {
            Pelicula ultimaPelicula = this.carteleraRepository.getUltimaPelicula();

            // Si no hay películas en la base de datos, empieza con ID 1
            if (ultimaPelicula == null) {
                return ID_INICIAL;
            }

            // Si hay películas, retorna el último ID + 1
            return ultimaPelicula.getId() + 1;

        } catch (Exception e) {
            // En caso de error al acceder al repositorio, también empieza con ID 1
            System.err.println("Error al obtener última película, usando ID inicial: " + e.getMessage());
            return ID_INICIAL;
        }
    }

    public void agregarPelicula(Pelicula pelicula) throws IdDuplicadoException {
        this.carteleraRepository.agregarPelicula(pelicula);
    }

    public Pelicula generarPeliculaSegura(
            String nombre,
            String director,
            int anno,
            int duracion,
            String genero
    ) throws IllegalArgumentException {
        // Obtiene el siguiente ID disponible de forma segura
        int nuevoId = obtenerSiguienteId();

        // Crea la instancia de película
        return new PeliculaBase(
                nuevoId,
                nombre,
                director,
                anno,
                duracion,
                Genero.valueOf(genero)
        );
    }
}