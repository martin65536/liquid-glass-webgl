package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class v50 extends gd0 {
    public final y60 a;
    public final ib b;
    public final dj0 c;

    public v50(y60 y60Var, ib ibVar, dj0 dj0Var) {
        this.a = y60Var;
        this.b = ibVar;
        this.c = dj0Var;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [y50, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        bd0Var.t = this.b;
        bd0Var.u = this.c;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof v50) {
                v50 v50Var = (v50) obj;
                if (!o20.e(this.a, v50Var.a) || !o20.e(this.b, v50Var.b) || this.c != v50Var.c) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        y50 y50Var = (y50) bd0Var;
        y50Var.s = this.a;
        y50Var.t = this.b;
        y50Var.u = this.c;
    }

    public final int hashCode() {
        return this.c.hashCode() + ((((this.b.hashCode() + (this.a.hashCode() * 31)) * 31) + 1237) * 31);
    }
}
