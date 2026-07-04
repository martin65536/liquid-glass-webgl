package defpackage;

import android.graphics.Rect;
import android.util.Log;
import android.view.WindowInsets;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class q61 extends x61 {
    public static Field f = null;
    public static boolean g = false;
    public static Constructor h = null;
    public static boolean i = false;
    public WindowInsets e;

    public q61() {
        this.e = i();
    }

    private static WindowInsets i() {
        if (!g) {
            try {
                f = WindowInsets.class.getDeclaredField("CONSUMED");
            } catch (ReflectiveOperationException e) {
                Log.i("WindowInsetsCompat", "Could not retrieve WindowInsets.CONSUMED field", e);
            }
            g = true;
        }
        Field field = f;
        if (field != null) {
            try {
                WindowInsets windowInsets = (WindowInsets) field.get(null);
                if (windowInsets != null) {
                    return new WindowInsets(windowInsets);
                }
            } catch (ReflectiveOperationException e2) {
                Log.i("WindowInsetsCompat", "Could not get value from WindowInsets.CONSUMED field", e2);
            }
        }
        if (!i) {
            try {
                h = WindowInsets.class.getConstructor(Rect.class);
            } catch (ReflectiveOperationException e3) {
                Log.i("WindowInsetsCompat", "Could not retrieve WindowInsets(Rect) constructor", e3);
            }
            i = true;
        }
        Constructor constructor = h;
        if (constructor != null) {
            try {
                return (WindowInsets) constructor.newInstance(new Rect());
            } catch (ReflectiveOperationException e4) {
                Log.i("WindowInsetsCompat", "Could not invoke WindowInsets(Rect) constructor", e4);
            }
        }
        return null;
    }

    @Override // defpackage.x61
    public k71 b() {
        a();
        k71 b = k71.b(this.e, null);
        g10[] g10VarArr = this.b;
        g71 g71Var = b.a;
        g71Var.u(g10VarArr);
        g71Var.w(null);
        g71Var.t(null);
        g71Var.y(this.c);
        g71Var.z(this.d);
        return b;
    }

    @Override // defpackage.x61
    public void g(g10 g10Var) {
        WindowInsets windowInsets = this.e;
        if (windowInsets != null) {
            this.e = windowInsets.replaceSystemWindowInsets(g10Var.a, g10Var.b, g10Var.c, g10Var.d);
        }
    }

    public q61(k71 k71Var) {
        super(k71Var);
        this.e = k71Var.a();
    }
}
