package ir.msob.jima.crud.client;

/**
 * The BaseCrudClient interface combines the functionality of both the BaseWriteClient and BaseReadClient interfaces.
 * It provides a unified interface for clients that need to perform both write and read operations when interacting
 * with a service in a software system.
 */
public interface BaseCrudClient extends BaseWriteClient, BaseReadClient {
}
