package defpackage;

import android.view.WindowInsets;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class z61 extends y61 {
    public g10 r;

    public z61(k71 k71Var, WindowInsets windowInsets) {
        super(k71Var, windowInsets);
        this.r = null;
    }

    @Override // defpackage.g71
    public k71 b() {
        return k71.b(this.c.consumeStableInsets(), null);
    }

    @Override // defpackage.g71
    public k71 c() {
        return k71.b(this.c.consumeSystemWindowInsets(), null);
    }

    @Override // defpackage.g71
    public final g10 k() {
        if (this.r == null) {
            WindowInsets windowInsets = this.c;
            this.r = g10.b(windowInsets.getStableInsetLeft(), windowInsets.getStableInsetTop(), windowInsets.getStableInsetRight(), windowInsets.getStableInsetBottom());
        }
        return this.r;
    }

    @Override // defpackage.g71
    public boolean q() {
        return this.c.isConsumed();
    }

    @Override // defpackage.g71
    public void w(g10 g10Var) {
        this.r = g10Var;
    }
}
