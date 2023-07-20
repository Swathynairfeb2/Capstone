
package com.example.planahead_capstone;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UserAccountSettings extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, phoneEditText;
    private Button saveButton, logoutButton;
    ImageView imgChangePassword,profileImageView;
    private static final int REQUEST_IMAGE_PICK = 1;
    private SwitchCompat darkModeSwitch;
    private CustomTheme app;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountsettings);

        // Initialize views
        profileImageView = findViewById(R.id.profileImage);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        saveButton = findViewById(R.id.saveButton);
        logoutButton = findViewById(R.id.logoutButton);
        imgChangePassword = findViewById(R.id.imgChangePassword);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);

        // Get a reference to the custom Application class
        app = (CustomTheme) getApplication();

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChangeOptionsDialog();
            }
        });


        // Set a listener to update the theme when the switch state changes
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the dark mode state and apply the new theme
                app.setDarkModeEnabled(isChecked);
               //recreate(); // Recreate the activity to apply the theme changes
            }
        });


        // Set a click listener on the change password ImageView to handle the click event
        imgChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the change password dialog
                showChangePasswordDialog();
            }
        });
        // Set onClickListener for the logoutButton
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccountSettings.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void showChangePasswordDialog() {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_change_password, null);
        builder.setView(dialogView);

        // Add action buttons to the dialog
        builder.setPositiveButton("Change", null); // We'll add the click listener later
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "Cancel" button click here (dismiss the dialog)
                dialog.dismiss();
            }
        });

        // Create the dialog
        final AlertDialog dialog = builder.create();

        // Show the dialog
        dialog.show();

        // Set the background color of the dialog window
        int backgroundColor = getResources().getColor(R.color.purpleb);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(backgroundColor));

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.peachbutton));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.peachbutton));

        // Get the positive button from the dialog
        final Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the "Change" button click here
                // Get references to the EditText fields in the custom dialog layout
                EditText oldPasswordEditText = dialogView.findViewById(R.id.oldPasswordEditText);
                EditText newPasswordEditText = dialogView.findViewById(R.id.newPasswordEditText);
                EditText confirmPasswordEditText = dialogView.findViewById(R.id.confirmPasswordEditText);


                // You can get the new password and confirm password from the EditText views in the dialog
                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                if (oldPassword.isEmpty()) {
                    oldPasswordEditText.setError("Old Password cannot be empty");
                    oldPasswordEditText.requestFocus();
                    return;
                }
              else if (newPassword.isEmpty()) {
                    newPasswordEditText.setError("New Password cannot be empty");
                    newPasswordEditText.requestFocus();
                    return;
                } else if (confirmPassword.isEmpty()) {
                    confirmPasswordEditText.setError("Confirm Password cannot be empty");
                    confirmPasswordEditText.requestFocus();
                    return;
                }

                // Add your password change logic here
                // For example, check if the passwords match and meet your criteria
                if (newPassword.equals(confirmPassword)) {
                    dialog.dismiss();


                    // Perform any other action after password change here
                } else {
                    confirmPasswordEditText.setError("Passwords do not match");
                    confirmPasswordEditText.requestFocus();
                    return;
                }
            }
        });
    }
    private void showImageChangeOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_image_change_options, null);
        builder.setView(dialogView);

        TextView galleryOptionTextView = dialogView.findViewById(R.id.galleryOptionTextView);

        final AlertDialog dialog = builder.create();
        int backgroundColor = getResources().getColor(R.color.purpleb);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(backgroundColor));


        // Handle gallery option click
        galleryOptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageGallery();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void openImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // Image selected from gallery successfully
            // Process the selected image (e.g., display it in an ImageView)
            Uri selectedImageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);

                // Rotate the image if necessary
                int rotation = getRotationFromExif(selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap = rotateImage(bitmap, rotation);

                profileImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show();

        }
    }

    private int getRotationFromExif(Uri imageUri) {
        int rotation = 0;
        try {
            ExifInterface exif = new ExifInterface(imageUri.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotation;
    }

    private int getRotationFromExif(String imagePath) {
        int rotation = 0;
        try {
            ExifInterface exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotation;
    }

    private Bitmap rotateImage(Bitmap bitmap, int rotation) {
        if (rotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

}
