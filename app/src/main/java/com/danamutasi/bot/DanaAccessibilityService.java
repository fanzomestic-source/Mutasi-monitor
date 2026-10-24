package com.danamutasi.bot;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.List;

public class DanaAccessibilityService extends AccessibilityService {
    private long lastSave = 0; private String lastText = "";
    @Override public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null || event.getPackageName() == null || !"id.dana".contentEquals(event.getPackageName())) return;
        long now = System.currentTimeMillis(); if (now - lastSave < 2500) return;
        AccessibilityNodeInfo root = getRootInActiveWindow(); if (root == null) return;
        List<String> chunks = new ArrayList<>(); collect(root, chunks); String text = String.join(" | ", chunks).trim();
        if (text.length() < 8 || text.equals(lastText)) return;
        String lower = text.toLowerCase();
        if (!(lower.contains("riwayat") || lower.contains("transaksi") || lower.contains("rp") || lower.contains("transfer") || lower.contains("pembayaran"))) return;
        lastText = text; lastSave = now; TransactionDatabase.add(this, "Teks layar DANA", text, now);
    }
    private void collect(AccessibilityNodeInfo node, List<String> out) {
        if (node == null || out.size() >= 80) return;
        CharSequence t = node.getText(); if (t != null) { String s = t.toString().trim(); if (!s.isEmpty() && !out.contains(s)) out.add(s); }
        for (int i=0; i<node.getChildCount(); i++) collect(node.getChild(i), out);
    }
    @Override public void onInterrupt() {}
}
