package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class y50 extends bd0 implements r40 {
    public static final w50 v = new Object();
    public y60 s;
    public ib t;
    public dj0 u;

    /* JADX WARN: Code restructure failed: missing block: B:24:0x001b, code lost:
    
        if (r4.u == defpackage.dj0.e) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x000d, code lost:
    
        if (r4.u == defpackage.dj0.f) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean D0(defpackage.u50 r5, int r6) {
        /*
            r4 = this;
            r0 = 5
            r1 = 0
            r2 = 1
            if (r6 != r0) goto L6
            goto L9
        L6:
            r0 = 6
            if (r6 != r0) goto L10
        L9:
            dj0 r0 = r4.u
            dj0 r3 = defpackage.dj0.f
            if (r0 != r3) goto L24
            goto L3f
        L10:
            r0 = 3
            if (r6 != r0) goto L14
            goto L17
        L14:
            r0 = 4
            if (r6 != r0) goto L1e
        L17:
            dj0 r0 = r4.u
            dj0 r3 = defpackage.dj0.e
            if (r0 != r3) goto L24
            goto L3f
        L1e:
            if (r6 != r2) goto L21
            goto L24
        L21:
            r0 = 2
            if (r6 != r0) goto L40
        L24:
            boolean r6 = r4.E0(r6)
            if (r6 == 0) goto L3a
            int r5 = r5.b
            y60 r4 = r4.s
            m70 r4 = r4.a
            h70 r4 = r4.g()
            int r4 = r4.n
            int r4 = r4 - r2
            if (r5 >= r4) goto L3f
            goto L3e
        L3a:
            int r4 = r5.a
            if (r4 <= 0) goto L3f
        L3e:
            return r2
        L3f:
            return r1
        L40:
            java.lang.String r4 = "Lazy list does not support beyond bounds layout for the specified direction"
            defpackage.v7.o(r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.y50.D0(u50, int):boolean");
    }

    public final boolean E0(int i) {
        if (i == 1) {
            return false;
        }
        if (i == 2) {
            return true;
        }
        if (i == 5) {
            return false;
        }
        if (i == 6) {
            return true;
        }
        if (i == 3) {
            int ordinal = k81.E(this).B.ordinal();
            if (ordinal == 0) {
                return false;
            }
            if (ordinal == 1) {
                return true;
            }
            v7.k();
            return false;
        }
        if (i == 4) {
            int ordinal2 = k81.E(this).B.ordinal();
            if (ordinal2 == 0) {
                return true;
            }
            if (ordinal2 == 1) {
                return false;
            }
            v7.k();
            return false;
        }
        v7.o("Lazy list does not support beyond bounds layout for the specified direction");
        return false;
    }

    @Override // defpackage.r40
    public final qc0 Y(ob0 ob0Var, kc0 kc0Var, long j) {
        em0 v2 = kc0Var.v(j);
        return ob0Var.e0(v2.e, v2.f, fr.e, new k8(v2, 3));
    }
}
