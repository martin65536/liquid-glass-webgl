package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class us extends sz0 implements lv {
    public int i;
    public /* synthetic */ ps j;
    public /* synthetic */ Object k;
    public final /* synthetic */ kv l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public us(kv kvVar, ij ijVar) {
        super(3, ijVar);
        this.l = kvVar;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        us usVar = new us(this.l, (ij) obj3);
        usVar.j = (ps) obj;
        usVar.k = obj2;
        return usVar.k(x31.a);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0039, code lost:
    
        if (r0.g(r6, r5) == r4) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003b, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002e, code lost:
    
        if (r6 == r4) goto L15;
     */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r6) {
        /*
            r5 = this;
            int r0 = r5.i
            r1 = 0
            r2 = 2
            r3 = 1
            ik r4 = defpackage.ik.e
            if (r0 == 0) goto L1d
            if (r0 == r3) goto L17
            if (r0 != r2) goto L11
            defpackage.o30.x(r6)
            goto L3c
        L11:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r5)
            return r1
        L17:
            ps r0 = r5.j
            defpackage.o30.x(r6)
            goto L31
        L1d:
            defpackage.o30.x(r6)
            ps r0 = r5.j
            java.lang.Object r6 = r5.k
            r5.j = r0
            r5.i = r3
            kv r3 = r5.l
            java.lang.Object r6 = r3.d(r6, r5)
            if (r6 != r4) goto L31
            goto L3b
        L31:
            r5.j = r1
            r5.i = r2
            java.lang.Object r5 = r0.g(r6, r5)
            if (r5 != r4) goto L3c
        L3b:
            return r4
        L3c:
            x31 r5 = defpackage.x31.a
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.us.k(java.lang.Object):java.lang.Object");
    }
}
