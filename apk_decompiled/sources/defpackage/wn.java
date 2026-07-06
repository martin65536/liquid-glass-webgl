package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wn extends z30 implements vu {
    public final /* synthetic */ boolean f;
    public final /* synthetic */ c4 g;
    public final /* synthetic */ String h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public wn(boolean z, c4 c4Var, String str) {
        super(0);
        this.f = z;
        this.g = c4Var;
        this.h = str;
    }

    @Override // defpackage.vu
    public final Object a() {
        if (this.f) {
            c4 c4Var = this.g;
            String str = this.h;
            os0 os0Var = (os0) c4Var.f;
            synchronized (os0Var.c) {
            }
        }
        return x31.a;
    }
}
