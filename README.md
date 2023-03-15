# MovieApp

Berikut adalah project test
Aplikasi ini mengambil data dari website TMDB.com
![image](https://user-images.githubusercontent.com/20677616/225365712-d02566f8-8e5e-4137-a231-4ffa61c736c1.png)

 
Aplikasi ini dibuat dengan arsitektur MVVM(Model View ViewModel) dan clean architecture yang memisahkan Domain Layer, Data Layer, dan Representation Layar. Dependecy Injection yang digunakan yaitu Dagger Hilt.
![image](https://user-images.githubusercontent.com/20677616/225365640-8a6db562-4cd7-45e0-ab8f-1cd708f42dd2.png)

 
Tugas tugas yang mandatory dalam soal ini yaitu
1. Create a screen to display the list of official genres for movies. 
2. Create a screen to list of discover movies by genre.
 3. Show the primary information about a movie when user click on of the movie.
 4. Show the users review for a movie. 
5. Show the youtube trailer of the movie. 
6. Implement endless scrolling on list of movies and users review. 
7. Cover positive and negative cases.

PEMBAHASAN
1.	Terdapat Bottom navigation bar yang menampilkan list movie dan genre. Pada bagian Genre akan menampilkan Genre official yang ada di TMDB melalui endpoint API
2.	Hal ini masih berhubungan dengan point pertama, Ketika user memiliki/mengklik genre maka akan diarahkan ke halaman baru yang berupa list movie berdasarkan genre yang dipilih, hal ini diambil dengan cara menghit API TMDB(bukan filter melalui Front End)
3.	User akan diarahkan ke halaman detail layout Ketika setelah mengklik list movie, disana terdapat “detail” informasi yang ditampilkan 
4.	Pada bagian detail movie, pada bagian bawah layout terdapat review dari beberapa user yang diambil dengan cara menghit API TMDB
5.	Pada halaman Detail pula terdapat Youtbe Media Player dengan menggunakan Third Party, yang me-load data berdasarkan Key yang didapatkan melalui hit-API TMDB
6.	Semua list yang ada di aplikasi ini telah mengimplementasikan “endless scrolling” Ketika user mengscrol layout, dan telah sampai batas akhir data, maka system akan menghit api namun dengan parameter page yang berbeda, dan page ini akan selalu increment **
7.	Semua tampilan pada aplikasi ini telah menerepakan multi state seperti: State LOADING, State SUCCESS, State ERROR, dan State EMPTY. *

**Mungkin hal ini masih memiliki beberapa bug(tetapi ini masih bisa diselesaikan) karena hal ini testing dan secara garis besar telah diimplementasikannya “endless scrolling”

*Mungkin terdapat beberapa layout yang tidak memiliki kondisi state seperti state error dan empty hal ini bisa dilakukan jika memang suatu layout tersebut memanggil API yang sudah pasti berhasil dan memiliki data


aplikasi ini bisa lebih ditunning dengan menerapkan Modularization
