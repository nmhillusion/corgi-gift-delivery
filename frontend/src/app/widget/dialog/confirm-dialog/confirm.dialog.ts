import { Component, EventEmitter, inject, Output } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";

@Component({
  selector: "app-confirm-dialog",
  templateUrl: "./confirm.dialog.html",
  styleUrls: ["./confirm.dialog.scss"],
  imports: [MatDividerModule, MatButtonModule],
})
export class ConfirmDialog {
  dialogData = inject(MAT_DIALOG_DATA);

  private $dialogRef: MatDialogRef<ConfirmDialog, Boolean> = inject(MatDialogRef<ConfirmDialog, Boolean>);

  constructor() {}

  confirm() {
    this.$dialogRef.close(true);
  }

  cancel() {
    this.$dialogRef.close(false);
  }
}
