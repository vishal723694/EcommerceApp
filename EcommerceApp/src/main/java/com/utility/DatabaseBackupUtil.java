package com.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Utility for automated database snapshot backup and restore operations for SQLite database.
 */
public class DatabaseBackupUtil {

    /**
     * Creates a timestamped backup copy of the SQLite database file.
     *
     * @param sourceDbPath Path to mydatabase.db
     * @param backupDir Directory to save backup snapshot
     * @return Path to created backup file, or null if failed
     */
    public static String createDatabaseSnapshot(String sourceDbPath, String backupDir) {
        try {
            File sourceFile = new File(sourceDbPath);
            if (!sourceFile.exists()) {
                return null;
            }

            File dir = new File(backupDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String timestamp = DateUtil.getCurrentDateTimeFormatted().replaceAll("[^0-9]", "_");
            File destFile = new File(dir, "mydatabase_backup_" + timestamp + ".db");

            FileChannel sourceChannel = new FileInputStream(sourceFile).getChannel();
            FileChannel destChannel = new FileOutputStream(destFile).getChannel();

            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

            sourceChannel.close();
            destChannel.close();

            return destFile.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
