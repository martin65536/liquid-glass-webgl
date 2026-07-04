package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xe0 extends iq0 implements kv {
    public iw g;
    public ye0 h;
    public long[] i;
    public int j;
    public int k;
    public int l;
    public int m;
    public long n;
    public int o;
    public /* synthetic */ Object p;
    public final /* synthetic */ ye0 q;
    public final /* synthetic */ iw r;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public xe0(ye0 ye0Var, iw iwVar, ij ijVar) {
        super(ijVar);
        this.q = ye0Var;
        this.r = iwVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((xe0) i((ij) obj2, (mv0) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        xe0 xe0Var = new xe0(this.q, this.r, ijVar);
        xe0Var.p = obj;
        return xe0Var;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0066  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x004f -> B:14:0x009f). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0051 -> B:6:0x0064). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:8:0x006d -> B:5:0x0094). Please report as a decompilation issue!!! */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r21) {
        /*
            r20 = this;
            r0 = r20
            int r1 = r0.o
            r2 = 0
            r3 = 8
            r4 = 1
            if (r1 == 0) goto L2c
            if (r1 != r4) goto L25
            int r1 = r0.m
            int r5 = r0.l
            long r6 = r0.n
            int r8 = r0.k
            int r9 = r0.j
            long[] r10 = r0.i
            ye0 r11 = r0.h
            iw r12 = r0.g
            java.lang.Object r13 = r0.p
            mv0 r13 = (defpackage.mv0) r13
            defpackage.o30.x(r21)
            goto L94
        L25:
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r0)
            r0 = 0
            return r0
        L2c:
            defpackage.o30.x(r21)
            java.lang.Object r1 = r0.p
            mv0 r1 = (defpackage.mv0) r1
            ye0 r5 = r0.q
            we0 r6 = r5.f
            long[] r6 = r6.a
            int r7 = r6.length
            int r7 = r7 + (-2)
            if (r7 < 0) goto La4
            iw r8 = r0.r
            r9 = r2
        L41:
            r10 = r6[r9]
            long r12 = ~r10
            r14 = 7
            long r12 = r12 << r14
            long r12 = r12 & r10
            r14 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r12 = r12 & r14
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 == 0) goto L9f
            int r12 = r9 - r7
            int r12 = ~r12
            int r12 = r12 >>> 31
            int r12 = 8 - r12
            r13 = r1
            r1 = r2
            r18 = r10
            r11 = r5
            r10 = r6
            r5 = r12
            r12 = r8
            r8 = r9
            r9 = r7
            r6 = r18
        L64:
            if (r1 >= r5) goto L97
            r14 = 255(0xff, double:1.26E-321)
            long r14 = r14 & r6
            r16 = 128(0x80, double:6.3E-322)
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 >= 0) goto L94
            int r2 = r8 << 3
            int r2 = r2 + r1
            r12.f = r2
            we0 r3 = r11.f
            java.lang.Object[] r3 = r3.b
            r2 = r3[r2]
            r0.p = r13
            r0.g = r12
            r0.h = r11
            r0.i = r10
            r0.j = r9
            r0.k = r8
            r0.n = r6
            r0.l = r5
            r0.m = r1
            r0.o = r4
            r13.b(r0, r2)
            ik r0 = defpackage.ik.e
            return r0
        L94:
            long r6 = r6 >> r3
            int r1 = r1 + r4
            goto L64
        L97:
            if (r5 != r3) goto La4
            r7 = r9
            r6 = r10
            r5 = r11
            r1 = r13
            r9 = r8
            r8 = r12
        L9f:
            if (r9 == r7) goto La4
            int r9 = r9 + 1
            goto L41
        La4:
            x31 r0 = defpackage.x31.a
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xe0.k(java.lang.Object):java.lang.Object");
    }
}
