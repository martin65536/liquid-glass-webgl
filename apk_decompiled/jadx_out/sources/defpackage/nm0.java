package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nm0 {
    public final mm0 a;

    public nm0(mm0 mm0Var) {
        this.a = mm0Var;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof nm0)) {
            return false;
        }
        if (o20.e(this.a, ((nm0) obj).a)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        mm0 mm0Var = this.a;
        if (mm0Var != null) {
            return mm0Var.hashCode();
        }
        return 0;
    }

    public final String toString() {
        return "PlatformTextStyle(spanStyle=null, paragraphSyle=" + this.a + ')';
    }
}
