export interface LogModel {
  logType: "error" | "warning" | "info";
  message: string;
}
