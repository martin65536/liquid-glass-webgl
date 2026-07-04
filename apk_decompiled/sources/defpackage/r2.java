package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class r2 extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ y6 k;
    public final /* synthetic */ long l;
    public final /* synthetic */ y6 m;
    public final /* synthetic */ float n;
    public final /* synthetic */ y6 o;
    public final /* synthetic */ float p;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ r2(y6 y6Var, long j, y6 y6Var2, float f, y6 y6Var3, float f2, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = y6Var;
        this.l = j;
        this.m = y6Var2;
        this.n = f;
        this.o = y6Var3;
        this.p = f2;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((r2) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((r2) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        switch (this.i) {
            case 0:
                return new r2(this.k, this.l, this.m, this.n, this.o, this.p, ijVar, 0);
            default:
                return new r2(this.k, this.l, this.m, this.n, this.o, this.p, ijVar, 1);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0054, code lost:
    
        if (r6.e(r1, r16) == r12) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0046, code lost:
    
        if (r9.e(r1, r16) == r12) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x009d, code lost:
    
        if (r6.e(r1, r16) == r12) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x008f, code lost:
    
        if (r9.e(r1, r16) == r12) goto L40;
     */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r17) {
        /*
            r16 = this;
            r0 = r16
            int r1 = r0.i
            x31 r2 = defpackage.x31.a
            float r3 = r0.p
            y6 r4 = r0.o
            float r5 = r0.n
            y6 r6 = r0.m
            long r7 = r0.l
            y6 r9 = r0.k
            r10 = 0
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            ik r12 = defpackage.ik.e
            r13 = 1
            r14 = 2
            r15 = 3
            switch(r1) {
                case 0: goto L66;
                default: goto L1d;
            }
        L1d:
            int r1 = r0.j
            if (r1 == 0) goto L38
            if (r1 == r13) goto L34
            if (r1 == r14) goto L30
            if (r1 != r15) goto L2b
            defpackage.o30.x(r17)
            goto L65
        L2b:
            defpackage.v7.o(r11)
            r2 = r10
            goto L65
        L30:
            defpackage.o30.x(r17)
            goto L57
        L34:
            defpackage.o30.x(r17)
            goto L49
        L38:
            defpackage.o30.x(r17)
            ch0 r1 = new ch0
            r1.<init>(r7)
            r0.j = r13
            java.lang.Object r1 = r9.e(r1, r0)
            if (r1 != r12) goto L49
            goto L64
        L49:
            java.lang.Float r1 = new java.lang.Float
            r1.<init>(r5)
            r0.j = r14
            java.lang.Object r1 = r6.e(r1, r0)
            if (r1 != r12) goto L57
            goto L64
        L57:
            java.lang.Float r1 = new java.lang.Float
            r1.<init>(r3)
            r0.j = r15
            java.lang.Object r0 = r4.e(r1, r0)
            if (r0 != r12) goto L65
        L64:
            r2 = r12
        L65:
            return r2
        L66:
            int r1 = r0.j
            if (r1 == 0) goto L81
            if (r1 == r13) goto L7d
            if (r1 == r14) goto L79
            if (r1 != r15) goto L74
            defpackage.o30.x(r17)
            goto Lae
        L74:
            defpackage.v7.o(r11)
            r2 = r10
            goto Lae
        L79:
            defpackage.o30.x(r17)
            goto La0
        L7d:
            defpackage.o30.x(r17)
            goto L92
        L81:
            defpackage.o30.x(r17)
            ch0 r1 = new ch0
            r1.<init>(r7)
            r0.j = r13
            java.lang.Object r1 = r9.e(r1, r0)
            if (r1 != r12) goto L92
            goto Lad
        L92:
            java.lang.Float r1 = new java.lang.Float
            r1.<init>(r5)
            r0.j = r14
            java.lang.Object r1 = r6.e(r1, r0)
            if (r1 != r12) goto La0
            goto Lad
        La0:
            java.lang.Float r1 = new java.lang.Float
            r1.<init>(r3)
            r0.j = r15
            java.lang.Object r0 = r4.e(r1, r0)
            if (r0 != r12) goto Lae
        Lad:
            r2 = r12
        Lae:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.r2.k(java.lang.Object):java.lang.Object");
    }
}
