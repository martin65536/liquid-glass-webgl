package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class b01 extends gd0 {
    public final gv a;

    public b01(gv gvVar) {
        this.a = gvVar;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [c01, i10, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        es esVar = o20.s;
        ?? i10Var = new i10();
        i10Var.u = esVar;
        i10Var.v = this.a;
        return i10Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof b01) {
                if (this.a == ((b01) obj).a) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        c01 c01Var = (c01) bd0Var;
        gv gvVar = c01Var.v;
        gv gvVar2 = this.a;
        if (gvVar != gvVar2) {
            c01Var.v = gvVar2;
            l71 l71Var = c01Var.w;
            if (l71Var != null) {
                f61 f61Var = (f61) gvVar2.e(l71Var);
                if (!o20.e(f61Var, c01Var.u)) {
                    c01Var.u = f61Var;
                    c01Var.D0();
                }
            }
        }
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
