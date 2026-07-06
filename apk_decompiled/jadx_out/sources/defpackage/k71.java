package defpackage;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import java.util.Objects;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k71 {
    public static final k71 b;
    public final g71 a;

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 34) {
            b = e71.w;
        } else if (i >= 30) {
            b = c71.v;
        } else {
            b = g71.b;
        }
    }

    public k71(WindowInsets windowInsets) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 35) {
            this.a = new f71(this, windowInsets);
            return;
        }
        if (i >= 34) {
            this.a = new e71(this, windowInsets);
            return;
        }
        if (i >= 31) {
            this.a = new d71(this, windowInsets);
            return;
        }
        if (i >= 30) {
            this.a = new c71(this, windowInsets);
            return;
        }
        if (i >= 29) {
            this.a = new b71(this, windowInsets);
        } else if (i >= 28) {
            this.a = new a71(this, windowInsets);
        } else {
            this.a = new z61(this, windowInsets);
        }
    }

    public static k71 b(WindowInsets windowInsets, View view) {
        windowInsets.getClass();
        k71 k71Var = new k71(windowInsets);
        if (view != null && view.isAttachedToWindow()) {
            int i = j51.a;
            k71 a = f51.a(view);
            g71 g71Var = k71Var.a;
            g71Var.v(a);
            View rootView = view.getRootView();
            g71Var.d(rootView);
            g71Var.o(rootView);
            g71Var.p();
            g71Var.x(view.getWindowSystemUiVisibility());
        }
        return k71Var;
    }

    public final WindowInsets a() {
        g71 g71Var = this.a;
        if (g71Var instanceof y61) {
            return ((y61) g71Var).c;
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof k71)) {
            return false;
        }
        return Objects.equals(this.a, ((k71) obj).a);
    }

    public final int hashCode() {
        g71 g71Var = this.a;
        if (g71Var == null) {
            return 0;
        }
        return g71Var.hashCode();
    }

    public k71() {
        this.a = new g71(this);
    }
}
