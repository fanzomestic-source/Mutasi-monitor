package com.danamutasi.bot;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class TransactionDatabase {
    private static final String PREF="transaction_store", KEY="items"; private static final int MAX=300;
    private TransactionDatabase() {}
    public static synchronized void add(Context c, String source, String text, long timestamp) {
        if (text == null || text.trim().isEmpty()) return;
        SharedPreferences p=c.getApplicationContext().getSharedPreferences(PREF, Context.MODE_PRIVATE); JSONArray old;
        try { old=new JSONArray(p.getString(KEY,"[]")); } catch(Exception ex) { old=new JSONArray(); }
        String value=text.trim();
        for(int i=0;i<old.length();i++){ JSONObject o=old.optJSONObject(i); if(o!=null && value.equals(o.optString("text"))) return; }
        JSONArray next=new JSONArray();
        try {
            JSONObject item=new JSONObject(); item.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(new Date(timestamp))); item.put("source",source); item.put("text",value); next.put(item);
            for(int i=0;i<old.length() && next.length()<MAX;i++){ JSONObject o=old.optJSONObject(i); if(o!=null) next.put(o); }
            p.edit().putString(KEY,next.toString()).apply();
        } catch(Exception ignored) {}
    }
    public static synchronized List<TransactionModel> getAll(Context c) {
        List<TransactionModel> result=new ArrayList<>(); SharedPreferences p=c.getApplicationContext().getSharedPreferences(PREF,Context.MODE_PRIVATE);
        try { JSONArray a=new JSONArray(p.getString(KEY,"[]")); for(int i=0;i<a.length();i++){ JSONObject o=a.optJSONObject(i); if(o!=null) result.add(new TransactionModel(o.optString("time"),o.optString("source"),o.optString("text"))); } } catch(Exception ignored) {}
        return result;
    }
}
