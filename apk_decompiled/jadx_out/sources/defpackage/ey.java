package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ey extends gd0 {
    public final bw0 a;
    public final vu b;

    public ey(bw0 bw0Var, vu vuVar) {
        this.a = bw0Var;
        this.b = vuVar;
    }

    @Override // defpackage.gd0
    public final bd0 e() {
        return new fy(this.a, this.b);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof ey) {
                ey eyVar = (ey) obj;
                if (this.a == eyVar.a && this.b.equals(eyVar.b)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.gd0
    public final void f(bd0 bd0Var) {
        fy fyVar = (fy) bd0Var;
        fyVar.getClass();
        fyVar.s = this.a;
        fyVar.t = this.b;
        o20.t(fyVar);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }
}
