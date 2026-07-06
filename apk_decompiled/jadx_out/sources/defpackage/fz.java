package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class fz {
    public static final a01 a = new a01(new c2(10));
    public static final a01 b = new a01(new c2(11));
    public static final a01 c = new a01(new c2(12));
    public static final c4 d = new c4(2);

    public static final Object a(String str, String str2, gl glVar, gv gvVar, sz0 sz0Var) {
        dz dzVar = new dz(gvVar, glVar, str, null);
        c4 c4Var = d;
        c4Var.getClass();
        return dl.q(new n8(c4Var, str2, dzVar, null), sz0Var);
    }

    public static final o5 b(zp zpVar, bw bwVar) {
        gl glVar;
        zp zpVar2;
        qy0 qy0Var = gq0.a;
        qy0Var.getClass();
        x4.a(bwVar, 0);
        gl glVar2 = (gl) bwVar.j(qy0Var);
        ((dq0) bwVar.j(eq0.b)).getClass();
        cq0 a2 = dq0.a(bwVar);
        Object L = bwVar.L();
        dt0 dt0Var = ph.a;
        if (L == dt0Var) {
            L = new c2(9);
            bwVar.f0(L);
        }
        vu vuVar = (vu) L;
        boolean f = bwVar.f(zpVar) | bwVar.f(a2) | bwVar.h(glVar2);
        Object L2 = bwVar.L();
        if (!f && L2 != dt0Var) {
            glVar = glVar2;
            zpVar2 = zpVar;
        } else {
            glVar = glVar2;
            zpVar2 = zpVar;
            bh bhVar = new bh(zpVar2, a2, glVar, null, 3);
            bwVar.f0(bhVar);
            L2 = bhVar;
        }
        return (o5) n30.C(zpVar2, glVar, a2, vuVar, (kv) L2, bwVar).getValue();
    }
}
