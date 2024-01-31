package ir.msob.jima.crud.api.grpc.service.test;

import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.api.grpc.service.base.CrudGrpcResource;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestService;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class TestDomainGrpcResource extends CrudGrpcResource<TestDomain, TestDto, TestCriteria, TestRepository, TestService> {

}
