package ir.msob.jima.graphql.restful.api.model;

import ir.msob.jima.platform.api.shared.BaseType;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CountType implements BaseType {
    private Long count;
}
