/**
 * Clase que agrupa los datos de un Torneo
 * @author Área de programación UQ
 * @since 2023-08
 * 
 * Licencia GNU/GPL V3.0 (https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE) 
 */
package co.edu.uniquindio.poo.torneodeportivo;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static co.edu.uniquindio.poo.util.AssertionUtil.ASSERTION;

public class Torneo {
    private final String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaInicioInscripciones;
    private LocalDate fechaCierreInscripciones;
    private final byte numeroParticipantes;
    private final byte limiteEdad;
    private final int valorInscripcion;
    private final TipoTorneo tipoTorneo;
    private final Collection<Participante> participantes;
    private final CaracterTorneo caracter;
    //REQUISITO #1 - Atributo Género para realizar torneos solo para hombres, mujeres o mixto -
    private final GeneroTorneo genero;
    //Coleccion Jueces (Coleccion de los jueces del Torneo)
    private final Collection<Juez> jueces;
    //Coleccion Enfrentamientos
    private final Collection<Enfrentamiento> enfrentamientos;

    
    public Torneo(String nombre, LocalDate fechaInicio,
            LocalDate fechaInicioInscripciones,
            LocalDate fechaCierreInscripciones, byte numeroParticipantes,
            byte limiteEdad, int valorInscripcion,TipoTorneo tipoTorneo,CaracterTorneo caracter, GeneroTorneo genero) {
        
        ASSERTION.assertion( nombre != null , "El nombre es requerido");
        
        
        ASSERTION.assertion( numeroParticipantes >= 0, "El número de participantes no puede ser negativo");
        ASSERTION.assertion( limiteEdad >= 0,"El limite de edad no puede ser negativo");
        ASSERTION.assertion( valorInscripcion >= 0,"El valor de la inscripción no puede ser negativo");
        //REQUISITO #1 - Validación atributo género -
        ASSERTION.assertion( genero != null ,"El genero es requerido");
        
        this.nombre = nombre;
        this.genero = genero;

        setFechaInicioInscripciones(fechaInicioInscripciones);
        setFechaCierreInscripciones(fechaCierreInscripciones); 
        setFechaInicio(fechaInicio);
        this.numeroParticipantes = numeroParticipantes;
        this.limiteEdad = limiteEdad;
        this.valorInscripcion = valorInscripcion;
        this.tipoTorneo = tipoTorneo;
        this.participantes = new LinkedList<>();
        this.jueces = new LinkedList<>();
        this.enfrentamientos = new LinkedList<>();
        this.caracter = Objects.requireNonNull(caracter,"El carácter del torneo es requerido");
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaInicioInscripciones() {
        return fechaInicioInscripciones;
    }

    public LocalDate getFechaCierreInscripciones() {
        return fechaCierreInscripciones;
    }

    public byte getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public byte getLimiteEdad() {
        return limiteEdad;
    }

    public int getValorInscripcion() {
        return valorInscripcion;
    }

    public TipoTorneo getTipoTorneo() {
        return tipoTorneo;
    }

    public CaracterTorneo getCaracter() {
        return caracter;
    }
    public GeneroTorneo getGeneroTorneo() {
        return genero;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        ASSERTION.assertion( fechaInicio != null , "La fecha de inicio es requerida");
        ASSERTION.assertion( ( fechaInicioInscripciones == null || fechaInicio.isAfter(fechaInicioInscripciones) ) &&
                ( fechaCierreInscripciones == null || fechaInicio.isAfter(fechaCierreInscripciones) ),"La fecha de inicio no es válida" );
        this.fechaInicio = fechaInicio;
    }

    public void setFechaInicioInscripciones(LocalDate fechaInicioInscripciones) {
        ASSERTION.assertion( fechaInicioInscripciones != null , "La fecha de inicio de inscripciones es requerida");
        this.fechaInicioInscripciones = fechaInicioInscripciones;
    }

    public void setFechaCierreInscripciones(LocalDate fechaCierreInscripciones) {
        ASSERTION.assertion( fechaCierreInscripciones != null , "La fecha de cierre es requerida");
        ASSERTION.assertion( fechaCierreInscripciones.isAfter(fechaInicioInscripciones),"La fecha de cierre de inscripciones debe ser posterior a la fecha de inicio de inscripciones" );
        this.fechaCierreInscripciones = fechaCierreInscripciones;
    }
    
    /**
     * Permite registrar un participante en el torneo
     * @param participante Participante a ser registrado
     * @throws RuntimeException, Se genera un error si ya existe un equipo registrado con el mismo nombre, o en caso de que las inscripciones del torneo no estén abiertas.
     */

    public void registrarParticipante(Participante participante) {
        validarParticipanteExiste(participante); 
        validarInscripciopnesAbiertas();
        validarGenero(participante); 
        validarCaracter(participante);

        participantes.add(participante);
    }
    /* REQUISITO #2 */
    public LinkedList<Juez> registroJueces(){
        for (Enfrentamiento elemento : enfrentamientos) {
            for (Juez juez : elemento.getJueces())  if(!(buscarJuez(juez).isPresent() == true)) jueces.add(juez);
        }
        return (LinkedList<Juez>) jueces;
    }
    /* REQUISITO #3 */
    public void agendarEnfrentamiento(Enfrentamiento enfrentamiento, Participante participante1, Participante participante2){
        validarEnfrentamiento(enfrentamiento);
        enfrentamiento.setParticipante1(buscarParticipante(participante1));
        enfrentamiento.setParticipante2(buscarParticipante(participante2));
        enfrentamientos.add(enfrentamiento);
    }
    
    /* REQUISITO #6 */
    public List<Equipo> equiposOrdenadosPorVictoriasEmpatesDerrotas() {
        List<Equipo> equipos = new ArrayList<>();
    
        for (Enfrentamiento enfrentamiento : enfrentamientos) {
            enfrentamiento.resultadoEnfrentamiento();
            equipos.add((Equipo) enfrentamiento.getParticipante1());
            equipos.add((Equipo) enfrentamiento.getParticipante2());
        }
        equipos = new ArrayList<>(new HashSet<>(equipos));
    
        Comparator<Equipo> condicion = Comparator
        .comparing((Equipo equipo) -> equipo.getValorDerrota().valor()).reversed()
        .thenComparing(equipo -> equipo.getValorEmpate().valor()).reversed()
        .thenComparing(equipo -> equipo.getValorVictoria().valor());
    
        List<Equipo> equiposOrdenados = equipos.stream()
                .sorted(condicion)
                .collect(Collectors.toList());

        return equiposOrdenados;
    }
    
    /**
     * Valida que el participante sea acorde con el carácter del torneo.
     * @param participante Participante a ser registrado
     */
    private void validarCaracter(Participante participante) {
        ASSERTION.assertion( caracter.esValido(participante),"Las inscripciones no están abiertas");
    }

    private void validarInscripciopnesAbiertas() {
        boolean inscripcionAbierta = fechaInicioInscripciones.isBefore(LocalDate.now()) && fechaCierreInscripciones.isAfter(LocalDate.now());
        ASSERTION.assertion( inscripcionAbierta,"Las inscripciones no están abiertas");
    }


    private void validarParticipanteExiste(Participante participante) {
        boolean existeEquipo = buscarParticipantePorNombre(participante.getNombreCompleto()).isPresent();
        ASSERTION.assertion( !existeEquipo,"El equipo ya esta registrado");
    }
    /* REQUISITO #1 */
    private void validarGenero(Participante participante){
        if (this.genero == GeneroTorneo.MASCULINO || this.genero == GeneroTorneo.FEMENINO ) {
            boolean generoJugador = participante.getGenero() == this.genero;
            ASSERTION.assertion( generoJugador,"El participante no corresponde al género del Torneo");
        }
    }
    /* REQUISITO #3 */
    private void validarEnfrentamiento(Enfrentamiento enfrentamiento) {
        boolean existente = buscarEnfrentamiento(enfrentamiento).isPresent();
        boolean duelosCruzados = buscarFechasCruzadas(enfrentamiento).isPresent();
        
        ASSERTION.assertion( !existente, "Ya existe un enfrentamiento agendado.");
        ASSERTION.assertion( !duelosCruzados, "Los equipos agendados para enfrentamiento tiene un juego ese día.");
    }

    /**
     * Permite obtener una copia no modificable de la lista de los participantes registrados.
     * @return Collection<Participante> no modificable de los participantes registrados en el torneo.
     */
    public Collection<Participante> getParticipantes() {
        return Collections.unmodifiableCollection(participantes);
    }
    
    /**
     * Permite buscar un participante por su nombre entre los participantes registrados en el torneo
     * @param nombre Nombre del participante que se está buscando
     * @return Un Optional<Participante> con el participante cuyo nombre sea igual al nombre buscado, o un Optional vacío en caso de no encontrar un participante con nombre igual al dado.
     */
    public Optional<Participante> buscarParticipantePorNombre(String nombre){
        Predicate<Participante> condicion = participante->participante.getNombreCompleto().equals(nombre);
        return participantes.stream().filter(condicion).findAny();
    }
    public Optional<Juez> buscarJuez(Juez juez){
        Predicate<Juez> condicion = j-> j.getNombre().equals(juez.getNombre()) && j.getApellido().equals(juez.getApellido());
        return jueces.stream().filter(condicion).findAny();
    }
    /* REQUISITO #3 */
    public Optional<Enfrentamiento> buscarEnfrentamiento(Enfrentamiento enfrentamiento){
        Predicate<Enfrentamiento> condicion = j-> j.getNombre().equals(enfrentamiento.getNombre())
        || j.getUbicacion().equals(enfrentamiento.getUbicacion())
        && j.getFecha().equals(enfrentamiento.getFecha())
        && j.getHora().equals(enfrentamiento.getHora());
        return enfrentamientos.stream()
        .filter(condicion)
        .findAny();
    }
    /* REQUISITO #3 */
    public Optional<Enfrentamiento> buscarFechasCruzadas(Enfrentamiento enfrentamiento){
        Predicate<Enfrentamiento> condicion = j-> j.getParticipante1().equals(enfrentamiento.getParticipante1())
        && j.getParticipante2().equals(enfrentamiento.getParticipante2()) && j.getHora().equals(enfrentamiento.getHora())
        && j.getFecha().equals(enfrentamiento.getFecha());
        return enfrentamientos.stream()
        .filter(condicion)
        .findAny();
    }
    /* REQUISITO #4 */
    public Collection<Enfrentamiento> buscarEnfrentamientoEquipo(String nombreParticipante){
        Predicate<Enfrentamiento> condicion = j-> j.getParticipante1().getNombreCompleto().equals(nombreParticipante)
        || j.getParticipante2().getNombreCompleto().equals(nombreParticipante);

        return enfrentamientos.stream().filter(condicion).toList();
    }

    /* REQUISITO #5 */
    public Collection<Enfrentamiento> buscarEnfrentamientosPorJuez(int licenciaJuez) {
        Predicate<Enfrentamiento> condicion = enfrentamiento ->
                enfrentamiento.getJueces().stream().anyMatch(juez -> juez.getLicencia() == licenciaJuez);
    
        return enfrentamientos.stream().filter(condicion).toList();
    }
    
    /**
     * Permite registrar un jugador en el equipo siempre y cuando este dentro de las fechas validas de registro, 
     * no exista ya un jugador registrado con el mismo nombre y apellido y el jugador no exceda el limite de edad del torneo.
     *  
     * @param nombre Nombre del equipo en que se desea registrar el jugador
     * @param jugador Jugador que se desea registrar.
     */
    public void registrarJugador(String nombre, Jugador jugador) {
        var participante = buscarParticipantePorNombre(nombre);
        
        participante.ifPresent( (e)->{
            if( e instanceof Equipo equipo){
                registrarJugador(equipo, jugador);
            }
        } );
    }

    /**
     * Permite registrar un jugador en el equipo siempre y cuando este dentro de las fechas validas de registro, 
     * no exista ya un jugador registrado con el mismo nombre y apellido y el jugador no exceda el limite de edad del torneo.
     * 
     * @param equipo Equipo en el que se desea registrar el jugador.
     * @param jugador Jugador que se desea registrar.
     */
    public void registrarJugador(Equipo equipo, Jugador jugador) {
        ASSERTION.assertion( !LocalDate.now().isAfter(fechaCierreInscripciones) , "No se pueden registrar jugadores después del a fecha de cierre de inscripciones");
        validarLimiteEdadJugador(jugador); 
        validarJugadorExiste(jugador);
        equipo.registrarJugador(jugador);
    }

    /**
     * Permite buscar un jugador basado en su nombre y apellido en todos los equipos registrados en el torneo.
     * @param jugador Jugador que se desea buscar
     * @return Optional con el jugador encontrado o un optional vacío en caso de no haber encontrado un jugador con el nombre y apellido del jugador buscado.
     */
    public Optional<Jugador> buscarJugador(Jugador jugador){
        return participantes.stream()
            .filter(p->p instanceof Equipo)
            .map(p->(Equipo)p)
            .map(equipo->equipo.buscarJugador(jugador))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findAny();
    }

    private void validarJugadorExiste(Jugador jugador) {
        boolean existeJugador = buscarJugador(jugador).isPresent();
        ASSERTION.assertion( !existeJugador,"El jugador ya esta registrado");
    }

    private void validarLimiteEdadJugador(Jugador jugador) {
        var edadAlInicioTorneo = jugador.calcularEdad(fechaInicio);
        ASSERTION.assertion( limiteEdad == 0 || limiteEdad >= edadAlInicioTorneo , "No se pueden registrar jugadores que excedan el limite de edad del torneo"); 
    }

    public void registrarEstadisticaParticipante(Participante participante, ValorEstadistica valorEstadistica) {
        final var participanteRegistrado = buscarParticipante(participante);
        participanteRegistrado.registrarEstadistica(valorEstadistica);
    }

    public Collection<ValorEstadistica> obtenerEstadisticas(Participante participante) {
        final var participanteRegistrado = buscarParticipante(participante);
        return participanteRegistrado.getEstadisticas();
    }

    private Participante buscarParticipante(Participante participante) {
        Objects.requireNonNull(participante,"El participante es requerido");
        var participanteRegistrado = buscarParticipantePorNombre(participante.getNombreCompleto());
        ASSERTION.assertion( participanteRegistrado.isPresent() ,"El participante no esta registrado");
        return participanteRegistrado.get();
    }

    public Optional<Participante> buscarMejor(Estadistica estadistica) {
        Predicate<Participante> condicion = participante -> participante.getEstadisticas()
                .stream()
                .anyMatch( e->e.estadistica().equals(estadistica) );
        Comparator<Participante> comparator = (p1,p2)->p1.comparar(p2,estadistica);
        return participantes.stream().filter(condicion).max(comparator);
    }

    public Collection<Participante> buscarSuperiorIgualA(Estadistica estadistica, double valor) {
        ValorEstadistica valorEstadistica = new ValorEstadistica(valor,estadistica);
        Predicate<Participante> condicion = participante -> participante.getEstadisticas().stream()
                .anyMatch(  e->e.estadistica().equals(estadistica)&&e.compareTo(valorEstadistica) >= 0 );
        return participantes.stream().filter(condicion).toList();
    }

    public Collection<Participante>  buscarInferiorA(Estadistica estadistica, double valor) {
        ValorEstadistica valorEstadistica = new ValorEstadistica(valor,estadistica);
        Predicate<Participante> condicion = participante -> participante.getEstadisticas().stream()
                .anyMatch(  e->e.estadistica().equals(estadistica)&&e.compareTo(valorEstadistica) < 0 );
        return participantes.stream().filter(condicion).toList();
    }

    public double promedioEstadistica(Estadistica estadistica) {
        var promedio = participantes.stream().flatMap( participante -> participante.getEstadisticas().stream() )
                .filter(e->e.estadistica().equals(estadistica))
                .mapToDouble(ValorEstadistica::valor)
                .average();
        ASSERTION.assertion(promedio.isPresent(),"No se encuentran datos para calcular la media");
        return promedio.getAsDouble();
    }

    public int compararParticipantes(Participante participanteA, Participante participanteB, Estadistica estadistica) {
        var a = buscarParticipante(participanteA);
        var b = buscarParticipante(participanteB);
        return a.comparar(b,estadistica);
    }
}
