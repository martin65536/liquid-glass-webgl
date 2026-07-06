package defpackage;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vj0 extends gd0 {
    public final uj0 a;
    public final ba b;
    public final dt0 c;
    public final float d;
    public final te e;

    public vj0(uj0 uj0Var, ba baVar, dt0 dt0Var, float f, te teVar) {
        this.a = uj0Var;
        this.b = baVar;
        this.c = dt0Var;
        this.d = f;
        this.e = teVar;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [wj0, bd0] */
    @Override // defpackage.gd0
    public final bd0 e() {
        ?? bd0Var = new bd0();
        bd0Var.s = this.a;
        bd0Var.t = true;
        bd0Var.u = this.b;
        bd0Var.v = this.c;
        bd0Var.w = this.d;
        bd0Var.x = this.e;
        return bd0Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof vj0) {
                vj0 vj0Var = (vj0) obj;
                if (!o20.e(this.a, vj0Var.a) || !o20.e(this.b, vj0Var.b) || !o20.e(this.c, vj0Var.c) || Float.compare(this.d, vj0Var.d) != 0 || !o20.e(this.e, vj0Var.e)) {
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
        boolean z;
        wj0 wj0Var = (wj0) bd0Var;
        boolean z2 = wj0Var.t;
        uj0 uj0Var = this.a;
        if (z2 && mw0.a(wj0Var.s.d(), uj0Var.d())) {
            z = false;
        } else {
            z = true;
        }
        wj0Var.s = uj0Var;
        wj0Var.t = true;
        wj0Var.u = this.b;
        wj0Var.v = this.c;
        wj0Var.w = this.d;
        wj0Var.x = this.e;
        if (z) {
            m20.v(wj0Var);
        }
        o20.t(wj0Var);
    }

    public final int hashCode() {
        int hashCode;
        int s = d3.s(this.d, (this.c.hashCode() + ((this.b.hashCode() + (((this.a.hashCode() * 31) + 1231) * 31)) * 31)) * 31, 31);
        te teVar = this.e;
        if (teVar == null) {
            hashCode = 0;
        } else {
            hashCode = teVar.hashCode();
        }
        return s + hashCode;
    }

    public final String toString() {
        return "PainterElement(painter=" + this.a + ", sizeToIntrinsics=true, alignment=" + this.b + ", contentScale=" + this.c + ", alpha=" + this.d + ", colorFilter=" + this.e + ')';
    }
}
