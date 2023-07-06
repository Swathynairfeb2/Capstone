package com.example.planahead_capstone;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.graphics.Typeface;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.BreakIterator;

public class InvitationActivity extends AppCompatActivity {

    private Button colorButton;
    private Button fontButton;
    private Button sendButton;
    private TextView eventNameTextView;
    private TextView locationTextView;
    private TextView dateTextView;
    private TextView timeTextView;
    private Button saveButton;
    private Button viewPdfButton;
    private TextView dateTimeTextView;
    UpcomingEvent event;
    RelativeLayout eventLayout;
    View viewLayout;
    int viewId;
    int eventId;
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;
    private TextView textView;
    private TextView messageTextView;
    private TextView titleTextView;
    private TextView contactInfoTextView;
    private TextView yourNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_invitation_page);

        colorButton = findViewById(R.id.colorButton);
        fontButton = findViewById(R.id.fontButton);
        saveButton = findViewById(R.id.saveButton);
        viewPdfButton = findViewById(R.id.viewPdfButton);
        eventLayout = findViewById(R.id.invitationLayout);
        viewLayout = findViewById(R.id.whiteBackground);
        titleTextView = findViewById(R.id.titleTextView);
        messageTextView = findViewById(R.id.messageTextView);
        eventNameTextView = findViewById(R.id.eventNameTextView);
        locationTextView = findViewById(R.id.locationTextView);
        dateTimeTextView = findViewById(R.id.dateTimeTextView);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        contactInfoTextView = findViewById(R.id.contactInfoTextView);
        yourNameTextView = findViewById(R.id.yourNameTextView);
        sendButton = findViewById(R.id.sendInvitationButton);
        // Find the background button by its ID
        Button backgroundButton = findViewById(R.id.backgroundButton);

        Intent intent = getIntent();
        if (intent != null) {
            event = intent.getParcelableExtra("event");
            eventId = Integer.valueOf(event.getEventId());

            String eventName = event.getEventName();
            String eventLocation = event.getEventLocation();
            String eventDate = event.getEventDate();
            String eventTime = event.getEventTime();

            eventNameTextView.setText(eventName);
            locationTextView.setText(eventLocation);
            dateTextView.setText(eventDate);
            timeTextView.setText(eventTime);

        }


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInvitation();
            }


        });

// Set an OnClickListener on the background button
        backgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(InvitationActivity.this);
                builder.setTitle("Select Background");

                // Create an array of background image resource IDs
                final int[] backgroundImages = {R.drawable.background_image1, R.drawable.background_image2, R.drawable.background_image3};

                // Create a custom layout for the dialog
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_background_selection, null);
                builder.setView(dialogView);

                // Get the ImageViews from the dialog layout
                ImageView image1 = dialogView.findViewById(R.id.imageView1);
                ImageView image2 = dialogView.findViewById(R.id.imageView2);
                ImageView image3 = dialogView.findViewById(R.id.imageView3);

                // Set the background images for the ImageViews
                image1.setImageResource(backgroundImages[0]);
                image2.setImageResource(backgroundImages[1]);
                image3.setImageResource(backgroundImages[2]);

                // Create and show the dialog
                final AlertDialog dialog = builder.create();
                dialog.show();

                // Set the click listeners for the ImageViews
                image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eventLayout.setBackgroundResource(backgroundImages[0]);
                        dialog.dismiss();
                    }
                });

                image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eventLayout.setBackgroundResource(backgroundImages[1]);
                        dialog.dismiss();
                    }
                });

                image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eventLayout.setBackgroundResource(backgroundImages[2]);
                        dialog.dismiss();
                    }
                });
            }
        });


        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open color palette or implement color selection logic
                openColorPalette();
            }
        });

        fontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open font options or implement font selection logic
                openFontOptions();
            }
        });
        viewPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the saved PDF for viewing
                viewPDF();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkWritePermission()) {
                    createPDF();
                } else {
                    requestWritePermission();
                }
            }
        });
    }

    private boolean checkWritePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestWritePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createPDF();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void openColorPalette() {
        // Create an array of colors
        final int[] colors = {Color.WHITE, Color.TRANSPARENT, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.LTGRAY};

        // Create a dialog to display the color palette
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Color");

        // Inflate a layout containing color buttons
        View colorPaletteView = getLayoutInflater().inflate(R.layout.dialog_color_palette, null);

        // Find the color buttons
        Button[] colorButtons = new Button[colors.length];
        colorButtons[0] = colorPaletteView.findViewById(R.id.colorButton1);
        colorButtons[1] = colorPaletteView.findViewById(R.id.colorButton2);
        colorButtons[2] = colorPaletteView.findViewById(R.id.colorButton3);
        colorButtons[3] = colorPaletteView.findViewById(R.id.colorButton4);
        colorButtons[4] = colorPaletteView.findViewById(R.id.colorButton5);
        colorButtons[5] = colorPaletteView.findViewById(R.id.colorButton6);

        // Set background colors and click listeners for color buttons
        for (int i = 0; i < colors.length; i++) {
            final int color = colors[i];
            colorButtons[i].setBackgroundColor(color);
            colorButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Update the background color of the invitation page
                    viewLayout.setBackgroundColor(color);
                }
            });
        }

        // Set the custom view for the dialog
        builder.setView(colorPaletteView);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openFontOptions() {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_font_options, null);
        builder.setView(dialogView);

        // Find the ListView in the dialog layout
        ListView fontListView = dialogView.findViewById(R.id.fontListView);

        // Create the adapter for the font names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getFontNames());

        // Set the adapter for the ListView
        fontListView.setAdapter(adapter);

        // Create the dialog
        final AlertDialog dialog = builder.create();

        // Set the click listener for the ListView items
        fontListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update the font of the text elements in the invitation page
                String selectedFontFileName = getFontNames()[position] + ".ttf";
                Typeface selectedTypeface = Typeface.createFromAsset(getAssets(), selectedFontFileName);
                updateFont(selectedTypeface);

                // Dismiss the dialog
                dialog.dismiss();
            }

        });

        // Show the dialog
        dialog.show();
    }


    private String[] getFontNames() {
        // Create an array of font file names
        return new String[]{"Agdasima-Regular", "Courgette-Regular", "DancingScript-VariableFont_wght", "IndieFlower-Regular"};
    }

    private void updateFont(Typeface typeface) {
        titleTextView.setTypeface(typeface);
        eventNameTextView.setTypeface(typeface);
        dateTimeTextView.setTypeface(typeface);
        dateTextView.setTypeface(typeface);
        timeTextView.setTypeface(typeface);
        locationTextView.setTypeface(typeface);
        messageTextView.setTypeface(typeface);
        yourNameTextView.setTypeface(typeface);
        contactInfoTextView.setTypeface(typeface);
    }


    private void createPDF() {

        // Create a new PDF document
        PdfDocument document = new PdfDocument();

        // Get the dimensions of the invitation layout
        int width = eventLayout.getWidth();
        int height = eventLayout.getHeight();

        // Create a page with the same dimensions as the invitation layout
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(width, height, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        // Create a canvas and draw the invitation layout onto the page
        Canvas canvas = page.getCanvas();
        eventLayout.draw(canvas);

        // Finish the page
        document.finishPage(page);

        // Generate a unique file name for the PDF
        String fileName = eventId + "_invitation.pdf";
        File directory = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(directory, fileName);

        try {
            // Save the PDF to the file
            FileOutputStream outputStream = new FileOutputStream(file);
            document.writeTo(outputStream);
            document.close();
            outputStream.close();

            Toast.makeText(this, "Invitation saved as PDF", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save invitation as PDF", Toast.LENGTH_SHORT).show();
        }
    }


    private void viewPDF() {
        // Generate the file path for the saved PDF
        String fileName = eventId + "_invitation.pdf";
        File directory = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(directory, fileName);

        // Check if the file exists
        if (file.exists()) {
            // Generate a content URI using FileProvider
            Uri contentUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);

            // Create an intent to open the PDF file
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(contentUri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Grant temporary permission to access the content URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                // Start the activity to view the PDF file
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Handle the case when a PDF viewer app is not installed
                Toast.makeText(this, "No PDF viewer app found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "PDF file not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTextView(String editedText) {
        if (viewId == R.id.titleTextView) {
            titleTextView.setText(editedText);
        } else if (viewId == R.id.messageTextView) {
            messageTextView.setText(editedText);
        } else if (viewId == R.id.contactInfoTextView) {
            contactInfoTextView.setText(editedText);
        } else if (viewId == R.id.yourNameTextView) {
            yourNameTextView.setText(editedText);
        }

    }

    public void showEditDialog(View view) {
        viewId = view.getId();
        String currentText = "";

        if (viewId == R.id.titleTextView) {
            currentText = titleTextView.getText().toString();
        } else if (viewId == R.id.messageTextView) {
            currentText = messageTextView.getText().toString();
        } else if (viewId == R.id.contactInfoTextView) {
            currentText = contactInfoTextView.getText().toString();
        } else if (viewId == R.id.yourNameTextView) {
            currentText = yourNameTextView.getText().toString();
        }

        EditDialogFragment dialogFragment = EditDialogFragment.newInstance(currentText);
        dialogFragment.show(getSupportFragmentManager(), "edit_dialog");
    }

    private void sendInvitation() {
        String fileName = eventId + "_invitation.pdf";
        File directory = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(directory, fileName);

        // Check if the file exists
        if (file.exists()) {
            if (event != null) {
                Intent intent = new Intent(this, SendInvitationActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "eventId is null", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            // Create an AlertDialog Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

// Set the title and message for the alert dialog
            builder.setTitle("Alert");
            builder.setMessage("Please save an invitation before proceeding");

// Set the positive button and its click listener
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

// Create the alert dialog
            AlertDialog alertDialog = builder.create();

// Show the alert dialog
            alertDialog.show();

        }
    }
}