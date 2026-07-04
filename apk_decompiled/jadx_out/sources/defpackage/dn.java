package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dn extends z30 implements gv {
    public final /* synthetic */ boolean f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dn(boolean z) {
        super(1);
        this.f = z;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        float f;
        float f2;
        float f3;
        np npVar = (np) obj;
        npVar.getClass();
        boolean z = this.f;
        if (z) {
            f = 0.2f;
        } else {
            f = 0.0f;
        }
        ue.a(npVar, f, 1.0f);
        if (z) {
            f2 = npVar.e;
            f3 = 16.0f;
        } else {
            f2 = npVar.e;
            f3 = 8.0f;
        }
        o4.o(npVar, f2 * f3);
        float f4 = npVar.e;
        d20.y(npVar, 24.0f * f4, f4 * 48.0f, 8);
        return x31.a;
    }
}
