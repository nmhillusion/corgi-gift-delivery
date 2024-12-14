import {PageEvent} from "@angular/material/paginator";

export const PAGE = {
  SIZE_OPTIONS: [5, 10, 25],
}

export const DEFAULT_PAGE_EVENT: PageEvent = {
  pageIndex: 0,
  pageSize: 10,
  length: 0,
}
