package defpackage;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class uy0 extends bz0 {
    public static int A(CharSequence charSequence, char c, int i, int i2) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        charSequence.getClass();
        if (!(charSequence instanceof String)) {
            char[] cArr = {c};
            if (charSequence instanceof String) {
                return ((String) charSequence).indexOf(cArr[0], i);
            }
            if (i < 0) {
                i = 0;
            }
            int length = charSequence.length() - 1;
            if (i <= length) {
                while (cArr[0] != charSequence.charAt(i)) {
                    if (i != length) {
                        i++;
                    } else {
                        return -1;
                    }
                }
                return i;
            }
            return -1;
        }
        return ((String) charSequence).indexOf(c, i);
    }

    public static boolean B(CharSequence charSequence) {
        charSequence.getClass();
        for (int i = 0; i < charSequence.length(); i++) {
            if (!k81.A(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static final List C(String str) {
        b90 b90Var = new b90(str);
        if (!b90Var.hasNext()) {
            return er.e;
        }
        Object next = b90Var.next();
        if (!b90Var.hasNext()) {
            return jc0.v(next);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(next);
        while (b90Var.hasNext()) {
            arrayList.add(b90Var.next());
        }
        return arrayList;
    }

    public static String D(String str, String str2) {
        str.getClass();
        if (bz0.z(str, str2, false)) {
            return str.substring(0, str.length() - str2.length());
        }
        return str;
    }

    public static String E(String str, String str2) {
        int indexOf = str.indexOf(str2, 0);
        if (indexOf == -1) {
            return str;
        }
        return str.substring(str2.length() + indexOf, str.length());
    }

    public static String F(String str, int i) {
        str.getClass();
        if (i >= 0) {
            int length = str.length();
            if (i > length) {
                i = length;
            }
            return str.substring(0, i);
        }
        v7.h("Requested character count ", i, " is less than zero.");
        return null;
    }
}
