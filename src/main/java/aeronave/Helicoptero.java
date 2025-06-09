package aeronave;

public class Helicoptero extends Aeronave {

    public Helicoptero(String id, SubclasesDeAeronave tipo) {
        super(id, tipo);
    }
    public Helicoptero() {}

    //Un helicoptero es más lento, es más probable que se retrase
    @Override
    public boolean retraso() {
        int rand = (int)(Math.random() * 101);

        return rand >= 85;
    }
}
