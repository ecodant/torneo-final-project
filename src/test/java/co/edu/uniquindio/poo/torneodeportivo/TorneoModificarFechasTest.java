/**
 * Clase para probar la modificación de fechas en el Torneo
 * @author Área de programación UQ
 * @since 2023-08
 * 
 * Licencia GNU/GPL V3.0 (https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE) 
 */
package co.edu.uniquindio.poo.torneodeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class TorneoModificarFechasTest {

    private static final Logger LOG = Logger.getLogger(TorneoModificarFechasTest.class.getName());

    @Test
    public void modificarFechaInicio() {
        LOG.info("Inicio de prueba modificar fecha de inicio valida...");
        // Almacenar los datos de prueba Copa Mundo|2023-10-01|2023-08-01|2023-09-15|24|0|0|LOCAL|GRUPAL
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 8, 1), LocalDate.of(2023, 9, 15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL,GeneroTorneo.MASCULINO);

        // Modificación de la fecha
        torneo.setFechaInicio(LocalDate.of(2023, 10, 12));
        
        assertEquals(LocalDate.of(2023, 10, 12),torneo.getFechaInicio());
        
        
        LOG.info("Fin de prueba modificar fecha de inicio valida...");
    }


    @Test
    public void modificarFechaInicioNull() {
        LOG.info("Inicio de prueba modificar fecha de inicio null...");
        // Almacenar los datos de prueba Copa Mundo|2023-10-01|2023-08-01|2023-09-15|24|0|0|LOCAL|GRUPAL
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 8, 1), LocalDate.of(2023, 9, 15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL,GeneroTorneo.MIXTO);

        // Modificación de la fecha
        assertThrows(Throwable.class,()->torneo.setFechaInicio(null));
        
        LOG.info("Fin de prueba modificar fecha de inicio null...");
    }

    @Test
    public void modificarFechaInicioAnteriorInscripciones() {
        LOG.info("Inicio de prueba modificar fecha de inicio anterior a inscripciones...");
        // Almacenar los datos de prueba Copa Mundo|2023-10-01|2023-08-01|2023-09-15|24|0|0|LOCAL|GRUPAL
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 8, 1), LocalDate.of(2023, 9, 15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL,GeneroTorneo.MASCULINO);

        // Modificación de la fecha
        assertThrows(Throwable.class,()->torneo.setFechaInicio(LocalDate.of(2023, 7, 1)));
        
        LOG.info("Fin de prueba modificar fecha de inicio anterior a inscripciones...");
    }

    @Test
    public void modificarFechaInicioInscripciones() {
        LOG.info("Inicio de prueba modificar fecha de inicio de inscripciones valida...");
        // Almacenar los datos de prueba Copa Mundo|2023-10-01|2023-08-01|2023-09-15|24|0|0|LOCAL|GRUPAL
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 8, 1), LocalDate.of(2023, 9, 15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL,GeneroTorneo.MIXTO);

        // Modificación de la fecha
        torneo.setFechaInicioInscripciones(LocalDate.of(2023, 8, 10));
        
        assertEquals(LocalDate.of(2023, 8, 10),torneo.getFechaInicioInscripciones());
        
        
        LOG.info("Fin de prueba modificar fecha de inicio de inscripciones valida...");
    }

    @Test
    public void modificarFechaInicioInscripcionesNull() {
        LOG.info("Inicio de prueba modificar fecha de inicio de inscripciones null...");
        // Almacenar los datos de prueba Copa Mundo|2023-10-01|2023-08-01|2023-09-15|24|0|0|LOCAL|GRUPAL
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 8, 1), LocalDate.of(2023, 9, 15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL,GeneroTorneo.MASCULINO);

        // Modificación de la fecha
        assertThrows(Throwable.class,()->torneo.setFechaInicioInscripciones(null));
        
        LOG.info("Fin de prueba modificar fecha de inicio de inscripciones null...");
    }
}
