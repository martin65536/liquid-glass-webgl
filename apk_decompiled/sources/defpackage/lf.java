package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lf implements cd0 {
    public final cd0 a;
    public final cd0 b;

    public lf(cd0 cd0Var, cd0 cd0Var2) {
        this.a = cd0Var;
        this.b = cd0Var2;
    }

    @Override // defpackage.cd0
    public final Object a(kv kvVar, Object obj) {
        return this.b.a(kvVar, this.a.a(kvVar, obj));
    }

    @Override // defpackage.cd0
    public final /* synthetic */ cd0 b(cd0 cd0Var) {
        return d3.d(this, cd0Var);
    }

    @Override // defpackage.cd0
    public final boolean d(gv gvVar) {
        if (this.a.d(gvVar) && this.b.d(gvVar)) {
            return true;
        }
        return false;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof lf) {
            lf lfVar = (lf) obj;
            if (this.a.equals(lfVar.a) && o20.e(this.b, lfVar.b)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return (this.b.hashCode() * 31) + this.a.hashCode();
    }

    public final String toString() {
        return "[" + ((String) a(kf.g, "")) + ']';
    }
}
