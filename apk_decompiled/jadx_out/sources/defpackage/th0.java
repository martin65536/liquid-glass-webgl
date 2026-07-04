package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class th0 extends yi0 {
    public static final th0 c = new yi0(0, 2, 1);

    @Override // defpackage.yi0
    public final void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        wv wvVar = (wv) aj0Var.b(0);
        Object b = aj0Var.b(1);
        if (b instanceof gw) {
            gw gwVar = (gw) b;
            mp0Var.e.b(gwVar);
            mp0Var.d.a(gwVar);
        }
        if (uw0Var.n != 0) {
            rh.a("Can only append a slot if not current inserting");
        }
        int i = uw0Var.i;
        int i2 = uw0Var.j;
        int c2 = uw0Var.c(wvVar);
        int g = uw0Var.g(uw0Var.b, uw0Var.r(c2 + 1));
        uw0Var.i = g;
        uw0Var.j = g;
        uw0Var.x(1, c2);
        if (i >= g) {
            i++;
            i2++;
        }
        uw0Var.c[g] = b;
        uw0Var.i = i;
        uw0Var.j = i2;
    }
}
