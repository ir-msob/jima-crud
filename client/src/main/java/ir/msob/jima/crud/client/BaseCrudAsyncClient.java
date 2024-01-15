package ir.msob.jima.crud.client;

/**
 * The BaseCrudAsyncClient interface combines functionality for asynchronous CRUD (Create, Read, Update, Delete) operations.
 * It extends multiple interfaces to provide clients with a comprehensive set of methods for interacting with a service.
 */
public interface BaseCrudAsyncClient
        extends BaseClient
        , BaseWriteAsyncClient
        , BaseReadAsyncClient {

}
