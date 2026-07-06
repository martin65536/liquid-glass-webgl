package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class js0 implements h80, AutoCloseable {
    public final String e;
    public final is0 f;
    public boolean g;

    public js0(String str, is0 is0Var) {
        this.e = str;
        this.f = is0Var;
    }

    public final void g(c4 c4Var, l80 l80Var) {
        c4Var.getClass();
        l80Var.getClass();
        if (!this.g) {
            this.g = true;
            l80Var.a(this);
            c4Var.u(this.e, (vf) this.f.a.e);
            return;
        }
        v7.o("Already attached to lifecycleOwner");
    }

    @Override // defpackage.h80
    public final void h(j80 j80Var, z70 z70Var) {
        if (z70Var == z70.ON_DESTROY) {
            this.g = false;
            j80Var.f().f(this);
        }
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
    }
}
