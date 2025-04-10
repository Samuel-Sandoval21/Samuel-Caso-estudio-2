package hotel;

import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class Hotel {

    static Habitacion[][] hotel = new Habitacion[5][5];

    public static void main(String[] args) {
        inicializarHotel();
        menu();

    }

    public static void inicializarHotel() {
        double[][] precios = {
            {30, 30, 30, 30, 40},
            {40, 40, 40, 40, 40},
            {50, 60, 40, 30, 40},
            {50, 60, 40, 40, 40},
            {50, 60, 40, 40, 40}
        };

        String[][] estados = {
            {"Libre", "Libre", "Libre", "Libre", "Sucia"},
            {"Libre", "Libre", "Libre", "Libre", "Sucia"},
            {"Sucia", "Libre", "Libre", "Libre", "Sucia"},
            {"Ocupada", "Libre", "Libre", "Libre", "Sucia"},
            {"Libre", "Libre", "Libre", "Libre", "Sucia"},};

        int numeroHabitacion = 101;
        for (int i = 0; i < 5; i++) { // pisos
            for (int j = 0; j < 5; j++) { // habitaciones
                String tipo = (j % 2 == 0) ? "Simple" : "Doble";
                hotel[i][j] = new Habitacion(String.valueOf(numeroHabitacion), tipo, precios[i][j], estados[i][j]);
                numeroHabitacion++;
            }

            numeroHabitacion += 95; // siguiente piso
        }
    }

    public static void menu() {
        int opcion;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "Hotel \n\n"
                    + "1. Ver todas las habitaciones\n"
                    + "2. Modificar habitacion\n"
                    + "3. Ver resumen del hotel\n"
                    + "4. Salir"
            ));

            switch (opcion) {
                case 1:
                    mostrarHabitaciones();
                    break;

                case 2:
                    modificarHabitacion();
                    break;

                case 3:
                    resumenHotel();
                    break;

                case 4:
                    JOptionPane.showMessageDialog(null, "Gracias por usar el sistema.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opcion invalida.");
            }
        } while (opcion != 4);
    }

    public static void mostrarHabitaciones() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append("Piso").append(5 - i).append(":\n");
            for (int j = 0; j < 5; j++) {
                sb.append(hotel[i][j].toString()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static void modificarHabitacion() {
        String numero = JOptionPane.showInputDialog("Ingrese el numero de habitacion (ejemplo. 101):");
        Habitacion hab = buscarHabitacion(numero);
        if (hab != null) {
            String nuevoEstado = JOptionPane.showInputDialog("Nuevo estado (Libre, Ocupada, Sucia):", hab.getEstado());
            if (!nuevoEstado.equals("Libre") && !nuevoEstado.equals("Ocupada") && !nuevoEstado.equals("Sucia")) {
                JOptionPane.showMessageDialog(null, "Estado invalido. No se ha realizado ninguna modificacion.");
                return;
            }

            String nuevoTipo = JOptionPane.showInputDialog("Nuevo tipo (Simple, Doble):", hab.getTipo());
            if (!nuevoTipo.equals("Simple") && !nuevoTipo.equals("Doble")) {
                JOptionPane.showMessageDialog(null, "Tipo invalido. No se ha realizado ninguna modificacion.");
                return;
            }

            double nuevoPrecio;
            try {
                nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog("Nuevo precio:", hab.getPrecio()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Precio invalido. No se ha realizado ninguna modificacion.");
                return;
            }

            // Actualizar los valores de la habitación
            hab.setEstado(nuevoEstado);
            hab.setTipo(nuevoTipo);
            hab.setPrecio(nuevoPrecio);

            JOptionPane.showMessageDialog(null, "Este es un mensaje de prueba.");
        }
    }

    public static Habitacion buscarHabitacion(String numero) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (hotel[i][j].getNumero().equals(numero)) {
                    return hotel[i][j];
                }
            }
        }

        return null;
    }

    public static void resumenHotel() {
        int libres = 0, ocupadas = 0, sucias = 0;
        double ganancias = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String estado = hotel[i][j].getEstado();
                switch (estado) {
                    case "Libre":
                        libres++;
                        break;
                    case "Ocupada":
                        ocupadas++;
                        ganancias += hotel[i][j].getPrecio();
                        break;
                    case "Sucia":
                        sucias++;
                        break;
                }
            }

        }

        int total = libres + ocupadas + sucias;
        double pLibres = (libres * 100.0) / total;
        double pOcupadas = (ocupadas * 100.0) / total;
        double pSucias = (sucias * 100.0) / total;

        JOptionPane.showMessageDialog(null,
                "Resumen del Hotel: \n"
                + "Libres: " + libres + " (" + String.format("%.2f", pLibres) + "%)\n"
                + "Ocupadas: " + ocupadas + "(" + String.format("%.2f", pOcupadas) + "%)\n"
                + "Sucias: " + ocupadas + "(" + String.format("%.2f", pSucias) + "%)\n"
                + "Ganancia actual: $" + ganancias
        );
    }
}
