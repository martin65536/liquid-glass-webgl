package defpackage;

import android.graphics.Insets;
import android.view.View;
import android.view.WindowInsets;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class e71 extends d71 {
    public static final k71 w;

    static {
        WindowInsets windowInsets;
        windowInsets = WindowInsets.CONSUMED;
        w = k71.b(windowInsets, null);
    }

    public e71(k71 k71Var, WindowInsets windowInsets) {
        super(k71Var, windowInsets);
    }

    @Override // defpackage.c71, defpackage.y61, defpackage.g71
    public g10 h(int i) {
        Insets insets;
        insets = this.c.getInsets(j71.a(i));
        return g10.c(insets);
    }

    @Override // defpackage.c71, defpackage.y61, defpackage.g71
    public g10 i(int i) {
        Insets insetsIgnoringVisibility;
        insetsIgnoringVisibility = this.c.getInsetsIgnoringVisibility(j71.a(i));
        return g10.c(insetsIgnoringVisibility);
    }

    @Override // defpackage.c71, defpackage.y61, defpackage.g71
    public boolean s(int i) {
        boolean isVisible;
        isVisible = this.c.isVisible(j71.a(i));
        return isVisible;
    }

    @Override // defpackage.y61, defpackage.g71
    public void o(View view) {
    }
}
