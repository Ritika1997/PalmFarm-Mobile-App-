package com.example.palmfarm;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import activity.Login;
import helper.SQLiteHandler;
import helper.SessionManager;

import static com.example.palmfarm.R.id.imageCam;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SessionManager session;
    private SQLiteHandler db;
    /*private View navHeader;
    private EditText inputName;
    private EditText inputEmail;*/
    private ImageView imageView;
    private File f;
    private static final int CAMERA_REQUEST = 1888;
    private static int RESULT_LOAD_IMAGE = 1;
    private String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                String[] TO = {"ritika.thakur2016@vitstudent.ac.in"};
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PalmFarm Message");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Toast.makeText(HomeScreen.this, "Sending...", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(HomeScreen.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        imageView = findViewById(imageCam);
        /*navHeader = navigationView.getHeaderView(0);
        inputName = findViewById(R.id.contactName);
        inputEmail = findViewById(R.id.contactEmail);
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        txtName.setText(name);
        txtWebsite.setText(email);*/
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        TextView txtName = findViewById(R.id.home_name);
        TextView txtEmail = findViewById(R.id.home_email);
        HashMap<String, String> user = db.getUserDetails();

        Log.i("NAME", user.get("name"));
        Log.i("EMAIL", user.get("email"));
//        String name = user.get("name");
//        String email = user.get("email");
        // Displaying the user details on the screen

//        txtName.setText(name);
//        txtEmail.setText(email);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(HomeScreen.this, Settings.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the action
        }
        else if (id == R.id.nav_farmer) {
            startActivity(new Intent(HomeScreen.this, Farmer.class));
            finish();
        }
        else if (id == R.id.nav_camera) {
            selectImage();
        } else if (id == R.id.nav_about_us) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
            builder.setTitle("ABOUT US");
            builder.setMessage("This is a user friendly app which will help you to make better decisions for your farm and " +
                    "will help you solve your issues with minimum efforts. So get started!!");
            builder.setNegativeButton("OK",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (id == R.id.nav_contact_us) {
            startActivity(new Intent(HomeScreen.this, ContactUs.class));
            finish();
        }
        else if(id==R.id.nav_faq){
            startActivity(new Intent(HomeScreen.this, faq.class));
            finish();
        }
        else if(id == R.id.logout){
            logoutUser();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void logoutUser() {
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
        builder.setTitle("LOGOUT");
        builder.setMessage("Are you sure you wanna logout?");
                builder.setPositiveButton("Logout",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                session.setLogin(false);
                                db.deleteUsers();
                                Intent intent = new Intent(HomeScreen.this, Login.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(HomeScreen.this,"You have been logged out.",Toast.LENGTH_LONG).show();
                            }
                        });

        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    try {
                        f = createImageFile();
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    public File getAlbumDir()
    {

        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                "PalmFarm/"
        );
        // Create directories if needed
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        return storageDir;
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname= getAlbumDir().toString() + "/image-"+ n +".jpg";
        //String imageFileName =getAlbumDir().toString() +"/image.jpg";
        File image= new File(fname);
        return image;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            Bitmap photo = BitmapFactory.decodeFile(f.getAbsolutePath());
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

            Intent i = new Intent(Intent.ACTION_SEND);
            Uri uri = Uri.fromFile(f);
            i.putExtra(Intent.EXTRA_STREAM, uri);
            try {
            i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ritika.thakur2016@vitstudent.ac.in"});
            i.putExtra(Intent.EXTRA_SUBJECT, "PalmFarm Farm photo");
            i.putExtra(Intent.EXTRA_TEXT   , "body of email");
            startActivity(Intent.createChooser(i,"Send email via:"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(HomeScreen.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            sendImage();

        }
    }
    public void sendImage() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+picturePath));
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"ritika.thakur2016@vitstudent.ac.in"});
        i.putExtra(Intent.EXTRA_SUBJECT, "PalmFarm Farm Image");
        i.putExtra(Intent.EXTRA_TEXT, "Issue: ");
        startActivity(Intent.createChooser(i,"Send email via:"));
    }
}
