package defpackage;

import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class il0 implements np0 {
    public final Set e;
    public final ef0 f = new ef0(new gw[16]);

    public il0(Set set) {
        this.e = set;
    }

    @Override // defpackage.np0
    public final void d() {
        ef0 ef0Var = this.f;
        Object[] objArr = ef0Var.e;
        int i = ef0Var.g;
        for (int i2 = 0; i2 < i; i2++) {
            np0 np0Var = ((gw) objArr[i2]).a;
            this.e.remove(np0Var);
            np0Var.d();
        }
    }

    @Override // defpackage.np0
    public final void f() {
    }

    @Override // defpackage.np0
    public final void k() {
    }
}
