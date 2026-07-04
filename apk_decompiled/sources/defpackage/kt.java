package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kt extends z30 implements gv {
    public final /* synthetic */ ep0 f;
    public final /* synthetic */ int g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public kt(int i, ep0 ep0Var) {
        super(1);
        this.f = ep0Var;
        this.g = i;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        Boolean valueOf = Boolean.valueOf(((pt) obj).K0(this.g));
        this.f.e = valueOf;
        return valueOf;
    }
}
