package com.example.genesis_trainer_device_improved.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ImageHelper {
    private static final String TAG = "ImageHelper";
    private static final String USER_IMAGE_FILE_PROVIDER = "com.example.genesis_trainer_device_improved.fileprovider";

    public ImageHelper() {

    }

    public File getUserImageDirectory(Context context) {
        File fileUsersPhotos = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "user");
        if (!fileUsersPhotos.exists()) {
            boolean created = fileUsersPhotos.mkdirs();
            Log.d(TAG, "createDirectories: fileUserPhotos image created = " + created);
        }
        return fileUsersPhotos;
    }

    public boolean deleteDirAndItsContent(File dir) {
        boolean deleted = false;
        if (dir.exists()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File f : children) {
                    Log.d(TAG, "deleteDirAndItsContent: deleting image name = " + f.getName());
                    boolean isDeleted = f.delete();
                    Log.d(TAG, "deleteDirAndItsContent: isdeleted = " + isDeleted + " " + f.getAbsolutePath());
                }
            }

            deleted = dir.delete();
            Log.d(TAG, "deleteDirAndItsContent: fileUsersPhotos image deleted = " + deleted + " " + dir.getName());
        }
        return deleted;
    }

    public File getTrainerImageDirectory(Context context) {
        File fileTrainersPhotos = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "trainer");
        if (!fileTrainersPhotos.exists() && !fileTrainersPhotos.mkdirs()) {
            boolean created = fileTrainersPhotos.mkdirs();
            Log.d(TAG, "createDirectories: fileTrainersPhotos image created = " + created);
        }
        return fileTrainersPhotos;
    }

    public File getClientImageDirectory(Context context) {
        File fileClientsPhotos = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "client");
        if (!fileClientsPhotos.exists()) {
            boolean created = fileClientsPhotos.mkdirs();
            Log.d(TAG, "createDirectories: fileClientsPhotos image created = " + created);
        }
        return fileClientsPhotos;
    }


    public File createImageFile(Bitmap b, String imageFileName, String typeSufix, File file) {
        Log.d(TAG, "createImageFile: == image creation oprating thread == " + Thread.currentThread().getName());
        try {
            File created = new File(file.getPath(), imageFileName + typeSufix);
            boolean c = false;
            if (!created.getParentFile().exists())   created.getParentFile().mkdirs();

            if (!created.exists()) c = created.createNewFile();

            Log.d(TAG, "createImageFile: created.getAbsolutePath()= " + created.getAbsolutePath() + " created= " + c);

            FileOutputStream fileOutputStream = new FileOutputStream(created, false);
            b.compress(Bitmap.CompressFormat.PNG, 40, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            return created;
        } catch (Exception e) {
            Log.d(TAG, "createImageFile: image cause of error msg = " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public File loadIfImageExists(File file, String name) {
        File[] allFiles = file.listFiles();
        Log.d(TAG, "loadIfImageExists: listing image files");
        if (allFiles != null) {
            Log.d(TAG, "loadIfImageExists: checking content of image files " + allFiles.length + " n=> " + file.getName());
            for (File f : allFiles) {
                Log.d(TAG, "loadIfImageExists: checking if image name exists=> " + f.getName());
                if (f.getName().contains(name)) {
                    Log.d(TAG, "loadIfImageExists: equals image = " + f.getAbsolutePath());
                    return f;
                }
                Log.d(TAG, "loadIfImageExists: === FILES IN DIRECTORY " + f.getAbsolutePath());
            }
        }
        Log.d(TAG, "loadIfImageExists: image not found returning null");
        return null;
    }


    public Bitmap getBitmapFromFile(File file) {
        return BitmapFactory.decodeFile(file.getPath());
    }


    public String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    public Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }



}