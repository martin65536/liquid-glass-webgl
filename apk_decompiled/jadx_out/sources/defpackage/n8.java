package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n8 extends sz0 implements kv {
    public qf0 i;
    public c4 j;
    public String k;
    public dz l;
    public int m;
    public /* synthetic */ Object n;
    public final /* synthetic */ c4 o;
    public final /* synthetic */ String p;
    public final /* synthetic */ dz q;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public n8(c4 c4Var, String str, dz dzVar, ij ijVar) {
        super(2, ijVar);
        this.o = c4Var;
        this.p = str;
        this.q = dzVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((n8) i((ij) obj2, (hk) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        n8 n8Var = new n8(this.o, this.p, this.q, ijVar);
        n8Var.n = obj;
        return n8Var;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0049, code lost:
    
        if (r8.e(r12) == r5) goto L29;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a7 A[RETURN] */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r13) {
        /*
            r12 = this;
            java.lang.Object r0 = r12.n
            hk r0 = (defpackage.hk) r0
            int r1 = r12.m
            r2 = 2
            r3 = 1
            r4 = 0
            ik r5 = defpackage.ik.e
            if (r1 == 0) goto L2b
            if (r1 == r3) goto L1f
            if (r1 != r2) goto L19
            qf0 r12 = r12.i
            gm r12 = (defpackage.gm) r12
            defpackage.o30.x(r13)
            return r13
        L19:
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r12)
            return r4
        L1f:
            dz r1 = r12.l
            java.lang.String r6 = r12.k
            c4 r7 = r12.j
            qf0 r8 = r12.i
            defpackage.o30.x(r13)
            goto L4c
        L2b:
            defpackage.o30.x(r13)
            c4 r7 = r12.o
            java.lang.Object r13 = r7.f
            r8 = r13
            qf0 r8 = (defpackage.qf0) r8
            r12.n = r0
            r12.i = r8
            r12.j = r7
            java.lang.String r6 = r12.p
            r12.k = r6
            dz r1 = r12.q
            r12.l = r1
            r12.m = r3
            java.lang.Object r13 = r8.e(r12)
            if (r13 != r5) goto L4c
            goto La6
        L4c:
            java.lang.Object r13 = r7.g     // Catch: java.lang.Throwable -> La8
            java.util.LinkedHashMap r13 = (java.util.LinkedHashMap) r13     // Catch: java.lang.Throwable -> La8
            java.lang.Object r13 = r13.get(r6)     // Catch: java.lang.Throwable -> La8
            gm r13 = (defpackage.gm) r13     // Catch: java.lang.Throwable -> La8
            r9 = 0
            if (r13 == 0) goto L74
            r10 = r13
            l30 r10 = (defpackage.l30) r10     // Catch: java.lang.Throwable -> La8
            java.lang.Object r10 = r10.Q()     // Catch: java.lang.Throwable -> La8
            boolean r11 = r10 instanceof defpackage.qf     // Catch: java.lang.Throwable -> La8
            if (r11 != 0) goto L72
            boolean r11 = r10 instanceof defpackage.k30     // Catch: java.lang.Throwable -> La8
            if (r11 == 0) goto L71
            k30 r10 = (defpackage.k30) r10     // Catch: java.lang.Throwable -> La8
            boolean r10 = r10.f()     // Catch: java.lang.Throwable -> La8
            if (r10 == 0) goto L71
            goto L72
        L71:
            r3 = r9
        L72:
            if (r3 == 0) goto L91
        L74:
            kk r13 = defpackage.kk.f     // Catch: java.lang.Throwable -> La8
            m8 r3 = new m8     // Catch: java.lang.Throwable -> La8
            r3.<init>(r1, r4, r9)     // Catch: java.lang.Throwable -> La8
            cr r1 = defpackage.cr.e     // Catch: java.lang.Throwable -> La8
            yj r0 = defpackage.f31.I(r0, r1)     // Catch: java.lang.Throwable -> La8
            r50 r1 = new r50     // Catch: java.lang.Throwable -> La8
            r1.<init>(r0, r3)     // Catch: java.lang.Throwable -> La8
            r1.o0(r13, r1, r3)     // Catch: java.lang.Throwable -> La8
            java.lang.Object r13 = r7.g     // Catch: java.lang.Throwable -> La8
            java.util.LinkedHashMap r13 = (java.util.LinkedHashMap) r13     // Catch: java.lang.Throwable -> La8
            r13.put(r6, r1)     // Catch: java.lang.Throwable -> La8
            r13 = r1
        L91:
            r8.g(r4)
            r12.n = r4
            r12.i = r4
            r12.j = r4
            r12.k = r4
            r12.l = r4
            r12.m = r2
            java.lang.Object r12 = r13.c(r12)
            if (r12 != r5) goto La7
        La6:
            return r5
        La7:
            return r12
        La8:
            r12 = move-exception
            r8.g(r4)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.n8.k(java.lang.Object):java.lang.Object");
    }
}
