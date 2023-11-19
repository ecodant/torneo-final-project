/**
 * Clase para probar el registro de los jugadores
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

public class JugadorTest {

    private static final Logger LOG = Logger.getLogger(JugadorTest.class.getName());
    
    @Test
    public void registrarJugadorEquipo() {
        LOG.info("Inicio de prueba registrarJugadorEquipo...");
        // Almacenar los datos de prueba Equipo{Uniquindio} Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300}, Jugador {Christian,Candela,chrcandela@email.com,6067431234, fechaActual - 15 años}
        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var equipo = new Equipo("Uniquindio", representante,GeneroTorneo.MASCULINO);
        var jugador = new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",LocalDate.now().minusYears(15),GeneroTorneo.MASCULINO);

        equipo.registrarJugador(jugador);

        // Recuperación y verificación de datos
        assertTrue(equipo.getJugadores().contains(jugador));
        assertEquals(1, equipo.getJugadores().size());
        LOG.info("Fin de prueba registrarJugadorEquipo...");
    }

    @Test
    public void registrarJugadorTorneo() {
        LOG.info("Inicio de prueba registrarJugadorTorneo...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\| fechaActual - 15 días\|fechaActual+15 días\|24\|18\|0\|LOCAL|GRUPAL}  Equipo{Uniquindio} Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300}, Jugador {Christian,Candela,chrcandela@email.com,6067431234, fechaActual - 15 años}

        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)18, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL,GeneroTorneo.MIXTO);

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var equipo = new Equipo("Uniquindio", representante,GeneroTorneo.MIXTO);
        var jugador = new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",LocalDate.now().minusYears(15),GeneroTorneo.MASCULINO);

        torneo.registrarParticipante(equipo);
        torneo.registrarJugador("Uniquindio",jugador);

        // Recuperación y verificación de datos
        assertTrue(equipo.getJugadores().contains(jugador));
        assertEquals(1, equipo.getJugadores().size());
        LOG.info("Fin de prueba registrarJugadorTorneo...");
    }

    @Test
    public void registrarJugadorTorneoSinLimiteEdad() {
        LOG.info("Inicio de prueba registrarJugadorTorneoSinLimiteEdad...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\| fechaActual - 15 días\|fechaActual+15 días\|24\|0\|0\|LOCAL|GRUPAL}  Equipo{Uniquindio} Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300}, Jugador {Christian,Candela,chrcandela@email.com,6067431234, fechaActual - 21 años}

        
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL,GeneroTorneo.MASCULINO);

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var equipo = new Equipo("Uniquindio", representante,GeneroTorneo.MASCULINO);
        var jugador = new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",LocalDate.now().minusYears(21),GeneroTorneo.MASCULINO);

        torneo.registrarParticipante(equipo);
        torneo.registrarJugador("Uniquindio",jugador);

        // Recuperación y verificación de datos
        assertTrue(equipo.getJugadores().contains(jugador));
        assertEquals(1, equipo.getJugadores().size());
        LOG.info("Fin de prueba registrarJugadorTorneoSinLimiteEdad...");
    }

    @Test
    public void registrarJugadorTorneoConLimiteEdad() {
        LOG.info("Inicio de prueba registrarJugadorTorneoConLimiteEdad...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\| fechaActual - 15 días\|fechaActual+15 días\|24\|18\|0\|LOCAL|GRUPAL}  Equipo{Uniquindio} Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300}, Jugador {Christian,Candela,chrcandela@email.com,6067431234, fechaActual - 21 años}

        
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)18, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL,GeneroTorneo.MIXTO);

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var equipo = new Equipo("Uniquindio", representante,GeneroTorneo.MIXTO);
        var jugador = new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",LocalDate.now().minusYears(21),GeneroTorneo.MASCULINO);

        torneo.registrarParticipante(equipo);
        assertThrows(Throwable.class,()->torneo.registrarJugador("Uniquindio",jugador));

        
        LOG.info("Fin de prueba registrarJugadorTorneoConLimiteEdad...");
    }

    @Test
    public void registrarJugadorTorneoInscripcionesCerradas() {
        LOG.info("Inicio de prueba registrarJugadorTorneoInscripcionesCerradas...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\| fechaActual - 15 días\|fechaActual-1 día\|24\|18\|0\|LOCAL|GRUPAL}  Equipo{Uniquindio} Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300} Jugador {Christian,Candela,chrcandela@email.com,6067431234, fechaActual - 15 años}

        
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(1), (byte)24, (byte)18, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL,GeneroTorneo.MASCULINO);

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var equipo = new Equipo("Uniquindio", representante,GeneroTorneo.MASCULINO);
        var jugador = new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",LocalDate.now().minusYears(15),GeneroTorneo.MASCULINO);

        torneo.registrarParticipante(equipo);
        torneo.setFechaCierreInscripciones(LocalDate.now().minusDays(1));
        assertThrows(Throwable.class,()->torneo.registrarJugador("Uniquindio",jugador));

        
        LOG.info("Fin de prueba registrarJugadorTorneoInscripcionesCerradas...");
    }

    @Test
    public void registrarJugadoresRepetidosEquipo() {
        LOG.info("Inicio de prueba registrarJugadorEquipo...");

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var equipo = new Equipo("Uniquindio", representante,GeneroTorneo.MIXTO);
        var jugador = new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",LocalDate.now().minusYears(15),GeneroTorneo.MASCULINO);
        var jugador2 = new Jugador("Christian", "Candela", "ccandela@email.com", "6067431235",LocalDate.now().minusYears(15),GeneroTorneo.MASCULINO);
        equipo.registrarJugador(jugador);

        assertThrows(Throwable.class,()->equipo.registrarJugador(jugador2));

        LOG.info("Fin de prueba registrarJugadorEquipo...");
    }

    @Test
    public void registrarJugadoresRepetidosTorneo() {
        LOG.info("Inicio de prueba registrarJugadoresRepetidosTorneo...");
        // Almacenar los datos de prueba Torneo{Copa Mundo\|fechaActual+ 1mes\| fechaActual - 15 días\|fechaActual+15 días\|24\|18\|0\|LOCAL|GRUPAL}  Equipo{Uniquindio} Representante{Robinson,Pulgarin,rpulgarin@email.com,6067359300},  Jugador {Christian,Candela,chrcandela@email.com,6067431234, fechaActual - 15 años}, Jugador {Christian,Candela,chrcandela@email.com,6067431234, fechaActual - 15 años}, Equipo{Quindío}

        var torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)18, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL,GeneroTorneo.MASCULINO);

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var equipo = new Equipo("Uniquindio", representante,GeneroTorneo.MASCULINO);
        var equipo2 = new Equipo("Quindío", representante,GeneroTorneo.MASCULINO);
        torneo.registrarParticipante(equipo);
        torneo.registrarParticipante(equipo2);

        var jugador = new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",LocalDate.now().minusYears(15),GeneroTorneo.MASCULINO);
        var jugador2 = new Jugador("Christian", "Candela", "ccandela@email.com", "6067431235",LocalDate.now().minusYears(15),GeneroTorneo.MASCULINO);
                
        torneo.registrarJugador("Uniquindio",jugador);
        assertThrows(Throwable.class,()->torneo.registrarJugador("Quindío",jugador2));

        LOG.info("Fin de prueba registrarJugadoresRepetidosTorneo...");
    }
    /* REQUISITO #1 */
    @Test
    public void jugadorGeneroMixto() {
        LOG.info("Inicio de prueba juagdor Género Mixto...");

        assertThrows(Throwable.class,()->new Jugador("Christian", "Candela", "chrcandela@email.com", "6067431234",LocalDate.now().minusYears(15),GeneroTorneo.MIXTO));
        
        LOG.info("Fin de prueba registrarJugadoresRepetidosTorneo...");
    }
}
