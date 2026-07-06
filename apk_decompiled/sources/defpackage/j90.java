package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j90 extends z30 implements gv {
    public final /* synthetic */ boolean f;
    public final /* synthetic */ al g;
    public final /* synthetic */ float h;
    public final /* synthetic */ hy0 i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public j90(boolean z, al alVar, float f, hy0 hy0Var) {
        super(1);
        this.f = z;
        this.g = alVar;
        this.h = f;
        this.i = hy0Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        float intBitsToFloat;
        lx lxVar = (lx) obj;
        lxVar.getClass();
        boolean z = this.f;
        hy0 hy0Var = this.i;
        float f = this.h;
        al alVar = this.g;
        if (z) {
            intBitsToFloat = s90.f(hy0Var) + (alVar.e() * f);
        } else {
            intBitsToFloat = (Float.intBitsToFloat((int) (lxVar.j() >> 32)) - ((alVar.e() + 1.0f) * f)) + s90.f(hy0Var);
        }
        lxVar.n(intBitsToFloat);
        return x31.a;
    }
}
