package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hl extends bd0 implements tp {
    public final je0 s;
    public boolean t;
    public boolean u;
    public boolean v;

    public hl(je0 je0Var) {
        this.s = je0Var;
    }

    @Override // defpackage.tp
    public final void R(b50 b50Var) {
        b50Var.r();
        yc ycVar = b50Var.e;
        if (this.t) {
            d3.q(b50Var, se.b(se.b, 0.3f), ycVar.f.v(), 0.0f, 0, 122);
        } else {
            if (!this.u && !this.v) {
                return;
            }
            d3.q(b50Var, se.b(se.b, 0.1f), ycVar.f.v(), 0.0f, 0, 122);
        }
    }

    @Override // defpackage.bd0
    public final void t0() {
        f31.G(p0(), null, new m8(this, (ij) null, 1), 3);
    }

    @Override // defpackage.tp
    public final /* synthetic */ void m0() {
    }
}
