import { WritableSignal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";

export interface Page<T> {
  content: T[];
  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  };
}

export function mapPage<T, R>(originalPage: Page<T>, mapper: (item: T) => R) {
  return {
    content: originalPage.content.map((it) => mapper(it)),
    page: originalPage.page,
  } as Page<R>;
}

export interface PaginatorHandler {
  onPageChange(pageEvent: PageEvent): void;
  length$: WritableSignal<number>;
  pageSize$: WritableSignal<number>;
  pageIndex$: WritableSignal<number>;
  pageSizeOptions$: WritableSignal<number[]>;
}
