package ir.msob.jima.crud.api.kafka.client;

import ir.msob.jima.core.commons.annotation.domain.DomainService;

import java.io.Serializable;

public class Constants {
    private Constants() {
    }

    public static String getChannel(String microservice, String domain, String operation) {
        return String.format("%s_%s_%s",
                        microservice,
                        domain,
                        operation)
                .toLowerCase();
    }

    public static <ID extends Comparable<ID> & Serializable> String getChannel(Class<?> clazz, String operation) {
        DomainService domainService = DomainService.info.getAnnotation(clazz);
        return Constants.getChannel(domainService.serviceName(), domainService.domainName(), operation);
    }
}
