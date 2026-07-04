package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ca0 extends z30 implements lv {
    public final /* synthetic */ he f;
    public final /* synthetic */ int g;
    public final /* synthetic */ gv h;
    public final /* synthetic */ boolean i;
    public final /* synthetic */ af0 j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ca0(he heVar, int i, gv gvVar, boolean z, af0 af0Var) {
        super(3);
        this.f = heVar;
        this.g = i;
        this.h = gvVar;
        this.i = z;
        this.j = af0Var;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        float floatValue;
        boolean z;
        al alVar = (al) obj;
        long j = ((c20) obj2).a;
        long j2 = ((ch0) obj3).a;
        alVar.getClass();
        af0 af0Var = this.j;
        if (!((Boolean) af0Var.getValue()).booleanValue()) {
            if (Float.intBitsToFloat((int) (j2 >> 32)) == 0.0f) {
                z = true;
            } else {
                z = false;
            }
            af0Var.setValue(Boolean.valueOf(!z));
        }
        he heVar = this.f;
        float intBitsToFloat = (Float.intBitsToFloat((int) (j2 >> 32)) / this.g) * (heVar.e - 0.0f);
        if (this.i) {
            floatValue = ((Number) n30.l(Float.valueOf(alVar.d() + intBitsToFloat), heVar)).floatValue();
        } else {
            floatValue = ((Number) n30.l(Float.valueOf(alVar.d() - intBitsToFloat), heVar)).floatValue();
        }
        this.h.e(Float.valueOf(floatValue));
        return x31.a;
    }
}
