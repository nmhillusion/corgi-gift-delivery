import { Component, inject, signal } from "@angular/core";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { AppCommonModule } from "@app/core/app-common.module";
import { NoteOwnerDto } from "@app/model/business/note.model";
import {
  RecipientFEModel,
  RecipientModel,
} from "@app/model/business/recipient.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";
import { AppNoteComponent } from "@app/pages/shared/note/note.component";
import { RecipientService } from "@app/service/recipient.service";
import { BehaviorSubject } from "rxjs";

@Component({
  standalone: true,
  templateUrl: "./view.component.html",
  styleUrls: ["./view.component.scss"],
  imports: [AppCommonModule, AppNoteComponent],
})
export class RecipientViewComponent extends BasePage {
  dialogData$ = inject<{
    recipient: RecipientModel;
  }>(MAT_DIALOG_DATA);

  recipient$ = signal<Nullable<RecipientFEModel>>(null);

  noteOwner$ = new BehaviorSubject<NoteOwnerDto>({
    recipientId: this.dialogData$.recipient.recipientId,
  });

  /// methods
  constructor(private $recipientService: RecipientService) {
    super();
  }

  protected override __ngOnInit__() {
    this.recipient$.set(
      this.$recipientService.convertToFEModel(this.dialogData$.recipient, this)
    );
  }
}
