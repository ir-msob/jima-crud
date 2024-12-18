package ir.msob.jima.crud.sample.restful.base.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ir.msob.jima.core.commons.Constants;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.crud.sample.restful.base.domain.ProjectDomain;
import org.bson.types.ObjectId;

/**
 * Interface for project-childdomain Data Transfer Objects (DTO).
 * It extends both the domain model and a base DTO interface.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = Constants.TYPE_PROPERTY_NAME)
public interface ProjectDto extends ProjectDomain, BaseDto<ObjectId> {

}
