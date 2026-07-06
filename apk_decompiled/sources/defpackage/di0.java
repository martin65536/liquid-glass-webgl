package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class di0 extends yi0 {
    public static final di0 c = new yi0(0, 1, 1);

    @Override // defpackage.yi0
    public final void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        ef0 ef0Var;
        mo0 mo0Var = (mo0) aj0Var.b(0);
        ve0 ve0Var = mp0Var.i;
        if (ve0Var != null && ((il0) ve0Var.g(mo0Var)) != null) {
            ArrayList arrayList = mp0Var.j;
            if (arrayList != null && (ef0Var = (ef0) arrayList.remove(arrayList.size() - 1)) != null) {
                mp0Var.e = ef0Var;
            }
            ve0Var.k(mo0Var);
        }
    }
}
