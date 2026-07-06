package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k90 extends z30 implements gv {
    public final /* synthetic */ al f;
    public final /* synthetic */ boolean g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public k90(al alVar, boolean z) {
        super(1);
        this.f = alVar;
        this.g = z;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        long b;
        up upVar = (up) obj;
        upVar.getClass();
        float b2 = this.f.b();
        if (this.g) {
            b = se.b(se.b, 0.1f);
        } else {
            b = se.b(se.c, 0.1f);
        }
        d3.q(upVar, b, 0L, 1.0f - b2, 0, 118);
        d3.q(upVar, se.b(se.b, b2 * 0.03f), 0L, 0.0f, 0, 126);
        return x31.a;
    }
}
