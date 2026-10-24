package com.danamutasi.bot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView status;
    private LinearLayout list;
    @Override public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); buildUi(); }
    @Override protected void onResume() { super.onResume(); if (status != null) refresh(); }

    private void buildUi() {
        ScrollView scroll = new ScrollView(this);
        LinearLayout page = new LinearLayout(this); page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(20), dp(22), dp(20), dp(24)); page.setBackgroundColor(0xFFF4F6FA); scroll.addView(page);
        TextView title = new TextView(this); title.setText("DANA MUTASI BOT"); title.setTextSize(24); title.setTextColor(0xFF123B75); title.setGravity(Gravity.CENTER); title.setTypeface(null, 1); page.addView(title, params());
        TextView subtitle = new TextView(this); subtitle.setText("Pemantau notifikasi dan teks transaksi yang tersedia"); subtitle.setTextSize(14); subtitle.setGravity(Gravity.CENTER); subtitle.setPadding(0, dp(6), 0, dp(18)); page.addView(subtitle, params());
        status = new TextView(this); status.setTextSize(15); status.setPadding(dp(14), dp(14), dp(14), dp(14)); status.setBackgroundColor(0xFFFFFFFF); page.addView(status, params());
        addButton(page, "Buka akses notifikasi", v -> startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")));
        addButton(page, "Buka aksesibilitas (opsional)", v -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));
        addButton(page, "Buka aplikasi DANA", v -> { Intent i = getPackageManager().getLaunchIntentForPackage("id.dana"); if (i != null) startActivity(i); else new android.app.AlertDialog.Builder(this).setMessage("Aplikasi DANA tidak ditemukan.").setPositiveButton("OK", null).show(); });
        addButton(page, "Muat ulang riwayat", v -> refresh());
        TextView h = new TextView(this); h.setText("RIWAYAT INFORMASI TERBACA"); h.setTextSize(17); h.setTypeface(null, 1); h.setTextColor(0xFF123B75); h.setPadding(0, dp(20), 0, dp(8)); page.addView(h, params());
        list = new LinearLayout(this); list.setOrientation(LinearLayout.VERTICAL); page.addView(list, params());
        TextView note = new TextView(this); note.setText("Catatan: hanya teks yang tersedia melalui notifikasi atau layanan aksesibilitas yang dapat dicatat. Tidak dijamin seluruh mutasi terbaca. Jangan masukkan PIN, OTP, atau kata sandi."); note.setTextSize(12); note.setPadding(0, dp(18), 0, 0); page.addView(note, params());
        setContentView(scroll); refresh();
    }
    private void refresh() {
        String enabled = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        boolean on = enabled != null && enabled.contains(getPackageName());
        status.setText("Akses notifikasi: " + (on ? "AKTIF" : "BELUM AKTIF") + "
Penyimpanan: lokal di perangkat");
        list.removeAllViews();
        for (TransactionModel item : TransactionDatabase.getAll(this)) {
            TextView row = new TextView(this); row.setText(item.time + "
" + item.source + "
" + item.text); row.setTextSize(14); row.setTextColor(0xFF20242A); row.setPadding(dp(12), dp(12), dp(12), dp(12)); row.setBackgroundColor(0xFFFFFFFF);
            LinearLayout.LayoutParams lp = params(); lp.bottomMargin = dp(8); list.addView(row, lp);
        }
        if (list.getChildCount() == 0) { TextView empty = new TextView(this); empty.setText("Belum ada informasi transaksi yang tercatat."); empty.setPadding(dp(12), dp(16), dp(12), dp(16)); empty.setBackgroundColor(0xFFFFFFFF); list.addView(empty, params()); }
    }
    private void addButton(LinearLayout parent, String label, View.OnClickListener click) { Button b = new Button(this); b.setText(label); b.setOnClickListener(click); LinearLayout.LayoutParams lp = params(); lp.topMargin = dp(8); parent.addView(b, lp); }
    private LinearLayout.LayoutParams params() { return new LinearLayout.LayoutParams(-1, -2); }
    private int dp(int v) { return (int)(v * getResources().getDisplayMetrics().density + 0.5f); }
}
