import { DialogRef } from "@angular/cdk/dialog";
import { Component, inject } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { NoteModel } from "@app/model/business/note.model";
import { BasePage } from "@app/pages/base.page";
import { NoteService } from "@app/service/note.service";

@Component({
  standalone: true,
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
  imports: [AppCommonModule],
})
export class EditComponent extends BasePage {
  dialogData = inject<{
    noteModel: NoteModel;
  }>(MAT_DIALOG_DATA);

  formGroup = new FormGroup({
    noteContent: new FormControl<string>("", [Validators.required]),
  });

  /// methods
  constructor(
    private $noteService: NoteService,
    private $dialogRef: DialogRef<NoteModel>
  ) {
    super();
  }

  save() {
    const entity = this.dialogData.noteModel;

    this.formUtils.revalidateForm(this.formGroup);

    if (!this.formGroup.valid) {
      throw new Error("Note form is invalid");
    }

    entity.noteContent = this.formGroup.controls.noteContent.value || "";

    this.registerSubscription(
      this.$noteService.save(entity).subscribe((_) => this.$dialogRef.close(_))
    );
  }
}
