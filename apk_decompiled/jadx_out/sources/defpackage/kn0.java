package defpackage;

import android.os.Build;
import java.util.Locale;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class kn0 {
    public static final jn0 a;

    /* JADX WARN: Multi-variable type inference failed */
    static {
        jn0 jn0Var;
        String str = Build.FINGERPRINT;
        if (str != null) {
            String lowerCase = str.toLowerCase(Locale.ROOT);
            lowerCase.getClass();
            if (lowerCase.equals("robolectric")) {
                jn0Var = new Object();
                a = jn0Var;
            }
        }
        jn0Var = null;
        a = jn0Var;
    }
}
