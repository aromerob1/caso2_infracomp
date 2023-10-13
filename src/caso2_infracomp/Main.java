package caso2_infracomp;
import java.util.Scanner;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        ReferenceGenerator referenceGenerator = new ReferenceGenerator();
        Pagination pagination;
        preguntar:
        while (true) {
            System.out.println("----- Menú -----");
            System.out.println("1. Generación de las referencias.");
            System.out.println("2. Calcular el número de fallas de página.");
            System.out.println("3. Salir.");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Generating references and creating file
                    System.out.print("Ingrese tamaño de página: ");
                    int tamPagina = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese número de filas de la matriz 1: ");
                    int nf1 = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese número de columnas de la matriz 1 y filas de matriz 2: ");
                    int nc1 = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese número de columnas de la matriz 2: ");
                    int nc2 = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese el nombre del archivo a crear (NO ponga la extension): ");
                    String archivo = scanner.nextLine();

                    referenceGenerator.generateReferences(tamPagina, nf1, nc1, nc2, archivo);
                    System.out.println("Referencias generadas en: "+ archivo +".txt");
                    break;

                case 2:
                    // Handling page faults with Pagination
                    System.out.print("Ingrese número de marcos de página: ");
                    int frameCount = scanner.nextInt();
                    System.out.print("Ingrese nombre del archivo de referencias (NO ponga la extension): ");
                    String references = scanner.next()+".txt";

                    pagination = new Pagination(frameCount, references);
                    pagination.start();
                    break preguntar;

                case 3:
                    // Exiting the program
                    System.out.println("Terminó la ejecución del programa");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}

