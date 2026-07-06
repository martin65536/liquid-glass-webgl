package defpackage;

import android.graphics.Insets;
import android.view.View;
import android.view.WindowInsets;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class c71 extends b71 {
    public static final k71 v;

    static {
        WindowInsets windowInsets;
        windowInsets = WindowInsets.CONSUMED;
        v = k71.b(windowInsets, null);
    }

    public c71(k71 k71Var, WindowInsets windowInsets) {
        super(k71Var, windowInsets);
    }

    @Override // defpackage.y61, defpackage.g71
    public g10 h(int i) {
        Insets insets;
        insets = this.c.getInsets(i71.a(i));
        return g10.c(insets);
    }

    @Override // defpackage.y61, defpackage.g71
    public g10 i(int i) {
        Insets insetsIgnoringVisibility;
        insetsIgnoringVisibility = this.c.getInsetsIgnoringVisibility(i71.a(i));
        return g10.c(insetsIgnoringVisibility);
    }

    @Override // defpackage.y61, defpackage.g71
    public boolean s(int i) {
        boolean isVisible;
        isVisible = this.c.isVisible(i71.a(i));
        return isVisible;
    }

    @Override // defpackage.y61, defpackage.g71
    public final void d(View view) {
    }
}
