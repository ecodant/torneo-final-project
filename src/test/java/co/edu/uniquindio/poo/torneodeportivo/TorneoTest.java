/**
 * Clase para probar el funcionamiento del Torneo
 * @author Área de programación UQ
 * @since 2023-08
 * 
 * Licencia GNU/GPL V3.0 (https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE) 
 */
package co.edu.uniquindio.poo.torneodeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;


import org.junit.jupiter.api.Test;

public class TorneoTest {

    private static final Logger LOG = Logger.getLogger(TorneoTest.class.getName());

    @Test
    public void datosCompletos() {
        LOG.info("Inicio de prueba datos completos...");
        // Almacenar los datos de prueba Copa Mundo|2023-10-01|2023-08-01|2023-09-15|24|0|0|LOCAL|GRUPAL
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 8, 1), LocalDate.of(2023, 9, 15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.MASCULINO);

        // Recuperación y verificación de datos
        assertEquals("Copa Mundo",torneo.getNombre());
        assertEquals(LocalDate.of(2023, 10, 1),torneo.getFechaInicio());
        assertEquals(LocalDate.of(2023, 8, 1),torneo.getFechaInicioInscripciones());
        assertEquals(LocalDate.of(2023, 9, 15),torneo.getFechaCierreInscripciones());
        assertEquals((byte)24,torneo.getNumeroParticipantes());
        assertEquals((byte)0,torneo.getLimiteEdad());
        assertEquals(0,torneo.getValorInscripcion());
        assertEquals(TipoTorneo.LOCAL,torneo.getTipoTorneo());
        assertEquals(GeneroTorneo.MASCULINO,torneo.getGeneroTorneo());
        LOG.info("Fin de prueba datos completos...");
    }

    @Test
    public void datosNulos() {
        LOG.info("Inicio de prueba datos nulos...");
        // Almacenar los datos de prueba null|null|null|null|24|0|0|null|LOCAL|GRUPAL
        assertThrows(Throwable.class, ()-> new Torneo(null, null, null, null, (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.MASCULINO));
        
        
        LOG.info("Fin de prueba datos nulos...");
    }

    @Test
    public void participantesNegativos() {
        LOG.info("Inicio de prueba número de participantes negativo...");
        // Almacenar los datos de prueba Copa Mundo|2023-10-01|2023-08-01|2023-09-15|-24|0|0|LOCAL|GRUPAL
        assertThrows(Throwable.class, ()-> new Torneo("Copa Mundo", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 8, 01), LocalDate.of(2023, 10, 15), (byte)-24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.FEMENINO));
        
        LOG.info("Fin de prueba  número de participantes negativo...");
    }


    @Test
    public void limiteEdadesNegativo() {
        LOG.info("Inicio de prueba limites de edades negativo...");
        // Almacenar los datos de prueba Copa Mundo|2023-10-01|2023-08-01|2023-09-15|24|-1|0|LOCAL|GRUPAL
        assertThrows(Throwable.class, ()-> new Torneo("Copa Mundo", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 8, 01), LocalDate.of(2023, 10, 15), (byte)24, (byte)-1, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.MIXTO));
        
        LOG.info("Fin de prueba  limites de edades negativo...");
    }

    @Test
    public void inscripcionNegativa() {
        LOG.info("Inicio de prueba inscripción negativa...");
        // Almacenar los datos de prueba Copa Mundo|2023-10-01|2023-08-01|2023-09-15|24|0|-1|LOCAL|GRUPAL
        assertThrows(Throwable.class, ()-> new Torneo("Copa Mundo", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 8, 01), LocalDate.of(2023, 10, 15), (byte)24, (byte)0, -1,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.MASCULINO));
        
        LOG.info("Fin de prueba inscripción negativa...");
    }

    @Test
    public void inscripcionTardia() {
        LOG.info("Inicio de prueba inscripción tardia...");
        // Almacenar los datos de prueba Copa Mundo|2023-10-01|2023-11-01|2023-11-15|24|0|0|LOCAL
        assertThrows(Throwable.class, ()-> new Torneo("Copa Mundo", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 11, 01), LocalDate.of(2023, 11, 15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.FEMENINO));
        
        LOG.info("Fin de prueba inscripción tardia...");
    }

    @Test
    public void cierreInscripcionAnteriorInicio() {
        LOG.info("Inicio de prueba Cierre inscripción anterior al inicio...");
        // Almacenar los datos de prueba Copa Mundo|2023-10-01|2023-11-01|2023-11-15|24|0|0|LOCAL|GRUPAL
        assertThrows(Throwable.class, ()-> new Torneo("Copa Mundo", LocalDate.of(2023, 10, 1), LocalDate.of(2023, 8, 15), LocalDate.of(2023, 8, 1), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.MIXTO));
        
        LOG.info("Fin de prueba Cierre inscripción anterior al inicio...");
    }
    /* REQUISITO #2 */
    @Test
    public void registroJuecesTorneo(){

        LOG.info("Inicio de prueba Enfrentamientos Torneo anterior al inicio...");

        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
         Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.MASCULINO);
        var equipo1 = new Equipo("Uniquindio", representante, GeneroTorneo.MASCULINO);
        var equipo2 = new Equipo("EAM", representante, GeneroTorneo.MASCULINO);

        torneo.registrarParticipante(equipo1);
        torneo.registrarParticipante(equipo2);
        Juez juez1 = new Juez("Gustavo", "Pinilla", "losmilitareslomejor@gmail.com", "6027339300", (int)912818);
        Juez juez2 = new Juez("Jorge", "Eliecer", "soyliberal@gmail.com", "60282673300", (int)9122118);
        Juez juez3 = new Juez("Gabriel", "Garcia", "elmandelbillete@gmail.com", "293723727", (int)8121818);
        Enfrentamiento enfrentamiento1 = new Enfrentamiento("EAM vs Uniquindio","Estadio Centenario", LocalDate.of(2024, 03, 10), LocalTime.of(12, 30));
        enfrentamiento1.asociarJuezEnfrentamiento(juez1);
        enfrentamiento1.asociarJuezEnfrentamiento(juez2);
        enfrentamiento1.asociarJuezEnfrentamiento(juez3);

        torneo.agendarEnfrentamiento(enfrentamiento1, equipo1, equipo2);

        Juez juez4 = new Juez("Maria", "Lopez", "maria@gmail.com", "1234567890", 5555555);
        Juez juez5 = new Juez("Pedro", "Ramirez", "pedro@gmail.com", "9876543210", 7777777);
        Juez juez6 = new Juez("Laura", "Rodriguez", "laura@gmail.com", "5555555555", 9999999);
      
        Enfrentamiento enfrentamiento2 = new Enfrentamiento("Gran Colombia vs Uniquindio","Estadio Medellín", LocalDate.of(2024, 03, 10), LocalTime.of(12, 30));

        enfrentamiento2.asociarJuezEnfrentamiento(juez4);
        enfrentamiento2.asociarJuezEnfrentamiento(juez5);
        enfrentamiento2.asociarJuezEnfrentamiento(juez6);
        torneo.agendarEnfrentamiento(enfrentamiento2, equipo1, equipo2);

        assertEquals(juez1, torneo.registroJueces().get(0));
        assertEquals(juez2, torneo.registroJueces().get(1));
        assertEquals(juez3, torneo.registroJueces().get(2));
        assertEquals(juez4, torneo.registroJueces().get(3));
        assertEquals(juez5, torneo.registroJueces().get(4));
        assertEquals(juez6, torneo.registroJueces().get(5));

        LOG.info("Fin de la prueba.");
    }
    /* REQUISITO #2 */
    @Test
    public void juecesRepetidosEnElRegistro(){

        LOG.info("Inicio de prueba jueces repetidos en el registro...");
        
        var representante = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");

        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.FEMENINO);
        var equipo1 = new Equipo("Uniquindio", representante, GeneroTorneo.FEMENINO);
        var equipo2 = new Equipo("EAM", representante, GeneroTorneo.FEMENINO);
        var equipo3 = new Equipo("Gran Colombia", representante, GeneroTorneo.FEMENINO);

        torneo.registrarParticipante(equipo1);
        torneo.registrarParticipante(equipo2);
        torneo.registrarParticipante(equipo3);

        Juez juez1 = new Juez("Gustavo", "Pinilla", "losmilitareslomejor@gmail.com", "6027339300", (int)912818);
    

        Enfrentamiento enfrentamiento1 = new Enfrentamiento("EAM vs Uniquindio","Estadio Centenario", LocalDate.of(2024, 03, 10), LocalTime.of(12, 30));
        Enfrentamiento enfrentamiento2 = new Enfrentamiento("Gran Colombia vs Uniquindio","Estadio Medellín", LocalDate.of(2024, 03, 11), LocalTime.of(12, 30));

        //Mismo juez asociado a dos enfrentamientos diferentes.
        enfrentamiento1.asociarJuezEnfrentamiento(juez1);
        enfrentamiento2.asociarJuezEnfrentamiento(juez1);

        torneo.agendarEnfrentamiento(enfrentamiento1, equipo2, equipo1);
        torneo.agendarEnfrentamiento(enfrentamiento2, equipo3, equipo1);

        assertEquals(1, torneo.registroJueces().size());

        LOG.info("Fin de la prueba.");
    }
    /* REQUISITO #4 */
    @Test
    public void listaEnfrentamientosPorNombreEquipo(){
        LOG.info("Inicio de la prueba 'Lista enfrentamiento por nombre del equipo/participante'..");

        var representante1 = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var representante2 = new Persona("Alfonso", "Cano", "alcano23@email.com", "6275325321");
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.FEMENINO);
        var equipo1 = new Equipo("Uniquindio", representante1, GeneroTorneo.FEMENINO);
        var equipo2 = new Equipo("EAM", representante2, GeneroTorneo.FEMENINO);
        var equipo3 = new Equipo("Gran Colombia", representante2, GeneroTorneo.FEMENINO);
        var equipo4 = new Equipo("Alexander Von", representante2, GeneroTorneo.FEMENINO);

        torneo.registrarParticipante(equipo1);
        torneo.registrarParticipante(equipo2);
        torneo.registrarParticipante(equipo3);
        torneo.registrarParticipante(equipo4);

        Enfrentamiento enfrentamiento1 = new Enfrentamiento("EAM vs Uniquindio","Estadio Centenario", LocalDate.of(2024, 03, 10), LocalTime.of(12, 30));
        Enfrentamiento enfrentamiento2 = new Enfrentamiento("Gran Colombia vs Uniquindio","Estadio Centenario", LocalDate.of(2024, 04, 10), LocalTime.of(12, 30));
        Enfrentamiento enfrentamiento3 = new Enfrentamiento("Alexander Von vs Uniquindio","Estadio Centenario", LocalDate.of(2024, 04, 11), LocalTime.of(12, 30));
        Collection<Enfrentamiento> enfrentamientosEsperados = new LinkedList<>();

        enfrentamientosEsperados.add(enfrentamiento1);
        enfrentamientosEsperados.add(enfrentamiento2);
        enfrentamientosEsperados.add(enfrentamiento3);

        torneo.agendarEnfrentamiento(enfrentamiento1, equipo1, equipo2);
        torneo.agendarEnfrentamiento(enfrentamiento2, equipo1, equipo3);
        torneo.agendarEnfrentamiento(enfrentamiento3, equipo1, equipo4);
        
        assertEquals(enfrentamientosEsperados, torneo.buscarEnfrentamientoEquipo("Uniquindio"));

        LOG.info("Fin de la prueba.");
    }
    /* REQUISITO #5 */
    @Test
    public void buscarEnfrentamientosPorJuezNumeroLicencia(){

        LOG.info("Inicio de prueba Buscar enfrentamientos por Juez...");
        var representante1 = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var representante2 = new Persona("Alfonso", "Cano", "alcano23@email.com", "6275325321");
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.MASCULINO);
        
        var equipo1 = new Equipo("Uniquindio", representante1, GeneroTorneo.MASCULINO);
        var equipo2 = new Equipo("EAM", representante2, GeneroTorneo.MASCULINO);
        var equipo3 = new Equipo("Gran Colombia", representante2, GeneroTorneo.MASCULINO);

        torneo.registrarParticipante(equipo1);
        torneo.registrarParticipante(equipo2);
        torneo.registrarParticipante(equipo3);

        Enfrentamiento enfrentamiento1 = new Enfrentamiento("EAM vs Uniquindio","Estadio Centenario", LocalDate.of(2024, 03, 10), LocalTime.of(12, 30));
        Enfrentamiento enfrentamiento2 = new Enfrentamiento("Gran Colombia vs Uniquindio","Estadio Centenario", LocalDate.of(2024, 04, 10), LocalTime.of(12, 30));

        Juez juez1 = new Juez("Gustavo", "Pinilla", "losmilitareslomejor@gmail.com", "6027339300", (int)912818);
        
        enfrentamiento1.asociarJuezEnfrentamiento(juez1);
        enfrentamiento2.asociarJuezEnfrentamiento(juez1);

        Collection<Enfrentamiento> enfrentamientosEsperados = new LinkedList<>();
        
        enfrentamientosEsperados.add(enfrentamiento1);
        enfrentamientosEsperados.add(enfrentamiento2);

        torneo.agendarEnfrentamiento(enfrentamiento1, equipo1, equipo2);
        torneo.agendarEnfrentamiento(enfrentamiento2, equipo1, equipo3);

        assertEquals(enfrentamientosEsperados, torneo.buscarEnfrentamientosPorJuez(912818));

        LOG.info("Fin de la prueba.");
    }
    /* REQUISITO #6 */
    @Test
    void EquiposOrdenadosPorVictoriasEmpatesDerrotas() {
        LOG.info("Inicio de prueba Requisito #6 Proyecto final...");
        var representante1 = new Persona("Robinson", "Pulgarin", "rpulgarin@email.com", "6067359300");
        var representante2 = new Persona("Alfonso", "Cano", "alcano23@email.com", "6275325321");
        Torneo torneo = new Torneo("Copa Mundo", LocalDate.now().plusMonths(1), LocalDate.now().minusDays(15), LocalDate.now().plusDays(15), (byte)24, (byte)0, 0,TipoTorneo.LOCAL,CaracterTorneo.GRUPAL, GeneroTorneo.FEMENINO);


        var equipo1 = new Equipo("Uniquindio", representante1, GeneroTorneo.FEMENINO);
        var equipo2 = new Equipo("EAM", representante2, GeneroTorneo.FEMENINO);
        var equipo3 = new Equipo("Gran Colombia", representante2, GeneroTorneo.FEMENINO);

        torneo.registrarParticipante(equipo1);
        torneo.registrarParticipante(equipo2);
        torneo.registrarParticipante(equipo3);

        Enfrentamiento enfrentamiento1 = new Enfrentamiento("EAM vs Uniquindio","Estadio Centenario", LocalDate.of(2024, 03, 10), LocalTime.of(12, 30));
        Enfrentamiento enfrentamiento2 = new Enfrentamiento("Gran Colombia vs Uniquindio","Estadio Centenario", LocalDate.of(2024, 04, 10), LocalTime.of(12, 30));
        Enfrentamiento enfrentamiento3 = new Enfrentamiento("EAM vs Gran Colombia", "Coliseo del café", LocalDate.of(2024, 03, 20), LocalTime.of(13, 45));
        Juez juez1 = new Juez("Gustavo", "Pinilla", "losmilitareslomejor@gmail.com", "6027339300", (int)912818);
        
        enfrentamiento1.asociarJuezEnfrentamiento(juez1);
        enfrentamiento2.asociarJuezEnfrentamiento(juez1);
        enfrentamiento3.asociarJuezEnfrentamiento(juez1);

        torneo.agendarEnfrentamiento(enfrentamiento1, equipo1, equipo2);
        torneo.agendarEnfrentamiento(enfrentamiento2, equipo1, equipo3);
        torneo.agendarEnfrentamiento(enfrentamiento3, equipo2, equipo3);

        List<Equipo> equiposOrdenados = torneo.equiposOrdenadosPorVictoriasEmpatesDerrotas();

        assertEquals(equipo1, equiposOrdenados.get(0)); 
        assertEquals(equipo2, equiposOrdenados.get(1)); 
        assertEquals(equipo3, equiposOrdenados.get(2)); 

        LOG.info("Fin de la prueba.");
    }
}
