package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class i90 extends z30 implements vu {
    public final /* synthetic */ int f;
    public final /* synthetic */ vu g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ i90(vu vuVar, int i) {
        super(0);
        this.f = i;
        this.g = vuVar;
    }

    @Override // defpackage.vu
    public final Object a() {
        switch (this.f) {
            case 0:
                return (Integer) this.g.a();
            case 1:
                return (Float) this.g.a();
            default:
                return (Boolean) this.g.a();
        }
    }
}
