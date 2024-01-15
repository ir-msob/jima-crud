export class TestModel {
  constructor(field: string | undefined) {
    this._field = field;
  }

  private _field: string | undefined;

  get field(): string | undefined {
    return this._field;
  }

  set field(value: string) {
    this._field = value;
  }

  toJSON() {
    const proto = Object.getPrototypeOf(this);
    const jsonObj: any = Object.assign({});

    Object.entries(Object.getOwnPropertyDescriptors(proto))
      .filter(([key, descriptor]) => typeof descriptor.get === 'function')
      .map(([key, descriptor]) => {
        if (descriptor && key[0] !== '_') {
          try {
            const val = (this as any)[key];
            jsonObj[key] = val;
          } catch (error) {
            console.error(`Error calling getter ${key}`, error);
          }
        }
      });

    return jsonObj;
  }
}
