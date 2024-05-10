package ir.msob.jima.crud.sample.restful.base.criteria;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ir.msob.jima.core.commons.Constants;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import org.bson.types.ObjectId;

/**
 * Basic class for domains filter
 *
 * @author Yaqub Abdi
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = Constants.TYPE_PROPERTY_NAME)
public interface ProjectCriteria extends BaseCriteria<ObjectId> {

}
