package ir.msob.jima.crud.reactive.client;

import ir.msob.jima.crud.reactive.client.domain.BaseReadDomainEventReactiveClient;
import ir.msob.jima.crud.reactive.client.domain.BaseWriteDomainEventReactiveClient;

/**
 * The BaseCrudEventReactiveClient interface combines functionality for asynchronous CRUD (Create, Read, Update, Delete) operations.
 * It extends multiple interfaces to provide clients with a comprehensive set of methods for interacting with a service.
 */
public interface BaseCrudEventReactiveClient
        extends BaseWriteDomainEventReactiveClient
        , BaseReadDomainEventReactiveClient {

}
