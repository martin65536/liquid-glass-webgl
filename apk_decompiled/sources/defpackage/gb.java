package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gb implements cb {
    public final mm a;
    public final long b;

    public gb(iz0 iz0Var, long j) {
        this.a = iz0Var;
        this.b = j;
    }

    @Override // defpackage.cb
    public final cd0 a(cd0 cd0Var, ba baVar) {
        return cd0Var.b(new ua(baVar));
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof gb) {
                gb gbVar = (gb) obj;
                if (!o20.e(this.a, gbVar.a) || !si.b(this.b, gbVar.b)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        long j = this.b;
        return ((int) (j ^ (j >>> 32))) + hashCode;
    }

    public final String toString() {
        return "BoxWithConstraintsScopeImpl(density=" + this.a + ", constraints=" + ((Object) si.k(this.b)) + ')';
    }
}
