import {
  HttpEventType,
  HttpHandlerFn,
  HttpRequest,
} from "@angular/common/http";
import { inject } from "@angular/core";
import { LoadingMonitor } from "@app/monitor/loading.monitor";
import { tap } from "rxjs";

export function interceptHttpRequestLoading(
  request: HttpRequest<unknown>,
  next: HttpHandlerFn
) {
  // tracking the loading state can be done here
  // e.g., by using a service to set a loading flag
  const loadingMonitor = inject(LoadingMonitor);

  console.log("HTTP Request started:", request.url);
  loadingMonitor.startLoading();

  return next(request).pipe(
    tap({
      next: (event) => {
        // handle successful response
        if (event.type === HttpEventType.Response) {
          console.log("HTTP Request completed:", event);
          loadingMonitor.stopLoading();
        }
      },
      error: (error) => {
        // handle error response
        console.error("HTTP Request failed:", error);
        loadingMonitor.stopLoading();
      },
    })
  );
}
