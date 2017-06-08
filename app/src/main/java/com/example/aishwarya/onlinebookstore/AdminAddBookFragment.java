package com.example.aishwarya.onlinebookstore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by aishwarya on 03-Apr-17.
 */

public class AdminAddBookFragment extends Fragment implements View.OnClickListener{

    EditText book_name,author_name,publication,description,book_price;
    TextView pdf,cover;
    Button browse_pdf,browse_pdf_cover,add_book;
    Spinner category;
    private static final String TAG = AdminHomeActivity.class.getSimpleName();
    ProgressDialog dialog;
    String FILES_TO_UPLOAD = "upload";
    public static Thread th2;
    String BookName,AuthorName,Publication,Description,Category,BookPrice;
    private static final String TAG1 = "FileSelection";
    ArrayList<String> files_paths;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.add_book_fragment, container, false);
        AdminHomeActivity.title.setText("Book Store");

        book_name = (EditText)mainView.findViewById(R.id.xBookNameEditText);
        author_name = (EditText)mainView.findViewById(R.id.xAuthorNameEditText);
        publication = (EditText)mainView.findViewById(R.id.xPublicationEditText);
        description = (EditText)mainView.findViewById(R.id.xBookDescriptionEditText);
        book_price = (EditText)mainView.findViewById(R.id.xBookPriceEditText);
        browse_pdf = (Button)mainView.findViewById(R.id.xBrowsePdf);
        browse_pdf_cover = (Button)mainView.findViewById(R.id.xBrowsePdfCover);
        add_book = (Button)mainView.findViewById(R.id.xAddBook);
        category = (Spinner)mainView.findViewById(R.id.xCategorySpinner);
        pdf = (TextView)mainView.findViewById(R.id.xPdfTextView);
        cover = (TextView)mainView.findViewById(R.id.xPdfCoverTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, AdminCategoriesFragment.category);
        category.setAdapter(adapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();

                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                Category = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        files_paths = new ArrayList<String>();

        browse_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        browse_pdf_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser1();
            }
        });
        add_book.setOnClickListener(this);

        return mainView;
    }

    private static final int FILE_SELECT_CODE = 0;
    private static final int FILE_SELECT_CODE1 = 1;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showFileChooser1() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE1);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v== add_book){

            BookName = book_name.getText().toString();
            AuthorName = author_name.getText().toString();
            Publication = publication.getText().toString();
            Description = description.getText().toString();
            BookPrice = book_price.getText().toString();
            //on upload button Click
            if(files_paths != null && !Description.equalsIgnoreCase("") && !AuthorName.equalsIgnoreCase("") &&
            !Publication.equalsIgnoreCase("") && !BookName.equalsIgnoreCase("") && !BookPrice.equalsIgnoreCase("")){
                dialog = ProgressDialog.show(getActivity(),"","Uploading File...",true);

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //creating new thread to handle Http Operations
                        Log.d(TAG, "upload button clicked.");
                        uploadFile(files_paths);
                    }
                });
                th.start();
            }else{
                Toast.makeText(getActivity(),"Please enter all fields.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    File myFile = new File(uri.getPath());
                    path = myFile.getAbsolutePath();
                    Log.d(TAG, "File Path: " + path);
                    files_paths.add(path);
                    pdf.setText("PDF : "+files_paths.get(files_paths.size()-1));
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
            case FILE_SELECT_CODE1:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    File myFile = new File(uri.getPath());
                    path = myFile.getAbsolutePath();
                    Log.d(TAG, "File Path: " + path);
                    files_paths.add(path);
                    cover.setText("PDF Cover : "+files_paths.get(files_paths.size()-1));
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void uploadFile(ArrayList<String> imgPaths) {

        String charset = "UTF-8";

        File sourceFile[] = new File[imgPaths.size()];
        for (int i = 0; i < imgPaths.size(); i++) {
            sourceFile[i] = new File(imgPaths.get(i));
        }

        String requestURL = "http://lambelltech.com/OnlineBookStore/upload.php";

        try {
            FileUploader multipart = new FileUploader(requestURL, charset);

            multipart.addHeaderField("Test-Header", "Header-Value");

            multipart.addFormField("book_name", BookName);
            multipart.addFormField("author_name", AuthorName);
            multipart.addFormField("publication", Publication);
            multipart.addFormField("description", Description);
            multipart.addFormField("category", Category);
            multipart.addFormField("price", BookPrice);

            for (int i = 0; i < imgPaths.size(); i++) {
                multipart.addFilePart("uploaded_file[]", sourceFile[i]);
            }

            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");

            try {
                th2.join();
                if (!th2.isAlive()) {
                    dialog.dismiss();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.xUserFrame, new AdminCategoriesFragment()).commit();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
