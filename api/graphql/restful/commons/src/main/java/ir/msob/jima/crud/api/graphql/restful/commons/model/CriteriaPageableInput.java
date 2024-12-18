package ir.msob.jima.crud.api.graphql.restful.commons.model;

import ir.msob.jima.core.commons.shared.BaseType;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaPageableInput implements BaseType {
    private String criteria;
    private String pageable;
}
