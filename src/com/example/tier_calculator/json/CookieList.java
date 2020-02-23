package com.example.tier_calculator.json;

import java.util.Iterator;

public class CookieList {
    public CookieList() {
    }

    public static JSONObject toJSONObject(String string) throws JSONException {
        JSONObject o = new JSONObject();
        JSONTokener x = new JSONTokener(string);

        while(x.more()) {
            String name = Cookie.unescape(x.nextTo('='));
            x.next('=');
            o.put(name, Cookie.unescape(x.nextTo(';')));
            x.next();
        }

        return o;
    }

    public static String toString(JSONObject o) throws JSONException {
        boolean b = false;
        Iterator keys = o.keys();
        StringBuffer sb = new StringBuffer();

        while(keys.hasNext()) {
            String s = keys.next().toString();
            if (!o.isNull(s)) {
                if (b) {
                    sb.append(';');
                }

                sb.append(Cookie.escape(s));
                sb.append("=");
                sb.append(Cookie.escape(o.getString(s)));
                b = true;
            }
        }

        return sb.toString();
    }
}

