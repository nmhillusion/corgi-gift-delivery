import { PageEvent } from "@angular/material/paginator";

export const PAGE = {
  SIZE_OPTIONS: [5, 10, 25],
  DEFAULT_PAGE_EVENT: {
    pageIndex: 0,
    pageSize: 10,
    length: 0,
  } as PageEvent,
};
