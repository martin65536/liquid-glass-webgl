package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yf0 extends y20 {
    public final rf0 a;

    public yf0(rf0 rf0Var) {
        rf0Var.getClass();
        this.a = rf0Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj != null && yf0.class == obj.getClass() && o20.e(this.a, ((yf0) obj).a)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.a.hashCode() - 31;
    }

    public final String toString() {
        return "InProgress(latestEvent=" + this.a + ", direction=-1)";
    }
}
