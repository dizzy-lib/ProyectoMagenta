import application.AgregarPeliculaUseCase;
import domain.services.ServicioPeliculas;
import infraestructure.CarteleraMySQLRepository;
import infraestructure.CarteleraRepository;
import presentation.FormularioPrincipal;
import javax.swing.SwingUtilities;

public class Application {
    public static void main(String[] args) {
       
        CarteleraRepository carteleraRepository = new CarteleraMySQLRepository();
        ServicioPeliculas servicioPeliculas = new ServicioPeliculas(carteleraRepository);
        AgregarPeliculaUseCase agregarPeliculaUseCase = new AgregarPeliculaUseCase(servicioPeliculas);
        
        SwingUtilities.invokeLater(() -> {
            new FormularioPrincipal(agregarPeliculaUseCase).setVisible(true);
            });
        
        
        
        
        
        
        /*CarteleraRepository carteleraRepository = new CarteleraMySQLRepository();
        ServicioPeliculas servicioPeliculas = new ServicioPeliculas(carteleraRepository);
        AgregarPeliculaUseCase agregarPeliculaUseCase = new AgregarPeliculaUseCase(servicioPeliculas);


        // Para hacer: esto al final hay que asociarlo al botón del formulario
        // es decir cuando el boton de agregar película se clickee se debe ejecutar esto
        agregarPeliculaUseCase.execute(
                "Interestellar",
                "Christofer Nolan",
                2014,
                1999,
                "ACCION"
        );
*/
    }
}
