package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mw extends z30 implements vu {
    public final /* synthetic */ int f;
    public final /* synthetic */ ek0 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ mw(ek0 ek0Var, int i) {
        super(0);
        this.f = i;
        this.g = ek0Var;
    }

    @Override // defpackage.vu
    public final Object a() {
        int i = this.f;
        ek0 ek0Var = this.g;
        switch (i) {
            case 0:
                return new jr0(ek0Var.g() * 128.0f);
            case 1:
                return Float.valueOf(ek0Var.g());
            case 2:
                return Float.valueOf(ek0Var.g());
            case 3:
                return Float.valueOf(ek0Var.g());
            case 4:
                return Float.valueOf(ek0Var.g());
            case 5:
                return Float.valueOf(ek0Var.g());
            case 6:
                return Float.valueOf(ek0Var.g());
            case 7:
                return Float.valueOf(ek0Var.g());
            default:
                return Float.valueOf(ek0Var.g());
        }
    }
}
