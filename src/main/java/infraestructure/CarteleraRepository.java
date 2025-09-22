package infraestructure;

import domain.entities.Pelicula;

/**
 * Interfaz que maneja las operaciones que se pueden
 * realizar en el repositorio de cartelera
 */
public interface CarteleraRepository {
    /**
     * Agrega una película dentro de la cartelera
     * @param pelicula pelicula a agregar
     */
    void agregarPelicula(Pelicula pelicula);

    /**
     * Obtiene la última película agregada (con el id actualizado)
     * @return última película agregada a la base de datos
     */
    Pelicula getUltimaPelicula();
}
