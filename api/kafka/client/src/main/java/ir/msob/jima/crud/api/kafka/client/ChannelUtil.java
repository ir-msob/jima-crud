package ir.msob.jima.crud.api.kafka.client;

import ir.msob.jima.core.commons.domain.DomainInfo;

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
        DomainInfo domainInfo = DomainInfo.info.getAnnotation(clazz);
        return ChannelUtil.getChannel(domainInfo.serviceName(), domainInfo.version(), domainInfo.domainName(), operation);
    }

    public static String getBaseChannel(Class<?> clazz) {
        DomainInfo domainInfo = DomainInfo.info.getAnnotation(clazz);
        return ChannelUtil.getChannel(domainInfo.serviceName(), domainInfo.version(), domainInfo.domainName());
    }
}
