package ir.msob.jima.crud.api.kafka.client;

import ir.msob.jima.core.commons.annotation.domain.DomainService;

public class ChannelUtil {
    private ChannelUtil() {
    }

    private static String getChannel(String microservice, String version, String domain, String operation) {
        return String.format("%s_%s_%s_%s",
                        microservice,
                        version,
                        domain,
                        operation)
                .toLowerCase();
    }

    private static String getChannel(String microservice, String version, String domain) {
        return String.format("%s_%s_%s",
                        microservice,
                        version,
                        domain)
                .toLowerCase();
    }

    public static String getChannel(Class<?> clazz, String operation) {
        DomainService domainService = DomainService.info.getAnnotation(clazz);
        return ChannelUtil.getChannel(domainService.serviceName(), domainService.version(), domainService.domainName(), operation);
    }

    public static String getBaseChannel(Class<?> clazz) {
        DomainService domainService = DomainService.info.getAnnotation(clazz);
        return ChannelUtil.getChannel(domainService.serviceName(), domainService.version(), domainService.domainName());
    }
}
