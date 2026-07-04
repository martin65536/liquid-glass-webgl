package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class i extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ je0 k;
    public final /* synthetic */ on0 l;
    public final /* synthetic */ de m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ i(je0 je0Var, on0 on0Var, de deVar, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = je0Var;
        this.l = on0Var;
        this.m = deVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((i) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((i) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        switch (this.i) {
            case 0:
                return new i(this.k, this.l, this.m, ijVar, 0);
            default:
                return new i(this.k, this.l, this.m, ijVar, 1);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003d, code lost:
    
        if (r3.a(r9, r10) == r6) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:?, code lost:
    
        return r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0034, code lost:
    
        if (defpackage.f31.r(r4, r10) == r6) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x006d, code lost:
    
        if (r3.a(r9, r10) == r6) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:?, code lost:
    
        return r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0064, code lost:
    
        if (defpackage.f31.r(r4, r10) == r6) goto L31;
     */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r11) {
        /*
            r10 = this;
            int r0 = r10.i
            x31 r1 = defpackage.x31.a
            de r2 = r10.m
            je0 r3 = r10.k
            r4 = 0
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            ik r6 = defpackage.ik.e
            r7 = 1
            r8 = 2
            on0 r9 = r10.l
            switch(r0) {
                case 0: goto L44;
                default: goto L14;
            }
        L14:
            int r0 = r10.j
            if (r0 == 0) goto L29
            if (r0 == r7) goto L25
            if (r0 != r8) goto L20
            defpackage.o30.x(r11)
            goto L41
        L20:
            defpackage.v7.o(r5)
            r1 = r4
            goto L43
        L25:
            defpackage.o30.x(r11)
            goto L37
        L29:
            defpackage.o30.x(r11)
            long r4 = defpackage.ee.a
            r10.j = r7
            java.lang.Object r11 = defpackage.f31.r(r4, r10)
            if (r11 != r6) goto L37
            goto L3f
        L37:
            r10.j = r8
            java.lang.Object r10 = r3.a(r9, r10)
            if (r10 != r6) goto L41
        L3f:
            r1 = r6
            goto L43
        L41:
            r2.E = r9
        L43:
            return r1
        L44:
            int r0 = r10.j
            if (r0 == 0) goto L59
            if (r0 == r7) goto L55
            if (r0 != r8) goto L50
            defpackage.o30.x(r11)
            goto L71
        L50:
            defpackage.v7.o(r5)
            r1 = r4
            goto L73
        L55:
            defpackage.o30.x(r11)
            goto L67
        L59:
            defpackage.o30.x(r11)
            long r4 = defpackage.ee.a
            r10.j = r7
            java.lang.Object r11 = defpackage.f31.r(r4, r10)
            if (r11 != r6) goto L67
            goto L6f
        L67:
            r10.j = r8
            java.lang.Object r10 = r3.a(r9, r10)
            if (r10 != r6) goto L71
        L6f:
            r1 = r6
            goto L73
        L71:
            r2.I = r9
        L73:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.i.k(java.lang.Object):java.lang.Object");
    }
}
