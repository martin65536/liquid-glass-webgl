package defpackage;

import androidx.compose.ui.input.pointer.PointerInputEventHandler;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class d5 implements PointerInputEventHandler {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ d5(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
    public final Object invoke(ym0 ym0Var, ij ijVar) {
        int i = this.a;
        int i2 = 1;
        ik ikVar = ik.e;
        int i3 = 0;
        Object obj = this.b;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                Object m = f31.m(ym0Var, new c5((e5) obj, null), ijVar);
                if (m == ikVar) {
                    return m;
                }
                return x31Var;
            case 1:
                al alVar = (al) obj;
                uk ukVar = new uk(alVar, i3);
                uk ukVar2 = new uk(alVar, i2);
                Object m2 = f31.m(ym0Var, new to(ukVar, new v2(2, alVar, ym0Var), new vk(alVar, i3), ukVar2, null), ijVar);
                if (m2 != ikVar) {
                    m2 = x31Var;
                }
                if (m2 == ikVar) {
                    return m2;
                }
                return x31Var;
            default:
                k20 k20Var = (k20) obj;
                j20 j20Var = new j20(k20Var, i3);
                j20 j20Var2 = new j20(k20Var, i2);
                Object m3 = f31.m(ym0Var, new to(j20Var, new o(7, k20Var), new n9(8, k20Var), j20Var2, null), ijVar);
                if (m3 != ikVar) {
                    m3 = x31Var;
                }
                if (m3 == ikVar) {
                    return m3;
                }
                return x31Var;
        }
    }
}
