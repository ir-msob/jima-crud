package ir.msob.jima.crud.sample.restful.base.domain;

import ir.msob.jima.core.commons.domain.BaseDomain;
import org.bson.types.ObjectId;

/**
 * Interface for project domain objects.
 * It extends a base domain interface with ObjectId as the identifier type.
 */
public interface ProjectDomain extends BaseDomain<ObjectId> {

}
