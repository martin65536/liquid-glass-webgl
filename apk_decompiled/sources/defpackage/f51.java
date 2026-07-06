package defpackage;

import android.view.View;
import android.view.WindowInsets;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class f51 {
    public static k71 a(View view) {
        WindowInsets rootWindowInsets = view.getRootWindowInsets();
        if (rootWindowInsets == null) {
            return null;
        }
        k71 b = k71.b(rootWindowInsets, null);
        g71 g71Var = b.a;
        g71Var.v(b);
        View rootView = view.getRootView();
        g71Var.d(rootView);
        g71Var.o(rootView);
        g71Var.p();
        return b;
    }
}
