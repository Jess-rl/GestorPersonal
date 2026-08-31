package modelo;

public class Recordatorio {
    
    private String asunto;
    private String fecha;
    private String hora; 
    private boolean completado;
    
    public Recordatorio() {
    }

    public Recordatorio(String asunto, String fecha, String hora, boolean completado) {
        this.asunto = asunto;
        this.fecha = fecha;
        this.hora = hora;
        this.completado = completado;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean getCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public String toString(){
        return "Recordatorio [Asunto: %s, Fecha: %s, Hora: %s, Completado: %s]".formatted(asunto, fecha, hora, completado);
    }
}
