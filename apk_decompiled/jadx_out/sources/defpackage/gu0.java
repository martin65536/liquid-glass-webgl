package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gu0 extends sz0 implements kv {
    public long i;
    public int j;
    public /* synthetic */ long k;
    public final /* synthetic */ hu0 l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public gu0(hu0 hu0Var, ij ijVar) {
        super(2, ijVar);
        this.l = hu0Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        long j = ((v41) obj).a;
        gu0 gu0Var = new gu0(this.l, (ij) obj2);
        gu0Var.k = j;
        return gu0Var.k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        gu0 gu0Var = new gu0(this.l, ijVar);
        gu0Var.k = ((v41) obj).a;
        return gu0Var;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x003d, code lost:
    
        if (r15 == r5) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006e  */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r15) {
        /*
            r14 = this;
            int r0 = r14.j
            r1 = 3
            r2 = 2
            r3 = 1
            hu0 r4 = r14.l
            ik r5 = defpackage.ik.e
            if (r0 == 0) goto L2e
            if (r0 == r3) goto L28
            if (r0 == r2) goto L20
            if (r0 != r1) goto L19
            long r0 = r14.i
            long r2 = r14.k
            defpackage.o30.x(r15)
            goto L70
        L19:
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r14)
            r14 = 0
            return r14
        L20:
            long r2 = r14.i
            long r6 = r14.k
            defpackage.o30.x(r15)
            goto L56
        L28:
            long r6 = r14.k
            defpackage.o30.x(r15)
            goto L40
        L2e:
            defpackage.o30.x(r15)
            long r6 = r14.k
            e3 r15 = r4.f
            r14.k = r6
            r14.j = r3
            java.lang.Object r15 = r15.l(r6, r14)
            if (r15 != r5) goto L40
            goto L6d
        L40:
            v41 r15 = (defpackage.v41) r15
            long r8 = r15.a
            long r8 = defpackage.v41.d(r6, r8)
            r14.k = r6
            r14.i = r8
            r14.j = r2
            java.lang.Object r15 = r4.a(r8, r14)
            if (r15 != r5) goto L55
            goto L6d
        L55:
            r2 = r8
        L56:
            v41 r15 = (defpackage.v41) r15
            long r11 = r15.a
            e3 r8 = r4.f
            long r9 = defpackage.v41.d(r2, r11)
            r14.k = r6
            r14.i = r11
            r14.j = r1
            r13 = r14
            java.lang.Object r15 = r8.k(r9, r11, r13)
            if (r15 != r5) goto L6e
        L6d:
            return r5
        L6e:
            r2 = r6
            r0 = r11
        L70:
            v41 r15 = (defpackage.v41) r15
            long r14 = r15.a
            long r14 = defpackage.v41.d(r0, r14)
            long r14 = defpackage.v41.d(r2, r14)
            v41 r0 = new v41
            r0.<init>(r14)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.gu0.k(java.lang.Object):java.lang.Object");
    }
}
