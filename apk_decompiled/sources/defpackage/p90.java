package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p90 extends z30 implements lv {
    public final /* synthetic */ float f;
    public final /* synthetic */ boolean g;
    public final /* synthetic */ int h;
    public final /* synthetic */ hk i;
    public final /* synthetic */ y6 j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public p90(float f, boolean z, int i, hk hkVar, y6 y6Var) {
        super(3);
        this.f = f;
        this.g = z;
        this.h = i;
        this.i = hkVar;
        this.j = y6Var;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        float f;
        al alVar = (al) obj;
        long j = ((c20) obj2).a;
        long j2 = ((ch0) obj3).a;
        alVar.getClass();
        float d = alVar.d();
        float intBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) / this.f;
        if (this.g) {
            f = 1.0f;
        } else {
            f = -1.0f;
        }
        float f2 = (intBitsToFloat * f) + d;
        float f3 = this.h - 1;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 <= f3) {
            f3 = f2;
        }
        alVar.f(f3);
        f31.G(this.i, null, new o90(this.j, j2, (ij) null), 3);
        return x31.a;
    }
}
