import { Injectable, signal } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class LoadingMonitor {
  private loadingCount$ = signal(0);
  private loadingState$ = signal(false);
  private listeners: ((isLoading: boolean) => void)[] = [];

  startLoading() {
    this.loadingCount$.set(this.loadingCount$() + 1);
    this.updateLoadingState();
  }

  stopLoading() {
    if (this.loadingCount$() > 0) {
      this.loadingCount$.set(this.loadingCount$() - 1);
    }
    this.updateLoadingState();
  }

  isLoading(): boolean {
    return this.loadingCount$() > 0;
  }

  onLoadingStateChange(listener: (isLoading: boolean) => void) {
    this.listeners.push(listener);
    listener(this.isLoading());
  }

  private updateLoadingState() {
    const isLoading = this.isLoading();
    if (isLoading !== this.loadingState$()) {
      this.loadingState$.set(isLoading);
      console.log("Loading state updated:", isLoading);

      this.listeners.forEach((listener) => listener(isLoading));
    }
  }
}
