import { Component, inject } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MAT_DIALOG_DATA, MatDialog } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";

@Component({
  selector: "app-alert-dialog",
  templateUrl: "./alert.dialog.html",
  styleUrls: ["./alert.dialog.scss"],
  imports: [MatDividerModule, MatButtonModule],
})
export class AlertDialog {
  dialogData = inject(MAT_DIALOG_DATA);
  dialogRef = inject(MatDialog);

  constructor() {}

  onClose() {
    this.dialogRef.closeAll();
  }
}
