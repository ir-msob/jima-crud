package ir.msob.jima.crud.sample.graphql.restful.base.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ir.msob.jima.core.commons.Constants;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.crud.sample.graphql.restful.base.domain.ProjectDomain;
import org.bson.types.ObjectId;

/**
 * @author Yaqub Abdi
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = Constants.TYPE_PROPERTY_NAME)
public interface ProjectDto extends ProjectDomain, BaseDto<ObjectId> {

}
