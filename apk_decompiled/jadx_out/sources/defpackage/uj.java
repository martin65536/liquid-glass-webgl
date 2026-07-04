package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class uj extends z30 implements lv {
    public final /* synthetic */ int f;
    public final /* synthetic */ hy0 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ uj(hy0 hy0Var, int i) {
        super(3);
        this.f = i;
        this.g = hy0Var;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        int i = this.f;
        fr frVar = fr.e;
        float f = 0.0f;
        hy0 hy0Var = this.g;
        switch (i) {
            case 0:
                rc0 rc0Var = (rc0) obj;
                kc0 kc0Var = (kc0) obj2;
                long j = ((si) obj3).a;
                rc0Var.getClass();
                kc0Var.getClass();
                em0 v = kc0Var.v(j);
                float floatValue = ((Number) hy0Var.getValue()).floatValue();
                int S = rc0Var.S(16.0f);
                float G = rc0Var.G(16.0f);
                float f2 = floatValue - 1.0f;
                if (f2 >= 0.0f) {
                    f = f2;
                }
                return rc0Var.e0(si.j(j), Math.round(G * f) + S, frVar, new p3(v, 1));
            default:
                rc0 rc0Var2 = (rc0) obj;
                kc0 kc0Var2 = (kc0) obj2;
                long j2 = ((si) obj3).a;
                rc0Var2.getClass();
                kc0Var2.getClass();
                em0 v2 = kc0Var2.v(j2);
                float floatValue2 = ((Number) hy0Var.getValue()).floatValue();
                int S2 = rc0Var2.S(16.0f);
                float G2 = rc0Var2.G(32.0f);
                float f3 = floatValue2 - 1.0f;
                if (f3 >= 0.0f) {
                    f = f3;
                }
                return rc0Var2.e0(si.j(j2), Math.round(G2 * f) + S2, frVar, new p3(v2, 2));
        }
    }
}
