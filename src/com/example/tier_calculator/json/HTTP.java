package com.example.tier_calculator.json;

import java.util.Iterator;

public class HTTP {
    public static final String CRLF = "\r\n";

    public HTTP() {
    }

    public static JSONObject toJSONObject(String string) throws JSONException {
        JSONObject o = new JSONObject();
        HTTPTokener x = new HTTPTokener(string);
        String t = x.nextToken();
        if (t.toUpperCase().startsWith("HTTP")) {
            o.put("HTTP-Version", t);
            o.put("Status-Code", x.nextToken());
            o.put("Reason-Phrase", x.nextTo('\u0000'));
            x.next();
        } else {
            o.put("Method", t);
            o.put("Request-URI", x.nextToken());
            o.put("HTTP-Version", x.nextToken());
        }

        while(x.more()) {
            String name = x.nextTo(':');
            x.next(':');
            o.put(name, x.nextTo('\u0000'));
            x.next();
        }

        return o;
    }

    public static String toString(JSONObject o) throws JSONException {
        Iterator keys = o.keys();
        StringBuffer sb = new StringBuffer();
        if (o.has("Status-Code") && o.has("Reason-Phrase")) {
            sb.append(o.getString("HTTP-Version"));
            sb.append(' ');
            sb.append(o.getString("Status-Code"));
            sb.append(' ');
            sb.append(o.getString("Reason-Phrase"));
        } else {
            if (!o.has("Method") || !o.has("Request-URI")) {
                throw new JSONException("Not enough material for an HTTP header.");
            }

            sb.append(o.getString("Method"));
            sb.append(' ');
            sb.append('"');
            sb.append(o.getString("Request-URI"));
            sb.append('"');
            sb.append(' ');
            sb.append(o.getString("HTTP-Version"));
        }

        sb.append("\r\n");

        while(keys.hasNext()) {
            String s = keys.next().toString();
            if (!s.equals("HTTP-Version") && !s.equals("Status-Code") && !s.equals("Reason-Phrase") && !s.equals("Method") && !s.equals("Request-URI") && !o.isNull(s)) {
                sb.append(s);
                sb.append(": ");
                sb.append(o.getString(s));
                sb.append("\r\n");
            }
        }

        sb.append("\r\n");
        return sb.toString();
    }
}