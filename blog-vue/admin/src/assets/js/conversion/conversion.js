import * as imageConversion from "image-conversion";

export function imageCompressed(file) {
  return new Promise((resolve) => {
    if (file.size / 1024 < this.config.UPLOAD_SIZE) {
      resolve(file);
    }
    // 压缩到200KB
    imageConversion
      .compressAccurately(file, this.config.UPLOAD_SIZE)
      .then((res) => {
        resolve(res);
      });
  });
}
