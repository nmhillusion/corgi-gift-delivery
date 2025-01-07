import { ActivatedRoute } from "@angular/router";

export class ParamUtils {
  public getParamOrThrow(
    activatedRoute: ActivatedRoute,
    paramName: string
  ): string {
    const paramValue = activatedRoute.snapshot.paramMap.get(paramName);

    if (!paramValue) {
      throw new Error(`Param ${paramName} not found`);
    }

    return paramValue;
  }
}
