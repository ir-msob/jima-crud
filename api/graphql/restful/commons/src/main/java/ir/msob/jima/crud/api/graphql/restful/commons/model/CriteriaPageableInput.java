package ir.msob.jima.crud.api.graphql.restful.commons.model;

import ir.msob.jima.core.commons.model.dto.BaseType;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaPageableInput implements BaseType {
    private String criteria;
    private String pageable;
}
