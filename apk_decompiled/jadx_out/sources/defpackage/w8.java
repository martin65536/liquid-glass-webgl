package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class w8 extends bd0 {
    public b21 s;
    public final /* synthetic */ x8 t;

    public w8(x8 x8Var) {
        this.t = x8Var;
    }

    public final void D0() {
        c cVar = new c(1, this, this.t);
        z40 E = k81.E(this);
        int i = E.f;
        yo0 rectManager = ((b4) c50.a(E)).getRectManager();
        c21 c21Var = rectManager.c;
        c21Var.getClass();
        he0 he0Var = c21Var.a;
        b21 b21Var = new b21(c21Var, i, this, cVar);
        Object b = he0Var.b(i);
        if (b == null) {
            he0Var.h(i, b21Var);
            b = b21Var;
        }
        b21 b21Var2 = (b21) b;
        if (b21Var2 != b21Var) {
            while (true) {
                b21 b21Var3 = b21Var2.d;
                if (b21Var3 == null) {
                    break;
                } else {
                    b21Var2 = b21Var3;
                }
            }
            b21Var2.d = b21Var;
        }
        if (k81.E(this.e).k) {
            rectManager.b.f(i, true);
        }
        rectManager.e = true;
        rectManager.i();
        this.s = b21Var;
    }

    @Override // defpackage.bd0
    public final void t0() {
        x8 x8Var = this.t;
        x8Var.a = this;
        if (x8Var.b != null) {
            D0();
        }
    }

    @Override // defpackage.bd0
    public final void v0() {
        x8 x8Var = this.t;
        if (x8Var.a == this) {
            x8Var.a = null;
        }
        b21 b21Var = this.s;
        if (b21Var != null) {
            b21Var.b();
        }
        this.s = null;
    }
}
