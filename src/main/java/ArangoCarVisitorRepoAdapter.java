import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import models.CarVisit;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public final class ArangoCarVisitorRepoAdapter implements ArangoServicePort<CarVisit, String> {

    private final ArangoDB arangoDB;
    private final String databaseName;
    private final String collectionName;

    public ArangoCarVisitorRepoAdapter(ArangoDB arangoDB, String databaseName, String collectionName) {
        this.arangoDB = arangoDB;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    @Override
    public void addDocument(String key, CarVisit carVisit) {
        BaseDocument documentObject = new BaseDocument(key);
        documentObject.addAttribute("title", carVisit.getTitle());
        documentObject.addAttribute("price", carVisit.getPrice().toString());
        documentObject.addAttribute("mark", carVisit.getMark());

        try {
            arangoDB.db(databaseName).collection(collectionName).insertDocument(documentObject);
            System.out.println("\tDocument created");
        } catch (ArangoDBException e) {
            System.err.println("Failed to create document. " + e.getMessage());
        }
    }

    @Override
    public CarVisit getByKey(String key) {
        try {
            BaseDocument myDocument = arangoDB.db(databaseName).collection(collectionName).getDocument(key, BaseDocument.class);

            String title = (String) myDocument.getAttribute("title");
            Double price = Double.valueOf(myDocument.getAttribute("price").toString());
            String mark = myDocument.getAttribute("mark").toString();
            CarVisit carVisit = new CarVisit(title, price, mark);
            System.out.println(carVisit);
            return carVisit;
        } catch (ArangoDBException e) {
            throw new ArangoDBException(e.getMessage());
        }
    }

    @Override
    public void updateDocument(String key, CarVisit carVisit) {
        try {

            BaseDocument updatedDocument = arangoDB.db(databaseName).collection(collectionName).getDocument(key, BaseDocument.class);
            updatedDocument.updateAttribute("title", carVisit.getTitle());
            updatedDocument.updateAttribute("price", carVisit.getPrice());
            updatedDocument.updateAttribute("mark", carVisit.getMark());
            arangoDB.db(databaseName).collection(collectionName).updateDocument(key, updatedDocument);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }

    @Override
    public void deleteDocument(String key) {
        try {
            arangoDB.db(databaseName).collection(collectionName).deleteDocument(key);
        } catch (ArangoDBException e) {
            System.err.println("Failed to delete document. " + e.getMessage());
        }
    }

    @Override
    public List<CarVisit> getByTitle(String title) {
        List<CarVisit> carVisits = new LinkedList<>();
        try {
            String query = "FOR t IN carVisitor FILTER t.title == @title RETURN t";
            Map<String, Object> bindVars = Collections.singletonMap("title", title);
            ArangoCursor<BaseDocument> cursor = arangoDB.db(databaseName).query(query, bindVars, null, BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                String title2 = aDocument.getAttribute("title").toString();
                Double price = Double.valueOf(aDocument.getAttribute("price").toString());
                String mark = aDocument.getAttribute("mark").toString();
                CarVisit carVisit = new CarVisit(title2, price, mark);
                carVisits.add(carVisit);
            });
            System.out.println("Wizyty po tytulach...\t");
            for (CarVisit carVisit : carVisits) {
                System.out.println(carVisit);
            }
            return carVisits;
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }
        return null;
    }

    @Override
    public Double findAveragePriceByMark(String mark) {
        List<CarVisit> carVisits = new LinkedList<>();
        try {
            String query = "FOR t IN carVisitor FILTER t.mark == @mark RETURN t";
            Map<String, Object> bindVars = Collections.singletonMap("mark", mark);
            ArangoCursor<BaseDocument> cursor = arangoDB.db(databaseName).query(query, bindVars, null, BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                String title = aDocument.getAttribute("title").toString();
                Double price = Double.valueOf(aDocument.getAttribute("price").toString());
                String mark2 = aDocument.getAttribute("mark").toString();
                CarVisit carVisit = new CarVisit(title, price, mark2);
                carVisits.add(carVisit);
            });
            System.out.println("Wizyty po markach...\t");
            for (CarVisit carVisit : carVisits) {
                System.out.println(carVisit);
            }
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }

        AtomicReference<Double> priceSum = new AtomicReference<>(0.0);
        carVisits.forEach(carVisit -> priceSum.updateAndGet(v -> v + carVisit.getPrice()));
        Double average = priceSum.get() / carVisits.size();
        System.out.println("Sredni koszt naprawy dla[ " + mark + "]" + "wynosi " + average + ".");
        return priceSum.get() / carVisits.size();
    }
}
