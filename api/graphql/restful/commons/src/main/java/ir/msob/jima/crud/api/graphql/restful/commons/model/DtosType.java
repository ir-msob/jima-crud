package ir.msob.jima.crud.api.graphql.restful.commons.model;

import ir.msob.jima.core.commons.model.dto.BaseType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtosType implements BaseType {
    private List<String> dtos = new ArrayList<>();
}
