package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ti0 extends yi0 {
    public static final ti0 c = new yi0(1, 0, 2);

    @Override // defpackage.yi0
    public final void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        int a = aj0Var.a(0);
        int i = uw0Var.v;
        int N = uw0Var.N(uw0Var.b, uw0Var.r(i));
        int g = uw0Var.g(uw0Var.b, uw0Var.r(i + 1));
        for (int max = Math.max(N, g - a); max < g; max++) {
            Object obj = uw0Var.c[uw0Var.h(max)];
            if (obj instanceof gw) {
                mp0Var.e((gw) obj);
            } else if (obj instanceof mo0) {
                ((mo0) obj).c();
            }
        }
        if (a <= 0) {
            rh.a("Check failed");
        }
        int i2 = uw0Var.v;
        int N2 = uw0Var.N(uw0Var.b, uw0Var.r(i2));
        int g2 = uw0Var.g(uw0Var.b, uw0Var.r(i2 + 1)) - a;
        if (g2 < N2) {
            rh.a("Check failed");
        }
        uw0Var.J(g2, a, i2);
        int i3 = uw0Var.i;
        if (i3 >= N2) {
            uw0Var.i = i3 - a;
        }
    }
}
