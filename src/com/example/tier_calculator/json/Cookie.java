package com.example.tier_calculator.json;

public class Cookie {
    public Cookie() {
    }

    public static String escape(String string) {
        String s = string.trim();
        StringBuffer sb = new StringBuffer();
        int len = s.length();

        for(int i = 0; i < len; ++i) {
            char c = s.charAt(i);
            if (c >= ' ' && c != '+' && c != '%' && c != '=' && c != ';') {
                sb.append(c);
            } else {
                sb.append('%');
                sb.append(Character.forDigit((char)(c >>> 4 & 15), 16));
                sb.append(Character.forDigit((char)(c & 15), 16));
            }
        }

        return sb.toString();
    }

    public static JSONObject toJSONObject(String string) throws JSONException {
        JSONObject o = new JSONObject();
        JSONTokener x = new JSONTokener(string);
        o.put("name", x.nextTo('='));
        x.next('=');
        o.put("value", x.nextTo(';'));
        x.next();

        String n;
        Object v;
        for(; x.more(); o.put(n, v)) {
            n = unescape(x.nextTo("=;"));
            if (x.next() != '=') {
                if (!n.equals("secure")) {
                    throw x.syntaxError("Missing '=' in cookie parameter.");
                }

                v = Boolean.TRUE;
            } else {
                v = unescape(x.nextTo(';'));
                x.next();
            }
        }

        return o;
    }

    public static String toString(JSONObject o) throws JSONException {
        StringBuffer sb = new StringBuffer();
        sb.append(escape(o.getString("name")));
        sb.append("=");
        sb.append(escape(o.getString("value")));
        if (o.has("expires")) {
            sb.append(";expires=");
            sb.append(o.getString("expires"));
        }

        if (o.has("domain")) {
            sb.append(";domain=");
            sb.append(escape(o.getString("domain")));
        }

        if (o.has("path")) {
            sb.append(";path=");
            sb.append(escape(o.getString("path")));
        }

        if (o.optBoolean("secure")) {
            sb.append(";secure");
        }

        return sb.toString();
    }

    public static String unescape(String s) {
        int len = s.length();
        StringBuffer b = new StringBuffer();

        for(int i = 0; i < len; ++i) {
            char c = s.charAt(i);
            if (c == '+') {
                c = ' ';
            } else if (c == '%' && i + 2 < len) {
                int d = JSONTokener.dehexchar(s.charAt(i + 1));
                int e = JSONTokener.dehexchar(s.charAt(i + 2));
                if (d >= 0 && e >= 0) {
                    c = (char)(d * 16 + e);
                    i += 2;
                }
            }

            b.append(c);
        }

        return b.toString();
    }
}
