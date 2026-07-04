package defpackage;

import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fr0 extends w40 {
    public static final fr0 b = new fr0(0);
    public final /* synthetic */ int a;

    public /* synthetic */ fr0(int i) {
        this.a = i;
    }

    @Override // defpackage.pc0
    public final qc0 e(rc0 rc0Var, List list, long j) {
        switch (this.a) {
            case 0:
                int size = list.size();
                fr frVar = fr.e;
                if (size != 0) {
                    if (size != 1) {
                        ArrayList arrayList = new ArrayList(list.size());
                        int size2 = list.size();
                        int i = 0;
                        int i2 = 0;
                        for (int i3 = 0; i3 < size2; i3++) {
                            em0 v = ((kc0) list.get(i3)).v(j);
                            i = Math.max(v.e, i);
                            i2 = Math.max(v.f, i2);
                            arrayList.add(v);
                        }
                        return rc0Var.e0(ti.f(i, j), ti.e(i2, j), frVar, new q2(19, arrayList));
                    }
                    em0 v2 = ((kc0) list.get(0)).v(j);
                    return rc0Var.e0(ti.f(v2.e, j), ti.e(v2.f, j), frVar, new p3(v2, 5));
                }
                return rc0Var.e0(si.j(j), si.i(j), frVar, oj0.j);
            default:
                throw new IllegalStateException("Undefined measure and it is required");
        }
    }
}
