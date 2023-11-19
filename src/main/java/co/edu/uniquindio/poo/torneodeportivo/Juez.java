package co.edu.uniquindio.poo.torneodeportivo;

import static co.edu.uniquindio.poo.util.AssertionUtil.ASSERTION;
/* REQUISITO #2 - Incorporación de la Clase Juez */
public class Juez extends Persona {
    private final int licencia;

    public Juez(String nombre, String apellido, String email, String celular, int licencia) {
        super(nombre, apellido, email, celular);
        ASSERTION.assertion( licencia > 0 , "El número de licencia no puede ser 0 o inferior.");
        this.licencia = licencia;
    }

    public int getLicencia() {
        return licencia;
    }
}
