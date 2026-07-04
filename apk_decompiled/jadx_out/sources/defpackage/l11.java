package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l11 {
    public static final l11 c = new l11(2, false);
    public static final l11 d = new l11(1, true);
    public final int a;
    public final boolean b;

    public l11(int i, boolean z) {
        this.a = i;
        this.b = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof l11)) {
            return false;
        }
        l11 l11Var = (l11) obj;
        if (this.a == l11Var.a && this.b == l11Var.b) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i;
        int i2 = this.a * 31;
        if (this.b) {
            i = 1231;
        } else {
            i = 1237;
        }
        return i2 + i;
    }

    public final String toString() {
        if (equals(c)) {
            return "TextMotion.Static";
        }
        if (equals(d)) {
            return "TextMotion.Animated";
        }
        return "Invalid";
    }
}
