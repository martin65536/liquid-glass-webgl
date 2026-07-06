package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tu0 {
    public final nu0 a;
    public final ie0 b;

    public tu0(su0 su0Var, t10 t10Var) {
        this.a = su0Var.d;
        List j = su0.j(4, su0Var);
        this.b = new ie0(j.size());
        int size = j.size();
        for (int i = 0; i < size; i++) {
            su0 su0Var2 = (su0) j.get(i);
            if (t10Var.a(su0Var2.f)) {
                this.b.a(su0Var2.f);
            }
        }
    }
}
