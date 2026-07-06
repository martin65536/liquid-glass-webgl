package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ce implements lv {
    public final /* synthetic */ a00 e;
    public final /* synthetic */ cr0 f;
    public final /* synthetic */ vu g;

    public ce(a00 a00Var, cr0 cr0Var, vu vuVar) {
        this.e = a00Var;
        this.f = cr0Var;
        this.g = vuVar;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        cd0 yzVar;
        bw bwVar = (bw) obj2;
        ((Number) obj3).intValue();
        bwVar.V(-1525724089);
        Object L = bwVar.L();
        if (L == ph.a) {
            L = new je0();
            bwVar.f0(L);
        }
        je0 je0Var = (je0) L;
        gi giVar = wz.a;
        a00 a00Var = this.e;
        if (a00Var == null) {
            yzVar = zc0.a;
        } else {
            yzVar = new yz(je0Var, a00Var);
        }
        cd0 b = yzVar.b(new be(je0Var, null, false, this.f, this.g));
        bwVar.p(false);
        return b;
    }
}
