package co.edu.uniquindio.poo.torneodeportivo;
import static co.edu.uniquindio.poo.util.AssertionUtil.ASSERTION;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

/* 
    Se desea poder agendar los enfrentamientos, indicando el lugar donde
    se realizará el enfrentamiento (nombre, ubicación), la fecha y hora
    del enfrentamiento, los equipos que se enfrentarán, el juez o jueces
    que arbitraran el encuentro, el resultado del enfrentamiento cuando
    haya concluido (puntos realizados por cada equipo) y el estado 
    del enfrentamiento. Los posibles estados de un enfrentamiento son:
 */
public class Enfrentamiento {
    private String nombre;
    private String ubicacion;
    private LocalDate fecha;
    private LocalTime hora;
    private Participante participante1;
    private Participante participante2;
    private Collection<Juez> jueces;
    private short puntosParticipante1;
    private short puntosParticipante2;
    private EstadoEnfrentamiento estado;

    
    public Enfrentamiento(String nombre, String ubicacion, LocalDate fecha, LocalTime hora){
        ASSERTION.assertion( nombre != null && !nombre.isBlank() , "El nombre es requerido");
        //Ubicación como condición para el estado APLAZADO.
        // ASSERTION.assertion( ubicacion != null && !ubicacion.isBlank() , "La ubicación es requerida");
        ASSERTION.assertion( fecha != null , "La fecha es requerida");
        ASSERTION.assertion( hora != null , "La hora es requerida");
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.hora = hora;

        this.jueces = new LinkedList<>();

        asignarEstado();
    }
    private void asignarEstado(){
        if (ubicacion == null) estado = EstadoEnfrentamiento.APLAZADO;
        else if (this.fecha.isAfter(LocalDate.now()) || 
        (this.fecha.isEqual(LocalDate.now()) && this.hora.isAfter(LocalTime.now()))) estado = EstadoEnfrentamiento.PENDIENTE;
        else if (this.fecha.isBefore(LocalDate.now()) || 
        (this.fecha.isEqual(LocalDate.now()) && this.hora.isBefore(LocalTime.now()))) estado = EstadoEnfrentamiento.EN_JUEGO;
    }
    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }
    public Juez getJuez(){
        return jueces.isEmpty() ? null : jueces.iterator().next();
    }
    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public Participante getParticipante1() {
        return participante1;
    }

    public Participante getParticipante2() {
        return participante2;
    }

    public Collection<Juez> getJueces() {
        return Collections.unmodifiableCollection(jueces);
    }

    public short getPuntosParticipante1() {
        return puntosParticipante1;
    }

    public short getPuntosParticipante2() {
        return puntosParticipante2;
    }

    public EstadoEnfrentamiento getEstado() {
        return estado;
    }
    public void setParticipante1(Participante participante){
        ASSERTION.assertion( participante != null , "El participante #1 no puede ser nullo");
        this.participante1 = participante;
        
    }
    public void setParticipante2(Participante participante){
        ASSERTION.assertion( participante != null , "El participante #2 no puede ser nullo");
        this.participante2 = participante;
    }

    public void asociarJuezEnfrentamiento(Juez juez){
        validarJuezExistente(juez); 
        jueces.add(juez);
    }

    private void validarJuezExistente(Juez juez) {
        boolean existeJuez = buscarJuez(juez).isPresent();
        ASSERTION.assertion( !existeJuez,"El juez ya esta registrado");
    }

    public Optional<Juez> buscarJuez(Juez juez){
        Predicate<Juez> condicion = j-> j.getNombre().equals(juez.getNombre()) && j.getApellido().equals(juez.getApellido());
        return jueces.stream().filter(condicion).findAny();
    }

    public void setEstado(EstadoEnfrentamiento estado) {
        this.estado = estado;
    }
    public boolean esAplazado(){
        return true;
    }

    public void resultadoEnfrentamiento(){
        
        Random random = new Random();

        puntosParticipante1= 0;
        puntosParticipante2 = 0;

        /*Valores para prueba siempre gana el participante #1 del enfrentamiento.*/

        this.puntosParticipante1 = (short) 2;
        this.puntosParticipante2 = (short) 1;

        /*Aleatoriedad para en los puntos "simular" un juego de verdad.*/
        // this.puntosParticipante1 = (short) (random.nextInt(3) + 1);
        // this.puntosParticipante2 = (short) (random.nextInt(3) + 1);

        if (participante1 != null && participante2 != null){
            if (puntosParticipante1 > puntosParticipante2) {

                ((Equipo) participante1).añadirVictoria();
                ((Equipo) participante2).añadirDerrota();
            } 
            else if (puntosParticipante1 < puntosParticipante2) {

                ((Equipo) participante1).añadirDerrota();
                ((Equipo) participante2).añadirVictoria();
            } else {

                ((Equipo) participante1).añadirEmpate();
                ((Equipo) participante2).añadirEmpate();
            }
        }    
        estado = EstadoEnfrentamiento.FINALIZADO;
    }
}


