import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";

const commonModules = [
  CommonModule,
  FormsModule,
  ReactiveFormsModule,

  // Material
  MatInputModule,
  MatButtonModule,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule
];

@NgModule({
  imports: commonModules,
  exports: commonModules,
})
export class AppCommonModule {}
