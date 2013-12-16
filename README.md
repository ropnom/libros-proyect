create user 'libros'@'localhost' identified by 'libros';
grant all privileges on librosdb.* to 'libros'@'localhost';
flush privileges;
exit;

//Crear proyecto Android.
android create project --name Libros --target android-10 --path /home/ropnom/dsa_proyect2/libros-proyect/libros-android --package edu.upc.eetac.dsa.rodrigo.sampedro.libros.android --activity Libros

