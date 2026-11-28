package trabajos;

import java.io.Serializable;
import java.time.LocalDateTime;

import excepciones.EstadoTrabajoIncorrectoException;

public class Treball implements Serializable{
    
    public enum Material {
        FILAMENTO, RESINA, NINGUNO;
    }

    public enum Estado {
        PENDIENTE, EN_CURSO, ACABADO;
    }

    private int codigo;                         // identifica al trabajo
    private String idMaquina;                   // identifica a la máquina
    private String idUsuario;                   // alias que identifica al usuario
    private Material material;                  // material de la impresora
    private LocalDateTime fechaSolicitud;       // Cuándo se pidió el trabajo
    private double duracion;                       // Horas de trabajo
    private double cantidadMaterial;               // g o mL. 0 si no hay material.
    private Estado estado;                      // pendiente, en curso o acabado
    private LocalDateTime fechaFin;             // cuándo acabó
    private LocalDateTime fechaInicio;          // cuando empezó

    public Treball(int codigo, String idMaquina, String idUsuario, Material material, LocalDateTime fechaSolicitud, double duracion, double cantidadMaterial) {
        if(idMaquina == null || idUsuario == null || material == null || fechaSolicitud == null || duracion <= 0 || cantidadMaterial < 0) throw new IllegalArgumentException("Trabajo creado incorrectamente");
        this.codigo = codigo;
        this.idMaquina = idMaquina;
        this.idUsuario = idUsuario;
        this.material = material;
        this.fechaSolicitud = fechaSolicitud;
        this.duracion = duracion;
        this.cantidadMaterial = cantidadMaterial;
        estado = Estado.PENDIENTE;
        this.fechaFin = null;
        this.fechaInicio = null;
    }

    public int getCodigo() {
        return codigo;
    }
    public String getIdMaquina() {
        return idMaquina;
    }
    public String getIdUsuario() {
        return idUsuario;
    }
    public Material getMaterial() {
        return material;
    }
    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
    public double getDuracion() {
        return duracion;
    }
    public double getCantidadMaterial() {
        return cantidadMaterial;
    }
    public Estado getEstado() {
        return estado;
    }
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void iniciar() throws EstadoTrabajoIncorrectoException{
        if(estado != Estado.PENDIENTE) throw new EstadoTrabajoIncorrectoException("Se ha iniciado un trabajo que no esta pendiente");
        estado = Estado.EN_CURSO;
    }

    public void finalizar(LocalDateTime ahora) throws EstadoTrabajoIncorrectoException {
        if(estado != Estado.EN_CURSO) throw new EstadoTrabajoIncorrectoException("Se ha finalizado un trabajo que no esta en curso");
        if(ahora.isBefore(fechaSolicitud)) throw new IllegalArgumentException("La fecha de finalizacion del trabajo no puede ser anterior a la fecha de solicitud");
        estado = Estado.ACABADO;
        fechaFin = ahora;
    }

    public boolean estaPendiente() {
        return estado == Estado.PENDIENTE || estado == Estado.EN_CURSO;
    }

    public boolean estaEnEspera() {
        return estado == Estado.PENDIENTE;
    }

    public boolean estaEnCurso() {
        return estado == Estado.EN_CURSO;
    }

}
