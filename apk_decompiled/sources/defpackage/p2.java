package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p2 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ y6 g;
    public final /* synthetic */ y6 h;
    public final /* synthetic */ y6 i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ p2(y6 y6Var, y6 y6Var2, y6 y6Var3, int i) {
        super(1);
        this.f = i;
        this.g = y6Var;
        this.h = y6Var2;
        this.i = y6Var3;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.f;
        x31 x31Var = x31.a;
        y6 y6Var = this.i;
        y6 y6Var2 = this.h;
        y6 y6Var3 = this.g;
        switch (i) {
            case 0:
                lx lxVar = (lx) obj;
                lxVar.getClass();
                long j = ((ch0) y6Var3.d()).a;
                float floatValue = ((Number) y6Var2.d()).floatValue();
                float floatValue2 = ((Number) y6Var.d()).floatValue();
                lxVar.n(Float.intBitsToFloat((int) (j >> 32)));
                lxVar.g(Float.intBitsToFloat((int) (j & 4294967295L)));
                lxVar.i(floatValue);
                lxVar.q(floatValue);
                lxVar.e(floatValue2);
                long floatToRawIntBits = Float.floatToRawIntBits(0.5f);
                int i2 = s21.b;
                lxVar.T((4294967295L & Float.floatToRawIntBits(0.5f)) | (floatToRawIntBits << 32));
                return x31Var;
            default:
                lx lxVar2 = (lx) obj;
                lxVar2.getClass();
                long j2 = ((ch0) y6Var3.d()).a;
                float floatValue3 = ((Number) y6Var2.d()).floatValue();
                float floatValue4 = ((Number) y6Var.d()).floatValue();
                lxVar2.n(Float.intBitsToFloat((int) (j2 >> 32)));
                lxVar2.g(Float.intBitsToFloat((int) (4294967295L & j2)));
                lxVar2.i(floatValue3);
                lxVar2.q(floatValue3);
                lxVar2.e(floatValue4);
                return x31Var;
        }
    }
}
