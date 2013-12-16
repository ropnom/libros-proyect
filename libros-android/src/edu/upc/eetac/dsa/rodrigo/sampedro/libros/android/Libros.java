package edu.upc.eetac.dsa.rodrigo.sampedro.libros.android;

import java.io.IOException;
import java.util.Properties;

import android.app.ListActivity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class Libros extends ListActivity
{
	private final static String TAG = Libros.class.getName();
    /** Called when the activity is first created. */
	private static final String[] items = { "lorem", "ipsum", "dolor", "sit",
		"amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
		"ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
		"erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
		"augue", "purus" };
	private ArrayAdapter<String> adapter;
	String serverAddress;
	String serverPort;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
    	
    	AssetManager assetManager = getAssets();
    	Properties config = new Properties();
    	try {
    		config.load(assetManager.open("config.properties"));
    		serverAddress = config.getProperty("server.address");
    		serverPort = config.getProperty("server.port");
     
    		Log.d(TAG, "Configured server " + serverAddress + ":" + serverPort);
    	} catch (IOException e) {
    		Log.e(TAG, e.getMessage(), e);
    		finish();
    	}
     
    	setContentView(R.layout.relative);
    	adapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_list_item_1, items);
    	setListAdapter(adapter);
    }
}
