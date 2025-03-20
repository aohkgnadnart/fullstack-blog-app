#!/bin/bash
# 1. Chuyển sang nhánh backup_db và kiểm tra
echo "Chuyển sang nhánh backup_db..."
if ! git checkout backup_db; then
  echo "Lỗi: Không thể chuyển sang nhánh backup_db. Dừng script."
  exit 1
fi

# 2. Kiểm tra thư mục chứa script và file backup-db.sh
TARGET_DIR="backend/springboot-blog-rest-api"
if [ ! -d "$TARGET_DIR" ]; then
  echo "Lỗi: Không tìm thấy thư mục $TARGET_DIR. Dừng script."
  exit 1
fi

cd "$TARGET_DIR"

if [ ! -f "backup-db.sh" ]; then
  echo "Lỗi: Không tìm thấy file backup-db.sh trong $TARGET_DIR. Dừng script."
  exit 1
fi

# 3. Chạy backup-db.sh
echo "Đang chạy backup-db.sh..."
if [ -x "./backup-db.sh" ]; then
  ./backup-db.sh
else
  echo "File backup-db.sh không có quyền thực thi, chạy bằng bash..."
  bash ./backup-db.sh
fi

# 4. Kiểm tra file init.sql
if [ ! -f "init.sql" ]; then
  echo "Lỗi: Không tìm thấy file init.sql trong $TARGET_DIR. Dừng script."
  exit 1
fi

# Nếu init.sql không có thay đổi, dừng script
if git diff --quiet init.sql; then
  echo "Không có thay đổi nào trong file init.sql. Dừng script."
  exit 0
fi

# Quay lại thư mục gốc chứa repo
cd - > /dev/null

# 5. Thêm file init.sql vào staging area
echo "Thêm file init.sql vào git..."
git add "$TARGET_DIR/init.sql"

# Kiểm tra xem có thay đổi nào được staged không
if git diff-index --quiet HEAD --; then
  echo "Không có thay đổi nào để commit. Dừng script."
  exit 0
fi

# 6. Commit và đẩy thay đổi
echo "Commit thay đổi với thông điệp 'backup db'..."
git commit -m "backup db"

echo "Đang đẩy thay đổi lên nhánh backup_db..."
git push origin backup_db

echo "Backup hoàn tất thành công."

