package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tm extends gd0 {
    public final f61 a;
    public final v7 b;

    public tm(f61 f61Var, v7 v7Var) {
        this.a = f61Var;
        this.b = v7Var;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [um, i10, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? i10Var = new i10();
        i10Var.u = this.a;
        i10Var.v = this.b;
        i10Var.w = o20.s;
        return i10Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof tm) {
                tm tmVar = (tm) obj;
                if (o20.e(this.a, tmVar.a) && this.b == tmVar.b) {
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
        um umVar = (um) bd0Var;
        f61 f61Var = umVar.u;
        f61 f61Var2 = this.a;
        boolean e = o20.e(f61Var, f61Var2);
        v7 v7Var = this.b;
        if (e && v7Var == umVar.v) {
            return;
        }
        umVar.u = f61Var2;
        umVar.v = v7Var;
        umVar.w = new tr(f61Var2, umVar.s);
        m20.v(umVar);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }
}
