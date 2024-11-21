package ir.msob.jima.crud.api.graphql.restful.commons.model;

import ir.msob.jima.core.commons.dto.BaseType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DtosInput implements BaseType {
    @Builder.Default
    private List<String> dtos = new ArrayList<>();
}
