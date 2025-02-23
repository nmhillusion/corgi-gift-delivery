import { Component, Input, signal } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { AppCommonModule } from "@app/core/app-common.module";
import { PAGE } from "@app/layout/page.constant";
import { SIZE } from "@app/layout/size.constant";
import { NoteModel, NoteOwnerDto } from "@app/model/business/note.model";
import { PaginatorHandler } from "@app/model/core/page.model";
import { BasePage } from "@app/pages/base.page";
import { NoteService } from "@app/service/note.service";
import { BehaviorSubject } from "rxjs";
import { EditComponent } from "./edit/edit.component";

@Component({
  standalone: true,
  selector: "app-note",
  templateUrl: "./note.component.html",
  styleUrls: ["./note.component.scss"],
  imports: [AppCommonModule],
})
export class AppNoteComponent extends BasePage {
  noteTableDataSource$ = new MatTableDataSource<NoteModel>();
  paginator = {
    pageIndex$: signal(0),
    pageSize$: signal(10),
    length$: signal(0),
    pageSizeOptions$: signal(PAGE.SIZE_OPTIONS),
    onPageChange: (evt) => {
      this.search(evt);
    },
  } as PaginatorHandler;

  @Input({
    required: false,
  })
  noteOwnerDto!: BehaviorSubject<NoteOwnerDto>;

  noteList$ = new MatTableDataSource<NoteModel>();

  displayedColumns = [
    "noteContent",
    "noteTime",
    ///
    "action",
  ];

  noteOwnerInfo$ = signal<string>("");

  /// methods
  constructor(private $noteService: NoteService) {
    super();
  }

  private calcNoteInfo(noteOwner: NoteOwnerDto) {
    let ownerInfo = "";

    if (noteOwner.deliveryAttemptId) {
      ownerInfo = `Delivery Attempt #${noteOwner.deliveryAttemptId}`;
    } else if (noteOwner.deliveryId) {
      ownerInfo = `Delivery #${noteOwner.deliveryId}`;
    } else if (noteOwner.importId) {
      ownerInfo = `Import #${noteOwner.importId}`;
    } else if (noteOwner.recipientId) {
      ownerInfo = `Recipient #${noteOwner.recipientId}`;
    } else if (noteOwner.warehouseItemId) {
      ownerInfo = `Warehouse Item #${noteOwner.warehouseItemId}`;
    }

    return ownerInfo;
  }

  protected override __ngOnInit__() {
    this.registerSubscription(
      this.noteOwnerDto.subscribe((noteOwner) => {
        if (noteOwner) {
          this.search(PAGE.DEFAULT_PAGE_EVENT);

          this.noteOwnerInfo$.set(this.calcNoteInfo(noteOwner));
        }
      })
    );
  }

  override search(pageEvt: PageEvent): void {
    this.registerSubscription(
      this.$noteService
        .search(
          this.noteOwnerDto.getValue(),
          pageEvt.pageIndex,
          pageEvt.pageSize
        )
        .subscribe((resultPage) => {
          console.log("Search Note: ", { resultPage });

          this.handlePageDataUpdate<NoteModel>(
            resultPage,
            this.paginator,
            this.noteTableDataSource$
          );
        })
    );
  }

  deleteNote(el: NoteModel) {
    const confirmToDelete = confirm("Confirm to delete this note?");

    if (confirmToDelete) {
      this.registerSubscription(
        this.$noteService
          .delete(el.noteId!)
          .subscribe((el) => this.search(PAGE.DEFAULT_PAGE_EVENT))
      );
    }
  }

  private openEditDialog(el?: NoteModel) {
    const dialogRef = this.$dialog.open<EditComponent>(EditComponent, {
      data: {
        noteModel: el,
      },
      width: `calc(${SIZE.DIALOG.width} * 0.9)`,
      maxHeight: SIZE.DIALOG.height,
    });

    this.registerSubscription(
      dialogRef.afterClosed().subscribe((_) => {
        this.search(PAGE.DEFAULT_PAGE_EVENT);
      })
    );
  }

  editNote(el: NoteModel) {
    this.openEditDialog(el);
  }

  addNote() {
    const defaultForm: NoteOwnerDto = this.noteOwnerDto.getValue();

    this.openEditDialog(defaultForm);
  }
}
