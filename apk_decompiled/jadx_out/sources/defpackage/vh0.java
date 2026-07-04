package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vh0 extends yi0 {
    public static final vh0 c = new yi0(0, 2, 1);

    @Override // defpackage.yi0
    public final void a(aj0 aj0Var, t7 t7Var, uw0 uw0Var, mp0 mp0Var, zi0 zi0Var) {
        int i = ((a20) aj0Var.b(0)).a;
        List list = (List) aj0Var.b(1);
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            int i3 = i + i2;
            t7Var.a(i3, obj);
            t7Var.d(i3, obj);
        }
    }
}
