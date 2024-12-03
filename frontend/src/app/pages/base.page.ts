import {
  OnInit,
  OnDestroy,
  Component,
  Injectable,
  inject,
  Inject,
} from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";

@Component({
  selector: "",
  template: "",
})
export class BasePage implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  private $router: Router = inject(Router);
  private $activatedRoute: ActivatedRoute = inject(ActivatedRoute);

  constructor(@Inject("_NONE_") pageTitle: string) {
    document.title = pageTitle;
  }

  ngOnInit() {
    this.__ngOnInit__();
  }

  protected __ngOnInit__(): any {
    // default: do nothing
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub_) => sub_.unsubscribe());
  }

  registerSubscription(subscription: Subscription) {
    this.subscriptions.push(subscription);
  }
}
