package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class w00 extends ng0 {
    public static final r5 W;
    public final e01 U;
    public v00 V;

    static {
        r5 f = o4.f();
        f.c(se.d);
        f.a.setStrokeWidth(1.0f);
        f.e(1);
        W = f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [e01, bd0] */
    /* JADX WARN: Type inference failed for: r3v4, types: [qb0] */
    public w00(z40 z40Var) {
        super(z40Var);
        v00 v00Var;
        ?? bd0Var = new bd0();
        bd0Var.h = 0;
        this.U = bd0Var;
        bd0Var.l = this;
        if (z40Var.l != null) {
            v00Var = new qb0(this);
        } else {
            v00Var = null;
        }
        this.V = v00Var;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [v00, qb0] */
    @Override // defpackage.ng0
    public final void K0() {
        if (this.V == null) {
            this.V = new qb0(this);
        }
    }

    @Override // defpackage.ng0
    public final qb0 N0() {
        return this.V;
    }

    @Override // defpackage.ng0
    public final bd0 P0() {
        return this.U;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13, types: [bd0] */
    /* JADX WARN: Type inference failed for: r5v14, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9, types: [bd0] */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [ef0] */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9, types: [ef0] */
    @Override // defpackage.ng0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void V0(defpackage.rt r18, long r19, defpackage.py r21, int r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 342
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.w00.V0(rt, long, py, int, boolean):void");
    }

    @Override // defpackage.ng0
    public final void f1(uc ucVar, hx hxVar) {
        z40 z40Var = this.s;
        mj0 a = c50.a(z40Var);
        ef0 v = z40Var.v();
        Object[] objArr = v.e;
        int i = v.g;
        for (int i2 = 0; i2 < i; i2++) {
            z40 z40Var2 = (z40) objArr[i2];
            if (z40Var2.F()) {
                z40Var2.i(ucVar, hxVar);
            }
        }
        if (((b4) a).getShowLayoutBounds()) {
            long j = this.g;
            ucVar.m(0.5f, 0.5f, ((int) (j >> 32)) - 0.5f, ((int) (j & 4294967295L)) - 0.5f, W);
        }
    }

    @Override // defpackage.em0
    public final void i0(long j, float f, gv gvVar) {
        g1(j, f, gvVar);
        if (this.n) {
            return;
        }
        this.s.I.p.q0();
    }

    @Override // defpackage.ob0
    public final int n0(ry ryVar) {
        v00 v00Var = this.V;
        if (v00Var != null) {
            return v00Var.n0(ryVar);
        }
        oc0 oc0Var = this.s.I.p;
        v40 v40Var = oc0Var.j.d;
        a50 a50Var = oc0Var.A;
        if (v40Var == v40.e) {
            a50Var.d = true;
            if (a50Var.b) {
                oc0Var.y = true;
                oc0Var.z = true;
            }
        } else {
            a50Var.e = true;
        }
        w00 I = oc0Var.I();
        boolean z = I.o;
        I.o = true;
        oc0Var.N();
        I.o = z;
        Integer num = (Integer) a50Var.g.get(ryVar);
        if (num != null) {
            return num.intValue();
        }
        return Integer.MIN_VALUE;
    }

    @Override // defpackage.kc0
    public final em0 v(long j) {
        l0(j);
        z40 z40Var = this.s;
        ef0 w = z40Var.w();
        Object[] objArr = w.e;
        int i = w.g;
        for (int i2 = 0; i2 < i; i2++) {
            ((z40) objArr[i2]).I.p.p = x40.g;
        }
        j1(z40Var.z.e(this, z40Var.I.p.m0(), j));
        a1();
        return this;
    }
}
