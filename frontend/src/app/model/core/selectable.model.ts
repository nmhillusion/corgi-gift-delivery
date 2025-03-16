import { WritableSignal } from "@angular/core";

export interface SelectableModel {
  selected$: WritableSignal<boolean>;
}
