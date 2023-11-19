package co.edu.uniquindio.poo.torneodeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

/* REQUISITO #3 */
public class EnfrentamientoTest {
    private static final Logger LOG = Logger.getLogger(EnfrentamientoTest.class.getName());

    @Test
    public void testPendiente() {
        LOG.info("Inicio de prueba testPendiente...");
        LocalDate fechaEnfrentamiento = LocalDate.of(2023, 12, 30);
        LocalTime horaEnfrentamiento = LocalTime.of(2, 20, 30);

        Enfrentamiento enfrentamiento = new Enfrentamiento("E1", "La Cancha Norte", fechaEnfrentamiento, horaEnfrentamiento);

       assertEquals(EstadoEnfrentamiento.PENDIENTE, enfrentamiento.getEstado());
        LOG.info("Fin de la prueba testPendiente.");
    }
    @Test
    public void testEnJuego() {
        LOG.info("Inicio de prueba testEnJuego...");
        LocalDate fechaEnfrentamiento = LocalDate.of(2023, 11, 20);
        LocalTime horaEnfrentamiento = LocalTime.of(3, 10, 30);

        Enfrentamiento enfrentamiento = new Enfrentamiento("E1", "La Cancha Norte", fechaEnfrentamiento, horaEnfrentamiento);

       assertEquals(EstadoEnfrentamiento.EN_JUEGO, enfrentamiento.getEstado());
        LOG.info("Fin de la prueba testEnJuego.");
    }

    @Test
    public void testeFinalizado() {
        LOG.info("Inicio de prueba testeFinalizado...");

        Enfrentamiento enfrentamiento = new Enfrentamiento("E1", "La Cancha Norte", LocalDate.of(2023, 11, 3), LocalTime.of(2, 20, 30));

        enfrentamiento.resultadoEnfrentamiento();

        assertEquals(EstadoEnfrentamiento.FINALIZADO, enfrentamiento.getEstado());
        LOG.info("Fin de la prueba testeFinalizado.");
    }
    @Test
    public void testAplazado() {
        LOG.info("Inicio de prueba testAplazado...");
        /*Dado el caso que no se registre lugar, se intuye que no existe un lugar
         para disputar el enfrentamiento por ende se establece como "APLAZADO"*/
        Enfrentamiento enfrentamiento = new Enfrentamiento("E1", null, LocalDate.of(2023, 11, 3), LocalTime.of(2, 20, 30));

        assertEquals(EstadoEnfrentamiento.APLAZADO, enfrentamiento.getEstado());
        LOG.info("Fin de la prueba testAplazado.");
    } 
}
