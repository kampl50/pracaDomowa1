import com.arangodb.ArangoDB;
import org.apache.log4j.BasicConfigurator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        ArangoDB arango = ArangoDBInitializer.getDefaultDB();
        ArangoDBInitializer.createDataBase("carVisitor");
        ArangoDBInitializer.createCollection("carVisitor", "carVisitor");
        ArangoCarVisitorRepoAdapter serviceRepo = new ArangoCarVisitorRepoAdapter(arango, "carVisitor", "carVisitor");
        ArangoCarVisitorService carVisitorService = new ArangoCarVisitorService(serviceRepo);
        Scanner scanner = new Scanner(System.in);
        int select;
        do {
            System.out.println("[0]wyjscie\t\t[1]]dodaj wizyte\t\t[2]usun wizyte\t\t[3]znajdz po kluczu\t\t[4]aktualizuj wizytet\\t[5]zwroc po tytule(query)\\[6]srednia cena naprawy dla danej marki\\t\"");
            select = scanner.nextInt();
            switch (select) {
                case 0:
                    break;
                case 1:
                    carVisitorService.save();
                    break;
                case 2:
                    carVisitorService.deleteByKey();
                    break;
                case 3:
                    carVisitorService.getByKey();
                    break;
                case 4:
                    carVisitorService.update();
                    break;
                case 5:
                    carVisitorService.getByTitle();
                    break;
                case 6:
                    carVisitorService.findAveragePriceByMark();
                    break;
                default:
                    throw new IllegalStateException("Nie ma takiej opcji pod tym numerem: " + select);
            }
        } while (select != 0);

    }
}
