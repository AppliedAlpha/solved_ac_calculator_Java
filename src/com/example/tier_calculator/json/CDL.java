package com.example.tier_calculator.json;

public class CDL {
    public CDL() {
    }

    private static String getValue(JSONTokener x) throws JSONException {
        char c;
        do {
            c = x.next();
        } while(c == ' ' || c == '\t');

        switch(c) {
            case '\u0000':
                return null;
            case '"':
            case '\'':
                char q = c;
                StringBuffer sb = new StringBuffer();

                while(true) {
                    c = x.next();
                    if (c == q) {
                        return sb.toString();
                    }

                    if (c == 0 || c == '\n' || c == '\r') {
                        throw x.syntaxError("Missing close quote '" + q + "'.");
                    }

                    sb.append(c);
                }
            case ',':
                x.back();
                return "";
            default:
                x.back();
                return x.nextTo(',');
        }
    }

    public static JSONArray rowToJSONArray(JSONTokener x) throws JSONException {
        JSONArray ja = new JSONArray();

        label33:
        while(true) {
            String value = getValue(x);
            if (value == null || ja.length() == 0 && value.length() == 0) {
                return null;
            }

            ja.put(value);

            char c;
            do {
                c = x.next();
                if (c == ',') {
                    continue label33;
                }
            } while(c == ' ');

            if (c != '\n' && c != '\r' && c != 0) {
                throw x.syntaxError("Bad character '" + c + "' (" + c + ").");
            }

            return ja;
        }
    }

    public static JSONObject rowToJSONObject(JSONArray names, JSONTokener x) throws JSONException {
        JSONArray ja = rowToJSONArray(x);
        return ja != null ? ja.toJSONObject(names) : null;
    }

    public static JSONArray toJSONArray(String string) throws JSONException {
        return toJSONArray(new JSONTokener(string));
    }

    public static JSONArray toJSONArray(JSONTokener x) throws JSONException {
        return toJSONArray(rowToJSONArray(x), x);
    }

    public static JSONArray toJSONArray(JSONArray names, String string) throws JSONException {
        return toJSONArray(names, new JSONTokener(string));
    }

    public static JSONArray toJSONArray(JSONArray names, JSONTokener x) throws JSONException {
        if (names != null && names.length() != 0) {
            JSONArray ja = new JSONArray();

            while(true) {
                JSONObject jo = rowToJSONObject(names, x);
                if (jo == null) {
                    return ja.length() == 0 ? null : ja;
                }

                ja.put(jo);
            }
        } else {
            return null;
        }
    }

    public static String rowToString(JSONArray ja) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < ja.length(); ++i) {
            if (i > 0) {
                sb.append(',');
            }

            Object o = ja.opt(i);
            if (o != null) {
                String s = o.toString();
                if (s.indexOf(44) < 0 && s.indexOf(10) < 0 && s.indexOf(13) < 0 && s.indexOf(0) < 0 && s.charAt(0) != '"') {
                    sb.append(s);
                } else {
                    sb.append('"');
                    int length = s.length();

                    for(int j = 0; j < length; ++j) {
                        char c = s.charAt(j);
                        if (c >= ' ' && c != '"') {
                            sb.append(c);
                        }
                    }

                    sb.append('"');
                }
            }
        }

        sb.append('\n');
        return sb.toString();
    }

    public static String toString(JSONArray ja) throws JSONException {
        JSONObject jo = ja.optJSONObject(0);
        if (jo != null) {
            JSONArray names = jo.names();
            if (names != null) {
                return rowToString(names) + toString(names, ja);
            }
        }

        return null;
    }

    public static String toString(JSONArray names, JSONArray ja) throws JSONException {
        if (names != null && names.length() != 0) {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < ja.length(); ++i) {
                JSONObject jo = ja.optJSONObject(i);
                if (jo != null) {
                    sb.append(rowToString(jo.toJSONArray(names)));
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }
}

