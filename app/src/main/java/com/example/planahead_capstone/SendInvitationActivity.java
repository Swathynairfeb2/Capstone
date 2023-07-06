package com.example.planahead_capstone;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SendInvitationActivity extends AppCompatActivity {

    private LinearLayout guestListLayout;
    private EditText guestNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private CheckBox selectAllCheckBox;
    private Button sendButton;
    String guestName;
    String email;
    String phone;
    private String eventId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invitation);
        selectAllCheckBox = findViewById(R.id.selectAllCheckBox);
        sendButton = findViewById(R.id.sendButton);
        guestListLayout = findViewById(R.id.guestListLayout);
        guestNameEditText = findViewById(R.id.guestNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        Button addButton = findViewById(R.id.addButton);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            eventId = String.valueOf(intent.getIntExtra("eventId", 0));
        }

        loadGuestsFromDatabase();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestName = guestNameEditText.getText().toString().trim();
                email = emailEditText.getText().toString().trim();
                phone = phoneEditText.getText().toString().trim();

                // Validate guest name
                if (guestName.isEmpty()) {
                    guestNameEditText.setError("Guest name is required");
                    guestNameEditText.requestFocus();
                    return;
                }

                // Validate email
                if (email.isEmpty()) {
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                    return;
                } else if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid email");
                    emailEditText.requestFocus();
                    return;
                }

                // Validate phone number
                if (phone.isEmpty()) {
                    phoneEditText.setError("Phone number is required");
                    phoneEditText.requestFocus();
                    return;
                } else if (!isValidPhoneNumber(phone)) {
                    phoneEditText.setError("Invalid phone number");
                    phoneEditText.requestFocus();
                    return;
                }
                addGuestToGrid(guestName,email,phone);
                databaseHelper.addGuestToDatabase(guestName,email,phone,eventId);

            }
        });

        // Set click listener for the Select All checkbox
        selectAllCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = selectAllCheckBox.isChecked();
                // Iterate through all checkboxes in the guest list and set their state
                for (int i = 0; i < guestListLayout.getChildCount(); i++) {
                    View childView = guestListLayout.getChildAt(i);
                    if (childView instanceof TableRow) {
                        TableRow row = (TableRow) childView;
                        if (row.getChildCount() > 3) { // Assuming the checkbox is the fourth view in the row
                            CheckBox checkBox = (CheckBox) row.getChildAt(3);
                            checkBox.setChecked(isChecked);
                        }
                    }
                }
            }
        });

        // Set click listener for the Send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect the selected guests and send the invitation
                sendInvitationToSelectedGuests();
            }

        });
    }

    private void loadGuestsFromDatabase() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_PHONE,
                DatabaseHelper.COLUMN_EVID
        };

        String selection = DatabaseHelper.COLUMN_EVID + " = ?";
        String[] selectionArgs = {eventId};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_GUESTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int nameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int emailColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL);
            int phoneColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE);

            guestName = cursor.getString(nameColumnIndex);
            email = cursor.getString(emailColumnIndex);
            phone = cursor.getString(phoneColumnIndex);
            addGuestToGrid(guestName, email, phone);
        }

        cursor.close();
    }

    private void addGuestToGrid(String guestName, String email, String phone) {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        TextView nameTextView = new TextView(this);
        nameTextView.setText(guestName);
        nameTextView.setTextColor(getResources().getColor(R.color.white)); // Set the desired color
        nameTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        row.addView(nameTextView);

        TextView emailTextView = new TextView(this);
        emailTextView.setText(email);
        emailTextView.setTextColor(getResources().getColor(R.color.white)); // Set the desired color
        emailTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        row.addView(emailTextView);

        TextView phoneTextView = new TextView(this);
        phoneTextView.setText(phone);
        phoneTextView.setTextColor(getResources().getColor(R.color.white)); // Set the desired color
        phoneTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        row.addView(phoneTextView);

        CheckBox checkGuest = new CheckBox(this);
        int peachColor = getResources().getColor(R.color.peachbutton);
        checkGuest.setButtonTintList(ColorStateList.valueOf(peachColor));
        checkGuest.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        row.addView(checkGuest);

        guestListLayout.addView(row);

        // Clear the input fields
        guestNameEditText.setText("");
        emailEditText.setText("");
        phoneEditText.setText("");
    }



    private boolean isValidEmail(String email) {
        // Perform email validation using regular expressions
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Perform phone number validation using regular expressions
        // This example assumes that a valid phone number consists of 10 digits
        String phoneNumberPattern = "\\d{10}";
        return phoneNumber.matches(phoneNumberPattern);
    }
    private void sendInvitationToSelectedGuests() {
        if (guestListLayout.getChildCount() == 0) {
            // Display an alert that no guest is added
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please add guests before sending the invitation")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }
        else {
            StringBuilder selectedGuests = new StringBuilder();
            List<String> selectedEmails = new ArrayList<>();

            for (int i = 0; i < guestListLayout.getChildCount(); i++) {
                View childView = guestListLayout.getChildAt(i);
                if (childView instanceof TableRow) {
                    TableRow row = (TableRow) childView;
                    if (row.getChildCount() > 3) { // Assuming the checkbox is the fourth view in the row
                        CheckBox checkBox = (CheckBox) row.getChildAt(3);
                        if (checkBox.isChecked()) {
                            TextView nameTextView = (TextView) row.getChildAt(0);
                            TextView emailTextView = (TextView) row.getChildAt(1);

                            String guestName = nameTextView.getText().toString();
                            String guestEmail = emailTextView.getText().toString();

                            selectedGuests.append(guestName).append(", ");
                            selectedEmails.add(guestEmail);
                            // Set row color for guests who have already received the mail
                            row.setBackgroundColor(Color.RED);
                        }
                    }
                }
            }

            // Remove trailing comma and whitespace
            if (selectedGuests.length() > 2) {
                selectedGuests.setLength(selectedGuests.length() - 2);
            }

            // Send the invitation to selected guests
            String invitationMessage = "Dear guests,\nyou are invited to our event.\n\nWe look forward to seeing you there!";
            // Clear the input fields and deselect all checkboxes
            guestNameEditText.setText("");
            emailEditText.setText("");
            phoneEditText.setText("");
            selectAllCheckBox.setChecked(false);

            // Send email to selected guests
            for (String email : selectedEmails) {
                // Create an Intent to send the email
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("application/pdf");

                // Generate the file name with the invitation ID
                String fileName = eventId + "_invitation.pdf";

                // Attach the PDF file
                fileName = eventId + "_invitation.pdf";
                File directory = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                File file = new File(directory, fileName);
                if (file.exists()) {

                    // File pdfFile = new File(Environment.getExternalStorageDirectory(), fileName);
                    Uri fileUri = FileProvider.getUriForFile(this, "com.example.planahead_capstone.fileprovider", file);
                    emailIntent.putExtra(Intent.EXTRA_STREAM, fileUri);

                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Invitation");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, invitationMessage);

                    emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    // File does not exist, handle the case accordingly
                    Toast.makeText(this, "PDF file does not exist in the directory.", Toast.LENGTH_SHORT).show();
                }

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email"));
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(this, "No email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}