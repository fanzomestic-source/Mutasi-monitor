# DANA Mutasi Bot — proyek Android awal

Proyek ini menggabungkan pemantauan notifikasi DANA dan pencatatan teks yang tersedia melalui layanan aksesibilitas Android. Ini adalah proyek awal yang perlu diuji pada perangkat dan versi aplikasi DANA yang digunakan.

## Build APK melalui GitHub
1. Buat repository baru di GitHub.
2. Ekstrak ZIP ini, lalu unggah **isi folder** ke root repository.
3. Buka tab **Actions** dan aktifkan workflow jika diminta.
4. Pilih **Build DANA Mutasi Bot APK** lalu tekan **Run workflow**, atau push ke branch `main`/`master`.
5. Setelah selesai, buka workflow run dan unduh artifact `DanaMutasiBot-debug-apk`.
6. Ekstrak artifact ZIP untuk memperoleh `app-debug.apk`.

## Penggunaan
1. Instal APK di perangkat Android milik sendiri.
2. Buka DANA Mutasi Bot.
3. Tekan **Buka akses notifikasi** dan aktifkan akses notifikasi untuk aplikasi ini.
4. Jika ingin membaca teks yang tampak di layar, aktifkan layanan aksesibilitas secara sadar melalui tombol yang disediakan.
5. Buka aplikasi DANA sendiri dan navigasikan ke halaman transaksi. Teks yang tersedia mungkin dicatat di riwayat aplikasi.

## Batasan dan keamanan
- Tidak memakai API privat, tidak melewati pengamanan DANA, dan tidak menjalankan transaksi keuangan.
- Hanya membaca teks yang benar-benar tersedia dari notifikasi atau tampilan yang dapat diakses Android. Data tersembunyi, riwayat lama yang belum dibuka, dan notifikasi yang tidak memuat nominal tidak dijamin terbaca.
- Akses notifikasi dan aksesibilitas adalah izin sensitif. Aktifkan hanya jika Anda memahami dan menyetujui penggunaannya.
- Aplikasi tidak membaca PIN, OTP, atau kata sandi.
- Penerusan WhatsApp belum disertakan; integrasi WhatsApp memerlukan mekanisme resmi/berizin tersendiri.
- Target minimum Android 7.0; target API 35. Kompatibilitas tetap perlu diuji di perangkat masing-masing.
