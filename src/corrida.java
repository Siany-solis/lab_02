import java.io.File;

public class corrida {
    public static void main(String[] args) {
        // Ruta absoluta del archivo
        File f = new File(System.getProperty("user.dir") + File.separator + "comandas.dat");

        if (f.exists()) {
            if (f.delete()) {
                System.out.println("Archivo comandas.dat borrado correctamente.");
            } else {
                System.out.println("No se pudo borrar comandas.dat.");
            }
        } else {
            System.out.println("No existe comandas.dat.");
        }

        new vista.login();
    }
}
