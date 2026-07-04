package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z90 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ al g;
    public final /* synthetic */ boolean h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public z90(int i, al alVar, boolean z) {
        super(1);
        this.f = i;
        this.g = alVar;
        this.h = z;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        float f;
        lx lxVar = (lx) obj;
        lxVar.getClass();
        float f2 = (-Float.intBitsToFloat((int) (lxVar.j() >> 32))) / 2.0f;
        float f3 = this.f;
        float c = (this.g.c() * f3) + f2;
        float f4 = (-Float.intBitsToFloat((int) (lxVar.j() >> 32))) / 4.0f;
        float intBitsToFloat = f3 - ((Float.intBitsToFloat((int) (lxVar.j() >> 32)) * 3.0f) / 4.0f);
        if (c < f4) {
            c = f4;
        }
        if (c <= intBitsToFloat) {
            intBitsToFloat = c;
        }
        if (this.h) {
            f = 1.0f;
        } else {
            f = -1.0f;
        }
        lxVar.n(intBitsToFloat * f);
        return x31.a;
    }
}
