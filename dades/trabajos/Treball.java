package trabajos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

import excepciones.*;
import maquinas.*;
import usuarios.*;

/**
 * Clase que representa una trabajo enviado a una máquina.
 * El usuario y la máquina se almacenan con su identificador, no con una instancia.
 */

public class Treball implements Serializable{
    private static final long serialVersionUID = 1L;
    public static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
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
    private double duracion;                    // Horas de trabajo
    private double cantidadMaterial;            // g o mL. 0 si no hay material.
    private Estado estado;                      // pendiente, en curso o acabado
    private LocalDateTime fechaFin;             // cuándo acabó
    private LocalDateTime fechaInicio;          // cuando empezó


    /**
     * Crea una nueva instancia de la clase Treball con todos sus parámetros.
     * @param codigo identificador del trabajo
     * @param idMaquina identificador de la máquina
     * @param idUsuario alias del usuario
     * @param material tipo de material (o ninguno)
     * @param fechaSolicitud fecha de solicitud del trabajo
     * @param duracion duración del trabajo en horas
     * @param cantidadMaterial cantidad de material (0 si no hay material)
     * @throws IllegalArgumentException si algun parámetro es null o, negativo o la cantidad de material no coincide con el tipo de material.
     */
    public Treball(int codigo, String idMaquina, String idUsuario, Material material, LocalDateTime fechaSolicitud, double duracion, double cantidadMaterial) {
        if(idMaquina == null || idUsuario == null || material == null || fechaSolicitud == null || duracion <= 0 || cantidadMaterial < 0) throw new IllegalArgumentException("Trabajo creado incorrectamente");
        if(material == Material.NINGUNO && cantidadMaterial != 0) throw new IllegalArgumentException("la cantidad de material debe ser 0 si el material es ninguno");
        if(material != Material.NINGUNO && cantidadMaterial == 0) throw new IllegalArgumentException("tiene que haber una cantidad de material diferente de 0");
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

    /**
     * Getter
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }
    /**
     * Getter
     * @return idMaquina
     */
    public String getIdMaquina() {
        return idMaquina;
    }
    /**
     * Getter
     * @return aliasUsuario
     */
    public String getIdUsuario() {
        return idUsuario;
    }
    /**
     * Getter
     * @return material
     */
    public Material getMaterial() {
        return material;
    }
    /**
     * Getter
     * @return fechaSolicitud
     */
    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
    /**
     * Getter
     * @return duracion
     */
    public double getDuracion() {
        return duracion;
    }
    /**
     * Getter 
     * @return cantidadMaterial
     */
    public double getCantidadMaterial() {
        return cantidadMaterial;
    }
    /**
     * Getter
     * @return estado
     */
    public Estado getEstado() {
        return estado;
    }
    /**
     * Getter
     * @return fechaFin
     */
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    /**
     * Getter
     * @return fechaFin
     */
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Inicia un trabajo que está pendiente. Actualiza su estado y su fecha de inicio.
     * @param ahora fecha y hora actual
     * @throws EstadoTrabajoIncorrectoException si el trabajo no está pendiente.
     * @throws IllegalArgumentException si la fecha indicada es null o si es anterior a la fecha de solicitud
     */
    public void iniciar(LocalDateTime ahora) throws EstadoTrabajoIncorrectoException{
        if(ahora == null) throw new IllegalArgumentException("fecha y hora null");
        if(ahora.isBefore(fechaSolicitud)) throw new IllegalArgumentException("la fecha y hora de inicio no puede ser anterior a la solicitud del trabajo");
        if(estado != Estado.PENDIENTE) throw new EstadoTrabajoIncorrectoException("Se ha iniciado un trabajo que no esta pendiente");
        estado = Estado.EN_CURSO;
        fechaInicio = ahora;
    }

    /**
     * Finaliza un trabajo que está en curso. Actualiza su estado y su fecha de finalización.
     * @param ahora fecha y hora actual
     * @throws EstadoTrabajoIncorrectoException si se finaliza un trabajo que no está en curso.
     * @throws IllegalArgumentException si la fecha indicada es null o es anterior a la fecha de inicio del trabajo.
     */
    public void finalizar(LocalDateTime ahora) throws EstadoTrabajoIncorrectoException {
        if(ahora == null) throw new IllegalArgumentException("tiempo null");
        if(estado != Estado.EN_CURSO) throw new EstadoTrabajoIncorrectoException("Se ha finalizado un trabajo que no esta en curso");
        if(ahora.isBefore(fechaInicio)) throw new IllegalArgumentException("La fecha de finalizacion del trabajo no puede ser anterior a la fecha de inicio");
        estado = Estado.ACABADO;
        fechaFin = ahora;
    }

    /**
     * Consulta si el trabajo está pendiente o en curso
     * @return true si está pendiente o en curso, false si está acabado
     */
    public boolean estaPendiente() {
        return estado == Estado.PENDIENTE || estado == Estado.EN_CURSO;
    }

    /**
     * Consulta si el trabajo está en espera.
     * @return true si el trabajo está en espera, false si está en curso o acabado.
     */
    public boolean estaEnEspera() {
        return estado == Estado.PENDIENTE;
    }

    /**
     * Consulta si el trabajo está en curso.
     * @return true si está en curso, false si está pendiente o acabado.
     */
    public boolean estaEnCurso() {
        return estado == Estado.EN_CURSO;
    }

    /**
     * Consulta si el trabajo está acabado.
     * @return true si está acabado, false si está en pendiente o en curso
     */
    public boolean estaAcabado() {
        return estado == Estado.ACABADO;
    }

    /**
     * Comprueba si el identificador de una máquina corresponde con la máquina del trabajo.
     * @param id identificador de la máquina a comprobar
     * @return true si corresponde, false si no corresponde.
     * @throws IllegalArgumentException si el identificador es null
     */
    public boolean esDeMaquina(String id) {
        if(id == null) throw new IllegalArgumentException("Identificador de máquina null");
        return idMaquina.equals(id);
    }

    /**
     * Comrueba si el alias de un usuario corresponde con el usuario del trabajo.
     * @param id alias del usuario a comprobar
     * @return true si corresponde, false si no corresponde.
     * @throws IllegalArgumentException si el alias es null.
     */
    public boolean esDeUsuario(String id) {
        if(id == null) throw new IllegalArgumentException("Alias de usuario null");
        return idUsuario.equals(id);
    }

    /**
     * Comprueba si el trabajo ha acbado entre una fecha inicial y otra final.
     * @param ini fecha de inicio
     * @param fin fecha de final
     * @return true, si ha acabado en ese intervalo, false si no ha acabado o ha acabado fuera del intervalo.
     * @throws IllegalArgumentException si la fecha inicial o final son null
     */
    public boolean acabadoEntre(LocalDateTime ini, LocalDateTime fin) {
        if(ini == null || fin == null) throw new IllegalArgumentException("Fecha y hora null");
        return estado == Estado.ACABADO && !fechaFin.isBefore(ini) && !fechaFin.isAfter(fin);
    }

    /**
     * Comprueba si el trabajo ha acabado en el mes indicado por parámetro.
     * @param mes mes a comprobar
     * @param ano año del mes a comprobar
     * @return true si ha acabado en ese mes, false si no ha acabado o ha acabado en otro mes.
     * @throws IllegalArgumentException si el mes o el año no tienen sentido.
     */
    public boolean acabadoAlMes(int mes, int ano) {
        if(mes < 1 || mes > 12 || ano < 0) throw new IllegalArgumentException("Mes o año no validos");
        if(estado != Estado.ACABADO) return false;
        return mes == fechaFin.getMonthValue() && ano == fechaFin.getYear();
    }

    /**
     * Devuelve la cantidad de dias que han pasado desde que se solicitó el trabajo.
     * @param ahora fecha actual
     * @return diasEspera
     * @throws IllegalArgumentException si la fecha es null o es anterior a la fecha de solicitud del trabajo
     */
    public int getDiasEspera(LocalDateTime ahora) {
        if(ahora == null || ahora.isBefore(fechaSolicitud)) throw new IllegalArgumentException("fecha no valida");
        return (int) ChronoUnit.DAYS.between(fechaSolicitud, ahora);
    }

    /**
     * Calcula el coste del trabajo según la duración y la cantidad de material.
     * @param m instancia de la clase Maquina de la máquina
     * @return coste
     * @throws IllegalArgumentException si la instancia de la maquina es null o no corresponde a la maquina del trabajo
     */
    public double calcularCoste(Maquina m) {
        if(m == null) throw new IllegalArgumentException("maquina null");
        if(!esDeMaquina(m.getId())) throw new IllegalArgumentException("la maquina introducida no coincide con la maquina del trabajo");
        return m.calcularCost(duracion, cantidadMaterial);
    }

    /**
     * Calcula el importe del trabajo según la duracón, la cantidad de material y el usuario.
     * @param m instancia de la clase Maquina de la máquina
     * @param u instancia de la clase Usuari del usuario
     * @return importe
     * @throws IllegalArgumentException si la instancia del usuario o de la maquina son null o si el alias del usuario no corresponde al del usuario del trabajo
     */
    public double calcularImporte(Maquina m, Usuari u) {
        if(m == null || u == null) throw new IllegalArgumentException("maquina o usuario null");
        if(!esDeUsuario(u.getId())) throw new IllegalArgumentException("el usuario introducido no coincide con el usuario del trabajo");
        return u.calcularImport(calcularCoste(m));
    }

    @Override 
    public String toString() {
        return "Trabajo: " +codigo+ "; Maquina: " +idMaquina+ "; Usuario: " +idUsuario+
               "; Material: " +material.name()+ "; Fecha de solicitud: " +fechaSolicitud.format(FORMATO)+
               "; Duracion: " +duracion+ "; Cantidad de material: " +cantidadMaterial+
               "; Estado: " +estado.name()+ "; Fecha de inicio: " +(fechaInicio == null ? "-" : fechaInicio.format(FORMATO))+
               "; Fecha de finalizacion: " +(fechaFin == null ? "-" : fechaFin.format(FORMATO));
    }

    @Override 
    public boolean equals(Object o) {
        if(!(o instanceof Treball)) return false;
        return codigo == ((Treball) o).codigo;
    }

    public Treball copia() {
        Treball t = new Treball(codigo, idMaquina, idUsuario, material, fechaSolicitud, duracion, cantidadMaterial);
        t.estado = estado;
        t.fechaInicio = fechaInicio;
        t.fechaFin = fechaFin;
        return t;
    }

}
