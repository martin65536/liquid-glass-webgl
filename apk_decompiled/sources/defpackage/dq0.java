package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dq0 {
    public static cq0 a(bw bwVar) {
        v11 v11Var;
        qm qmVar;
        bwVar.V(1808039825);
        ta0 a = km0.a.g().a();
        boolean D = n20.D(bwVar);
        mm mmVar = (mm) bwVar.j(fi.h);
        boolean f = bwVar.f(a) | bwVar.g(D) | bwVar.f(mmVar);
        Object L = bwVar.L();
        if (f || L == ph.a) {
            a40 a40Var = new a40(a.a.getLanguage());
            hp0 hp0Var = new hp0(a.a.getCountry());
            v11.e.getClass();
            if (D) {
                v11Var = v11.g;
            } else {
                v11Var = v11.f;
            }
            float B = mmVar.B();
            qm.f.getClass();
            double d = B;
            if (d <= 0.75d) {
                qmVar = qm.g;
            } else if (d <= 1.0d) {
                qmVar = qm.h;
            } else if (d <= 1.5d) {
                qmVar = qm.i;
            } else if (d <= 2.0d) {
                qmVar = qm.j;
            } else if (d <= 3.0d) {
                qmVar = qm.k;
            } else {
                qmVar = qm.l;
            }
            L = new cq0(a40Var, hp0Var, v11Var, qmVar);
            bwVar.f0(L);
        }
        cq0 cq0Var = (cq0) L;
        bwVar.p(false);
        return cq0Var;
    }
}
