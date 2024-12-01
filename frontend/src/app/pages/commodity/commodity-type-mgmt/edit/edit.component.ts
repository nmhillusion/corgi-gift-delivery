import { Component, OnDestroy, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MatFormField, MatLabel } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { AppCommonModule } from "@app/core/app-common.module";
import { MainLayoutComponent } from "@app/layout/main-layout/main-layout.component";
import { CommodityTypeService } from "@app/service/commodity-type.service";
import { Subscription } from "rxjs";

@Component({
  standalone: true,
  templateUrl: "./edit.component.html",
  styleUrl: "./edit.component.scss",
  imports: [
    AppCommonModule,
    MainLayoutComponent,
    MatFormField,
    MatLabel
  ],
})
export class EditComponent implements OnInit, OnDestroy {
  title = "edit-mgmt";

  private subscriptions: Subscription[] = [];

  formGroup: FormGroup = new FormGroup({
    typeName: new FormControl("", [Validators.required]),
  });

  constructor(private $commodityTypeService: CommodityTypeService) {}

  ngOnInit() {
    this.subscriptions.push(
      this.$commodityTypeService.findAll().subscribe((list) => {
        console.log({ list });
      })
    );
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
