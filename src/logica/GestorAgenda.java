package logica;

import modelo.Contacto;
import modelo.Nota;
import modelo.Recordatorio;

import java.util.ArrayList;
import java.util.Comparator;

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

    public void agregarContacto(Contacto nuevoContacto) {
        contactos.add(nuevoContacto);
    }

    public void eliminarContacto(int indice) {
        if (indice >= 0 && indice < contactos.size()) {
            contactos.remove(indice);
        }
    }

    public void modificarContacto(int indice, Contacto cActualizar) {
        if (indice >= 0 && indice < contactos.size()) {
            contactos.set(indice, cActualizar);
        }
    }

    public ArrayList<Contacto> getContactos() {
        return new ArrayList<>(contactos);
    }

    // Metodos para Notas
    // CRUD-------------------------------------------------------------

    public void agregarNota(Nota n) {
        notas.add(n);
    }

    public void eliminarNota(int indice) {
        if (indice >= 0 && indice < notas.size()) {
            notas.remove(indice);
        }
    }

    public void modificarNota(int indice, Nota nActualizar) {
        if (indice >= 0 && indice < notas.size()) {
            notas.set(indice, nActualizar);
        }
    }

    public ArrayList<Nota> getNotas() {
        return new ArrayList<>(notas);
    }

    public ArrayList<Nota> getNotasRecientes() {
        ArrayList<Nota> ordenadas = new ArrayList<>(notas);
        ordenadas.sort(Comparator.comparing(Nota::getFecha).reversed());

        int limite = Math.min(3, ordenadas.size());
        return new ArrayList<>(ordenadas.subList(0, limite));
    }

    // Metodos para Recordatorio
    // CRUD-------------------------------------------------------------

    public void agregarRecordatorio(Recordatorio r) {
        recordatorios.add(r);
    }

    public void eliminarRecordatorio(int indice) {
        if (indice >= 0 && indice < recordatorios.size()) {
            recordatorios.remove(indice);
        }
    }

    public ArrayList<Recordatorio> getRecordatorios() {
        return new ArrayList<>(recordatorios);
    }

    public void marcarRecordatorio(int indice, boolean completado) {
        if (indice >= 0 && indice < recordatorios.size()) {
            recordatorios.get(indice).setCompletado(completado);
        }
    }
}
