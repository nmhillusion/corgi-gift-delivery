import { Component, inject } from "@angular/core";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { NoteOwnerDto } from "@app/model/business/note.model";
import { BehaviorSubject } from "rxjs";
import { AppNoteComponent } from "../note.component";

@Component({
  standalone: true,
  templateUrl: "./note-dialog.component.html",
  styleUrls: ["./note-dialog.component.scss"],
  imports: [AppNoteComponent],
})
export class NoteDialog {
  dialogData$ = inject<{
    noteOwner$: BehaviorSubject<NoteOwnerDto>;
  }>(MAT_DIALOG_DATA);
}
