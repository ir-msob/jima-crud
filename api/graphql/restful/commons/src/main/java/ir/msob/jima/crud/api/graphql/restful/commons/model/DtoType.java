package ir.msob.jima.crud.api.graphql.restful.commons.model;

import ir.msob.jima.core.commons.dto.BaseType;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DtoType implements BaseType {
    private String dto;
}
