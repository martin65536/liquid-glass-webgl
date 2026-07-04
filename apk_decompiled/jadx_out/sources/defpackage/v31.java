package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class v31 extends ct0 {
    public final ThreadLocal k;
    private volatile boolean threadLocalIsSet;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public v31(defpackage.yj r3, defpackage.sz0 r4) {
        /*
            r2 = this;
            tc r0 = defpackage.tc.g
            wj r1 = r3.j(r0)
            if (r1 != 0) goto Ld
            yj r0 = r3.i(r0)
            goto Le
        Ld:
            r0 = r3
        Le:
            r2.<init>(r4, r0)
            java.lang.ThreadLocal r0 = new java.lang.ThreadLocal
            r0.<init>()
            r2.k = r0
            yj r4 = r4.f
            r4.getClass()
            x1 r0 = defpackage.x1.A
            wj r4 = r4.j(r0)
            boolean r4 = r4 instanceof defpackage.ak
            if (r4 != 0) goto L32
            r4 = 0
            java.lang.Object r4 = defpackage.k81.Q(r3, r4)
            defpackage.k81.G(r3, r4)
            r2.q0(r3, r4)
        L32:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.v31.<init>(yj, sz0):void");
    }

    @Override // defpackage.ct0, defpackage.l30
    public final void B(Object obj) {
        if (this.threadLocalIsSet) {
            xj0 xj0Var = (xj0) this.k.get();
            if (xj0Var != null) {
                k81.G((yj) xj0Var.e, xj0Var.f);
            }
            this.k.remove();
        }
        Object y = o20.y(obj);
        ij ijVar = this.j;
        yj r = ijVar.r();
        v31 v31Var = null;
        Object Q = k81.Q(r, null);
        if (Q != k81.o) {
            v31Var = f31.W(ijVar, r, Q);
        }
        try {
            this.j.u(y);
            if (v31Var != null && !v31Var.p0()) {
                return;
            }
            k81.G(r, Q);
        } catch (Throwable th) {
            if (v31Var == null || v31Var.p0()) {
                k81.G(r, Q);
            }
            throw th;
        }
    }

    public final boolean p0() {
        boolean z;
        if (this.threadLocalIsSet && this.k.get() == null) {
            z = true;
        } else {
            z = false;
        }
        this.k.remove();
        return !z;
    }

    public final void q0(yj yjVar, Object obj) {
        this.threadLocalIsSet = true;
        this.k.set(new xj0(yjVar, obj));
    }
}
