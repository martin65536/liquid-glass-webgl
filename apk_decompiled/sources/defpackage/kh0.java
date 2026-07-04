package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kh0 extends t20 {
    public final c9 a;

    public kh0(c9 c9Var) {
        c9Var.getClass();
        this.a = c9Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof kh0) || !o20.e(this.a, ((kh0) obj).a)) {
                return false;
            }
            return true;
        }
        return true;
    }

    public final int hashCode() {
        return this.a.hashCode() * 31;
    }

    public final String toString() {
        return "OnBackPressedCallbackInfo(callback=" + this.a + ", owner=null)";
    }
}
