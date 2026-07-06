package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l50 implements fz0 {
    public final ie0 a;
    public final /* synthetic */ n50 b;
    public final /* synthetic */ Object c;

    public l50(n50 n50Var, Object obj) {
        this.b = n50Var;
        this.c = obj;
        int[] iArr = b20.a;
        this.a = new ie0();
    }

    @Override // defpackage.fz0
    public final void a() {
        n50.c(this.b, this.c);
    }

    @Override // defpackage.fz0
    public final int b() {
        z40 z40Var = (z40) this.b.n.g(this.c);
        if (z40Var != null) {
            return ((bf0) z40Var.m()).e.g;
        }
        return 0;
    }

    @Override // defpackage.fz0
    public final void c(int i, long j) {
        n50 n50Var = this.b;
        z40 z40Var = (z40) n50Var.n.g(this.c);
        if (z40Var != null && z40Var.E()) {
            int i2 = ((bf0) z40Var.m()).e.g;
            if (i < 0 || i >= i2) {
                q00.d("Index (" + i + ") is out of bound of [0, " + i2 + ')');
            }
            if (z40Var.F()) {
                q00.a("Pre-measure called on node that is not placed");
            }
            z40 z40Var2 = n50Var.e;
            z40Var2.t = true;
            ((b4) c50.a(z40Var)).y((z40) ((bf0) z40Var.m()).get(i), j);
            z40Var2.t = false;
            this.a.a(i);
        }
    }

    @Override // defpackage.fz0
    public final void d(l lVar) {
        bd0 bd0Var;
        lg0 lg0Var;
        z40 z40Var = (z40) this.b.n.g(this.c);
        if (z40Var != null && (lg0Var = z40Var.H) != null) {
            bd0Var = lg0Var.f;
        } else {
            bd0Var = null;
        }
        if (bd0Var != null && bd0Var.r) {
            d20.L(bd0Var, "androidx.compose.foundation.lazy.layout.TraversablePrefetchStateNode", lVar);
        }
    }
}
