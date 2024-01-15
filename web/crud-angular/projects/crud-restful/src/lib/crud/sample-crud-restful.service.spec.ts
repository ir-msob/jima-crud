import {
  DEFAULT_NUMBER,
  DEFAULT_STRING,
  DEFAULT_STRINGS,
  JsonPatchSpace,
  Page,
  SampleCriteria,
  SampleDto
} from "@msob-jima-core/core-commons";
import {HttpClient,} from "@angular/common/http";
import {SampleCrudRestfulService} from "./sample-crud-restful.service";
import {of} from "rxjs";
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
import {TestModel} from "./test-model";
import JsonPatch = JsonPatchSpace.JsonPatch;
import JsonPatchOperation = JsonPatchSpace.JsonPatchOperation;
import Operation = JsonPatchSpace.Operation;

describe('SampleCrudRestfulServiceService', () => {
  let service: SampleCrudRestfulService<string, SampleDto<string>, SampleCriteria<string>>;
  let httpClientSpy: jasmine.SpyObj<HttpClient>;

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'patch', 'put', 'delete']);
    service = new SampleCrudRestfulService(httpClientSpy);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('JSON TEST', () => {
    let tm: TestModel = new TestModel('ssss');

    console.log('tm', JSON.stringify(tm));
    // console.log('tm to json',tm.toJSON());
  });

  it(COUNT, () => {
    httpClientSpy.get.and.returnValue(of(DEFAULT_NUMBER));
    service.count().subscribe((data: number) => {
      expect(data).toEqual(DEFAULT_NUMBER);
      expect(httpClientSpy.get).toHaveBeenCalled();
    });
  });

  it(COUNT_ALL, () => {
    httpClientSpy.get.and.returnValue(of(DEFAULT_NUMBER));
    service.countAll().subscribe((data: number) => {
      expect(data).toEqual(DEFAULT_NUMBER);
      expect(httpClientSpy.get).toHaveBeenCalled();
    });
  });

  it(GET_ONE, () => {
    let dto: SampleDto<string> = prepareDto();
    httpClientSpy.get.and.returnValue(of(dto));
    service.getOne().subscribe((data: SampleDto<string>) => {
      expect(data).not.toBeNull();
      expect(data.id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.get).toHaveBeenCalled();
    });
  });

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

  it(DELETE, () => {
    httpClientSpy.delete.and.returnValue(of(DEFAULT_STRING));
    service.delete().subscribe((data: string) => {
      expect(data).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.delete).toHaveBeenCalled();
    });
  });

  it(DELETE_MANY, () => {
    httpClientSpy.delete.and.returnValue(of(DEFAULT_STRINGS));
    service.deleteMany().subscribe((data: string[]) => {
      expect(data).toEqual(DEFAULT_STRINGS);
      expect(httpClientSpy.delete).toHaveBeenCalled();
    });
  });

  it(SAVE, () => {
    let dto: SampleDto<string> = prepareDto();
    httpClientSpy.post.and.returnValue(of(dto));
    service.save(dto).subscribe((data: SampleDto<string>) => {
      expect(data).not.toBeNull();
      expect(data.id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.post).toHaveBeenCalled();
    });
  });

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

  it(UPDATE, () => {
    let dto: SampleDto<string> = prepareDto();
    httpClientSpy.put.and.returnValue(of(dto));
    service.update(dto).subscribe((data: SampleDto<string>) => {
      expect(data).not.toBeNull();
      expect(data.id).toEqual(DEFAULT_STRING);
      expect(httpClientSpy.put).toHaveBeenCalled();
    });
  });

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

  function prepareOperation(): JsonPatchOperation {
    let jsonPatchOperation: JsonPatchOperation = new JsonPatchOperation();
    jsonPatchOperation.op = Operation.add;
    jsonPatchOperation.value = DEFAULT_STRING;
    jsonPatchOperation.path = "/path";
    return jsonPatchOperation;
  }

  function preparePatch(): JsonPatch {
    let jsonPatch: JsonPatch = new JsonPatch();
    jsonPatch.operations = [prepareOperation()]
    return jsonPatch;
  }

  function prepareManyDto(): SampleDto<string>[] {
    return [prepareDto()];
  }

  function preparePage(): Page<SampleDto<string>> {
    let dto: Page<SampleDto<string>> = new Page<SampleDto<string>>();
    dto.content = [prepareDto()]
    return dto;
  }

  function prepareDto(): SampleDto<string> {
    let dto: SampleDto<string> = new SampleDto<string>();
    dto.id = DEFAULT_STRING;
    return dto;
  }
});
