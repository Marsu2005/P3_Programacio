package trabajos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import excepciones.EstadoTrabajoIncorrectoException;
import excepciones.ListaTrabajosLlenaException;
import excepciones.MaquinaSaturadaException;
import excepciones.MaquinaVaciaException;

public class llistaTreballs {
    private Treball[] lista;
    private int numElem;

    public llistaTreballs(int capacidad) {
        lista = new Treball[capacidad];
        numElem = 0;
    }

    public int getNumTrabajos() {
        return numElem;
    }

    public Treball getTrabajo(int pos) {
        if(pos < 0 || pos > numElem) throw new IllegalArgumentException("la posicion a consultar debe de existir");
        return lista[pos].copia();
    }

    public Treball buscarTrabajo(int codigo) {
        Treball t = null;
        int i = 0;
        while(i < numElem && t == null) {
            if(lista[i].getCodigo() == codigo) {
                t = lista[i].copia();
            } else {
                i++;
            }
        }
        return t;
    }

    public int siguienteCodigo() {
        int codigoMax = 1;
        if(numElem == 0) return codigoMax;
        for(int i = 0; i < numElem; i++) {
            if(lista[i].getCodigo() > codigoMax) codigoMax = lista[i].getCodigo();
        }
        return ++codigoMax;
    }

    private void insertar(Treball t, int pos) throws ListaTrabajosLlenaException{
        if(pos < 0 || pos > numElem) throw new IllegalArgumentException("posicion de la lista inexistente");
        if(numElem == lista.length) throw new ListaTrabajosLlenaException("la lista ya esta llena");
        for(int i = numElem-1; i >= pos; i--) {
            lista[i+1] = lista[i];
        }
        lista[pos] = t.copia();
        numElem++;
    }

    public void afegir(Treball t) throws ListaTrabajosLlenaException {
        if(t == null) throw new IllegalArgumentException("trabajo null");
        boolean found = false;
        int i = 0; 
        while(i < numElem && !found) {
            if(lista[i].getCodigo() == t.getCodigo()) {
                found = true;
            } else {
                i++;
            }
        }
        if(found) throw new IllegalArgumentException("ya hay un trabajo con ese codigo");
        i = 0;
        while(i < numElem && t.getFechaSolicitud().isAfter(lista[i].getFechaSolicitud())) {
            i++;
        }
        insertar(t, i);
    }

    private void eliminar(int pos) {
        if(pos < 0 || pos >= numElem) throw new IllegalArgumentException("indicar una posicion existente a eliminar")
        for(int i = pos+1; i < numElem; i++) {
            lista[i-1] = lista[i];
        }
        lista[numElem] = null;
        numElem--;
    }

    public void cancelar(int codigo) throws EstadoTrabajoIncorrectoException{
        boolean found = false;
        int i = 0;
        while(i < numElem && !found) {
            if(lista[i].getCodigo() == codigo) {
                if(lista[i].getEstado() != Treball.Estado.PENDIENTE) {
                    throw new EstadoTrabajoIncorrectoException("no se puede cancelar un trabajo que no esta pendiente");
                } else {
                    eliminar(i);
                }
            } else {
                i++;
            }
        }
    }

    public Treball getEnCurso(int codigo) {
        Treball t = null;
        int i = 0;
        while(i < numElem && t == null) {
            if(lista[i].getCodigo() == codigo && lista[i].getEstado() == Treball.Estado.EN_CURSO) {
                t = lista[i].copia();
            } else {
                i++;
            }
        }
        return t;
    }

    public Treball iniciarSiguiente(int codigo, LocalDateTime ahora) throws MaquinaSaturadaException, MaquinaVaciaException{
        if(getEnCurso(codigo) != null) throw new MaquinaSaturadaException("Ya hay un trabajo en curso en la maquina");
        if(ahora == null) throw new IllegalArgumentException("fecha y hora null");
        int i = 0;
        boolean found = false;
        while(i < numElem && !found) {
            if(lista[i].getCodigo() == codigo && lista[i].getEstado() == Treball.Estado.ESPERA) {
                lista[i].iniciar(ahora);
            }
        }
        if(!found) throw new MaquinaVaciaException("No hay trabajos pendientes en la maquina");
        return lista[i].copia();
    }

    public Treball finalizarEnCurso(int codigo, LocalDateTime ahora) throws MaquinaVaciaException{
        if(getEnCurso(codigo) == null) throw new MaquinaVaciaException("No hay trabajos en curso en la maquina");
        if(ahora == null) throw new IllegalArgumentException("fecha y hora null");
        Treball t;
        int i = 0; 
        while(i < numElem && t == null) {
            if(lista[i].getCodigo() == codigo && lista[i].getEstado() == Treball.Estado.EN_CURSO) {
                
            }
        }
    }
}
