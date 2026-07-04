package defpackage;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowInsets;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class y61 extends g71 {
    public static boolean m = false;
    public static Method n;
    public static Class o;
    public static Field p;
    public static Field q;
    public final WindowInsets c;
    public g10[] d;
    public g10 e;
    public k71 f;
    public g10 g;
    public int h;
    public int i;
    public int j;
    public Rect[][] k;
    public Rect[][] l;

    public y61(k71 k71Var, WindowInsets windowInsets) {
        super(k71Var);
        this.e = null;
        this.k = new Rect[10];
        this.l = new Rect[10];
        this.c = windowInsets;
    }

    private qn A(View view) {
        Display display;
        int i;
        int i2;
        int i3;
        if (view == null || (display = view.getDisplay()) == null) {
            return null;
        }
        Point point = new Point();
        display.getRealSize(point);
        if (this.a.a.r()) {
            return qn.a(point.x, point.y, true, 0, 0, 0, 0);
        }
        int i4 = 0;
        hr0 r = jc0.r(display, 0);
        hr0 r2 = jc0.r(display, 1);
        hr0 r3 = jc0.r(display, 2);
        hr0 r4 = jc0.r(display, 3);
        int i5 = point.x;
        int i6 = point.y;
        if (r != null) {
            i = r.b;
        } else {
            i = 0;
        }
        if (r2 != null) {
            i2 = r2.b;
        } else {
            i2 = 0;
        }
        if (r3 != null) {
            i3 = r3.b;
        } else {
            i3 = 0;
        }
        if (r4 != null) {
            i4 = r4.b;
        }
        return qn.a(i5, i6, false, i, i2, i3, i4);
    }

    private static List<Rect> B(Rect[][] rectArr, int i) {
        Rect[] rectArr2;
        Rect[] rectArr3 = null;
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0 && (rectArr2 = rectArr[d20.w(i2)]) != null) {
                if (rectArr3 == null) {
                    rectArr3 = rectArr2;
                } else {
                    Rect[] rectArr4 = new Rect[rectArr3.length + rectArr2.length];
                    System.arraycopy(rectArr3, 0, rectArr4, 0, rectArr3.length);
                    System.arraycopy(rectArr2, 0, rectArr4, rectArr3.length, rectArr2.length);
                    rectArr3 = rectArr4;
                }
            }
        }
        if (rectArr3 == null) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(rectArr3);
    }

    private Rect[] C(g10 g10Var) {
        ArrayList arrayList = new ArrayList();
        int i = g10Var.a;
        int i2 = g10Var.d;
        int i3 = g10Var.c;
        int i4 = g10Var.b;
        if (i != 0) {
            arrayList.add(new Rect(0, 0, g10Var.a, this.i));
        }
        if (i4 != 0) {
            arrayList.add(new Rect(0, 0, this.j, i4));
        }
        if (i3 != 0) {
            int i5 = this.j;
            arrayList.add(new Rect(i5 - i3, 0, i5, this.i));
        }
        if (i2 != 0) {
            int i6 = this.i;
            arrayList.add(new Rect(0, i6 - i2, this.j, i6));
        }
        return (Rect[]) arrayList.toArray(new Rect[arrayList.size()]);
    }

    private g10 D(int i, boolean z) {
        g10 g10Var = g10.e;
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0) {
                g10Var = g10.a(g10Var, E(i2, z));
            }
        }
        return g10Var;
    }

    private g10 F() {
        k71 k71Var = this.f;
        if (k71Var != null) {
            return k71Var.a.k();
        }
        return g10.e;
    }

    private g10 G(View view) {
        if (Build.VERSION.SDK_INT < 30) {
            if (!m) {
                I();
            }
            Method method = n;
            if (method != null && o != null && p != null) {
                try {
                    Object invoke = method.invoke(view, null);
                    if (invoke == null) {
                        Log.w("WindowInsetsCompat", "Failed to get visible insets. getViewRootImpl() returned null from the provided view. This means that the view is either not attached or the method has been overridden", new NullPointerException());
                        return null;
                    }
                    Rect rect = (Rect) p.get(q.get(invoke));
                    if (rect == null) {
                        return null;
                    }
                    return g10.b(rect.left, rect.top, rect.right, rect.bottom);
                } catch (ReflectiveOperationException e) {
                    Log.e("WindowInsetsCompat", "Failed to get visible insets. (Reflection error). " + e.getMessage(), e);
                }
            }
            return null;
        }
        throw new UnsupportedOperationException("getVisibleInsets() should not be called on API >= 30. Use WindowInsets.isVisible() instead.");
    }

    private static void I() {
        try {
            n = View.class.getDeclaredMethod("getViewRootImpl", null);
            Class<?> cls = Class.forName("android.view.View$AttachInfo");
            o = cls;
            p = cls.getDeclaredField("mVisibleInsets");
            q = Class.forName("android.view.ViewRootImpl").getDeclaredField("mAttachInfo");
            p.setAccessible(true);
            q.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            Log.e("WindowInsetsCompat", "Failed to get visible insets. (Reflection error). " + e.getMessage(), e);
        }
        m = true;
    }

    public static boolean K(int i, int i2) {
        if ((i & 6) == (i2 & 6)) {
            return true;
        }
        return false;
    }

    public g10 E(int i, boolean z) {
        int i2;
        on g;
        int i3;
        int i4;
        int i5;
        g10 g10Var = g10.e;
        int i6 = 0;
        if (i != 1) {
            g10 g10Var2 = null;
            if (i != 2) {
                if (i != 8) {
                    if (i != 16) {
                        if (i != 32) {
                            if (i != 64) {
                                if (i == 128) {
                                    k71 k71Var = this.f;
                                    if (k71Var != null) {
                                        g = k71Var.a.g();
                                    } else {
                                        g = g();
                                    }
                                    if (g != null) {
                                        int i7 = Build.VERSION.SDK_INT;
                                        if (i7 >= 28) {
                                            i3 = nn.e(g.a);
                                        } else {
                                            i3 = 0;
                                        }
                                        if (i7 >= 28) {
                                            i4 = nn.g(g.a);
                                        } else {
                                            i4 = 0;
                                        }
                                        if (i7 >= 28) {
                                            i5 = nn.f(g.a);
                                        } else {
                                            i5 = 0;
                                        }
                                        if (i7 >= 28) {
                                            i6 = nn.d(g.a);
                                        }
                                        return g10.b(i3, i4, i5, i6);
                                    }
                                }
                            } else {
                                return n();
                            }
                        } else {
                            return j();
                        }
                    } else {
                        return l();
                    }
                } else {
                    g10[] g10VarArr = this.d;
                    if (g10VarArr != null) {
                        g10Var2 = g10VarArr[d20.w(8)];
                    }
                    if (g10Var2 != null) {
                        return g10Var2;
                    }
                    g10 m2 = m();
                    g10 F = F();
                    int i8 = m2.d;
                    if (i8 > F.d) {
                        return g10.b(0, 0, 0, i8);
                    }
                    g10 g10Var3 = this.g;
                    if (g10Var3 != null && !g10Var3.equals(g10Var) && (i2 = this.g.d) > F.d) {
                        return g10.b(0, 0, 0, i2);
                    }
                }
            } else {
                if (z) {
                    g10 F2 = F();
                    g10 k = k();
                    return g10.b(Math.max(F2.a, k.a), 0, Math.max(F2.c, k.c), Math.max(F2.d, k.d));
                }
                if ((this.h & 2) == 0) {
                    g10 m3 = m();
                    k71 k71Var2 = this.f;
                    if (k71Var2 != null) {
                        g10Var2 = k71Var2.a.k();
                    }
                    int i9 = m3.d;
                    if (g10Var2 != null) {
                        i9 = Math.min(i9, g10Var2.d);
                    }
                    return g10.b(m3.a, 0, m3.c, i9);
                }
            }
        } else {
            if (z) {
                return g10.b(0, Math.max(F().b, m().b), 0, 0);
            }
            if ((this.h & 4) == 0) {
                return g10.b(0, m().b, 0, 0);
            }
        }
        return g10Var;
    }

    public boolean H(int i) {
        if (i != 1 && i != 2) {
            if (i == 4) {
                return false;
            }
            if (i != 8 && i != 128) {
                return true;
            }
        }
        return !E(i, false).equals(g10.e);
    }

    public void J(g10 g10Var) {
        this.g = g10Var;
    }

    @Override // defpackage.g71
    public void d(View view) {
        this.j = view.getWidth();
        this.i = view.getHeight();
        g10 G = G(view);
        if (G == null) {
            G = g10.e;
        }
        J(G);
    }

    @Override // defpackage.g71
    public List<Rect> e(int i) {
        return B(this.k, i);
    }

    @Override // defpackage.g71
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        y61 y61Var = (y61) obj;
        if (!Objects.equals(this.g, y61Var.g) || !K(this.h, y61Var.h)) {
            return false;
        }
        return true;
    }

    @Override // defpackage.g71
    public List<Rect> f(int i) {
        return B(this.l, i);
    }

    @Override // defpackage.g71
    public g10 h(int i) {
        return D(i, false);
    }

    @Override // defpackage.g71
    public g10 i(int i) {
        return D(i, true);
    }

    @Override // defpackage.g71
    public final g10 m() {
        if (this.e == null) {
            WindowInsets windowInsets = this.c;
            this.e = g10.b(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
        }
        return this.e;
    }

    @Override // defpackage.g71
    public void o(View view) {
        A(view);
    }

    @Override // defpackage.g71
    public void p() {
        for (int i = 1; i <= 512; i <<= 1) {
            int w = d20.w(i);
            this.k[w] = C(h(i));
            if (i != 8) {
                this.l[w] = C(i(i));
            }
        }
    }

    @Override // defpackage.g71
    public boolean r() {
        return this.c.isRound();
    }

    @Override // defpackage.g71
    public boolean s(int i) {
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0 && !H(i2)) {
                return false;
            }
        }
        return true;
    }

    @Override // defpackage.g71
    public void u(g10[] g10VarArr) {
        this.d = g10VarArr;
    }

    @Override // defpackage.g71
    public void v(k71 k71Var) {
        this.f = k71Var;
    }

    @Override // defpackage.g71
    public void x(int i) {
        this.h = i;
    }

    @Override // defpackage.g71
    public void y(Rect[][] rectArr) {
        Objects.requireNonNull(rectArr);
        this.k = (Rect[][]) rectArr.clone();
    }

    @Override // defpackage.g71
    public void z(Rect[][] rectArr) {
        Objects.requireNonNull(rectArr);
        this.l = (Rect[][]) rectArr.clone();
    }

    @Override // defpackage.g71
    public void t(qn qnVar) {
    }
}
