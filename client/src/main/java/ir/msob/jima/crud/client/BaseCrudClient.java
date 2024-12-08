package ir.msob.jima.crud.client;

import ir.msob.jima.crud.client.domain.BaseReadDomainClient;
import ir.msob.jima.crud.client.domain.BaseWriteDomainClient;

/**
 * The BaseCrudClient interface combines the functionality of both the BaseWriteDomainClient and BaseReadDomainClient interfaces.
 * It provides a unified interface for clients that need to perform both write and read operations when interacting
 * with a service in a software system.
 */
public interface BaseCrudClient extends BaseWriteDomainClient, BaseReadDomainClient {
}
