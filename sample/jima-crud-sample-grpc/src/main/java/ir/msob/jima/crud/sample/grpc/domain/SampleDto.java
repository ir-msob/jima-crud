package ir.msob.jima.crud.sample.grpc.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.msob.jima.crud.sample.grpc.base.dto.ProjectDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SampleDto extends SampleDomain implements ProjectDto {

    public enum FN {
    }
}
