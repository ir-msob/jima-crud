package ir.msob.jima.crud.api.graphql.restful.commons.model;

import ir.msob.jima.core.commons.model.dto.BaseType;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IdInput implements BaseType {
    private String id;
}
