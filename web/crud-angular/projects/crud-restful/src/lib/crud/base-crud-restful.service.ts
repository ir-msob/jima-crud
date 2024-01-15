import {Observable} from "rxjs";
import {Criteria, Dto, JsonPatchSpace, Page} from "@msob-jima-core/core-commons";
import {
  COUNT,
  COUNT_ALL,
  DELETE,
  DELETE_MANY,
  EDIT,
  EDIT_MANY,
  GET_MANY,
  GET_ONE,
  GET_PAGE,
  SAVE,
  SAVE_MANY,
  UPDATE,
  UPDATE_MANY
} from "../constants";
import {BaseErrorHandlerService, Pagination, RestUtil} from "@msob-jima-core/core-restful";
import {NullableT} from "@msob-jima-core/core-commons/lib/constants";
import BaseDto = Dto.BaseDto;
import BaseCriteria = Criteria.BaseCriteria;
import JsonPatch = JsonPatchSpace.JsonPatch;

export abstract class BaseCrudRestfulService<ID, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> extends BaseErrorHandlerService {


  count(criteria?: NullableT<C>): Observable<number> {
    return super.getWithErrors('/' + COUNT, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }

  countAll(): Observable<number> {
    return super.getWithErrors('/' + COUNT_ALL, {
      needToken: true,
    });
  }

  getPage(criteria?: NullableT<C>, pagination?: Pagination): Observable<Page<DTO>> {
    return super.getWithErrors('/' + GET_PAGE, {
      params: RestUtil.generateParams(criteria, pagination), needToken: true,
    });
  }

  getMany(criteria?: NullableT<C>): Observable<DTO[]> {
    return super.getWithErrors('/' + GET_MANY, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }

  getOne(criteria?: NullableT<C>): Observable<DTO> {
    return super.getWithErrors('/' + GET_ONE, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }


  delete(criteria?: NullableT<C>): Observable<ID> {
    return super.deleteWithErrors('/' + DELETE, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }

  deleteMany(criteria?: NullableT<C>): Observable<ID[]> {
    return super.deleteWithErrors('/' + DELETE_MANY, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }

  save(body: DTO): Observable<DTO> {
    return super.postWithErrors('/' + SAVE, body, {
      needToken: true,
    });
  }

  saveMany(body: DTO[]): Observable<DTO[]> {
    return super.postWithErrors('/' + SAVE_MANY, body, {
      needToken: true,
    });
  }


  update(body: DTO): Observable<DTO> {
    return super.putWithErrors('/' + UPDATE, body, {
      needToken: true,
    });
  }

  updateMany(body: DTO[]): Observable<DTO[]> {
    return super.putWithErrors('/' + UPDATE_MANY, body, {
      needToken: true,
    });
  }

  edit(body: JsonPatch, criteria?: NullableT<C>): Observable<DTO> {
    return super.patchWithErrors('/' + EDIT, body, {
      needToken: true,
    });
  }

  editMany(body: JsonPatch, criteria?: NullableT<C>): Observable<DTO[]> {
    return super.patchWithErrors('/' + EDIT_MANY, body, {
      needToken: true,
    });
  }
}
