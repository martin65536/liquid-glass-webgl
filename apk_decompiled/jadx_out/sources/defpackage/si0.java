package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class si0 extends yi0 {
    public static final si0 c = new yi0(0, 1, 1);

    @Override // defpackage.yi0
    public final void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        il0 il0Var;
        mo0 mo0Var = (mo0) aj0Var.b(0);
        ve0 ve0Var = mp0Var.i;
        if (ve0Var != null) {
            il0Var = (il0) ve0Var.g(mo0Var);
        } else {
            il0Var = null;
        }
        if (il0Var != null) {
            ArrayList arrayList = mp0Var.j;
            if (arrayList == null) {
                arrayList = new ArrayList();
                mp0Var.j = arrayList;
            }
            arrayList.add(mp0Var.e);
            mp0Var.e = il0Var.f;
        }
    }
}
