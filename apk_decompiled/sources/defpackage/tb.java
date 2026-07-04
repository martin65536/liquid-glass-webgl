package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tb implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;

    public /* synthetic */ tb(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        long j;
        switch (this.e) {
            case 0:
                ((rc) this.f).cancel();
                return x31.a;
            default:
                ax0 ax0Var = (ax0) obj;
                synchronized (cx0.c) {
                    j = cx0.e;
                    cx0.e = 1 + j;
                }
                return new jo0(j, ax0Var, (gv) this.f);
        }
    }
}
