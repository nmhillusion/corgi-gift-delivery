import { Component } from "@angular/core";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { NoteModel } from "@app/model/business/note.model";
import { BasePage } from "@app/pages/base.page";
import { NoteService } from "@app/service/note.service";

@Component({
  standalone: true,
  selector: "app-note",
  templateUrl: "./note.component.html",
  styleUrls: ["./note.component.scss"],
  imports: [AppCommonModule],
})
export class NoteComponent extends BasePage {
  noteTableDataSource$ = new MatTableDataSource<NoteModel>();
  paginator = this.generatePaginator();

  /// methods
  constructor(private $noteService: NoteService) {
    super();
  }
}
