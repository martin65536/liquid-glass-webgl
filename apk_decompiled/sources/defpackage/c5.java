package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c5 extends iq0 implements kv {
    public int g;
    public /* synthetic */ Object h;
    public final /* synthetic */ e5 i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public c5(e5 e5Var, ij ijVar) {
        super(ijVar);
        this.i = e5Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((c5) i((ij) obj2, (xz0) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        c5 c5Var = new c5(this.i, ijVar);
        c5Var.h = obj;
        return c5Var;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x004a, code lost:
    
        if (r13 != r5) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x004c, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0035, code lost:
    
        if (r13 == r5) goto L16;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x004a -> B:6:0x004d). Please report as a decompilation issue!!! */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r13) {
        /*
            r12 = this;
            int r0 = r12.g
            r1 = 2
            r2 = 0
            e5 r3 = r12.i
            r4 = 1
            ik r5 = defpackage.ik.e
            if (r0 == 0) goto L25
            if (r0 == r4) goto L1d
            if (r0 != r1) goto L17
            java.lang.Object r0 = r12.h
            xz0 r0 = (defpackage.xz0) r0
            defpackage.o30.x(r13)
            goto L4d
        L17:
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r12)
            return r2
        L1d:
            java.lang.Object r0 = r12.h
            xz0 r0 = (defpackage.xz0) r0
            defpackage.o30.x(r13)
            goto L38
        L25:
            defpackage.o30.x(r13)
            java.lang.Object r13 = r12.h
            r0 = r13
            xz0 r0 = (defpackage.xz0) r0
            r12.h = r0
            r12.g = r4
            java.lang.Object r13 = defpackage.o01.b(r0, r12, r1)
            if (r13 != r5) goto L38
            goto L4c
        L38:
            um0 r13 = (defpackage.um0) r13
            long r6 = r13.a
            r3.h = r6
            long r6 = r13.c
            r3.b = r6
        L42:
            r12.h = r0
            r12.g = r1
            java.lang.Object r13 = defpackage.d3.m(r0, r12)
            if (r13 != r5) goto L4d
        L4c:
            return r5
        L4d:
            pm0 r13 = (defpackage.pm0) r13
            java.util.List r13 = r13.a
            java.util.ArrayList r4 = new java.util.ArrayList
            int r6 = r13.size()
            r4.<init>(r6)
            int r6 = r13.size()
            r7 = 0
            r8 = r7
        L60:
            if (r8 >= r6) goto L73
            java.lang.Object r9 = r13.get(r8)
            r10 = r9
            um0 r10 = (defpackage.um0) r10
            boolean r10 = r10.d
            if (r10 == 0) goto L70
            r4.add(r9)
        L70:
            int r8 = r8 + 1
            goto L60
        L73:
            int r13 = r4.size()
        L77:
            if (r7 >= r13) goto L8e
            java.lang.Object r6 = r4.get(r7)
            r8 = r6
            um0 r8 = (defpackage.um0) r8
            long r8 = r8.a
            long r10 = r3.h
            boolean r8 = defpackage.n30.s(r8, r10)
            if (r8 == 0) goto L8b
            goto L8f
        L8b:
            int r7 = r7 + 1
            goto L77
        L8e:
            r6 = r2
        L8f:
            um0 r6 = (defpackage.um0) r6
            if (r6 != 0) goto L9a
            java.lang.Object r13 = defpackage.me.T(r4)
            r6 = r13
            um0 r6 = (defpackage.um0) r6
        L9a:
            if (r6 == 0) goto La4
            long r7 = r6.a
            r3.h = r7
            long r6 = r6.c
            r3.b = r6
        La4:
            boolean r13 = r4.isEmpty()
            if (r13 == 0) goto L42
            r12 = -1
            r3.h = r12
            x31 r12 = defpackage.x31.a
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.c5.k(java.lang.Object):java.lang.Object");
    }
}
