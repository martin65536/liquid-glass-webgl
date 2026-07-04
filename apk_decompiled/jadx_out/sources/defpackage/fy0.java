package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fy0 extends sz0 implements lv {
    public int i;
    public /* synthetic */ ps j;
    public /* synthetic */ int k;
    public final /* synthetic */ gy0 l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public fy0(gy0 gy0Var, ij ijVar) {
        super(3, ijVar);
        this.l = gy0Var;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj2).intValue();
        fy0 fy0Var = new fy0(this.l, (ij) obj3);
        fy0Var.j = (ps) obj;
        fy0Var.k = intValue;
        return fy0Var.k(x31.a);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x007b, code lost:
    
        if (r0.g(defpackage.gw0.g, r8) == r7) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x006e, code lost:
    
        if (defpackage.f31.r(Long.MAX_VALUE, r8) == r7) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005e, code lost:
    
        if (r0.g(defpackage.gw0.f, r8) == r7) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0044, code lost:
    
        if (r0.g(defpackage.gw0.e, r8) == r7) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0051, code lost:
    
        if (defpackage.f31.r(0, r8) == r7) goto L32;
     */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r9) {
        /*
            r8 = this;
            int r0 = r8.i
            r1 = 0
            r2 = 5
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            ik r7 = defpackage.ik.e
            if (r0 == 0) goto L33
            if (r0 == r6) goto L2f
            if (r0 == r5) goto L29
            if (r0 == r4) goto L23
            if (r0 == r3) goto L1d
            if (r0 != r2) goto L17
            goto L2f
        L17:
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r8)
            return r1
        L1d:
            ps r0 = r8.j
            defpackage.o30.x(r9)
            goto L71
        L23:
            ps r0 = r8.j
            defpackage.o30.x(r9)
            goto L61
        L29:
            ps r0 = r8.j
            defpackage.o30.x(r9)
            goto L54
        L2f:
            defpackage.o30.x(r9)
            goto L7e
        L33:
            defpackage.o30.x(r9)
            ps r0 = r8.j
            int r9 = r8.k
            if (r9 <= 0) goto L47
            r8.i = r6
            gw0 r9 = defpackage.gw0.e
            java.lang.Object r8 = r0.g(r9, r8)
            if (r8 != r7) goto L7e
            goto L7d
        L47:
            r8.j = r0
            r8.i = r5
            r5 = 0
            java.lang.Object r9 = defpackage.f31.r(r5, r8)
            if (r9 != r7) goto L54
            goto L7d
        L54:
            r8.j = r0
            r8.i = r4
            gw0 r9 = defpackage.gw0.f
            java.lang.Object r9 = r0.g(r9, r8)
            if (r9 != r7) goto L61
            goto L7d
        L61:
            r8.j = r0
            r8.i = r3
            r3 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            java.lang.Object r9 = defpackage.f31.r(r3, r8)
            if (r9 != r7) goto L71
            goto L7d
        L71:
            r8.j = r1
            r8.i = r2
            gw0 r9 = defpackage.gw0.g
            java.lang.Object r8 = r0.g(r9, r8)
            if (r8 != r7) goto L7e
        L7d:
            return r7
        L7e:
            x31 r8 = defpackage.x31.a
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fy0.k(java.lang.Object):java.lang.Object");
    }
}
