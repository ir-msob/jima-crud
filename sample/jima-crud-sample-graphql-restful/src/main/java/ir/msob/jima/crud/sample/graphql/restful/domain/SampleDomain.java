package ir.msob.jima.crud.sample.graphql.restful.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.core.commons.domain.DomainInfo;
import ir.msob.jima.crud.sample.graphql.restful.base.Microservices;
import ir.msob.jima.crud.sample.graphql.restful.base.domain.ProjectDomainAbstract;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = SampleDomain.DOMAIN_NAME)
@DomainInfo(serviceName = Microservices.SAMPLE_MICROSERVICE, version = Microservices.VERSION, domainName = SampleDomain.DOMAIN_URI)
public class SampleDomain extends ProjectDomainAbstract {
    @Transient
    public static final String DOMAIN_NAME = "SampleDomain";
    @Transient
    public static final String DOMAIN_URI = "sample-domain";

    private String domainField;
    @NotBlank
    private String domainMandatoryField;

    public enum FN {
        domainField, domainMandatoryField
    }
}
