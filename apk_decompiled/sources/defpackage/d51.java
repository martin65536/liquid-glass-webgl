package defpackage;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class d51 implements View.OnApplyWindowInsetsListener {
    public k71 a = null;
    public final /* synthetic */ View b;
    public final /* synthetic */ fh0 c;

    public d51(View view, fh0 fh0Var) {
        this.b = view;
        this.c = fh0Var;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        k71 b = k71.b(windowInsets, view);
        int i = Build.VERSION.SDK_INT;
        fh0 fh0Var = this.c;
        if (i < 30) {
            e51.a(windowInsets, this.b);
            if (b.equals(this.a)) {
                return fh0Var.a(view, b).a();
            }
        }
        this.a = b;
        k71 a = fh0Var.a(view, b);
        if (i >= 30) {
            return a.a();
        }
        int i2 = j51.a;
        view.requestApplyInsets();
        return a.a();
    }
}
