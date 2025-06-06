import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TorreDeControl {
    private List<Aeronave> aterrizajesPendientes;
    private List<Aeronave> despeguesPendientes;
    private boolean pistaDespejada;

    public TorreDeControl(List<Aeronave> aterrizajesPendientes, List<Aeronave> despeguesPendientes) {
        this.aterrizajesPendientes = aterrizajesPendientes;
        this.despeguesPendientes = despeguesPendientes;
        this.pistaDespejada = true;
    }

    public List<Aeronave> getAterrizajesPendientes() {
        return aterrizajesPendientes;
    }

    public void addAterrizajePendiente(Aeronave aterrizajePendiente) {
        this.aterrizajesPendientes.add(aterrizajePendiente);
    }

    public List<Aeronave> getDespeguesPendientes() {
        return despeguesPendientes;
    }

    public void addDespeguePendiente(Aeronave despeguePendiente) {
        this.despeguesPendientes.add(despeguePendiente);
    }

    public boolean isPistaDespejada() {
        return pistaDespejada;
    }

    public void setPistaDespejada(boolean pistaDespejada) {
        this.pistaDespejada = pistaDespejada;
    }

    public void procesarSiguienteEvento() {
        if (pistaDespejada) {
            if (aterrizajesPendientes.size() > 0) {
                Collections.sort(aterrizajesPendientes);
                System.out.println("Pista en uso por: " + aterrizajesPendientes.getFirst());
                aterrizajesPendientes.removeFirst();
                pistaDespejada = false;

            } else if (despeguesPendientes.size() > 0) {
                Collections.sort(despeguesPendientes);
                System.out.println("Pista en uso por: " + despeguesPendientes.getFirst());
                despeguesPendientes.removeFirst();
                pistaDespejada = false;

            } else {
                System.out.println("Sin aviones en espera");
            }
        } else {
            System.out.println("La pista se encuentra ocupada");
        }
    }

    public void liberarPista() {
        this.pistaDespejada = true;
    }

}
