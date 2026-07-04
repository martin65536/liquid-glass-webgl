package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hi implements i41 {
    public final gv a;

    public hi(gv gvVar) {
        this.a = gvVar;
    }

    @Override // defpackage.i41
    public final Object a(ll0 ll0Var) {
        return this.a.e(ll0Var);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof hi) || !this.a.equals(((hi) obj).a)) {
                return false;
            }
            return true;
        }
        return true;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "ComputedValueHolder(compute=" + this.a + ')';
    }
}
