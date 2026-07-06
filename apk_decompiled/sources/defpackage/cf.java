package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class cf {
    public static final ef a = new ef(o20.b, x1.r);

    public static final ef a(y7 y7Var, z9 z9Var, bw bwVar, int i) {
        boolean z;
        if (y7Var.equals(o20.b) && z9Var.equals(x1.r)) {
            bwVar.V(-1446604504);
            bwVar.p(false);
            return a;
        }
        bwVar.V(-1446550657);
        boolean z2 = true;
        if ((((i & 14) ^ 6) > 4 && bwVar.f(y7Var)) || (i & 6) == 4) {
            z = true;
        } else {
            z = false;
        }
        if ((((i & 112) ^ 48) <= 32 || !bwVar.f(z9Var)) && (i & 48) != 32) {
            z2 = false;
        }
        boolean z3 = z | z2;
        Object L = bwVar.L();
        if (z3 || L == ph.a) {
            L = new ef(y7Var, z9Var);
            bwVar.f0(L);
        }
        ef efVar = (ef) L;
        bwVar.p(false);
        return efVar;
    }
}
