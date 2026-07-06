package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yh0 extends yi0 {
    public static final yh0 c = new yi0(0, 2, 1);

    @Override // defpackage.yi0
    public final void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        int i;
        int i2;
        a20 a20Var = (a20) aj0Var.b(0);
        int c2 = uw0Var.c((wv) aj0Var.b(1));
        if (uw0Var.t >= c2) {
            rh.a("Check failed");
        }
        m20.B(uw0Var, t7Var, c2);
        int i3 = uw0Var.t;
        int i4 = uw0Var.v;
        while (i4 >= 0 && !uw0Var.y(i4)) {
            i4 = uw0Var.E(uw0Var.b, i4);
        }
        int i5 = i4 + 1;
        int i6 = 0;
        while (i5 < i3) {
            if (uw0Var.v(i3, i5)) {
                if (uw0Var.y(i5)) {
                    i6 = 0;
                }
                i5++;
            } else {
                if (uw0Var.y(i5)) {
                    i2 = 1;
                } else {
                    i2 = uw0Var.b[(uw0Var.r(i5) * 5) + 1] & 67108863;
                }
                i6 += i2;
                i5 += uw0Var.u(i5);
            }
        }
        while (true) {
            i = uw0Var.t;
            if (i >= c2) {
                break;
            }
            if (uw0Var.v(c2, i)) {
                int i7 = uw0Var.t;
                if (i7 < uw0Var.u && (uw0Var.b[(uw0Var.r(i7) * 5) + 1] & 1073741824) != 0) {
                    t7Var.b(uw0Var.D(uw0Var.t));
                    i6 = 0;
                }
                uw0Var.P();
            } else {
                i6 += uw0Var.L();
            }
        }
        if (i != c2) {
            rh.a("Check failed");
        }
        a20Var.a = i6;
    }
}
