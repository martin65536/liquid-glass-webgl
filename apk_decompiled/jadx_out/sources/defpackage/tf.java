package defpackage;

import android.window.OnBackInvokedDispatcher;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class tf implements h80 {
    public final /* synthetic */ mh0 e;
    public final /* synthetic */ cg f;

    public /* synthetic */ tf(mh0 mh0Var, cg cgVar) {
        this.e = mh0Var;
        this.f = cgVar;
    }

    @Override // defpackage.h80
    public final void h(j80 j80Var, z70 z70Var) {
        OnBackInvokedDispatcher onBackInvokedDispatcher;
        a01 a01Var = this.e.b;
        if (z70Var == z70.ON_CREATE) {
            onBackInvokedDispatcher = this.f.getOnBackInvokedDispatcher();
            onBackInvokedDispatcher.getClass();
            ((lh0) a01Var.getValue()).c.i(new gh0(onBackInvokedDispatcher, 0), 1);
            ((lh0) a01Var.getValue()).c.i(new gh0(onBackInvokedDispatcher, 1000000), 0);
        }
    }
}
