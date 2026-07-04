package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u90 extends z30 implements gv {
    public final /* synthetic */ long f;
    public final /* synthetic */ long g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public u90(long j, long j2) {
        super(1);
        this.f = j;
        this.g = j2;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        up upVar = (up) obj;
        upVar.getClass();
        long j = this.f;
        if (j != 16) {
            d3.q(upVar, j, 0L, 0.0f, 25, 62);
            d3.q(upVar, se.b(this.f, 0.75f), 0L, 0.0f, 0, 126);
        }
        long j2 = this.g;
        if (j2 != 16) {
            d3.q(upVar, j2, 0L, 0.0f, 0, 126);
        }
        return x31.a;
    }
}
