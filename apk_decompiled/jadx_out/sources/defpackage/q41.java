package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class q41 extends uj0 {
    public final ik0 e = n30.B(new mw0(0));
    public final ik0 f = n30.B(Boolean.FALSE);
    public final l41 g;
    public final ik0 h;
    public float i;
    public te j;

    public q41(sx sxVar) {
        l41 l41Var = new l41(sxVar);
        l41Var.f = new n9(17, this);
        this.g = l41Var;
        this.h = new ik0(x31.a, x1.S);
        this.i = 1.0f;
    }

    @Override // defpackage.uj0
    public final void a(float f) {
        this.i = f;
    }

    @Override // defpackage.uj0
    public final void b(te teVar) {
        this.j = teVar;
    }

    @Override // defpackage.uj0
    public final long d() {
        return ((mw0) this.e.getValue()).a;
    }

    @Override // defpackage.uj0
    public final void e(b50 b50Var) {
        yc ycVar = b50Var.e;
        te teVar = this.j;
        l41 l41Var = this.g;
        if (teVar == null) {
            teVar = (te) l41Var.g.getValue();
        }
        if (((Boolean) this.f.getValue()).booleanValue() && b50Var.getLayoutDirection() == m40.f) {
            long W = ycVar.W();
            r7 r7Var = ycVar.f;
            long v = r7Var.v();
            r7Var.q().h();
            try {
                ((j2) r7Var.f).o(-1.0f, 1.0f, W);
                l41Var.e(b50Var, this.i, teVar);
            } finally {
                r7Var.q().f();
                r7Var.G(v);
            }
        } else {
            l41Var.e(b50Var, this.i, teVar);
        }
        this.h.getValue();
    }
}
