import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import aeronave.Aeronave;
import aeronave.Avion;
import aeronave.SubclasesDeAeronave;
import org.reflections.Reflections;

public class Simulador {

    public static void main(String[] args) throws InterruptedException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);
        int opcion;

        List<Aeronave> aterrizajesPendientes = new ArrayList<>();
        List<Aeronave> despeguesPendientes = new ArrayList<>();
        TorreDeControl torreDeControl = new TorreDeControl(aterrizajesPendientes, despeguesPendientes);

        do {
            System.out.println("\n*** Simulador ***");
            System.out.println("1. Agregar aeronave a la cola de despegue");
            System.out.println("2. Agregar aeronave a la cola de aterrizaje");
            System.out.println("3. Procesar siguiente evento");
            System.out.println("4. Ver log");
            System.out.println("0. Salir");
            opcion = comprobarInt(sc, "Ingrese una opcion:");

            switch (opcion) {
                case 1:
                    torreDeControl.addDespeguePendiente(registrarPeticion(sc));
                    break;

                case 2:
                    torreDeControl.addAterrizajePendiente(registrarPeticion(sc));
                    break;

                case 3:
                    torreDeControl.procesarSiguienteEvento();
                    break;

                case 4:
                    torreDeControl.mostrarLog();
                    break;

                case 0:
                    System.exit(0);

                default:
                    System.out.println("Opción invalida");
            }

        } while (true);


    }

    //Introducimos una aeronave a la cola, junto con sus datos
    public static Aeronave registrarPeticion(Scanner sc) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println("Ingresa el id del vuelo: ");
        String id = sc.nextLine();

        SubclasesDeAeronave tipo = sacarCategoriaAeronave(sc);

        Aeronave avion = elejirTipoAeronave(sc);
        avion.setTipo(tipo);
        avion.setId(id);
        avion.setPrioridad(tipo.getPrioridad());

        return avion;
    }

    //Nos permite elejir que tipo de aeronave queremos instanciar (Avión, helicoptero, caza...)
    public static Aeronave elejirTipoAeronave(Scanner sc) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections("aeronave");
        Set<Class<? extends Aeronave>> subclasses = reflections.getSubTypesOf(Aeronave.class);
        List<Class<? extends Aeronave>> listaSubclases = new ArrayList<>(subclasses);

        int i = 0;
        for (Class<?> subclass : subclasses) {
            System.out.println(i + ". " + subclass.getSimpleName());
            i++;
        }

        int opcion;
        do {
            opcion = comprobarInt(sc, "Tipo de aeronave: ");

            if (opcion > subclasses.size()) {
                System.out.println("Tipo de aeronave invalido");
            } else {
                break;
            }

        } while (true);


        Class<? extends Aeronave> elejido = listaSubclases.get(opcion);

        return elejido.getDeclaredConstructor().newInstance();
    }

    //Asignamos un tipo a la aeronave
    public static SubclasesDeAeronave sacarCategoriaAeronave(Scanner sc) {

        int i = 0;
        for (SubclasesDeAeronave subclase : SubclasesDeAeronave.values()) {
            System.out.println(i + ". " + subclase);
            i++;
        }

        int tipo = comprobarInt(sc, "¿De que tipo es el vuelo?");

        return SubclasesDeAeronave.values()[tipo];
    }

    //Método para asegurar los int del usuario
    public static int comprobarInt(Scanner sc, String mensaje) {
        int dato;

        do {
            try {
                System.out.println(mensaje);
                dato = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Debe ingresar un número entero");
                sc.nextLine();
                dato = -1;
            }
        } while (dato < 0);
        return dato;
    }
}
