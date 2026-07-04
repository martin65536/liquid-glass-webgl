package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mf0 extends sz0 implements kv {
    public qf0 i;
    public Object j;
    public Object k;
    public nf0 l;
    public int m;
    public /* synthetic */ Object n;
    public final /* synthetic */ gf0 o;
    public final /* synthetic */ nf0 p;
    public final /* synthetic */ kv q;
    public final /* synthetic */ Object r;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public mf0(gf0 gf0Var, nf0 nf0Var, kv kvVar, Object obj, ij ijVar) {
        super(2, ijVar);
        this.o = gf0Var;
        this.p = nf0Var;
        this.q = kvVar;
        this.r = obj;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((mf0) i((ij) obj2, (hk) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        mf0 mf0Var = new mf0(this.o, this.p, this.q, this.r, ijVar);
        mf0Var.n = obj;
        return mf0Var;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstInlineVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected instance arg in invoke
        	at jadx.core.dex.visitors.ConstInlineVisitor.addExplicitCast(ConstInlineVisitor.java:285)
        	at jadx.core.dex.visitors.ConstInlineVisitor.replaceArg(ConstInlineVisitor.java:267)
        	at jadx.core.dex.visitors.ConstInlineVisitor.replaceConst(ConstInlineVisitor.java:177)
        	at jadx.core.dex.visitors.ConstInlineVisitor.checkInsn(ConstInlineVisitor.java:110)
        	at jadx.core.dex.visitors.ConstInlineVisitor.process(ConstInlineVisitor.java:55)
        	at jadx.core.dex.visitors.ConstInlineVisitor.visit(ConstInlineVisitor.java:47)
        */
    @Override // defpackage.s9
    public final java.lang.Object k(java.lang.Object r9) {
        /*
            r8 = this;
            int r0 = r8.m
            r1 = 2
            r2 = 1
            r3 = 0
            ik r4 = defpackage.ik.e
            if (r0 == 0) goto L3c
            if (r0 == r2) goto L25
            if (r0 != r1) goto L1f
            java.lang.Object r0 = r8.j
            nf0 r0 = (defpackage.nf0) r0
            qf0 r1 = r8.i
            java.lang.Object r8 = r8.n
            if0 r8 = (defpackage.if0) r8
            defpackage.o30.x(r9)     // Catch: java.lang.Throwable -> L1c
            goto L90
        L1c:
            r9 = move-exception
            goto Lab
        L1f:
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r8)
            return r3
        L25:
            nf0 r0 = r8.l
            java.lang.Object r2 = r8.k
            java.lang.Object r5 = r8.j
            kv r5 = (defpackage.kv) r5
            qf0 r6 = r8.i
            java.lang.Object r7 = r8.n
            if0 r7 = (defpackage.if0) r7
            defpackage.o30.x(r9)
            r9 = r6
            r6 = r5
            r5 = r9
            r9 = r0
            r0 = r7
            goto L78
        L3c:
            defpackage.o30.x(r9)
            java.lang.Object r9 = r8.n
            hk r9 = (defpackage.hk) r9
            if0 r0 = new if0
            yj r9 = r9.g()
            x1 r5 = defpackage.x1.L
            wj r9 = r9.j(r5)
            r9.getClass()
            d30 r9 = (defpackage.d30) r9
            gf0 r5 = r8.o
            r0.<init>(r5, r9)
            nf0 r9 = r8.p
            defpackage.nf0.a(r9, r0)
            qf0 r5 = r9.b
            r8.n = r0
            r8.i = r5
            kv r6 = r8.q
            r8.j = r6
            java.lang.Object r7 = r8.r
            r8.k = r7
            r8.l = r9
            r8.m = r2
            java.lang.Object r2 = r5.e(r8)
            if (r2 != r4) goto L77
            goto L8a
        L77:
            r2 = r7
        L78:
            r8.n = r0     // Catch: java.lang.Throwable -> La5
            r8.i = r5     // Catch: java.lang.Throwable -> La5
            r8.j = r9     // Catch: java.lang.Throwable -> La5
            r8.k = r3     // Catch: java.lang.Throwable -> La5
            r8.l = r3     // Catch: java.lang.Throwable -> La5
            r8.m = r1     // Catch: java.lang.Throwable -> La5
            java.lang.Object r8 = r6.d(r2, r8)     // Catch: java.lang.Throwable -> La5
            if (r8 != r4) goto L8b
        L8a:
            return r4
        L8b:
            r1 = r9
            r9 = r8
            r8 = r0
            r0 = r1
            r1 = r5
        L90:
            java.util.concurrent.atomic.AtomicReference r0 = r0.a     // Catch: java.lang.Throwable -> La3
        L92:
            boolean r2 = r0.compareAndSet(r8, r3)     // Catch: java.lang.Throwable -> La3
            if (r2 == 0) goto L99
            goto L9f
        L99:
            java.lang.Object r2 = r0.get()     // Catch: java.lang.Throwable -> La3
            if (r2 == r8) goto L92
        L9f:
            r1.g(r3)
            return r9
        La3:
            r8 = move-exception
            goto Lbb
        La5:
            r8 = move-exception
            r1 = r9
            r9 = r8
            r8 = r0
            r0 = r1
            r1 = r5
        Lab:
            java.util.concurrent.atomic.AtomicReference r0 = r0.a     // Catch: java.lang.Throwable -> La3
        Lad:
            boolean r2 = r0.compareAndSet(r8, r3)     // Catch: java.lang.Throwable -> La3
            if (r2 != 0) goto Lba
            java.lang.Object r2 = r0.get()     // Catch: java.lang.Throwable -> La3
            if (r2 != r8) goto Lba
            goto Lad
        Lba:
            throw r9     // Catch: java.lang.Throwable -> La3
        Lbb:
            r1.g(r3)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.mf0.k(java.lang.Object):java.lang.Object");
    }
}
