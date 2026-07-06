package defpackage;

import android.os.Build;
import android.view.DisplayCutout;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class on {
    public final DisplayCutout a;

    public on(DisplayCutout displayCutout) {
        this.a = displayCutout;
    }

    public final g10 a() {
        if (Build.VERSION.SDK_INT >= 30) {
            return g10.c(f1.c(this.a));
        }
        return g10.e;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && on.class == obj.getClass()) {
            return this.a.equals(((on) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        int hashCode;
        hashCode = this.a.hashCode();
        return hashCode;
    }

    public final String toString() {
        return "DisplayCutoutCompat{" + this.a + "}";
    }
}
