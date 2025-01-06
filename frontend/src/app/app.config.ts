import { ApplicationConfig, provideZoneChangeDetection } from "@angular/core";
import { provideRouter } from "@angular/router";

import { provideHttpClient, withFetch } from "@angular/common/http";
import { provideMomentDateAdapter } from "@angular/material-moment-adapter";
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from "@angular/material/form-field";
import { provideAnimationsAsync } from "@angular/platform-browser/animations/async";
import { routes } from "./app.routes";
import { environment } from "@app/../environments/environment";

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideAnimationsAsync(),
    provideHttpClient(withFetch()),
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: "outline" },
    },
    {
      provide: "PAGE_TITLE_DEFAULT",
      useValue: "Slight Transportation",
    },
    provideMomentDateAdapter({
      parse: {
        dateInput: environment.FORMAT.DATE_FORMAT.toUpperCase(),
      },
      display: {
        dateInput: environment.FORMAT.DATE_FORMAT.toUpperCase(),
        monthYearLabel: "MMM YYYY",
        dateA11yLabel: "LL",
        monthYearA11yLabel: "MMMM YYYY",
      },
    }),
  ],
};
