package ir.msob.jima.graphql.restful.api.model;

import ir.msob.jima.platform.api.shared.BaseType;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DtoInput implements BaseType {
    private String id;
    private String dto;
}
