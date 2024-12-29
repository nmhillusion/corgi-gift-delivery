import { Component, Input, WritableSignal } from "@angular/core";
import { LogModel } from "@app/model/core/log.model";
import { Nullable } from "@app/model/core/nullable.model";
import { BasePage } from "@app/pages/base.page";

@Component({
  selector: "app-inline-log-message",
  templateUrl: "./inline-log-message.component.html",
  styleUrls: ["./inline-log-message.component.scss"],
  imports: [],
})
export class AppInlineLogMessage extends BasePage {
  @Input({
    required: true,
    alias: "logMessage",
  })
  logMessage$!: WritableSignal<Nullable<LogModel>>;

  /// Methods

  constructor() {
    super("");
  }
}
