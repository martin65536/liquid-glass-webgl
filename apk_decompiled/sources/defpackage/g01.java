package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class g01 extends iq0 implements kv {
    public long g;
    public int h;
    public /* synthetic */ Object i;
    public final /* synthetic */ um0 j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public g01(um0 um0Var, ij ijVar) {
        super(ijVar);
        this.j = um0Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((g01) i((ij) obj2, (xz0) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        g01 g01Var = new g01(this.j, ijVar);
        g01Var.i = obj;
        return g01Var;
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0047 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003e A[RETURN] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:8:0x003c -> B:5:0x003f). Please report as a decompilation issue!!! */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r7) {
        /*
            r6 = this;
            int r0 = r6.h
            r1 = 1
            if (r0 == 0) goto L18
            if (r0 != r1) goto L11
            long r2 = r6.g
            java.lang.Object r0 = r6.i
            xz0 r0 = (defpackage.xz0) r0
            defpackage.o30.x(r7)
            goto L3f
        L11:
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r6)
            r6 = 0
            return r6
        L18:
            defpackage.o30.x(r7)
            java.lang.Object r7 = r6.i
            xz0 r7 = (defpackage.xz0) r7
            um0 r0 = r6.j
            long r2 = r0.b
            l51 r0 = r7.E()
            r0.getClass()
            r4 = 40
            long r4 = r4 + r2
            r0 = r7
            r2 = r4
        L2f:
            r6.i = r0
            r6.g = r2
            r6.h = r1
            r7 = 3
            java.lang.Object r7 = defpackage.o01.b(r0, r6, r7)
            ik r4 = defpackage.ik.e
            if (r7 != r4) goto L3f
            return r4
        L3f:
            um0 r7 = (defpackage.um0) r7
            long r4 = r7.b
            int r4 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r4 < 0) goto L2f
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.g01.k(java.lang.Object):java.lang.Object");
    }
}
