package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class or0 {
    public static final pr0 a = new pr0(o20.a, x1.p);

    public static final pr0 a(w7 w7Var, bw bwVar, int i) {
        boolean z;
        aa aaVar = x1.q;
        if (w7Var.equals(o20.a) && aaVar.equals(x1.p)) {
            bwVar.V(-1073830487);
            bwVar.p(false);
            return a;
        }
        bwVar.V(-1073779616);
        if ((((i & 14) ^ 6) > 4 && bwVar.f(w7Var)) || (i & 6) == 4) {
            z = true;
        } else {
            z = false;
        }
        Object L = bwVar.L();
        if (z || L == ph.a) {
            L = new pr0(w7Var, aaVar);
            bwVar.f0(L);
        }
        pr0 pr0Var = (pr0) L;
        bwVar.p(false);
        return pr0Var;
    }
}
