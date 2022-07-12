package com.caijinfu.utcool.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import androidx.annotation.NonNull;

/** 常用操作文件工具类 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 创建根缓存图片地址
     *
     * @return 图片地址
     */
    public static File createExternalStorageDirectoryPath() {
        String directoryPath = "";
        if (isSdCardAvailable()) {
            // /storage/emulated/0
            directoryPath = Environment.getExternalStorageDirectory().getPath();
        } else {
            // /data
            directoryPath = Environment.getDataDirectory().getPath();
        }
        File file = new File(directoryPath);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file;
            }
        }
        return null;
    }

    /**
     * SdCard是否存在
     *
     * @return true存在
     */
    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 创建外部文档目录
     *
     * @return 文档File
     */
    public static File createExternalDocumentsFile() {
        if (createExternalStorageDirectoryPath() != null) {
            File file = new File(createExternalStorageDirectoryPath(), Environment.DIRECTORY_DOCUMENTS);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    return file;
                }
            }
        }
        return null;
    }

    /**
     * 获取系统文档地址
     *
     * @return 系统文档File
     */
    public static File createSystemDocumentsFile() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file;
            }
        }
        return null;
    }

    /**
     * 通知android媒体库更新文件夹
     *
     * @param context
     *            上下文
     * @param filePath
     *            ilePath 文件绝对路径，、/sda/aaa/jjj.jpg
     * @return true：成功，false：失败
     */
    public static boolean scanFile(@NonNull Context context, String filePath) {
        try {
            MediaScannerConnection.scanFile(context, new String[] {filePath}, null, (path, uri) -> {
                Log.i("TAG", "Scanned: " + path + ", " + "uri: " + uri);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取系统下载地址
     *
     * @return 系统下载File
     */
    public static File createSystemDownloadFile() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file;
            }
        }
        return null;
    }

    /**
     * 创建外部图片文件
     *
     * @return 图片文件
     */
    public static File createExternalPictureFile() {
        if (createExternalStorageDirectoryPath() != null) {
            File file = new File(createExternalStorageDirectoryPath(), Environment.DIRECTORY_PICTURES);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    return file;
                }
            }
        }
        return null;
    }

    /**
     * 创建根缓存图片文件
     *
     * @param context
     *            上下文
     * @return 存储文件
     */
    public static File createPictureFile(@NonNull Context context) {
        File file;
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/files/Pictures
            file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            // /data/data/<application package>/files
            file = context.getFilesDir();
        }
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file;
            }
        }
        return null;
    }

    /**
     * 创建用户文档目录文件
     * 
     * @param context
     *            上下文
     * @return 下载文件
     */
    public static File createDocumentsFile(@NonNull Context context) {
        File file;
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/files/Download
            file = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        } else {
            // /data/data/<application package>/files
            file = context.getFilesDir();
        }
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file;
            }
        }
        return null;
    }

    /**
     * 创建根缓存下载目录文件
     * 
     * @param context
     *            上下文
     * @return 下载文件
     */
    public static File createDownLoadFile(Context context) {
        File file;
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/files/Download
            file = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        } else {
            // /data/data/<application package>/files
            file = context.getFilesDir();
        }
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file;
            }
        }
        return null;
    }

    /**
     * 创建根缓存音频文件目录
     * 
     * @param context
     *            上下文
     * @return 缓存文件
     */
    public static File createAudioFile(@NonNull Context context) {
        File cachedir;
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/files/Music
            cachedir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        } else {
            // /data/data/<application package>/files
            cachedir = context.getFilesDir();
        }
        if (!cachedir.exists()) {
            cachedir.mkdirs();
        }
        return cachedir;
    }

    /**
     * 创建文件夹
     *
     * @param dirPath
     *            目录路径
     * @return 成功：true，失败：false
     */
    public static boolean createDir(String dirPath) {
        try {
            File file = new File(dirPath);
            return file.mkdir();
        } catch (Exception e) {
            Log.e(TAG, "Exception：", e);
        }
        return false;
    }

    /**
     * 重命名文件名称，oldPath 和 newPath必须是新旧文件的绝对路径
     *
     * @param oldPath
     *            旧路径
     * @param newPath
     *            新路径
     * @return 成功：true，失败：false
     */
    public static boolean renameFile(String oldPath, String newPath) {
        if (TextUtils.isEmpty(oldPath)) {
            return false;
        }
        if (TextUtils.isEmpty(newPath)) {
            return false;
        }
        File file = new File(oldPath);
        return file.renameTo(new File(newPath));
    }

    /**
     * 创建新文件
     *
     * @param file
     *            需要创建的文件
     * @return 成功：true，失败：false
     */
    public static boolean createFile(File file) {
        try {
            return file.createNewFile();
        } catch (Exception e) {
            Log.e(TAG, "Exception：", e);
        }
        return false;
    }

    /**
     * 删除指定文件
     *
     * @param file
     *            待删除的文件
     * @return 成功：true，失败：false
     */
    public static boolean deleteSingleFile(File file) {
        if (file != null && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 获取图片缓存目录
     *
     * @param context
     *            上下文
     * @return 成功：true，失败：false
     */
    public static String getImageCachePath(@NonNull Context context) {
        File imgDir = new File(createCachePath(context), Environment.DIRECTORY_DOWNLOADS);
        File file = new File(imgDir.getAbsolutePath());
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file.getPath();
            }
        }
        return "";
    }

    /**
     * 创建根缓存目录
     *
     * @param context
     *            上下文
     * @return 目录地址
     */
    public static String createCachePath(@NonNull Context context) {
        String cacheRootPath;
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/cache
            cacheRootPath = context.getExternalCacheDir().getPath();
        } else {
            // /data/data/<application package>/cache
            cacheRootPath = context.getCacheDir().getPath();
        }
        File file = new File(cacheRootPath);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return cacheRootPath;
            }
        }
        return "";
    }

    /**
     * 获取音乐目录路径
     *
     * @param context
     *            上下文
     * @return true：成功，false：失败
     */
    public static String getMusicCachePath(@NonNull Context context) {
        File imgDir = new File(createCachePath(context), Environment.DIRECTORY_MUSIC);
        File file = new File(imgDir.getAbsolutePath());
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file.getPath();
            }
        }
        return "";
    }

    /**
     * 将内容写入文件
     *
     * @param filePath
     *            eg:/mnt/sdcard/demo.txt
     * @param content
     *            内容
     * @param isAppend
     *            是否往内容后面添加
     * @return true：成功，false：失败
     */
    public static boolean writeFileSdcard(String filePath, String content, boolean isAppend) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath, isAppend);
            byte[] bytes = content.getBytes();
            fos.write(bytes);
            fos.close();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Exception：", e);
        }
        return false;
    }

    /**
     * 打开Asset下的文件
     *
     * @param context
     *            上下文
     * @param fileName
     *            文件名
     * @return InputStream
     */
    public static InputStream openAssetFile(@NonNull Context context, String fileName) {
        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            is = am.open(fileName);
        } catch (IOException e) {
            Log.e(TAG, "Exception：", e);
        }
        return is;
    }

    /**
     * 读取assets文件内容字节数组
     *
     * @param context
     *            上下文
     * @param fileName
     *            文件名
     * @return byte[]
     */
    public static byte[] readAudioFile(@NonNull Context context, String fileName) {
        try {
            InputStream ins = context.getAssets().open(fileName);
            byte[] data = new byte[ins.available()];
            ins.read(data);
            ins.close();
            return data;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字节缓冲区按照固定大小进行分割成数组
     *
     * @param buffer
     *            缓冲区
     * @param length
     *            缓冲区大小
     * @param spSize
     *            切割块大小
     * @return ArrayList<byte [ ]>
     */
    public static ArrayList<byte[]> splitBuffer(byte[] buffer, int length, int spSize) {
        ArrayList<byte[]> array = new ArrayList<>();
        if (spSize <= 0 || length <= 0 || buffer == null || buffer.length < length) {
            return array;
        }
        int size = 0;
        while (size < length) {
            int left = length - size;
            if (spSize < left) {
                byte[] sdata = new byte[spSize];
                System.arraycopy(buffer, size, sdata, 0, spSize);
                array.add(sdata);
                size += spSize;
            } else {
                byte[] sdata = new byte[left];
                System.arraycopy(buffer, size, sdata, 0, left);
                array.add(sdata);
                size += left;
            }
        }
        return array;
    }

    /**
     * 获取Raw下的文件内容
     *
     * @param context
     *            上下文
     * @return 文件内容
     */
    public static String getFileFromRaw(@NonNull Context context, int resId) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            Log.e(TAG, "Exception：", e);
        }
        return "";
    }

    /**
     * 文件拷贝
     *
     * @param src
     *            源文件
     * @param desc
     *            目的文件
     * @return true：成功，false：失败
     */
    public static boolean fileChannelCopy(File src, File desc) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        try {
            fi = new FileInputStream(src);
            fo = new FileOutputStream(desc);
            // 得到对应的文件通道
            FileChannel in = fi.getChannel();
            // 得到对应的文件通道
            FileChannel out = fo.getChannel();
            // 连接两个通道，并且从in通道读取，然后写入out通道
            in.transferTo(0, in.size(), out);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "IOException：", e);
        } finally {
            try {
                if (fo != null) {
                    fo.close();
                }
                if (fi != null) {
                    fi.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException：", e);
            }
        }
        return false;
    }

    /**
     * 转换文件大小
     *
     * @param fileLen
     *            单位B
     * @return 文件大小
     */
    public static String formatFileSizeToString(long fileLen) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileLen < 1024) {
            fileSizeString = df.format((double)fileLen) + "B";
        } else if (fileLen < 1048576) {
            fileSizeString = df.format((double)fileLen / 1024) + "K";
        } else if (fileLen < 1073741824) {
            fileSizeString = df.format((double)fileLen / 1048576) + "M";
        } else {
            fileSizeString = df.format((double)fileLen / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取文件的大小
     * 
     * @param file
     *            文件
     * @return 文件大小
     */
    public static String getFileSize(File file) {
        String size = "";
        if (file.exists() && file.isFile()) {
            long fileS = file.length();
            DecimalFormat df = new DecimalFormat("#.00");
            if (fileS < 1024) {
                size = df.format((double)fileS) + "BT";
            } else if (fileS < 1048576) {
                size = df.format((double)fileS / 1024) + "KB";
            } else if (fileS < 1073741824) {
                size = df.format((double)fileS / 1048576) + "MB";
            } else {
                size = df.format((double)fileS / 1073741824) + "GB";
            }
        } else if (file.exists() && file.isDirectory()) {
            size = "";
        } else {
            size = "0BT";
        }
        return size;
    }

    /**
     * 删除文件
     * 
     * @param file
     *            文件
     * @return true：成功，false：失败
     */
    public static boolean deleteFile(File file) {
        return deleteFileOrDirectory(file);
    }

    /**
     * 删除指定文件，如果是文件夹，则递归删除
     * 
     * @param file
     *            文件
     * @return true：成功，false：失败
     */
    public static boolean deleteFileOrDirectory(File file) {
        try {
            if (file != null && file.isFile()) {
                return file.exists() && file.delete();
            }
            if (file != null && file.isDirectory()) {
                File[] childFiles = file.listFiles();
                // 删除空文件夹
                if (childFiles == null || childFiles.length == 0) {
                    return file.delete();
                }
                // 递归删除文件夹下的子文件
                for (int i = 0; i < childFiles.length; i++) {
                    deleteFileOrDirectory(childFiles[i]);
                }
                return file.delete();
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception：", e);
        }
        return false;
    }

    /**
     * 获取文件扩展名
     *
     * @param filename
     *            文件名
     * @return 扩展名
     */
    public static String getFileType(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     *
     * @param fileName
     *            带扩展名的文件名
     * @return 不带扩展名的文件名
     */
    public static String getFileNameNoEx(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length()))) {
                return fileName.substring(0, dot);
            }
        }
        return "";
    }

    /**
     * 是否是图片类型
     *
     * @param fileName
     *            文件名 或者文件的类型 或者文件的路径
     * @return true：是图片类型，false：不是图片类型
     */
    public static boolean isPictureType(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return false;
        }
        String toLowerCase = fileName.toLowerCase();
        return toLowerCase.endsWith("png") || toLowerCase.endsWith("jpg") || toLowerCase.endsWith("jpeg");
    }

    /**
     * 获取文件内容
     *
     * @param path
     *            文件路径
     * @return 文件内容
     */
    public static String getFileOutputString(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path), 8192);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append("\n").append(line);
            }
            bufferedReader.close();
            return sb.toString();
        } catch (IOException e) {
            Log.e(TAG, "IOException：", e);
        }
        return "";
    }

    /**
     * 根据Uri获取文件的绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param context
     *            上下文
     * @param fileUri
     *            文件uri
     * @return 文件的绝对路径
     */
    public static String getFileAbsolutePath(@NonNull Context context, Uri fileUri) {
        Log.d("SelectFile", "fileUri: " + fileUri.toString());
        return getRealPathFromUri(context, fileUri);
    }

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context
     *            上下文对象
     * @param uri
     *            图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(@NonNull Context context, Uri uri) {
        Log.d("RealPathFromUriUtils", "uri:" + uri.toString());
        int sdkVersion = Build.VERSION.SDK_INT;
        // api >= 19
        if (sdkVersion >= 19) {
            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19
            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context
     *            上下文对象
     * @param uri
     *            图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(@NonNull Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            // MediaProvider
            if (isMediaDocument(uri)) {
                // 使用':'分割
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                final String[] split = documentId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                filePath = getDataColumn(context, contentUri, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) {
                try {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.parseLong(documentId));
                    filePath = getDataColumn(context, contentUri, null, null);
                } catch (NumberFormatException e) {
                    Log.e(TAG, "文件选择转换documentId问题：", e);
                    filePath = "";
                }
            }
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri
            if (isQQMediaDocument(uri)) {
                String path = uri.getPath();
                File fileDir = Environment.getExternalStorageDirectory();
                File file = new File(fileDir, path.substring("/QQBrowser".length()));
                return file.exists() ? file.toString() : null;
            }
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            filePath = getDataColumn(context, uri, null, null);
        } else if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        // 如果都获取不到，则用最后一种方式获取
        if (TextUtils.isEmpty(filePath)) {
            String fileNameByUri = getFileNameByUri(context, uri);
            filePath = copyUriToExternalFilesDir(context, uri, fileNameByUri);
        }
        return filePath;
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context
     *            上下文对象
     * @param uri
     *            图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(@NonNull Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /** 获取数据库表中的 _data 列，即返回Uri对应的文件路径 */
    private static String getDataColumn(@NonNull Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[] {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                if (columnIndex > -1) {
                    path = cursor.getString(columnIndex);
                }
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
            Log.e(TAG, "IOException：", e);
        }
        return path;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /** 使用第三方qq文件管理器打开 */
    public static boolean isQQMediaDocument(Uri uri) {
        return "com.tencent.mtt.fileprovider".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /** 根据Uri获取文件的名称 */
    public static String getFileNameByUri(@NonNull Context context, Uri uri) {
        String fileName = "" + System.currentTimeMillis();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME));
            cursor.close();
        }
        return fileName;
    }

    /** 将Uri中的内容复制到getExternalFilesDir的临时文件夹中，获取文件的绝对路径，解决所有版本Uri转换 */
    public static String copyUriToExternalFilesDir(@NonNull Context context, Uri uri, String fileName) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            File tempDir = context.getExternalFilesDir("temp");
            if (inputStream != null && tempDir != null) {
                File file = new File(tempDir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                byte[] byteArray = new byte[1024];
                int bytes = bis.read(byteArray);
                while (bytes > 0) {
                    bos.write(byteArray, 0, bytes);
                    bos.flush();
                    bytes = bis.read(byteArray);
                }
                bos.close();
                fos.close();
                return file.getAbsolutePath();
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException：", e);
        }
        return null;
    }

    public static String formatFileSizeToKB(String fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        long size = Long.parseLong(fileSize);
        return df.format((double)size / 1024) + "KB";
    }

    public static String formatFileSize(@NonNull Context context, long sizeBytes) {
        return Formatter.formatFileSize(context, sizeBytes);
    }
}
