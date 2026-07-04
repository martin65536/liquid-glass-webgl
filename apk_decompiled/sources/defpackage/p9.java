package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p9 extends gd0 {
    public final long a;
    public final float b = 1.0f;
    public final zv0 c;

    public p9(long j, zv0 zv0Var) {
        this.a = j;
        this.c = zv0Var;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [q9, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        bd0Var.t = this.c;
        bd0Var.u = 9205357640488583168L;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        p9 p9Var;
        if (obj instanceof p9) {
            p9Var = (p9) obj;
        } else {
            p9Var = null;
        }
        if (p9Var != null && se.c(this.a, p9Var.a) && this.b == p9Var.b && o20.e(this.c, p9Var.c)) {
            return true;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        q9 q9Var = (q9) bd0Var;
        q9Var.s = this.a;
        zv0 zv0Var = q9Var.t;
        zv0 zv0Var2 = this.c;
        if (!o20.e(zv0Var, zv0Var2)) {
            q9Var.t = zv0Var2;
            m20.w(q9Var);
        }
        o20.t(q9Var);
    }

    public final int hashCode() {
        return this.c.hashCode() + d3.s(this.b, se.i(this.a) * 961, 31);
    }
}
