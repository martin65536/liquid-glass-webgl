package defpackage;

import android.os.Build;
import java.util.Locale;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class va0 {
    public static final /* synthetic */ int b = 0;
    public final xa0 a;

    static {
        a(new Locale[0]);
    }

    public va0(xa0 xa0Var) {
        this.a = xa0Var;
    }

    public static va0 a(Locale... localeArr) {
        if (Build.VERSION.SDK_INT >= 24) {
            return new va0(new ya0(li.a(localeArr)));
        }
        return new va0(new wa0(localeArr));
    }

    public final boolean equals(Object obj) {
        if (obj instanceof va0) {
            if (this.a.equals(((va0) obj).a)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a.toString();
    }
}
