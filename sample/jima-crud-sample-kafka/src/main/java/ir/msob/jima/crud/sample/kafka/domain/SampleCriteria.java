package ir.msob.jima.crud.sample.kafka.domain;

import ir.msob.jima.core.commons.model.criteria.filter.Filter;
import ir.msob.jima.crud.sample.kafka.base.criteria.ProjectCriteriaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Setter
@Getter
@NoArgsConstructor
public class SampleCriteria extends ProjectCriteriaAbstract {
    @Serial
    private static final long serialVersionUID = -8938843863555450000L;
    private Filter<String> domainField;
    private Filter<String> domainMandatoryField;
}
