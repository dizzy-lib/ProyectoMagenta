/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation;

import application.AgregarPeliculaUseCase;
import domain.entities.Genero;
import domain.services.ServicioPeliculas;
import infraestructure.CarteleraMySQLRepository;
import infraestructure.CarteleraRepository;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author kevin
 */
public class FormularioPrincipal extends JFrame {
    
        //cuerpo del formulario
        private JPanel formularioPanel;
        private JLabel tituloLabel, directorLabel, annoLabel, duracionLabel, generoLabel;
        private JTextField tituloField, directorField;
        private JSpinner annoSpinner, duracionSpinner;
        private JComboBox<String> generoComboBox;
        private JButton agregarButton, limpiarButton;   
    
        private final AgregarPeliculaUseCase agregarPeliculaUseCase;
    
    
    
    public FormularioPrincipal(AgregarPeliculaUseCase agregarPeliculaUseCase){
        this.agregarPeliculaUseCase = agregarPeliculaUseCase;

        //configuraciones de la ventana emergente
        setTitle("Gestor de Películas - CINE_DB");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        //panel del formulario
        formularioPanel = new JPanel();
        formularioPanel.setLayout(new GridLayout(6, 2, 10, 10)); // seis filas con dos columnas
        formularioPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        //titulo
        tituloLabel = new JLabel("Título:");
        tituloField = new JTextField();
        formularioPanel.add(tituloLabel);
        formularioPanel.add(tituloField);
        
        //director
        directorLabel = new JLabel("Director:");
        directorField = new JTextField();
        formularioPanel.add(directorLabel);
        formularioPanel.add(directorField);
        
        //año
        annoLabel = new JLabel("Año:");
        SpinnerModel annoModel = new SpinnerNumberModel(2023, 1000, 9999, 1);
        annoSpinner = new JSpinner(annoModel);
        formularioPanel.add(annoLabel);
        formularioPanel.add(annoSpinner);
        
        //duracion
        duracionLabel = new JLabel("Duración (min):");
        SpinnerModel duracionModel = new SpinnerNumberModel(90, 1, 1000, 1);
        duracionSpinner = new JSpinner(duracionModel);
        formularioPanel.add(duracionLabel);
        formularioPanel.add(duracionSpinner);
        
        //genero
        generoLabel = new JLabel("Género:");
        generoComboBox = new JComboBox<>(obtenerGeneros());
        formularioPanel.add(generoLabel);
        formularioPanel.add(generoComboBox);
        
        //botones
        agregarButton = new JButton("Agregar Película");
        limpiarButton = new JButton("Limpiar Campos");
        formularioPanel.add(limpiarButton);
        formularioPanel.add(agregarButton);
        
        add(formularioPanel, BorderLayout.CENTER);
        
        //acciones de cada boton
        agregarButton.addActionListener(e -> agregarPelicula());
        limpiarButton.addActionListener(e -> limpiarCampos());
        
        
    }
    private String[] obtenerGeneros() {
            Genero[] generos = Genero.values();
            String[] nombresGeneros = new String[generos.length];
            for (int i = 0; i < generos.length; i++) {
                nombresGeneros[i] = generos[i].name();
            }
            return nombresGeneros;
    }
    
    private void agregarPelicula(){
        try{
            String titulo = tituloField.getText();
            String director = directorField.getText();
            int anno = (int) annoSpinner.getValue();
            int duracion = (int) duracionSpinner.getValue();
            String genero = (String) generoComboBox.getSelectedItem();
            
            agregarPeliculaUseCase.execute(titulo, director, anno, duracion, genero);
            
            JOptionPane.showMessageDialog(
                this,
                "Película agregada exitosamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
            limpiarCampos(); //deja vacios los campos despues de agregar
            
        }catch(IllegalArgumentException e){
           //para errores en validacion
            JOptionPane.showMessageDialog(
                this,
                "Error de validación: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            ); 
        }catch(RuntimeException e){
            //para errores en base de datos
            JOptionPane.showMessageDialog(
                this,
                "Error al agregar la película: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void limpiarCampos(){
        tituloField.setText("");
        directorField.setText("");
        annoSpinner.setValue(2023);
        duracionSpinner.setValue(90);
        generoComboBox.setSelectedIndex(0);
    }

}
