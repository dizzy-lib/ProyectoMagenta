package infraestructure;

import java.sql.*;

import domain.entities.Genero;
import domain.entities.Pelicula;
import domain.entities.PeliculaBase;

public class CarteleraMySQLRepository implements CarteleraRepository{
    private static final String URL = "jdbc:mysql://localhost:3306/cine_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public CarteleraMySQLRepository() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver MySQL: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public boolean testConection() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch(SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método auxiliar para crear una película desde ResultSet
     * @param rs ResultSet con los datos de la película
     * @return Pelicula objeto película creado
     * @throws SQLException si hay error al leer los datos
     */
    private Pelicula crearPeliculaDesdeResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String titulo = rs.getString("titulo");
        String director = rs.getString("director");
        int anno = rs.getInt("anno");
        int duracion = rs.getInt("duracion");
        String generoStr = rs.getString("genero");

        // Convertir string a enum Genero
        Genero genero = Genero.valueOf(generoStr);

        // Crear y retornar la película (ajusta según tu clase concreta)
        return new PeliculaBase(id, titulo, director, anno, duracion, genero);
    }

    @Override
    public void agregarPelicula(Pelicula pelicula) {
        String sql = "INSERT INTO cartelera (titulo, director, anno, duracion, genero) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, pelicula.getTitulo());
            statement.setString(2, pelicula.getDirector());
            statement.setInt(3, pelicula.getAnno());
            statement.setInt(4, pelicula.getDuracion());
            statement.setString(5, pelicula.getGenero().toString());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Película agregada exitosamente: " + pelicula.getTitulo());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar película: " + e.getMessage(), e);
        }
    }

    @Override
    public Pelicula getUltimaPelicula() {
        String sql = "SELECT * FROM cartelera ORDER BY id DESC LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                // Aquí necesitarías crear la instancia de tu clase Pelicula concreta
                // Asumo que tienes PeliculaBase como implementación
                return crearPeliculaDesdeResultSet(resultSet);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la última película: " + e.getMessage(), e);
        }
    }

    // Método main para probar la conexión
    public static void main(String[] args) {
        CarteleraMySQLRepository repository = new CarteleraMySQLRepository();

        if (repository.testConection()) {
            System.out.println("✓ Conexión a MySQL exitosa!");
        } else {
            System.out.println("✗ Error en la conexión a MySQL");
        }
    }
}
