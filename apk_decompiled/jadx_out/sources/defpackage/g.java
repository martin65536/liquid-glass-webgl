package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class g extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ long k;
    public Object l;
    public final /* synthetic */ Object m;
    public final /* synthetic */ Object n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ g(Object obj, long j, Object obj2, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.m = obj;
        this.k = j;
        this.n = obj2;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                return ((g) i((ij) obj2, (hk) obj)).k(x31Var);
            default:
                return ((g) i((ij) obj2, (fu0) obj)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        Object obj2 = this.n;
        Object obj3 = this.m;
        switch (i) {
            case 0:
                return new g((d30) obj3, this.k, (je0) obj2, ijVar, 0);
            default:
                g gVar = new g((hu0) obj3, this.k, (bp0) obj2, ijVar, 1);
                gVar.l = obj;
                return gVar;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r18) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.g.k(java.lang.Object):java.lang.Object");
    }
}
