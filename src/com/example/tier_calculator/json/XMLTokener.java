package com.example.tier_calculator.json;

import java.util.HashMap;

public class XMLTokener extends JSONTokener {
    public static final HashMap entity = new HashMap(8);

    static {
        entity.put("amp", XML.AMP);
        entity.put("apos", XML.APOS);
        entity.put("gt", XML.GT);
        entity.put("lt", XML.LT);
        entity.put("quot", XML.QUOT);
    }

    public XMLTokener(String s) {
        super(s);
    }

    public String nextCDATA() throws JSONException {
        StringBuffer sb = new StringBuffer();

        int i;
        do {
            char c = this.next();
            if (c == 0) {
                throw this.syntaxError("Unclosed CDATA");
            }

            sb.append(c);
            i = sb.length() - 3;
        } while(i < 0 || sb.charAt(i) != ']' || sb.charAt(i + 1) != ']' || sb.charAt(i + 2) != '>');

        sb.setLength(i);
        return sb.toString();
    }

    public Object nextContent() throws JSONException {
        char c;
        do {
            c = this.next();
        } while(Character.isWhitespace(c));

        if (c == 0) {
            return null;
        } else if (c == '<') {
            return XML.LT;
        } else {
            StringBuffer sb;
            for(sb = new StringBuffer(); c != '<' && c != 0; c = this.next()) {
                if (c == '&') {
                    sb.append(this.nextEntity(c));
                } else {
                    sb.append(c);
                }
            }

            this.back();
            return sb.toString().trim();
        }
    }

    public Object nextEntity(char a) throws JSONException {
        StringBuffer sb = new StringBuffer();

        while(true) {
            char c = this.next();
            if (!Character.isLetterOrDigit(c) && c != '#') {
                if (c == ';') {
                    String s = sb.toString();
                    Object e = entity.get(s);
                    return e != null ? e : a + s + ";";
                }

                throw this.syntaxError("Missing ';' in XML entity: &" + sb);
            }

            sb.append(Character.toLowerCase(c));
        }
    }

    public Object nextMeta() throws JSONException {
        char c;
        do {
            c = this.next();
        } while(Character.isWhitespace(c));

        switch(c) {
            case '\u0000':
                throw this.syntaxError("Misshaped meta tag");
            case '!':
                return XML.BANG;
            case '"':
            case '\'':
                char q = c;

                do {
                    c = this.next();
                    if (c == 0) {
                        throw this.syntaxError("Unterminated string");
                    }
                } while(c != q);

                return Boolean.TRUE;
            case '/':
                return XML.SLASH;
            case '<':
                return XML.LT;
            case '=':
                return XML.EQ;
            case '>':
                return XML.GT;
            case '?':
                return XML.QUEST;
            default:
                while(true) {
                    c = this.next();
                    if (Character.isWhitespace(c)) {
                        return Boolean.TRUE;
                    }

                    switch(c) {
                        case '\u0000':
                        case '!':
                        case '"':
                        case '\'':
                        case '/':
                        case '<':
                        case '=':
                        case '>':
                        case '?':
                            this.back();
                            return Boolean.TRUE;
                    }
                }
        }
    }

    public Object nextToken() throws JSONException {
        char c;
        do {
            c = this.next();
        } while(Character.isWhitespace(c));

        StringBuffer sb;
        switch(c) {
            case '\u0000':
                throw this.syntaxError("Misshaped element");
            case '!':
                return XML.BANG;
            case '"':
            case '\'':
                char q = c;
                sb = new StringBuffer();

                while(true) {
                    c = this.next();
                    if (c == 0) {
                        throw this.syntaxError("Unterminated string");
                    }

                    if (c == q) {
                        return sb.toString();
                    }

                    if (c == '&') {
                        sb.append(this.nextEntity(c));
                    } else {
                        sb.append(c);
                    }
                }
            case '/':
                return XML.SLASH;
            case '<':
                throw this.syntaxError("Misplaced '<'");
            case '=':
                return XML.EQ;
            case '>':
                return XML.GT;
            case '?':
                return XML.QUEST;
            default:
                sb = new StringBuffer();

                while(true) {
                    sb.append(c);
                    c = this.next();
                    if (Character.isWhitespace(c)) {
                        return sb.toString();
                    }

                    switch(c) {
                        case '\u0000':
                            return sb.toString();
                        case '!':
                        case '/':
                        case '=':
                        case '>':
                        case '?':
                        case '[':
                        case ']':
                            this.back();
                            return sb.toString();
                        case '"':
                        case '\'':
                        case '<':
                            throw this.syntaxError("Bad character in a name");
                    }
                }
        }
    }

    public boolean skipPast(String to) throws JSONException {
        int offset = 0;
        int n = to.length();
        char[] circle = new char[n];

        char c;
        int i;
        for(i = 0; i < n; ++i) {
            c = this.next();
            if (c == 0) {
                return false;
            }

            circle[i] = c;
        }

        while(true) {
            int j = offset;
            boolean b = true;

            for(i = 0; i < n; ++i) {
                if (circle[j] != to.charAt(i)) {
                    b = false;
                    break;
                }

                ++j;
                if (j >= n) {
                    j -= n;
                }
            }

            if (b) {
                return true;
            }

            c = this.next();
            if (c == 0) {
                return false;
            }

            circle[offset] = c;
            ++offset;
            if (offset >= n) {
                offset -= n;
            }
        }
    }
}
