import {Criteria, Dto} from "@ir-msob/jima-core-commons";
import {BaseCrudRestfulService} from "./base-crud-restful.service";
import {HttpClient} from "@angular/common/http";
import BaseDto = Dto.BaseDto;
import BaseCriteria = Criteria.BaseCriteria;

/**
 * This class extends the BaseCrudRestfulService and provides CRUD operations for a sample service.
 * It also overrides the handleError method from the base class.
 *
 * @template ID - the type of the ID of the DTO
 * @template DTO - the type of the DTO, which extends BaseDto
 * @template C - the type of the criteria, which extends BaseCriteria
 */
export class SampleCrudRestfulService<ID, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> extends BaseCrudRestfulService<ID, DTO, C> {

  /**
   * The prefix path for the sample service.
   */
  public static prefixPath: string = 'sample';

  /**
   * The constructor initializes the base class with the HttpClient and the prefix path.
   *
   * @param http - the HttpClient to use for HTTP requests
   */
  constructor(http: HttpClient) {
    super(http, SampleCrudRestfulService.prefixPath);
  }

  /**
   * This method is used to handle errors. It is currently empty and should be implemented.
   *
   * @param error - the error to handle
   */
  handleError(error: any): void {
  }
}
