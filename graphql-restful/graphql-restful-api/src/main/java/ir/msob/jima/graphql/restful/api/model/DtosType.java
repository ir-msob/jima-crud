package ir.msob.jima.graphql.restful.api.model;

import ir.msob.jima.platform.api.shared.BaseType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DtosType implements BaseType {
    @Builder.Default
    private List<String> dtos = new ArrayList<>();
}
