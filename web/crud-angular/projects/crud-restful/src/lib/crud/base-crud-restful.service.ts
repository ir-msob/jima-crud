import {Observable} from "rxjs";
import {Criteria, Dto, JsonPatchSpace, Page} from "@ir-msob-jima/core-commons";
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
import {BaseErrorHandlerService, Pagination, RestUtil} from "@ir-msob-jima/core-restful";
import {NullableT} from "@ir-msob-jima/core-commons/lib/constants";
import BaseDto = Dto.BaseDto;
import BaseCriteria = Criteria.BaseCriteria;
import JsonPatch = JsonPatchSpace.JsonPatch;

/**
 * This is an abstract class that provides CRUD operations for a RESTful service.
 * It extends the BaseErrorHandlerService to handle errors.
 *
 * @template ID - the type of the ID of the DTO
 * @template DTO - the type of the DTO, which extends BaseDto
 * @template C - the type of the criteria, which extends BaseCriteria
 */
export abstract class BaseCrudRestfulService<ID, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> extends BaseErrorHandlerService {

  /**
   * Counts the number of DTOs that match the given criteria.
   *
   * @param criteria - the criteria to match
   * @returns an Observable that emits the count
   */
  count(criteria?: NullableT<C>): Observable<number> {
    return super.getWithErrors('/' + COUNT, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }

  /**
   * Counts all DTOs.
   *
   * @returns an Observable that emits the count
   */
  countAll(): Observable<number> {
    return super.getWithErrors('/' + COUNT_ALL, {
      needToken: true,
    });
  }

  /**
   * Gets a page of DTOs that match the given criteria.
   *
   * @param criteria - the criteria to match
   * @param pagination - the pagination settings
   * @returns an Observable that emits the page of DTOs
   */
  getPage(criteria?: NullableT<C>, pagination?: Pagination): Observable<Page<DTO>> {
    return super.getWithErrors('/' + GET_PAGE, {
      params: RestUtil.generateParams(criteria, pagination), needToken: true,
    });
  }

  /**
   * Gets many DTOs that match the given criteria.
   *
   * @param criteria - the criteria to match
   * @returns an Observable that emits the DTOs
   */
  getMany(criteria?: NullableT<C>): Observable<DTO[]> {
    return super.getWithErrors('/' + GET_MANY, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }

  /**
   * Gets a DTO by its ID.
   *
   * @param id - the ID of the DTO
   * @returns an Observable that emits the DTO
   */
  getById(id: NullableT<ID>): Observable<DTO> {
    return super.getWithErrors('/' + id, {
      needToken: true,
    });
  }

  /**
   * Gets one DTO that matches the given criteria.
   *
   * @param criteria - the criteria to match
   * @returns an Observable that emits the DTO
   */
  getOne(criteria?: NullableT<C>): Observable<DTO> {
    return super.getWithErrors('/' + GET_ONE, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }

  /**
   * Deletes a DTO by its ID.
   *
   * @param id - the ID of the DTO
   * @returns an Observable that emits the ID of the deleted DTO
   */
  deleteById(id: NullableT<ID>): Observable<ID> {
    return super.deleteWithErrors('/' + id, {
      needToken: true,
    });
  }

  /**
   * Deletes DTOs that match the given criteria.
   *
   * @param criteria - the criteria to match
   * @returns an Observable that emits the ID of the deleted DTO
   */
  delete(criteria?: NullableT<C>): Observable<ID> {
    return super.deleteWithErrors('/' + DELETE, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }

  /**
   * Deletes many DTOs that match the given criteria.
   *
   * @param criteria - the criteria to match
   * @returns an Observable that emits the IDs of the deleted DTOs
   */
  deleteMany(criteria?: NullableT<C>): Observable<ID[]> {
    return super.deleteWithErrors('/' + DELETE_MANY, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }

  /**
   * Saves a DTO.
   *
   * @param body - the DTO to save
   * @returns an Observable that emits the saved DTO
   */
  save(body: DTO): Observable<DTO> {
    return super.postWithErrors('/' + SAVE, body, {
      needToken: true,
    });
  }

  /**
   * Saves many DTOs.
   *
   * @param body - the DTOs to save
   * @returns an Observable that emits the saved DTOs
   */
  saveMany(body: DTO[]): Observable<DTO[]> {
    return super.postWithErrors('/' + SAVE_MANY, body, {
      needToken: true,
    });
  }

  /**
   * Updates a DTO by its ID.
   *
   * @param id - the ID of the DTO
   * @param body - the DTO to update
   * @returns an Observable that emits the updated DTO
   */
  updateById(id: ID, body: DTO): Observable<DTO> {
    return super.putWithErrors('/' + id, body, {
      needToken: true,
    });
  }

  /**
   * Updates a DTO.
   *
   * @param body - the DTO to update
   * @returns an Observable that emits the updated DTO
   */
  update(body: DTO): Observable<DTO> {
    return super.putWithErrors('/' + UPDATE, body, {
      needToken: true,
    });
  }

  /**
   * Updates many DTOs.
   *
   * @param body - the DTOs to update
   * @returns an Observable that emits the updated DTOs
   */
  updateMany(body: DTO[]): Observable<DTO[]> {
    return super.putWithErrors('/' + UPDATE_MANY, body, {
      needToken: true,
    });
  }

  /**
   * Edits a DTO by its ID using a JSON Patch.
   *
   * @param id - the ID of the DTO
   * @param body - the JSON Patch to apply
   * @returns an Observable that emits the edited DTO
   */
  editById(id: ID, body: JsonPatch): Observable<DTO> {
    return super.patchWithErrors('/' + id, body, {
      needToken: true,
    });
  }

  /**
   * Edits a DTO that matches the given criteria using a JSON Patch.
   *
   * @param body - the JSON Patch to apply
   * @param criteria - the criteria to match
   * @returns an Observable that emits the edited DTO
   */
  edit(body: JsonPatch, criteria?: NullableT<C>): Observable<DTO> {
    return super.patchWithErrors('/' + EDIT, body, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }

  /**
   * Edits many DTOs that match the given criteria using a JSON Patch.
   *
   * @param body - the JSON Patch to apply
   * @param criteria - the criteria to match
   * @returns an Observable that emits the edited DTOs
   */
  editMany(body: JsonPatch, criteria?: NullableT<C>): Observable<DTO[]> {
    return super.patchWithErrors('/' + EDIT_MANY, body, {
      params: RestUtil.generateParams(criteria), needToken: true,
    });
  }
}
