package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class uh0 extends yi0 {
    public static final uh0 c = new yi0(0, 2, 1);

    @Override // defpackage.yi0
    public final void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        int i;
        c4 c4Var;
        a20 a20Var = (a20) aj0Var.b(1);
        if (a20Var != null) {
            i = a20Var.a;
        } else {
            i = 0;
        }
        cd cdVar = (cd) aj0Var.b(0);
        if (i > 0) {
            t7Var = new dh0(t7Var, i);
        }
        if (zi0Var != null) {
            c4Var = new c4(15, zi0Var, uw0Var);
        } else {
            c4Var = null;
        }
        cdVar.L(t7Var, uw0Var, mp0Var, c4Var);
    }
}
