package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class qg0 {
    public final ef0 a = new ef0(new ig0[16]);
    public final pe0 b = new pe0(10);

    public boolean a(kb0 kb0Var, l40 l40Var, c4 c4Var, boolean z) {
        ef0 ef0Var = this.a;
        Object[] objArr = ef0Var.e;
        int i = ef0Var.g;
        boolean z2 = false;
        for (int i2 = 0; i2 < i; i2++) {
            if (!((ig0) objArr[i2]).a(kb0Var, l40Var, c4Var, z) && !z2) {
                z2 = false;
            } else {
                z2 = true;
            }
        }
        return z2;
    }

    public void b(c4 c4Var) {
        ef0 ef0Var = this.a;
        int i = ef0Var.g;
        while (true) {
            i--;
            if (-1 < i) {
                if (((ig0) ef0Var.e[i]).d.a == 0) {
                    ef0Var.k(i);
                }
            } else {
                return;
            }
        }
    }
}
