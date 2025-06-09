package aeronave;

//Clase padre para todas las aeronaves
public abstract class Aeronave implements Comparable<Aeronave> {
    private String id;
    private SubclasesDeAeronave tipo;
    private int prioridad;

    public Aeronave(String id, SubclasesDeAeronave tipo) {
        this.id = id;
        this.tipo = tipo;
        this.prioridad = tipo.getPrioridad();
    }
    public Aeronave() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SubclasesDeAeronave getTipo() {
        return tipo;
    }

    public void setTipo(SubclasesDeAeronave tipo) {
        this.tipo = tipo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Tipo: " + tipo + " Prioridad: " + prioridad;
    }

    //Con la interfaz comparable podemos ordenar las clases de manera más nativa
    //y cómoda
    @Override
    public int compareTo(Aeronave o) {
        return Integer.compare(this.getPrioridad(), o.getPrioridad());
    }

    //Saber si una aeronave a tenido un retraso o no (simulación)
    public boolean retraso() {
        int rand = (int)(Math.random() * 101);

        return rand >= 90;
    }
}
