import { FormControl } from "@angular/forms";

export class FormUtils {
  extractError(formControl: FormControl) {
    if (formControl && formControl.errors) {
      let errors: { [key: string]: any } = {};

      for (const err of Object.keys(formControl.errors)) {
        errors[`error.${err}`] = formControl.errors[err];
      }

      return JSON.stringify(errors);
    }

    return "";
  }
}
