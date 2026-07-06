package defpackage;

import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mi0 extends yi0 {
    public static final mi0 c = new yi0(0, 1, 1);

    @Override // defpackage.yi0
    public final void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        mo0 mo0Var = (mo0) aj0Var.b(0);
        Set set = mp0Var.a;
        if (set == null) {
            return;
        }
        il0 il0Var = new il0(set);
        ve0 ve0Var = mp0Var.i;
        if (ve0Var == null) {
            long[] jArr = zs0.a;
            ve0Var = new ve0();
            mp0Var.i = ve0Var;
        }
        ve0Var.m(mo0Var, il0Var);
        mp0Var.e.b(new gw(il0Var, -1));
    }
}
