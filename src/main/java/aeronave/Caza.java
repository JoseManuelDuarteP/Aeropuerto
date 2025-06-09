package aeronave;

public class Caza extends Aeronave {

    public Caza(String id, SubclasesDeAeronave tipo) {
        super(id, tipo);
    }
    public Caza() {}

    //Un caza es más rápido, es menos probable que se retrase
    @Override
    public boolean retraso() {
        int rand = (int)(Math.random() * 101);

        return rand >= 95;
    }
}
