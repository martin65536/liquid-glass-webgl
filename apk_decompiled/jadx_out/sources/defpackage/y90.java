package defpackage;

import androidx.compose.ui.input.pointer.PointerInputEventHandler;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class y90 implements PointerInputEventHandler {
    public final /* synthetic */ he a;
    public final /* synthetic */ int b;
    public final /* synthetic */ boolean c;
    public final /* synthetic */ al d;
    public final /* synthetic */ gv e;

    public y90(he heVar, int i, boolean z, al alVar, gv gvVar) {
        this.a = heVar;
        this.b = i;
        this.c = z;
        this.d = alVar;
        this.e = gvVar;
    }

    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
    public final Object invoke(ym0 ym0Var, ij ijVar) {
        Object q = dl.q(new bh(ym0Var, o01.a, new x90(this.a, this.b, this.c, this.d, this.e), null, 4), ijVar);
        x31 x31Var = x31.a;
        ik ikVar = ik.e;
        if (q != ikVar) {
            q = x31Var;
        }
        if (q == ikVar) {
            return q;
        }
        return x31Var;
    }
}
