package com.example.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class LoginActivity extends Activity
{
    // Lien vers votre page php sur votre serveur
    private static final String	UPDATE_URL	= "file:///C:/Users/Ouzzine/AndroidStudioProjects/ANDROID/script.php";

    public ProgressDialog				progressDialog;

    private EditText						UserEditText;

    private EditText						PassEditText;

    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialisation d'une progress bar
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        // Récupération des éléments de la vue définis dans le xml
        UserEditText = (EditText) findViewById(R.id.username);

        PassEditText = (EditText) findViewById(R.id.password);
        Button button = (Button) findViewById(R.id.okbutton);

        // Définition du listener du bouton
        button.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {

                int usersize = UserEditText.getText().length();

                int passsize = PassEditText.getText().length();
                // si les deux champs sont remplis
                if (usersize > 0 && passsize > 0)
                {

                    progressDialog.show();

                    String user = UserEditText.getText().toString();

                    String pass = PassEditText.getText().toString();
                    // On appelle la fonction doLogin qui va communiquer avec le PHP
                    doLogin(user, pass);

                }
                else
                    createDialog("Error", "Please enter Username and Password");

            }

        });

    }

    private void quit(boolean success, Intent i)
    {
        // On envoie un résultat qui va permettre de quitter l'appli
        setResult((success) ? Activity.RESULT_OK : Activity.RESULT_CANCELED, i);

    }

    private void createDialog(String title, String text)
    {
        // Création d'une popup affichant un message
        AlertDialog ad = new AlertDialog.Builder(this)
                .setPositiveButton("Ok", null).setTitle(title).setMessage(text)
                .create();
        ad.show();

    }

    private void doLogin(final String login, final String pass)
    {

        final String pw = md5(pass);
        // Création d'un thread
        Thread t = new Thread()
        {

            public void run()
            {

                Looper.prepare();
                // On se connecte au serveur afin de communiquer avec le PHP
                DefaultHttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 15000);

                HttpResponse response;
                HttpEntity entity;

                try
                {
                    // On établit un lien avec le script PHP
                    HttpPost post = new HttpPost(UPDATE_URL);

                    List<NameValuePair> nvps = new ArrayList<NameValuePair>();

                    nvps.add(new BasicNameValuePair("username", login));

                    nvps.add(new BasicNameValuePair("password", pw));

                    post.setHeader("Content-Type", "application/android");
                    // On passe les paramètres login et password qui vont être récupérés
                    // par le script PHP en post
                    post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                    // On récupère le résultat du script
                    response = client.execute(post);

                    entity = response.getEntity();

                    InputStream is = entity.getContent();
                    // On appelle une fonction définie plus bas pour traduire la réponse
                    read(is);
                    is.close();

                    if (entity != null)
                        entity.consumeContent();

                }
                catch (Exception e)
                {

                    progressDialog.dismiss();
                    createDialog("Error", "Couldn't establish a connection");

                }

                Looper.loop();

            }

        };

        t.start();

    }

    private void read(InputStream in)
    {
        // On traduit le résultat d'un flux
        SAXParserFactory spf = SAXParserFactory.newInstance();

        SAXParser sp;

        try
        {

            sp = spf.newSAXParser();

            XMLReader xr = sp.getXMLReader();
            // Cette classe est définie plus bas
            LoginContentHandler uch = new LoginContentHandler();

            xr.setContentHandler(uch);

            xr.parse(new InputSource(in));

        }
        catch (ParserConfigurationException e)
        {

        }
        catch (SAXException e)
        {

        }
        catch (IOException e)
        {
        }

    }

    private String md5(String in)
    {

        MessageDigest digest;

        try
        {

            digest = MessageDigest.getInstance("MD5");

            digest.reset();

            digest.update(in.getBytes());

            byte[] a = digest.digest();

            int len = a.length;

            StringBuilder sb = new StringBuilder(len << 1);

            for (int i = 0; i < len; i++)
            {

                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));

                sb.append(Character.forDigit(a[i] & 0x0f, 16));

            }

            return sb.toString();

        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return null;

    }

    private class LoginContentHandler extends DefaultHandler
    {
        // Classe traitant le message de retour du script PHP
        private boolean	in_loginTag		= false;
        private int			userID;
        private boolean	error_occured	= false;

        public void startElement(String n, String l, String q, Attributes a)

                throws SAXException

        {

            if (l == "login")
                in_loginTag = true;
            if (l == "error")
            {

                progressDialog.dismiss();

                switch (Integer.parseInt(a.getValue("value")))
                {
                    case 1:
                        createDialog("Error", "Couldn't connect to Database");
                        break;
                    case 2:
                        createDialog("Error", "Error in Database: Table missing");
                        break;
                    case 3:
                        createDialog("Error", "Invalid username and/or password");
                        break;
                }
                error_occured = true;

            }

            if (l == "user" && in_loginTag && a.getValue("id") != "")
                // Dans le cas où tout se passe bien on récupère l'ID de l'utilisateur
                userID = Integer.parseInt(a.getValue("id"));

        }

        public void endElement(String n, String l, String q) throws SAXException
        {
            // on renvoie l'id si tout est ok
            if (l == "login")
            {
                in_loginTag = false;

                if (!error_occured)
                {
                    progressDialog.dismiss();
                    Intent i = new Intent();
                    i.putExtra("userid", userID);
                    Intent myIntent = new Intent(LoginActivity.this, ClientActivity.class);
                    LoginActivity.this.startActivity(myIntent);                }
            }
        }

        public void characters(char ch[], int start, int length)
        {
        }

        public void startDocument() throws SAXException
        {
        }

        public void endDocument() throws SAXException
        {
        }

    }

}
