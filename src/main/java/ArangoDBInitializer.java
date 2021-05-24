import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.CollectionEntity;

public final class ArangoDBInitializer {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final Integer DEFAULT_PORT = 8529;
    private static ArangoDB ARANGO_DB_INSTANCE;

    public static ArangoDB getDefaultDB() {
        if (ARANGO_DB_INSTANCE == null) {
            ARANGO_DB_INSTANCE = new ArangoDB.Builder()
                    .host(DEFAULT_HOST, DEFAULT_PORT)
                    .build();
        }
        return ARANGO_DB_INSTANCE;
    }

    public static ArangoDB getCustomDB(String customHost, String customPort) {
        if (ARANGO_DB_INSTANCE == null) {
            ARANGO_DB_INSTANCE = new ArangoDB.Builder()
                    .host(customHost, Integer.parseInt(customPort))
                    .build();
        }
        return ARANGO_DB_INSTANCE;
    }

    public static void createDataBase(String name) {
        try {
            ARANGO_DB_INSTANCE.createDatabase(name);
            System.out.println("Database created: " + name);
        } catch (ArangoDBException e) {
            System.err.println("Failed to create database: " + name + "; " + e.getMessage());
        }
    }

    public static void createCollection(String dbName, String collectionName) {
        try {
            CollectionEntity myArangoCollection = ARANGO_DB_INSTANCE.db(dbName).createCollection(collectionName);
            System.out.println("Collection created: " + myArangoCollection.getName());
        } catch (ArangoDBException e) {
            System.err.println("Failed to create collection: " + collectionName + "; " + e.getMessage());
        }
    }
}
