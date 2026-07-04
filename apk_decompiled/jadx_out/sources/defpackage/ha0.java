package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ha0 extends z30 implements gv {
    public final /* synthetic */ al f;
    public final /* synthetic */ boolean g;
    public final /* synthetic */ float h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ha0(al alVar, boolean z, float f) {
        super(1);
        this.f = alVar;
        this.g = z;
        this.h = f;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        float z;
        lx lxVar = (lx) obj;
        lxVar.getClass();
        float e = this.f.e();
        float G = lxVar.G(2.0f);
        boolean z2 = this.g;
        float f = this.h;
        if (z2) {
            z = d20.z(G, f + G, e);
        } else {
            z = d20.z(-G, -(G + f), e);
        }
        lxVar.n(z);
        return x31.a;
    }
}
