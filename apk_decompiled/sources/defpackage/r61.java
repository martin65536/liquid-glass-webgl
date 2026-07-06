package defpackage;

import android.view.WindowInsets;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class r61 extends x61 {
    public final WindowInsets.Builder e;

    public r61(k71 k71Var) {
        super(k71Var);
        WindowInsets.Builder g;
        WindowInsets a = k71Var.a();
        if (a != null) {
            g = kd0.h(a);
        } else {
            g = kd0.g();
        }
        this.e = g;
    }

    @Override // defpackage.x61
    public k71 b() {
        WindowInsets build;
        a();
        build = this.e.build();
        k71 b = k71.b(build, null);
        g10[] g10VarArr = this.b;
        g71 g71Var = b.a;
        g71Var.u(g10VarArr);
        g71Var.t(null);
        g71Var.y(this.c);
        g71Var.z(this.d);
        return b;
    }

    @Override // defpackage.x61
    public void e(g10 g10Var) {
        this.e.setMandatorySystemGestureInsets(g10Var.d());
    }

    @Override // defpackage.x61
    public void f(g10 g10Var) {
        this.e.setSystemGestureInsets(g10Var.d());
    }

    @Override // defpackage.x61
    public void g(g10 g10Var) {
        this.e.setSystemWindowInsets(g10Var.d());
    }

    @Override // defpackage.x61
    public void h(g10 g10Var) {
        this.e.setTappableElementInsets(g10Var.d());
    }

    public r61() {
        this.e = kd0.g();
    }
}
