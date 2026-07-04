package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class o2 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ y6 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ o2(y6 y6Var, int i) {
        super(1);
        this.f = i;
        this.g = y6Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        float z;
        float z2;
        int i = this.f;
        x31 x31Var = x31.a;
        y6 y6Var = this.g;
        switch (i) {
            case 0:
                np npVar = (np) obj;
                npVar.getClass();
                float f = 1.0f;
                float floatValue = (((Number) y6Var.d()).floatValue() * 2.0f) - 1.0f;
                float signum = Math.signum(floatValue) * floatValue * floatValue;
                if (signum > 0.0f) {
                    z = d20.z(0.1f, 0.5f, signum);
                } else {
                    z = d20.z(0.1f, -0.2f, -signum);
                }
                if (signum > 0.0f) {
                    f = d20.z(1.0f, 0.0f, signum);
                }
                ue.a(npVar, z, f);
                if (signum > 0.0f) {
                    float f2 = npVar.e;
                    z2 = d20.z(8.0f * f2, f2 * 16.0f, signum);
                } else {
                    float f3 = npVar.e;
                    z2 = d20.z(8.0f * f3, f3 * 2.0f, -signum);
                }
                o4.o(npVar, z2);
                d20.y(npVar, npVar.e * 24.0f, mw0.b(npVar.g) / 2.0f, 8);
                return x31Var;
            case 1:
                lx lxVar = (lx) obj;
                lxVar.getClass();
                float G = lxVar.G(4.0f) * ((Number) y6Var.d()).floatValue();
                if (G > 0.0f) {
                    lxVar.p(new ia(null, G, G, 0));
                }
                return x31Var;
            default:
                np npVar2 = (np) obj;
                npVar2.getClass();
                float floatValue2 = ((Number) y6Var.d()).floatValue();
                ue.c(npVar2, ue.a);
                float f4 = npVar2.e;
                d20.y(npVar2, 24.0f * f4 * floatValue2, f4 * 48.0f * floatValue2, 8);
                return x31Var;
        }
    }
}
