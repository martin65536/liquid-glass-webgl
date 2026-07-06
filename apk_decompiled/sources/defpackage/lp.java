package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lp extends gd0 {
    public final m9 a;
    public final bw0 b;
    public final gv c;
    public final gv d;
    public final c40 e;
    public final kv f;
    public final gv g;

    public lp(m9 m9Var, bw0 bw0Var, gv gvVar, gv gvVar2, c40 c40Var, kv kvVar, gv gvVar3) {
        m9Var.getClass();
        gvVar.getClass();
        this.a = m9Var;
        this.b = bw0Var;
        this.c = gvVar;
        this.d = gvVar2;
        this.e = c40Var;
        this.f = kvVar;
        this.g = gvVar3;
    }

    @Override // defpackage.gd0
    public final bd0 e() {
        return new op(this.a, this.b, this.c, this.d, this.e, this.f, this.g);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof lp) {
                lp lpVar = (lp) obj;
                if (o20.e(this.a, lpVar.a) && this.b == lpVar.b && o20.e(this.c, lpVar.c) && o20.e(this.d, lpVar.d) && o20.e(this.e, lpVar.e) && this.f.equals(lpVar.f) && o20.e(this.g, lpVar.g)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        op opVar = (op) bd0Var;
        opVar.getClass();
        m9 m9Var = this.a;
        m9Var.getClass();
        opVar.s = m9Var;
        opVar.t = this.b;
        gv gvVar = this.c;
        gvVar.getClass();
        opVar.u = gvVar;
        opVar.v = this.d;
        c40 c40Var = opVar.w;
        c40 c40Var2 = this.e;
        if (!o20.e(c40Var, c40Var2)) {
            c40 c40Var3 = opVar.w;
            if (c40Var3 != null) {
                c40Var3.d(null);
            }
            opVar.w = c40Var2;
        }
        opVar.x = this.f;
        opVar.y = this.g;
        o30.u(opVar, new f6(1, opVar));
    }

    public final int hashCode() {
        int i;
        int i2;
        int hashCode = (this.c.hashCode() + ((this.b.hashCode() + (this.a.hashCode() * 31)) * 31)) * 31;
        int i3 = 0;
        gv gvVar = this.d;
        if (gvVar != null) {
            i = gvVar.hashCode();
        } else {
            i = 0;
        }
        int i4 = (hashCode + i) * 31;
        c40 c40Var = this.e;
        if (c40Var != null) {
            i2 = c40Var.hashCode();
        } else {
            i2 = 0;
        }
        int hashCode2 = (this.f.hashCode() + ((i4 + i2) * 961)) * 31;
        gv gvVar2 = this.g;
        if (gvVar2 != null) {
            i3 = gvVar2.hashCode();
        }
        return (hashCode2 + i3) * 31;
    }
}
