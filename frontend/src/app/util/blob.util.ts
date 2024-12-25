export class BlobUtil {
  static downloadBlob(blob: Blob, filename: string) {
    const url = window.URL.createObjectURL(blob);
    const aEl = document.createElement("a");
    aEl.href = url;
    aEl.download = filename;
    document.body.appendChild(aEl);
    aEl.click();
    aEl.remove();
  }
}
