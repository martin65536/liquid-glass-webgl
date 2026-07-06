package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ii0 extends yi0 {
    public static final ii0 c = new yi0(0, 3, 1);

    @Override // defpackage.yi0
    public final void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        c4 c4Var;
        rw0 rw0Var = (rw0) aj0Var.b(1);
        wv wvVar = (wv) aj0Var.b(0);
        fs fsVar = (fs) aj0Var.b(2);
        uw0 d = rw0Var.d();
        if (zi0Var != null) {
            try {
                c4Var = new c4(15, zi0Var, uw0Var);
            } catch (Throwable th) {
                d.e(false);
                throw th;
            }
        } else {
            c4Var = null;
        }
        if (!fsVar.b.F()) {
            rh.a("FixupList has pending fixup operations that were not realized. Were there mismatched insertNode() and endNodeInsert() calls?");
        }
        fsVar.a.E(t7Var, d, mp0Var, c4Var);
        d.e(true);
        uw0Var.d();
        wvVar.getClass();
        uw0Var.A(rw0Var, rw0Var.a(wvVar));
        uw0Var.k();
    }
}
