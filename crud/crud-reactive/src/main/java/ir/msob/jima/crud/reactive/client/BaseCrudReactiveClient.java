package ir.msob.jima.crud.reactive.client;

import ir.msob.jima.crud.reactive.client.domain.BaseReadDomainReactiveClient;
import ir.msob.jima.crud.reactive.client.domain.BaseWriteDomainReactiveClient;

/**
 * The BaseCrudReactiveClient interface combines the functionality of both the BaseWriteDomainReactiveClient and BaseReadDomainReactiveClient interfaces.
 * It provides a unified interface for clients that need to perform both write and read operations when interacting
 * with a service in a software system.
 */
public interface BaseCrudReactiveClient extends BaseWriteDomainReactiveClient, BaseReadDomainReactiveClient {
}
