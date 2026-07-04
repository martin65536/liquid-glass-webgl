package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mb0 extends z30 implements vu {
    public final /* synthetic */ ob0 f;
    public final /* synthetic */ long g;
    public final /* synthetic */ long h;
    public final /* synthetic */ gm0 i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public mb0(ob0 ob0Var, long j, long j2, gm0 gm0Var) {
        super(0);
        this.f = ob0Var;
        this.g = j;
        this.h = j2;
        this.i = gm0Var;
    }

    @Override // defpackage.vu
    public final Object a() {
        ob0 ob0Var = this.f;
        ob0Var.z0().e = false;
        ob0Var.z0().f = this.g;
        ob0Var.z0().g = this.h;
        gv c = this.i.e.c();
        if (c != null) {
            c.e(ob0Var.z0());
        }
        return x31.a;
    }
}
