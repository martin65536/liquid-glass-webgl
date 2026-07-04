package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c40 implements m9 {
    public final hx a;
    public final ik0 b;
    public x20 c;

    public c40(hx hxVar) {
        hxVar.getClass();
        this.a = hxVar;
        this.b = n30.B(null);
    }

    @Override // defpackage.m9
    public final boolean a() {
        return true;
    }

    @Override // defpackage.m9
    public final void b(up upVar, mm mmVar, l40 l40Var, gv gvVar) {
        l40 l40Var2;
        long f;
        upVar.getClass();
        mmVar.getClass();
        if (l40Var == null || (l40Var2 = (l40) this.b.getValue()) == null) {
            return;
        }
        r7 J = upVar.J();
        long v = J.v();
        J.q().h();
        try {
            j2 j2Var = (j2) J.f;
            if (gvVar != null) {
                c().r(j2Var, mmVar, gvVar);
            }
            try {
                f = l40Var2.R(l40Var, 0L);
            } catch (Exception unused) {
                f = ch0.f(l40Var.z(0L), l40Var2.z(0L));
            }
            j2Var.q(-Float.intBitsToFloat((int) (f >> 32)), -Float.intBitsToFloat((int) (f & 4294967295L)));
            n20.r(upVar, this.a);
        } finally {
            J.q().f();
            J.G(v);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [x20, java.lang.Object] */
    public final x20 c() {
        x20 x20Var = this.c;
        if (x20Var != null) {
            x20Var.e = 9205357640488583168L;
            x20Var.f = 1.0f;
            x20Var.g = 1.0f;
            x20Var.h = 1.0f;
            x20Var.i = 1.0f;
            int i = mx.b;
            x20Var.j = 0.0f;
            long j = s21.a;
            x20Var.k = null;
            return x20Var;
        }
        ?? obj = new Object();
        obj.e = 9205357640488583168L;
        obj.f = 1.0f;
        obj.g = 1.0f;
        obj.h = 1.0f;
        obj.i = 1.0f;
        int i2 = mx.b;
        long j2 = s21.a;
        this.c = obj;
        return obj;
    }

    public final void d(ng0 ng0Var) {
        this.b.setValue(ng0Var);
    }
}
