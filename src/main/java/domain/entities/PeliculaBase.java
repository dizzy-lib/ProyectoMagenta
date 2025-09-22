package domain.entities;

import shared.utils.Validaciones;

public class PeliculaBase implements Pelicula{
    private final int id;
    private String titulo;
    private String director;
    private int anno;
    private int duracion;
    private Genero genero;

    public PeliculaBase(
            int id,
            String titulo,
            String director,
            int anno,
            int duracion,
            Genero genero
    ) throws IllegalArgumentException {
        // Realiza validaciones de dominio
        Validaciones.validarPositivo(id);
        Validaciones.validarTexto(titulo);
        Validaciones.validarTexto(director);
        Validaciones.validarNumero(anno, 1000, 9999);
        Validaciones.validarPositivo(duracion);

        this.id = id;

        // Hace trim para que borre espacios al inicio
        // y fin de los datos cargados
        this.titulo = titulo.trim();
        this.director = director.trim();

        this.anno = anno;
        this.duracion = duracion;
        this.genero = genero;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getTitulo() {
        return this.titulo;
    }

    @Override
    public String getDirector() {
        return this.director;
    }

    @Override
    public int getAnno() {
        return this.anno;
    }

    @Override
    public int getDuracion() {
        return this.duracion;
    }

    @Override
    public Genero getGenero() {
        return this.genero;
    }

    @Override
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public void setAnno(int anno) {
        this.anno = anno;
    }

    @Override
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    @Override
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return String.format(
                "PeliculaBase{id=%d, titulo='%s', director='%s', anno=%d, duracion=%d min, genero=%s}",
                id,
                titulo,
                director,
                anno,
                duracion,
                genero != null ? genero : "No especificado"
        );
    }

    // pruebas unitarias
    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBAS UNITARIAS PARA PeliculaBase ===\n");

        // 1. La película tiene un id positivo mayor a 0
        System.out.println("1. Prueba ID positivo:");
        try {
            PeliculaBase pelicula1 = new PeliculaBase(1, "Titulo", "Director", 2023, 120, Genero.ACCION);
            System.out.println("✓ PASS: ID positivo válido = " + pelicula1.getId());
        } catch (Exception e) {
            System.out.println("✗ FAIL: " + e.getMessage());
        }

        try {
            PeliculaBase peliculaInvalida = new PeliculaBase(0, "Titulo", "Director", 2023, 120, Genero.ACCION);
            System.out.println("✗ FAIL: Debería fallar con ID = 0");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ PASS: Rechaza correctamente ID = 0 - " + e.getMessage());
        }

        try {
            PeliculaBase peliculaInvalida2 = new PeliculaBase(-1, "Titulo", "Director", 2023, 120, Genero.ACCION);
            System.out.println("✗ FAIL: Debería fallar con ID negativo");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ PASS: Rechaza correctamente ID negativo - " + e.getMessage());
        }

        // 2. El nombre de la película no debe ser null
        System.out.println("\n2. Prueba título no null:");
        try {
            PeliculaBase pelicula2 = new PeliculaBase(1, "El Padrino", "Director", 2023, 120, Genero.DRAMA);
            System.out.println("✓ PASS: Título válido - " + pelicula2.getTitulo());
        } catch (Exception e) {
            System.out.println("✗ FAIL: " + e.getMessage());
        }

        try {
            PeliculaBase peliculaInvalida = new PeliculaBase(1, null, "Director", 2023, 120, Genero.DRAMA);
            System.out.println("✗ FAIL: Debería fallar con título null");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ PASS: Rechaza correctamente título null - " + e.getMessage());
        }

        try {
            PeliculaBase peliculaInvalida2 = new PeliculaBase(1, "", "Director", 2023, 120, Genero.DRAMA);
            System.out.println("✗ FAIL: Debería fallar con título vacío");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ PASS: Rechaza correctamente título vacío - " + e.getMessage());
        }

        // 3. El director de la película no debe ser null
        System.out.println("\n3. Prueba director no null:");
        try {
            PeliculaBase pelicula3 = new PeliculaBase(1, "Titulo", "Christopher Nolan", 2023, 120, Genero.ACCION);
            System.out.println("✓ PASS: Director válido - " + pelicula3.getDirector());
        } catch (Exception e) {
            System.out.println("✗ FAIL: " + e.getMessage());
        }

        try {
            PeliculaBase peliculaInvalida = new PeliculaBase(1, "Titulo", null, 2023, 120, Genero.ACCION);
            System.out.println("✗ FAIL: Debería fallar con director null");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ PASS: Rechaza correctamente director null - " + e.getMessage());
        }

        try {
            PeliculaBase peliculaInvalida2 = new PeliculaBase(1, "Titulo", "   ", 2023, 120, Genero.ACCION);
            System.out.println("✗ FAIL: Debería fallar con director solo espacios");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ PASS: Rechaza correctamente director solo espacios - " + e.getMessage());
        }

        // 4. El año de la película debe estar entre 1000 y 9999
        System.out.println("\n4. Prueba año válido (1000-9999):");
        try {
            PeliculaBase pelicula4 = new PeliculaBase(1, "Titulo", "Director", 2023, 120, Genero.DRAMA);
            System.out.println("✓ PASS: Año válido - " + pelicula4.getAnno());
        } catch (Exception e) {
            System.out.println("✗ FAIL: " + e.getMessage());
        }

        try {
            PeliculaBase peliculaInvalida = new PeliculaBase(1, "Titulo", "Director", 999, 120, Genero.DRAMA);
            System.out.println("✗ FAIL: Debería fallar con año < 1000");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ PASS: Rechaza correctamente año 999 - " + e.getMessage());
        }

        try {
            PeliculaBase peliculaInvalida2 = new PeliculaBase(1, "Titulo", "Director", 10000, 120, Genero.DRAMA);
            System.out.println("✗ FAIL: Debería fallar con año > 9999");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ PASS: Rechaza correctamente año 10000 - " + e.getMessage());
        }

        // 5. La duración de la película debe ser mayor a 0
        System.out.println("\n5. Prueba duración positiva:");
        try {
            PeliculaBase pelicula5 = new PeliculaBase(1, "Titulo", "Director", 2023, 120, Genero.ACCION);
            System.out.println("✓ PASS: Duración válida - " + pelicula5.getDuracion() + " min");
        } catch (Exception e) {
            System.out.println("✗ FAIL: " + e.getMessage());
        }

        try {
            PeliculaBase peliculaInvalida = new PeliculaBase(1, "Titulo", "Director", 2023, 0, Genero.ACCION);
            System.out.println("✗ FAIL: Debería fallar con duración = 0");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ PASS: Rechaza correctamente duración 0 - " + e.getMessage());
        }

        try {
            PeliculaBase peliculaInvalida2 = new PeliculaBase(1, "Titulo", "Director", 2023, -10, Genero.ACCION);
            System.out.println("✗ FAIL: Debería fallar con duración negativa");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ PASS: Rechaza correctamente duración negativa - " + e.getMessage());
        }

        // 6. Se debe hacer trim del nombre de la película
        System.out.println("\n6. Prueba trim del título:");
        try {
            PeliculaBase pelicula6 = new PeliculaBase(1, "   El Padrino   ", "Director", 2023, 120, Genero.DRAMA);
            String tituloTrimmed = pelicula6.getTitulo();
            if (tituloTrimmed.equals("El Padrino")) {
                System.out.println("✓ PASS: Trim del título funciona correctamente - '" + tituloTrimmed + "'");
            } else {
                System.out.println("✗ FAIL: Trim del título no funciona - '" + tituloTrimmed + "'");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: " + e.getMessage());
        }

        // 7. Se debe hacer trim del director de la película
        System.out.println("\n7. Prueba trim del director:");
        try {
            PeliculaBase pelicula7 = new PeliculaBase(1, "Titulo", "   Steven Spielberg   ", 2023, 120, Genero.TERROR);
            String directorTrimmed = pelicula7.getDirector();
            if (directorTrimmed.equals("Steven Spielberg")) {
                System.out.println("✓ PASS: Trim del director funciona correctamente - '" + directorTrimmed + "'");
            } else {
                System.out.println("✗ FAIL: Trim del director no funciona - '" + directorTrimmed + "'");
            }
        } catch (Exception e) {
            System.out.println("✗ FAIL: " + e.getMessage());
        }

        // Prueba completa con objeto válido
        System.out.println("\n8. Prueba creación de objeto completo válido:");
        try {
            PeliculaBase peliculaCompleta = new PeliculaBase(
                    42,
                    "  Inception  ",
                    "  Christopher Nolan  ",
                    2010,
                    148,
                    Genero.ACCION
            );
            System.out.println("✓ PASS: Película creada exitosamente:");
            System.out.println(peliculaCompleta.toString());
        } catch (Exception e) {
            System.out.println("✗ FAIL: " + e.getMessage());
        }

        System.out.println("\n=== PRUEBAS UNITARIAS COMPLETADAS ===");
    }
}
