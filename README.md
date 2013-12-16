create user 'libros'@'localhost' identified by 'libros';
grant all privileges on librosdb.* to 'libros'@'localhost';
flush privileges;
exit;

//Crear proyecto Android.
android create project --name Libros --target android-10 --path /home/ropnom/dsa_proyect2/libros-proyect/libros-android --package edu.upc.eetac.dsa.rodrigo.sampedro.libros.android --activity Libros

1º vamos al manifes y ponemso lo de las versiones
<uses-sdk
        android:maxSdkVersion="18"
        android:minSdkVersion="10"
        android:targetSdkVersion="10" />
2º Añadimso el Icono en Nuevo otros, android Set icon y depsues en el manifest
allado de donde esta el nombre dela aplicacion:

android:icon=”@drawable/ic_launcher”

3º Ponemos el tag
private final static String TAG = Libros.class.getName();

4º creamos el relative.xml como relative layaout

5º Permisos de internet
<uses-permission android:name="android.permission.INTERNET" />

6º añadimos un list view en el relative layaout

 <ListView
        android:id="@android:id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

7º Creamos assets/config.properties

server.address = 10.89.46.98
server.port = 8000

8º Modificamos el Libros

String serverAddress;
String serverPort;

En Oncreate

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


9º Creamos LibrosAPI en el api.

