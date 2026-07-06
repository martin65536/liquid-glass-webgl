package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class t60 extends gd0 {
    public final vu a;
    public final s60 b;
    public final dj0 c;
    public final boolean d;

    public t60(vu vuVar, s60 s60Var, dj0 dj0Var, boolean z) {
        this.a = vuVar;
        this.b = s60Var;
        this.c = dj0Var;
        this.d = z;
    }

    @Override // defpackage.gd0
    public final bd0 e() {
        return new x60(this.a, this.b, this.c, this.d);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof t60) {
            t60 t60Var = (t60) obj;
            if (this.a == t60Var.a && o20.e(this.b, t60Var.b) && this.c == t60Var.c && this.d == t60Var.d) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        x60 x60Var = (x60) bd0Var;
        x60Var.s = this.a;
        x60Var.t = this.b;
        dj0 dj0Var = x60Var.u;
        dj0 dj0Var2 = this.c;
        if (dj0Var != dj0Var2) {
            x60Var.u = dj0Var2;
            m20.w(x60Var);
        }
        boolean z = x60Var.v;
        boolean z2 = this.d;
        if (z == z2) {
            return;
        }
        x60Var.v = z2;
        x60Var.D0();
        m20.w(x60Var);
    }

    public final int hashCode() {
        int i;
        int hashCode = (this.c.hashCode() + ((this.b.hashCode() + (this.a.hashCode() * 31)) * 31)) * 31;
        if (this.d) {
            i = 1231;
        } else {
            i = 1237;
        }
        return ((hashCode + i) * 31) + 1237;
    }
}
