import models.CarVisit;

import java.util.List;
import java.util.Scanner;

public class ArangoCarVisitorService {

    private ArangoCarVisitorRepoAdapter carVisitorRepo;

    public ArangoCarVisitorService(ArangoCarVisitorRepoAdapter carVisitorRepo) {
        this.carVisitorRepo = carVisitorRepo;
    }

    public void save() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj [klucz]:");
        String key = scanner.nextLine();

        System.out.println("Podaj tytuł serwisu:");
        String title = scanner.nextLine();

        System.out.println("Podaj cene:");
        Double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Podaj marke pojazdu:");
        String mark = scanner.nextLine();

        CarVisit carVisit = new CarVisit(title, price, mark);
        carVisitorRepo.addDocument(key, carVisit);

    }

    public Double findAveragePriceByMark(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj marke:");
        String mark = scanner.nextLine();

        return carVisitorRepo.findAveragePriceByMark(mark);
    }

    public CarVisit getByKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj [klucz]:");
        String key = scanner.nextLine();

        return carVisitorRepo.getByKey(key);
    }

    public List<CarVisit> getByTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj tytul:");
        String title = scanner.nextLine();

        return carVisitorRepo.getByTitle(title);
    }

    public void deleteByKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj [klucz]:");
        String key = scanner.nextLine();

        carVisitorRepo.deleteDocument(key);
    }

    public void update() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj [klucz]:");
        String key = scanner.nextLine();


        System.out.println("Podaj tytuł serwisu:");
        String title = scanner.nextLine();

        System.out.println("Podaj marke pojazdu:");
        String mark = scanner.nextLine();

        System.out.println("Podaj cene:");
        Double price = scanner.nextDouble();

        CarVisit carVisit = new CarVisit(title, price, mark);
        carVisitorRepo.updateDocument(key, carVisit);
    }
}
