package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class sj extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ long g;
    public final /* synthetic */ Object h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ sj(Object obj, long j, int i) {
        super(1);
        this.f = i;
        this.h = obj;
        this.g = j;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.f;
        x31 x31Var = x31.a;
        long j = this.g;
        Object obj2 = this.h;
        switch (i) {
            case 0:
                b50 b50Var = (b50) obj;
                b50Var.getClass();
                float floatValue = ((Number) ((y6) obj2).d()).floatValue();
                b50Var.r();
                d3.q(b50Var, se.b(j, se.d(j) * floatValue), 0L, 0.0f, 0, 126);
                return x31Var;
            default:
                h6 h6Var = (h6) obj;
                h6Var.getClass();
                np npVar = (np) obj2;
                h6Var.a.setFloatUniform("size", Float.intBitsToFloat((int) (npVar.g >> 32)), Float.intBitsToFloat((int) (npVar.g & 4294967295L)));
                h6Var.a.setColorUniform("tint", f31.P(j));
                h6Var.a.setFloatUniform("tintIntensity", 0.8f);
                return x31Var;
        }
    }
}
