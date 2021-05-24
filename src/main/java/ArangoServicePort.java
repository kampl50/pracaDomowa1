import models.CarVisit;

import java.util.List;

public interface ArangoServicePort<T, K> {

    void addDocument(K key, T document);

    CarVisit getByKey(String key);

    void updateDocument(K key, T document);

    void deleteDocument(K key);

    List<CarVisit> getByTitle(String title);

    Double findAveragePriceByMark(String mark);
}
