package ir.msob.jima.graphql.restful.api.model;

import ir.msob.jima.platform.api.shared.BaseType;
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
