import { signal, WritableSignal } from "@angular/core";
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

export interface PaginatorHandler {
  onPageChange(pageEvent: PageEvent): void;
  length$: WritableSignal<number>;
  pageSize$: WritableSignal<number>;
  pageIndex$: WritableSignal<number>;
  pageSizeOptions$: WritableSignal<number[]>;
}
