package aeronave;
//Ya que la única funcionalidad en la que se diferencian las distintas aeronaves es su prioridad,
//he optado por usar un enum aquí.

public enum SubclasesDeAeronave {
    EMERGENCIA(1),
    MILITAR(2),
    PASAJEROS(3),
    CARGA(4);

    private int prioridad;

    SubclasesDeAeronave(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getPrioridad() {
        return this.prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return switch (this) {
            case EMERGENCIA -> "Emergencia";
            case MILITAR -> "Militar";
            case PASAJEROS -> "Pasajeros";
            case CARGA -> "Carga";
        };
    }
}
