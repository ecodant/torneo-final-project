/**
 * Clase para probar el registro de los equipos
 * @author Área de programación UQ
 * @since 2023-08
 * 
 * Licencia GNU/GPL V3.0 (https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE) 
 */
package co.edu.uniquindio.poo.torneodeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class EquipoTest {
    private static final Logger LOG = Logger.getLogger(EquipoTest.class.getName());
    
    @Test
    public void registrarEquipo() {
        LOG.info("Inicio de prueba registrarEquipo...");
        
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.FEMENINO);
        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var equipo = new Equipo("Uniquindio", representante, GeneroTorneo.FEMENINO);

        torneo.registrarParticipante(equipo);

        assertTrue(torneo.getParticipantes().contains(equipo));
        assertEquals(1, torneo.getParticipantes().size());
        LOG.info("Fin de prueba registrarEquipo...");
    }

    @Test
    public void nombreEquipoRepetido() {

        LOG.info("Inicio de prueba nombreEquipoRepetido...");
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.FEMENINO);
        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        var equipo = new Equipo("Uniquindio", representante, GeneroTorneo.FEMENINO);
        var equipo2 = new Equipo("Uniquindio", representante, GeneroTorneo.FEMENINO);
        torneo.registrarParticipante(equipo);

        assertThrows(Throwable.class, ()-> torneo.registrarParticipante(equipo2));
        
        LOG.info("Fin de prueba nombreEquipoRepetido...");
    }
 
    @Test
    public void inscripcionCerrada() {
        LOG.info("Inicio de prueba inscripcionCerrada...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\| fechaActual - 15 días\|fechaActual-1 días\|24\|0\|0\|LOCAL|GRUPAL}  Equipo{Uniquindio} Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300}
        
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().minusDays(1), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.MASCULINO);
        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var equipo = new Equipo("Uniquindio", representante, GeneroTorneo.MASCULINO); 

        assertThrows(Throwable.class, ()-> torneo.registrarParticipante(equipo));
        
        LOG.info("Fin de prueba inscripcionCerrada...");
    }

    @Test
    public void inscripcionNoAbierta() {

        LOG.info("Inicio de prueba inscripcionNoAbierta...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\| fechaActual + 1 día\|fechaActual+15 días\|24\|0\|0\|LOCAL|GRUPAL}  Equipo{Uniquindio} Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300}
        
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().minusDays(1), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.MASCULINO);
        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var equipo = new Equipo("Uniquindio", representante, GeneroTorneo.MASCULINO); 

        assertThrows(Throwable.class, ()-> torneo.registrarParticipante(equipo));
        
        LOG.info("Fin de prueba inscripcionNoAbierta...");
    }

    /* REQUISITO #1 */
    @Test
    public void validarGeneroIndividual() {
        LOG.info("Inicio de prueba validar Género Individual...");

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        var equipo = new Equipo("Uniquindio", representante, GeneroTorneo.MASCULINO);
        var jugador = new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",LocalDate.now().minusYears(15), GeneroTorneo.FEMENINO);
        assertThrows(Throwable.class, ()-> equipo.registrarJugador(jugador));
        
        LOG.info("Fin de prueba validar Género Individual...");
    }

    /* REQUISITO #1 */
    @Test
    public void validarGeneroMixto() {
        LOG.info("Inicio de prueba validar Género Mixto...");

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        var equipo = new Equipo("Uniquindio", representante, GeneroTorneo.MIXTO);
        var jugador1 = new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",LocalDate.now().minusYears(15), GeneroTorneo.MASCULINO);
        var jugador2 = new Jugador("Sofia", "Ramirez", "cramiresa@email.com", "293297322",LocalDate.now().minusYears(15), GeneroTorneo.FEMENINO);
        equipo.registrarJugador(jugador1);
        equipo.registrarJugador(jugador2);

        assertTrue(equipo.getJugadores().contains(jugador1));
        assertTrue(equipo.getJugadores().contains(jugador2));
        
        LOG.info("Fin de prueba  validar Género Mixto...");
    }
    /* REQUISITO #6 */
    @Test
    public void validarEstadisticasEquipo() {
        LOG.info("Inicio de prueba validar Estadistica victorias, derrotas, empates...");

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        var equipo = new Equipo("Uniquindio", representante, GeneroTorneo.MIXTO);

        equipo.añadirVictoria();
        equipo.añadirDerrota();
        equipo.añadirEmpate();

        assertEquals((short)1, (short) equipo.getValorVictoria().valor());
        assertEquals((short)1, (short) equipo.getValorEmpate().valor());
        assertEquals((short)1, (short) equipo.getValorDerrota().valor());

        LOG.info("Fin de la prueba.");
    }
}
