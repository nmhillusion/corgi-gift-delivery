import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class ErrorUtil {
  retrieErrorMessage(error: any): string {
    if (error.error && error.error.message) {
      return error.error.message;
    } else if (error.message) {
      return error.message;
    } else if (typeof error === "string") {
      return error;
    } else {
      console.error("Unknown error format:", error);
      return "An unknown error occurred: " + JSON.stringify(error);
    }
  }
}
