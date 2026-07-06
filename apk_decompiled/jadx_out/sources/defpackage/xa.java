package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xa implements pc0 {
    public static final xa b = new xa(0);
    public static final xa c = new xa(1);
    public static final pb d = new pb(6);
    public static final xa e = new xa(2);
    public static final xa f = new xa(3);
    public final /* synthetic */ int a;

    public /* synthetic */ xa(int i) {
        this.a = i;
    }

    @Override // defpackage.pc0
    public final qc0 e(rc0 rc0Var, List list, long j) {
        int i;
        int i2 = this.a;
        fr frVar = fr.e;
        switch (i2) {
            case 0:
                return rc0Var.e0(si.j(j), si.i(j), frVar, new pb(6));
            case 1:
                return rc0Var.e0(si.h(j), si.g(j), frVar, d);
            case 2:
                return rc0Var.e0(si.j(j), si.i(j), frVar, new pb(6));
            default:
                int i3 = 0;
                if (si.f(j)) {
                    i = si.h(j);
                } else {
                    i = 0;
                }
                if (si.e(j)) {
                    i3 = si.g(j);
                }
                return rc0Var.e0(i, i3, frVar, new pb(6));
        }
    }
}
