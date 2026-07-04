package defpackage;

import android.view.View;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c01 extends i10 implements r40 {
    public f61 u;
    public gv v;
    public l71 w;

    @Override // defpackage.i10
    public final void D0() {
        this.t = new w31(this.s, this.u);
        d20.L(this, "androidx.compose.foundation.layout.ConsumedInsetsProvider", new h10(this, 0));
        m20.v(this);
    }

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        final int a = this.t.a(ob0Var, ob0Var.getLayoutDirection()) - this.s.a(ob0Var, ob0Var.getLayoutDirection());
        final int b = this.t.b(ob0Var) - this.s.b(ob0Var);
        int c = (this.t.c(ob0Var, ob0Var.getLayoutDirection()) - this.s.c(ob0Var, ob0Var.getLayoutDirection())) + a;
        int d = (this.t.d(ob0Var) - this.s.d(ob0Var)) + b;
        final em0 v = kc0Var.v(ti.h(-c, -d, j));
        return ob0Var.e0(ti.f(v.e + c, j), ti.e(v.f + d, j), fr.e, new gv() { // from class: l10
            @Override // defpackage.gv
            public final Object e(Object obj) {
                dm0.z((dm0) obj, em0.this, a, b);
                return x31.a;
            }
        });
    }

    @Override // defpackage.i10, defpackage.bd0
    public final void t0() {
        View Y = o4.Y(this);
        WeakHashMap weakHashMap = l71.v;
        l71 g = ey0.g(Y);
        g.a(Y);
        f61 f61Var = (f61) this.v.e(g);
        if (!o20.e(f61Var, this.u)) {
            this.u = f61Var;
            D0();
        }
        this.w = g;
        super.t0();
    }

    @Override // defpackage.i10, defpackage.bd0
    public final void v0() {
        View Y = o4.Y(this);
        l71 l71Var = this.w;
        if (l71Var != null) {
            int i = l71Var.t - 1;
            l71Var.t = i;
            if (i == 0) {
                int i2 = j51.a;
                e51.b(Y, null);
                j51.a(Y, null);
                Y.removeOnAttachStateChangeListener(l71Var.u);
            }
        }
        super.v0();
    }
}
