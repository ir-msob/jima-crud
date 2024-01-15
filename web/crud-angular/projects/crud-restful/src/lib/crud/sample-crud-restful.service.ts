import {Criteria, Dto} from "@msob-jima-core/core-commons";
import {BaseCrudRestfulService} from "./base-crud-restful.service";
import {HttpClient} from "@angular/common/http";
import BaseDto = Dto.BaseDto;
import BaseCriteria = Criteria.BaseCriteria;

export class SampleCrudRestfulService<ID, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> extends BaseCrudRestfulService<ID, DTO, C> {

  public static prefixPath: string = 'sample';

  constructor(http: HttpClient) {
    super(http, SampleCrudRestfulService.prefixPath);
  }

  handleError(error: any): void {
  }
}
