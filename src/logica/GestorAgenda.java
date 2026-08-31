package logica;

import modelo.Contacto;
import modelo.Nota;
import modelo.Recordatorio;

import java.util.ArrayList;

public class GestorAgenda {

    private ArrayList<Contacto> contactos;
    private ArrayList<Nota> notas;
    private ArrayList<Recordatorio> recordatorios;

    public GestorAgenda() {
        this.contactos = new ArrayList<>();
        this.notas = new ArrayList<>();
        this.recordatorios = new ArrayList<>();
    }

    // Metodos para contactos
    // CRUD---------------------------------------------------------

    // Agregar
    public void agregarContacto(Contacto nuevoContacto) {
        contactos.add(nuevoContacto);
    }

    // Eliminar
    public void eliminarContacto(int indice) {
        if (indice >= 0 && indice < contactos.size()) {
            contactos.remove(indice);
        }
    }

    // Modificar
    public void modificarContacto(int indice, Contacto cActualizar) {
        if (indice >= 0 && indice < contactos.size()) {
            contactos.set(indice, cActualizar);
        }
    }

    // Obtener
    public ArrayList<Contacto> getContactos() {
        return contactos;
    }

    // Metodos para Notas
    // CRUD-------------------------------------------------------------

    //  Agregar
    public void agregarNota(Nota n) {
        notas.add(n);
    }

    // Eliminar
    public void eliminarNota(int indice) {
        if (indice >= 0 && indice < notas.size()) {
            notas.remove(indice);
        }
    }

    // Modificar
    public void modificarNota(int indice, Nota cActualizar) {
        if (indice >= 0 && indice < notas.size()) {
            notas.set(indice, cActualizar);
        }
    }

    // Obtener
    public ArrayList<Nota> getNotas() {
        return notas;
    }

    // Visualizar notas
    public ArrayList<Nota> getNotasRecientes() {
        ArrayList<Nota> recientes = new ArrayList<>();

        for (int i = notas.size() - 1; i >= 0 && recientes.size() < 3; i--) {
            recientes.add(notas.get(i));
        }

        return recientes;
    }

    // Metodos para Recordatorio
    // CRUD-------------------------------------------------------------

    //Agregar
    public void agregarRecordatorio(Recordatorio r){
        recordatorios.add(r);
    }

    //Eliminar
    public void eliminarRecordatorio(int indice){
        if (indice >= 0 && indice < recordatorios.size()){
            recordatorios.remove(indice);
        }
    }

    // Obtener
    public ArrayList<Recordatorio> getRecordatorios(){
        return recordatorios;
    }

    //cambio estado
    public void cambiarEstadoRecordatorio(int indice){
        if(indice >= 0 && indice < recordatorios.size()){
            Recordatorio r = recordatorios.get(indice);
            r.setCompletado(!r.getCompletado());
        }
    }

}