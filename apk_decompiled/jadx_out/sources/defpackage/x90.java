package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x90 extends z30 implements gv {
    public final /* synthetic */ he f;
    public final /* synthetic */ int g;
    public final /* synthetic */ boolean h;
    public final /* synthetic */ al i;
    public final /* synthetic */ gv j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public x90(he heVar, int i, boolean z, al alVar, gv gvVar) {
        super(1);
        this.f = heVar;
        this.g = i;
        this.h = z;
        this.i = alVar;
        this.j = gvVar;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        float f;
        long j = ((ch0) obj).a;
        he heVar = this.f;
        float f2 = heVar.e;
        float intBitsToFloat = (Float.intBitsToFloat((int) (j >> 32)) / this.g) * (f2 - 0.0f);
        if (this.h) {
            f = 0.0f + intBitsToFloat;
        } else {
            f = f2 - intBitsToFloat;
        }
        float floatValue = ((Number) n30.l(Float.valueOf(f), heVar)).floatValue();
        this.i.a(floatValue);
        this.j.e(Float.valueOf(floatValue));
        return x31.a;
    }
}
