import aeronave.Aeronave;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TorreDeControl {
    private List<Aeronave> aterrizajesPendientes;
    private List<Aeronave> despeguesPendientes;
    private boolean pistaDespejada;
    private File logFile = new File("log.txt");

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

    //Elejimos la siguiente aeronave en realizar una operación según la prioridad dada entre estas
    public void procesarSiguienteEvento() throws InterruptedException, IOException {
        if (pistaDespejada) {
            //Priorizamos aterrizajes siempre
            if (aterrizajesPendientes.size() > 0) {
                //Ordenamos la clase según hemos definido con la interfaz comparable
                Collections.sort(aterrizajesPendientes);
                simularOperacion(aterrizajesPendientes.getFirst(), "Aterrizaje", aterrizajesPendientes);

            } else if (despeguesPendientes.size() > 0) {
                Collections.sort(despeguesPendientes);
                simularOperacion(despeguesPendientes.getFirst(), "Despegue", despeguesPendientes);

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

    //Simulamos las distitnas operaciones
    public void simularOperacion(Aeronave aeronave, String operacion, List<Aeronave> listOP) throws InterruptedException, IOException {
        //Si el metodo retraso devuelve true hacemos esto
        if (aeronave.retraso()) {
            simularRetraso(aeronave, operacion);

        } else {
            //Si no, simulamos el tiempo de la operación con sleep()
            this.pistaDespejada = false;

            System.out.println("Pista en uso por: " + aeronave);
            System.out.println("...");
            Thread.sleep(2000);
            System.out.println("...");
            Thread.sleep(2000);
            System.out.println("...");
            System.out.println(operacion + " efectuado con éxito");

            //Actualizamos el historial
            registrarLog(aeronave, operacion);
        }

        //Quitamos la aeronave de la cola
        listOP.removeFirst();
        liberarPista();
    }

    //Actualizamos un 'historial' que registra todas las operaciones llevadas con éxito
    public void registrarLog(Aeronave aeronave, String operacion) throws IOException {
        FileWriter fw = new FileWriter(logFile, true);
        LocalDateTime hora = LocalDateTime.now();

        fw.write(hora + " || " + aeronave + " || " + "Operación: " + operacion +
                " || " + "Clasificación: " + aeronave.getClass().getSimpleName());
        fw.write("\n");
        fw.close();
    }

    //Para visualizar el historial
    public void mostrarLog() throws IOException {
        FileReader fr = new FileReader(logFile);

        BufferedReader br = new BufferedReader(fr);
        String linea;

        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }
    }

    //En caso de que retraso() = true, simulamos un retraso
    public void simularRetraso(Aeronave aeronave, String operacion) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);

        int rand = (int)(Math.random() * 241);

        System.out.println("Esta operación a sufrido un retraso innesperado de " + rand + " minutos");
        System.out.println("Desea esperar o saltar a la siguiente?(S/N)");
        String opcion = sc.nextLine();

        do {
            if (opcion.equalsIgnoreCase("S")) {
                pistaDespejada = false;
                System.out.println("En espera...");
                Thread.sleep((long) rand * 60 * 1000); //Tiempo aleatorio entre 0 minutos y 4 horas
                System.out.println("Espera finalizada");
                registrarLog(aeronave, operacion);
                return;

            } else if (opcion.equalsIgnoreCase("N")) {
                System.out.println("Saltando...");
                return;
                //Si saltamos no guardamos en el historial porque no hemos realizado esa acción

            } else {
                System.out.println("Opción incorrecta");
            }

        } while (true);
    }
}
