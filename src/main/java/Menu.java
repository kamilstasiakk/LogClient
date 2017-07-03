import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Created by Kamil on 2016-11-11.
 */
public class Menu {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\n");
        System.out.println("Podaj adres klienta: ");
        LogClient logClient = new LogClient(scanner.next());
        while(true) {
            System.out.println("1 - Dodaj log");
            System.out.println("2 - Dodaj nowy rekord");
            System.out.println("q -  Zamknij program");
            String choice = scanner.next();

            if( choice.equals("1")) {
                List<String> fields = new ArrayList<String>();
                System.out.println("Podaj nazwe logu: ");
                String name = scanner.next();
                while(true) {
                    System.out.println("1 - Podaj nazwę kolejnego dodatkowego pola (podstawowe to adres IP oraz czas): ");
                    System.out.println("2 - Zakończ dodawanie pół i wyślij rekord logu");
                    String choice2 = scanner.next();
                    String fieldName;
                    if ( choice2.equals("1")) {
                        System.out.println("Podaj nazwe kolejnego pola: ");
                        fieldName = scanner.next();
                        fields.add(fieldName);
                    } else if (choice2.equals("2")) {
                        break;
                    }
                }


                System.out.println(logClient.addLog(name, fields));

            } else if(choice.equals("2")) {
                List<String> fields = new ArrayList<String>();
                System.out.println("Podaj nazwe logu: ");
                String name = scanner.next();
                while(true) {
                    System.out.println("1 - Podaj wartość kolejnego dodatkowego pola: ");
                    System.out.println("2 - Zakończ dodawanie pól i wyślij rekord logu");
                    String choice2 = scanner.next();
                    if ( choice2.equals("1")) {
                        System.out.println("Podaj wartość kolejnego pola: ");
                        String field = scanner.next();
                        fields.add(field);
                    } else if (choice2.equals("2")) {
                        break;
                    }
                }

                System.out.println(logClient.addLogRecord(name, fields));
            } else if(choice.equals("q")) {
                break;
            }

        }

    }

}
