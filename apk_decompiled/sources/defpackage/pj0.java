package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pj0 {
    public final ox0 a;
    public final w3 b = w3.J;
    public final oj0 c = oj0.g;
    public final oj0 d = oj0.h;
    public final w3 e = w3.F;
    public final w3 f = w3.G;
    public final w3 g = w3.H;
    public final w3 h = w3.I;

    public pj0(x3 x3Var) {
        this.a = new ox0(x3Var);
    }

    public final void a() {
        ox0 ox0Var = this.a;
        wa waVar = ox0Var.d;
        cx0.e(cx0.a);
        synchronized (cx0.c) {
            cx0.h = me.a0(cx0.h, waVar);
        }
        ox0Var.h = new g2(waVar);
    }

    public final void b() {
        g2 g2Var = this.a.h;
        if (g2Var != null) {
            g2Var.a();
        }
        ox0 ox0Var = this.a;
        synchronized (ox0Var.g) {
            ef0 ef0Var = ox0Var.f;
            Object[] objArr = ef0Var.e;
            int i = ef0Var.g;
            for (int i2 = 0; i2 < i; i2++) {
                nx0 nx0Var = (nx0) objArr[i2];
                nx0Var.e.a();
                nx0Var.f.a();
                nx0Var.l.a();
                nx0Var.m.clear();
            }
        }
    }
}
