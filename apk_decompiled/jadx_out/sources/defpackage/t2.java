package defpackage;

import androidx.compose.ui.input.pointer.PointerInputEventHandler;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class t2 implements PointerInputEventHandler {
    public final /* synthetic */ int a;
    public final /* synthetic */ y6 b;
    public final /* synthetic */ y6 c;
    public final /* synthetic */ y6 d;
    public final /* synthetic */ hk e;

    public /* synthetic */ t2(y6 y6Var, y6 y6Var2, y6 y6Var3, hk hkVar, int i) {
        this.a = i;
        this.b = y6Var;
        this.c = y6Var2;
        this.d = y6Var3;
        this.e = hkVar;
    }

    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
    public final Object invoke(ym0 ym0Var, ij ijVar) {
        int i = this.a;
        ik ikVar = ik.e;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                Object m = f31.m(ym0Var, new r21(new s2(this.b, this.c, this.d, this.e, 0), null), ijVar);
                if (m != ikVar) {
                    m = x31Var;
                }
                if (m == ikVar) {
                    return m;
                }
                return x31Var;
            default:
                Object m2 = f31.m(ym0Var, new r21(new s2(this.b, this.c, this.d, this.e, 1), null), ijVar);
                if (m2 != ikVar) {
                    m2 = x31Var;
                }
                if (m2 == ikVar) {
                    return m2;
                }
                return x31Var;
        }
    }
}
