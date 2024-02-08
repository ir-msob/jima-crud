import {
  DEFAULT_NUMBER,
  DEFAULT_STRING,
  DEFAULT_STRINGS,
  JsonPatchSpace,
  Page,
  SampleCriteria,
  SampleDto
} from "@ir-msob-jima/core-commons";
import {HttpClient,} from "@angular/common/http";
import {SampleCrudRestfulService} from "./sample-crud-restful.service";
import {of} from "rxjs";
import {
  COUNT,
  COUNT_ALL,
  DELETE,
  DELETE_BY_ID,
  DELETE_MANY,
  EDIT,
  EDIT_BY_ID,
  EDIT_MANY,
  GET_BY_ID,
  GET_MANY,
  GET_ONE,
  GET_PAGE,
  SAVE,
  SAVE_MANY,
  UPDATE,
  UPDATE_BY_ID,
  UPDATE_MANY
} from "../constants";
import JsonPatch = JsonPatchSpace.JsonPatch;
import JsonPatchOperation = JsonPatchSpace.JsonPatchOperation;
import Operation = JsonPatchSpace.Operation;

/**
 * This test suite is for the SampleCrudRestfulService.
 * It includes tests for all CRUD operations provided by the service.
 */
describe('SampleCrudRestfulServiceService', () => {
  let service: SampleCrudRestfulService<string, SampleDto<string>, SampleCriteria<string>>;
  let httpClientSpy: jasmine.SpyObj<HttpClient>;

  /**
   * This function is run before each test.
   * It creates a spy for the HttpClient and initializes the service with it.
   */
  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'patch', 'put', 'delete']);
    service = new SampleCrudRestfulService(httpClientSpy);
  });

  /**
   * This test checks if the service is created successfully.
   */
  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  /**
   * This test checks the count() method of the service.
   * It expects the HttpClient's get method to be called and the returned data to be DEFAULT_NUMBER.
   */
  it(COUNT, () => {
    httpClientSpy.get.and.returnValue(of(DEFAULT_NUMBER));
    service.count().subscribe((data: number) => {
      expect(data).toEqual(DEFAULT_NUMBER);
      expect(httpClientSpy.get).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the countAll() method of the service.
   * It expects the HttpClient's get method to be called and the returned data to be DEFAULT_NUMBER.
   */
  it(COUNT_ALL, () => {
    httpClientSpy.get.and.returnValue(of(DEFAULT_NUMBER));
    service.countAll().subscribe((data: number) => {
      expect(data).toEqual(DEFAULT_NUMBER);
      expect(httpClientSpy.get).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the getById() method of the service.
   * It expects the HttpClient's get method to be called and the returned data to be a SampleDto object.
   */
  it(GET_BY_ID, () => {
    let dto: SampleDto<string> = prepareDto();
    let id: string = DEFAULT_STRING;
    httpClientSpy.get.and.returnValue(of(dto));
    service.getById(id).subscribe((data: SampleDto<string>) => {
      expect(data).not.toBeNull();
      expect(data.id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.get).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the getOne() method of the service.
   * It expects the HttpClient's get method to be called and the returned data to be a SampleDto object.
   */
  it(GET_ONE, () => {
    let dto: SampleDto<string> = prepareDto();
    httpClientSpy.get.and.returnValue(of(dto));
    service.getOne().subscribe((data: SampleDto<string>) => {
      expect(data).not.toBeNull();
      expect(data.id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.get).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the getPage() method of the service.
   * It expects the HttpClient's get method to be called and the returned data to be a Page object.
   */
  it(GET_PAGE, () => {
    let dto: Page<SampleDto<string>> = preparePage();
    httpClientSpy.get.and.returnValue(of(dto));
    service.getPage().subscribe((data: Page<SampleDto<string>>) => {
      expect(data).not.toBeNull();
      expect(data.content).not.toBeNull();
      expect(data.content).toHaveSize(1);
      expect(data.content![0].id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.get).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the getMany() method of the service.
   * It expects the HttpClient's get method to be called and the returned data to be an array of SampleDto objects.
   */
  it(GET_MANY, () => {
    let dto: SampleDto<string>[] = prepareManyDto();
    httpClientSpy.get.and.returnValue(of(dto));
    service.getMany().subscribe((data: SampleDto<string>[]) => {
      expect(data).not.toBeNull();
      expect(data).toHaveSize(1);
      expect(data[0].id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.get).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the deleteById() method of the service.
   * It expects the HttpClient's delete method to be called and the returned data to be DEFAULT_STRING.
   */
  it(DELETE_BY_ID, () => {
    let id: string = DEFAULT_STRING;
    httpClientSpy.delete.and.returnValue(of(DEFAULT_STRING));
    service.deleteById(id).subscribe((data: string) => {
      expect(data).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.delete).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the delete() method of the service.
   * It expects the HttpClient's delete method to be called and the returned data to be DEFAULT_STRING.
   */
  it(DELETE, () => {
    httpClientSpy.delete.and.returnValue(of(DEFAULT_STRING));
    service.delete().subscribe((data: string) => {
      expect(data).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.delete).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the deleteMany() method of the service.
   * It expects the HttpClient's delete method to be called and the returned data to be DEFAULT_STRINGS.
   */
  it(DELETE_MANY, () => {
    httpClientSpy.delete.and.returnValue(of(DEFAULT_STRINGS));
    service.deleteMany().subscribe((data: string[]) => {
      expect(data).toEqual(DEFAULT_STRINGS);
      expect(httpClientSpy.delete).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the save() method of the service.
   * It expects the HttpClient's post method to be called and the returned data to be a SampleDto object.
   */
  it(SAVE, () => {
    let dto: SampleDto<string> = prepareDto();
    httpClientSpy.post.and.returnValue(of(dto));
    service.save(dto).subscribe((data: SampleDto<string>) => {
      expect(data).not.toBeNull();
      expect(data.id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.post).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the saveMany() method of the service.
   * It expects the HttpClient's post method to be called and the returned data to be an array of SampleDto objects.
   */
  it(SAVE_MANY, () => {
    let dto: SampleDto<string>[] = prepareManyDto();
    httpClientSpy.post.and.returnValue(of(dto));
    service.saveMany(dto).subscribe((data: SampleDto<string>[]) => {
      expect(data).not.toBeNull();
      expect(data).toHaveSize(1);
      expect(data[0].id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.post).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the updateById() method of the service.
   * It expects the HttpClient's put method to be called and the returned data to be a SampleDto object.
   */
  it(UPDATE_BY_ID, () => {
    let dto: SampleDto<string> = prepareDto();
    let id: string = DEFAULT_STRING;
    httpClientSpy.put.and.returnValue(of(dto));
    service.updateById(id, dto).subscribe((data: SampleDto<string>) => {
      expect(data).not.toBeNull();
      expect(data.id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.put).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the update() method of the service.
   * It expects the HttpClient's put method to be called and the returned data to be a SampleDto object.
   */
  it(UPDATE, () => {
    let dto: SampleDto<string> = prepareDto();
    httpClientSpy.put.and.returnValue(of(dto));
    service.update(dto).subscribe((data: SampleDto<string>) => {
      expect(data).not.toBeNull();
      expect(data.id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.put).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the updateMany() method of the service.
   * It expects the HttpClient's put method to be called and the returned data to be an array of SampleDto objects.
   */
  it(UPDATE_MANY, () => {
    let dto: SampleDto<string>[] = prepareManyDto();
    httpClientSpy.put.and.returnValue(of(dto));
    service.updateMany(dto).subscribe((data: SampleDto<string>[]) => {
      expect(data).not.toBeNull();
      expect(data).toHaveSize(1);
      expect(data[0].id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.put).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the editById() method of the service.
   * It expects the HttpClient's patch method to be called and the returned data to be a SampleDto object.
   */
  it(EDIT_BY_ID, () => {
    let payload: JsonPatch = preparePatch();
    let response: SampleDto<string> = prepareDto();
    let id: string = DEFAULT_STRING;
    httpClientSpy.patch.and.returnValue(of(response));
    service.editById(id, payload).subscribe((data: SampleDto<string>) => {
      expect(data).not.toBeNull();
      expect(data.id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.patch).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the edit() method of the service.
   * It expects the HttpClient's patch method to be called and the returned data to be a SampleDto object.
   */
  it(EDIT, () => {
    let payload: JsonPatch = preparePatch();
    let response: SampleDto<string> = prepareDto();
    httpClientSpy.patch.and.returnValue(of(response));
    service.edit(payload).subscribe((data: SampleDto<string>) => {
      expect(data).not.toBeNull();
      expect(data.id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.patch).toHaveBeenCalled();
    });
  });

  /**
   * This test checks the editMany() method of the service.
   * It expects the HttpClient's patch method to be called and the returned data to be an array of SampleDto objects.
   */
  it(EDIT_MANY, () => {
    let payload: JsonPatch = preparePatch();
    let response: SampleDto<string>[] = prepareManyDto();
    httpClientSpy.patch.and.returnValue(of(response));
    service.editMany(payload).subscribe((data: SampleDto<string>[]) => {
      expect(data).not.toBeNull();
      expect(data).toHaveSize(1);
      expect(data[0].id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.patch).toHaveBeenCalled();
    });
  });

  /**
   * This helper function prepares a JsonPatchOperation object for testing.
   */
  function prepareOperation(): JsonPatchOperation {
    let jsonPatchOperation: JsonPatchOperation = new JsonPatchOperation();
    jsonPatchOperation.op = Operation.add;
    jsonPatchOperation.value = DEFAULT_STRING;
    jsonPatchOperation.path = "/path";
    return jsonPatchOperation;
  }

  /**
   * This helper function prepares a JsonPatch object for testing.
   */
  function preparePatch(): JsonPatch {
    let jsonPatch: JsonPatch = new JsonPatch();
    jsonPatch.operations = [prepareOperation()]
    return jsonPatch;
  }

  /**
   * This helper function prepares an array of SampleDto objects for testing.
   */
  function prepareManyDto(): SampleDto<string>[] {
    return [prepareDto()];
  }

  /**
   * This helper function prepares a Page object for testing.
   */
  function preparePage(): Page<SampleDto<string>> {
    let dto: Page<SampleDto<string>> = new Page<SampleDto<string>>();
    dto.content = [prepareDto()]
    return dto;
  }

  /**
   * This helper function prepares a SampleDto object for testing.
   */
  function prepareDto(): SampleDto<string> {
    let dto: SampleDto<string> = new SampleDto<string>();
    dto.id = DEFAULT_STRING;
    return dto;
  }
});
