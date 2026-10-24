package com.danamutasi.bot;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import java.util.Locale;

public class NotificationService extends NotificationListenerService {
    @Override public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn == null || !"id.dana".equals(sbn.getPackageName())) return;
        Notification n = sbn.getNotification(); if (n == null) return;
        Bundle e = n.extras; if (e == null) return;
        String title = safe(e.getCharSequence(Notification.EXTRA_TITLE));
        String text = safe(e.getCharSequence(Notification.EXTRA_TEXT));
        String big = safe(e.getCharSequence(Notification.EXTRA_BIG_TEXT));
        String combined = (title + " " + text + " " + big).trim(); if (combined.isEmpty()) return;
        String lower = combined.toLowerCase(Locale.ROOT);
        if (!(lower.contains("rp") || lower.contains("berhasil") || lower.contains("diterima") || lower.contains("pembayaran") || lower.contains("transaksi") || lower.contains("transfer") || lower.contains("saldo"))) return;
        TransactionDatabase.add(this, "Notifikasi DANA", combined, sbn.getPostTime());
    }
    private String safe(CharSequence c) { return c == null ? "" : c.toString().trim(); }
}
