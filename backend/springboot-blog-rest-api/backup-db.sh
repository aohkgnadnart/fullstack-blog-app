#!/bin/bash

# Thông tin kết nối database
MYSQL_USER="root"
MYSQL_PASSWORD="P@ssw0rd"
DATABASE="myblog_v2"
MYSQL_PORT="3307"
BACKUP_PATH="init.sql"

# Display start message
echo "Starting database backup..."

# Thực hiện backup
if mysqldump -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" -P"$MYSQL_PORT" "$DATABASE" > "$BACKUP_PATH"; then
    echo "Backup successful! File saved at: $BACKUP_PATH"
else
    echo "An error occurred during the backup process!"
    exit 1
fi 