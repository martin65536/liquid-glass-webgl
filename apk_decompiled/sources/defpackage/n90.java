package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n90 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ hk g;
    public final /* synthetic */ fk0 h;
    public final /* synthetic */ y6 i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public n90(int i, hk hkVar, fk0 fk0Var, y6 y6Var) {
        super(1);
        this.f = i;
        this.g = hkVar;
        this.h = fk0Var;
        this.i = y6Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        al alVar = (al) obj;
        alVar.getClass();
        int round = Math.round(alVar.d());
        int i = this.f - 1;
        if (round < 0) {
            round = 0;
        }
        if (round <= i) {
            i = round;
        }
        this.h.h(i);
        alVar.a(i);
        f31.G(this.g, null, new rw(this.i, null, 3), 3);
        return x31.a;
    }
}
