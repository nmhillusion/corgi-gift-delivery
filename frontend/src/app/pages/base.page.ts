import {
  Component,
  inject,
  Inject,
  Injector,
  OnDestroy,
  OnInit,
  signal,
} from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { ActivatedRoute, Router } from "@angular/router";
import { environment } from "@app/../environments/environment";
import { PAGE } from "@app/layout/page.constant";
import { Nullable } from "@app/model/core/nullable.model";
import { Page, PaginatorHandler } from "@app/model/core/page.model";
import { FormUtils } from "@app/util/form.util";
import { ParamUtils } from "@app/util/param.util";
import { AlertDialog } from "@app/widget/dialog/alert-dialog/alert.dialog";
import { ConfirmDialog } from "@app/widget/dialog/confirm-dialog/confirm.dialog";
import { Subscription } from "rxjs";

@Component({
  selector: "",
  template: "",
})
export class BasePage implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  protected $router: Router = inject(Router);
  protected $activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  protected $dialog = inject(MatDialog);
  public $injector = inject(Injector);

  CONSTANTS = {
    FORMAT: {
      DATE_FORMAT: environment.FORMAT.DATE_FORMAT,
      DATETIME_FORMAT: environment.FORMAT.DATETIME_FORMAT,
    },
  };

  formUtils = new FormUtils();
  paramUtils = new ParamUtils();

  constructor(
    @Inject("PAGE_TITLE_DEFAULT") public pageTitle?: Nullable<string>
  ) {
    if (pageTitle) {
      document.title = pageTitle;
    }
  }

  ngOnInit() {
    this.__ngOnInit__();
  }

  protected __ngOnInit__(): any {
    // default: do nothing
  }

  generatePaginator() {
    return {
      pageIndex$: signal(0),
      pageSize$: signal(10),
      length$: signal(0),
      pageSizeOptions$: signal(PAGE.SIZE_OPTIONS),
      onPageChange: (evt) => {
        this.search(evt);
      },
    } as PaginatorHandler;
  }

  search(pageEvt: PageEvent) {
    throw new Error("Method not implemented.");
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub_) => sub_.unsubscribe());
  }

  registerSubscription(...subscriptions: Subscription[]) {
    this.subscriptions.push(...subscriptions);
  }

  handlePageDataUpdate<T>(
    pageData: Page<T>,
    paginatorHandler: PaginatorHandler,
    matTableDs: MatTableDataSource<T>
  ) {
    paginatorHandler.length$.set(pageData.page.totalElements || 0);
    paginatorHandler.pageIndex$.set(pageData.page.number || 0);
    paginatorHandler.pageSize$.set(pageData.page.size || 0);

    console.log("pageData: ", pageData, "paginatorHandler: ", paginatorHandler);

    matTableDs.data = pageData.content || [];
  }

  dialogHandler = {
    alert: (message: string) => {
      this.$dialog.open<AlertDialog>(AlertDialog, {
        data: {
          message,
        },
      });
    },

    confirm: (message: string) => {
      return new Promise((resolve, reject) => {
        const ref_ = this.$dialog.open<ConfirmDialog, any, Boolean>(
          ConfirmDialog,
          {
            data: {
              message,
            },
          }
        );

        const subscription = ref_.afterClosed().subscribe({
          next: resolve,
          error: reject,
          complete: () => {
            subscription.unsubscribe();
          },
        });
      });
    },
  };
}
