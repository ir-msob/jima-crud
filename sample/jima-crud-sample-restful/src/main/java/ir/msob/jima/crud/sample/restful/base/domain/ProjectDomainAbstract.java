package ir.msob.jima.crud.sample.restful.base.domain;

import ir.msob.jima.core.commons.model.domain.BaseDomainAbstract;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.io.Serial;

/**
 * @author Yaqub Abdi
 */
@Setter
@Getter
@ToString(callSuper = true)
public abstract class ProjectDomainAbstract extends BaseDomainAbstract<ObjectId> implements ProjectDomain {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -6601527731070113825L;

    protected ProjectDomainAbstract() {
        super();
    }

    public enum FN {
        id
    }
}
