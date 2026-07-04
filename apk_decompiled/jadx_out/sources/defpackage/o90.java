package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class o90 extends sz0 implements kv {
    public final /* synthetic */ int i = 1;
    public int j;
    public final /* synthetic */ long k;
    public final /* synthetic */ Object l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public o90(long j, xz0 xz0Var, ij ijVar) {
        super(2, ijVar);
        this.k = j;
        this.l = xz0Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((o90) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((o90) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        Object obj2 = this.l;
        long j = this.k;
        switch (i) {
            case 0:
                return new o90((y6) obj2, j, ijVar);
            default:
                return new o90(j, (xz0) obj2, ijVar);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x003d, code lost:
    
        if (defpackage.f31.r(8, r12) == r5) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:?, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0034, code lost:
    
        if (defpackage.f31.r(r7 - 8, r12) == r5) goto L16;
     */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r13) {
        /*
            r12 = this;
            int r0 = r12.i
            x31 r1 = defpackage.x31.a
            java.lang.Object r2 = r12.l
            r3 = 0
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            ik r5 = defpackage.ik.e
            r6 = 1
            long r7 = r12.k
            switch(r0) {
                case 0: goto L55;
                default: goto L11;
            }
        L11:
            int r0 = r12.j
            r9 = 8
            r11 = 2
            if (r0 == 0) goto L29
            if (r0 == r6) goto L25
            if (r0 != r11) goto L20
            defpackage.o30.x(r13)
            goto L41
        L20:
            defpackage.v7.o(r4)
            r1 = r3
            goto L54
        L25:
            defpackage.o30.x(r13)
            goto L37
        L29:
            defpackage.o30.x(r13)
            long r3 = r7 - r9
            r12.j = r6
            java.lang.Object r13 = defpackage.f31.r(r3, r12)
            if (r13 != r5) goto L37
            goto L3f
        L37:
            r12.j = r11
            java.lang.Object r12 = defpackage.f31.r(r9, r12)
            if (r12 != r5) goto L41
        L3f:
            r1 = r5
            goto L54
        L41:
            xz0 r2 = (defpackage.xz0) r2
            pc r12 = r2.g
            if (r12 == 0) goto L54
            rm0 r13 = new rm0
            r13.<init>(r7)
            jq0 r0 = new jq0
            r0.<init>(r13)
            r12.u(r0)
        L54:
            return r1
        L55:
            int r0 = r12.j
            if (r0 == 0) goto L64
            if (r0 != r6) goto L5f
            defpackage.o30.x(r13)
            goto L8b
        L5f:
            defpackage.v7.o(r4)
            r1 = r3
            goto L8b
        L64:
            defpackage.o30.x(r13)
            y6 r2 = (defpackage.y6) r2
            java.lang.Object r13 = r2.d()
            java.lang.Number r13 = (java.lang.Number) r13
            float r13 = r13.floatValue()
            r0 = 32
            long r3 = r7 >> r0
            int r0 = (int) r3
            float r0 = java.lang.Float.intBitsToFloat(r0)
            float r0 = r0 + r13
            java.lang.Float r13 = new java.lang.Float
            r13.<init>(r0)
            r12.j = r6
            java.lang.Object r12 = r2.e(r13, r12)
            if (r12 != r5) goto L8b
            r1 = r5
        L8b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o90.k(java.lang.Object):java.lang.Object");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public o90(y6 y6Var, long j, ij ijVar) {
        super(2, ijVar);
        this.l = y6Var;
        this.k = j;
    }
}
