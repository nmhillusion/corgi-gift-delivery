import { Component, Input } from "@angular/core";
import { AppCommonModule } from "@app/core/app-common.module";
import { BasePage } from "@app/pages/base.page";
import { BehaviorSubject, map, Observable } from "rxjs";

@Component({
  standalone: true,
  selector: "app-input-file",
  templateUrl: "./input-file.component.html",
  styleUrls: ["./input-file.component.scss"],
  imports: [AppCommonModule],
})
export class AppInputFileComponent extends BasePage {
  @Input({
    required: true,
    alias: "inputFile",
  })
  inputFile$!: BehaviorSubject<File[]>;

  inputFileDisplayText$: Observable<string> | null = null;

  @Input({
    required: false,
    alias: "multiple",
  })
  multiple: boolean = false;

  @Input({
    required: false,
    alias: "title",
  })
  title = "File Input";

  /// Methods

  constructor() {
    super("");
  }

  protected override __ngOnInit__() {
    this.inputFileDisplayText$ = this.inputFile$.pipe(
      map((fileList) => fileList.map((it) => it.name).join(";"))
    );
  }

  onChangeFile($event: any) {
    const target = $event.target as HTMLInputElement;
    const nativeFileList = target.files;

    if (!nativeFileList) {
      throw new Error("Empty FileList");
    }

    if (1 > (nativeFileList?.length || 0)) {
      return;
    }

    if (!this.multiple) {
      const firstItem = nativeFileList?.item(0);

      if (!firstItem) {
        throw new Error("First item of FileList is empty");
      }

      this.inputFile$.next([firstItem]);

      return;
    }

    {
      const outFileList = [];

      for (let idx = 0; idx < nativeFileList.length; ++idx) {
        const currentItem = nativeFileList.item(idx);

        if (!currentItem) {
          throw new Error(`Current item[${idx}] is empty`);
        }

        outFileList.push(currentItem);
      }

      this.inputFile$.next(outFileList);
      return;
    }
  }
}
