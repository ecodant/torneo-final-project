/**
 * Registro que agrupa los datos de un Equipo
 * @author Área de programación UQ
 * @since 2023-09
 *
 * Licencia GNU/GPL V3.0 (https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE) 
 */

package co.edu.uniquindio.poo.torneodeportivo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

import static co.edu.uniquindio.poo.util.AssertionUtil.ASSERTION;

public class Equipo implements Participante {
    private String nombre;
    private Persona representante;
    private Collection<Jugador> jugadores;
    private RegistroEstadistica registroEstadistica;
    private GeneroTorneo genero;
    /*Estadisticas*/
    private Estadistica victorias =  new Estadistica("victorias", TipoEstadistica.POSITIVA);
    private Estadistica derrotas = new Estadistica("derrotas", TipoEstadistica.NEGATIVA);
    private Estadistica empates  = new Estadistica("empates", TipoEstadistica.NEUTRAL);
    private  ValorEstadistica valorVictorias;  
    private  ValorEstadistica valorDerrotas;  
    private  ValorEstadistica valorEmpates;  

    public Equipo (String nombre, Persona representante, Collection<Jugador> jugadores,
                RegistroEstadistica registroEstadistica, GeneroTorneo genero){

        ASSERTION.assertion( nombre != null && !nombre.isBlank() , "El nombre es requerido");
        ASSERTION.assertion( representante != null , "El representante es requerido");
        ASSERTION.assertion( genero != null , "El género es requerido");

        this.nombre = nombre;
        this.representante = representante;
        this.jugadores = new LinkedList<>();
        this.genero = genero;

        valorVictorias = new ValorEstadistica((int)0, victorias);
        valorDerrotas = new ValorEstadistica((int)0, derrotas);
        valorEmpates = new ValorEstadistica((int)0, empates);
        this.registroEstadistica = new RegistroEstadisticaImpl();
    }

    public Equipo(String nombre,Persona representante, GeneroTorneo genero){
        this(nombre,representante,new LinkedList<>(),new RegistroEstadisticaImpl(), genero);
    }
    public Persona getRepresentante()
    {
        return representante;
    }
    /**
     * Permite registrar un jugador en un equipo siempre y cuando no exista ya un jugador registrado en el equipo con el mismo nombre y apellido
     * @param jugador Jugador que se desea registrar.
     */
    public void registrarJugador(Jugador jugador) {
        validarGenero(jugador);
        validarJugadorExiste(jugador);
        jugadores.add(jugador);
    }
    public void añadirVictoria(){
        valorVictorias = new ValorEstadistica(valorVictorias.valor() + 1, victorias);
        registrarEstadistica(valorVictorias);
    }
    public void añadirDerrota(){
        valorDerrotas = new ValorEstadistica(valorDerrotas.valor() + 1, derrotas);
        registrarEstadistica(valorDerrotas);
    }

    public void añadirEmpate(){

        valorEmpates = new ValorEstadistica(valorEmpates.valor() + 1, empates);
        registrarEstadistica(valorEmpates);
    }
    public ValorEstadistica getValorVictoria(){
        return valorVictorias;
    }
    public ValorEstadistica getValorDerrota(){
        return valorDerrotas;
    }
    public ValorEstadistica getValorEmpate(){
        return valorEmpates;
    }
    /**
     * Permimte buscar un jugador en el equipo basado en su nombre y apellido.
     * @param jugador Jugador que se desea buscar
     * @return Optional con el jugador que coincida con el nombre y apellido del jugador buscado,
     * o Optinal vacío en caso de no encontrar un jugador en el equipo con dicho nombre y apellido.
     */
    public Optional<Jugador> buscarJugador(Jugador jugador){
        Predicate<Jugador> nombreIgual = j->j.getNombre().equals(jugador.getNombre());
        Predicate<Jugador> apellidoIgual = j->j.getApellido().equals(jugador.getApellido());
        return jugadores.stream().filter(nombreIgual.and(apellidoIgual)).findAny();
    }

    public Collection<Jugador> getJugadores(){
        return jugadores;
    }

    private void validarJugadorExiste(Jugador jugador) {
        boolean existeJugador = buscarJugador(jugador).isPresent();
        ASSERTION.assertion( !existeJugador,"El jugador ya esta registrado");
    }

    /* REQUISITO #1 - Validación del Genero de un jugador para el equipo */

    private void validarGenero(Jugador jugador){
        if (this.genero == GeneroTorneo.MASCULINO || this.genero == GeneroTorneo.FEMENINO){
            boolean generoJugador = jugador.getGenero() == this.genero;
            ASSERTION.assertion( generoJugador,"El jugador tiene un género diferente al del Equipo");
        } 
    }

    @Override
    public String getNombreCompleto() {
        return nombre;
    }
    @Override
    public GeneroTorneo getGenero() {
        return genero;
    }
    @Override
    public RegistroEstadistica getEstadisticaRegister() {
        return registroEstadistica;
    }
}
