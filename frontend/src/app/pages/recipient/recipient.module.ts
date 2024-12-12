import { NgModule } from "@angular/core";
import { RecipientRoutingModule } from "./recipient.routes";

@NgModule({
  imports: [RecipientRoutingModule],
  exports: [RecipientRoutingModule],
})
export class RecipientModule {}
