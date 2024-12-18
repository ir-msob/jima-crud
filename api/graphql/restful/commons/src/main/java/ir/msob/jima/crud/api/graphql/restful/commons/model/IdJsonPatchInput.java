package ir.msob.jima.crud.api.graphql.restful.commons.model;

import ir.msob.jima.core.commons.shared.BaseType;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IdJsonPatchInput implements BaseType {
    private String id;
    private String jsonPatch;
}
