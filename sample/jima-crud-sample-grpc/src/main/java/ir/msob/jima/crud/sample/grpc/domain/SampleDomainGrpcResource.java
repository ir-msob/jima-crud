package ir.msob.jima.crud.sample.grpc.domain;


import ir.msob.jima.core.commons.model.scope.Resource;
import ir.msob.jima.crud.sample.grpc.base.resource.CrudGrpcResource;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import static ir.msob.jima.crud.sample.grpc.base.resource.ResourceType.GRPC_RESOURCE_TYPE;

@RequiredArgsConstructor
@Resource(value = SampleDomain.DOMAIN_URI, type = GRPC_RESOURCE_TYPE)
@GrpcService
public class SampleDomainGrpcResource extends CrudGrpcResource<SampleDomain, SampleDto, SampleCriteria, SampleRepository, SampleService> {

}

