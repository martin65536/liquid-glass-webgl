package defpackage;

import android.graphics.Path;
import android.os.Build;
import android.view.View;
import com.kyant.backdrop.catalog.R;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l71 {
    public static final WeakHashMap v = new WeakHashMap();
    public final u6 a;
    public final u6 b;
    public final u6 c;
    public final u6 d;
    public final u6 e;
    public final u6 f;
    public final u6 g;
    public final u6 h;
    public final u6 i;
    public final j41 j;
    public final ik0 k;
    public final j41 l;
    public final j41 m;
    public final j41 n;
    public final j41 o;
    public final j41 p;
    public final j41 q;
    public final j41 r;
    public final boolean s;
    public int t;
    public final k10 u;

    public l71(View view) {
        View view2;
        Object obj;
        Boolean bool;
        boolean z;
        u6 d = ey0.d("captionBar", 4);
        this.a = d;
        u6 d2 = ey0.d("displayCutout", 128);
        this.b = d2;
        u6 d3 = ey0.d("ime", 8);
        this.c = d3;
        u6 d4 = ey0.d("mandatorySystemGestures", 32);
        this.d = d4;
        u6 d5 = ey0.d("navigationBars", 2);
        this.e = d5;
        u6 d6 = ey0.d("statusBars", 1);
        this.f = d6;
        u6 d7 = ey0.d("systemBars", 519);
        this.g = d7;
        u6 d8 = ey0.d("systemGestures", 16);
        this.h = d8;
        u6 d9 = ey0.d("tappableElement", 64);
        this.i = d9;
        j41 j41Var = new j41(new m10(0, 0, 0, 0), "waterfall");
        this.j = j41Var;
        this.k = n30.B(null);
        new w31(new w31(new w31(d7, d3), d2), new w31(new w31(new w31(d9, d4), d8), j41Var));
        this.l = ey0.f("captionBarIgnoringVisibility", 4);
        this.m = ey0.f("navigationBarsIgnoringVisibility", 2);
        this.n = ey0.f("statusBarsIgnoringVisibility", 1);
        this.o = ey0.f("systemBarsIgnoringVisibility", 519);
        this.p = ey0.f("tappableElementIgnoringVisibility", 64);
        this.q = new j41(new m10(0, 0, 0, 0), "imeAnimationTarget");
        this.r = new j41(new m10(0, 0, 0, 0), "imeAnimationSource");
        Object parent = view.getParent();
        if (parent instanceof View) {
            view2 = (View) parent;
        } else {
            view2 = null;
        }
        if (view2 != null) {
            obj = view2.getTag(R.id.consume_window_insets_tag);
        } else {
            obj = null;
        }
        if (obj instanceof Boolean) {
            bool = (Boolean) obj;
        } else {
            bool = null;
        }
        if (bool != null) {
            z = bool.booleanValue();
        } else {
            z = false;
        }
        this.s = z;
        this.u = new k10(this);
        int i = j51.a;
        k71 a = f51.a(view);
        if (a != null) {
            g71 g71Var = a.a;
            d.f(g71Var.s(4));
            d2.f(g71Var.s(128));
            d3.f(g71Var.s(8));
            d4.f(g71Var.s(32));
            d5.f(g71Var.s(2));
            d6.f(g71Var.s(1));
            d7.f(g71Var.s(519));
            d8.f(g71Var.s(16));
            d9.f(g71Var.s(64));
        }
    }

    public static void b(l71 l71Var, k71 k71Var) {
        g10 g10Var;
        Path path;
        boolean z = false;
        l71Var.a.g(k71Var, 0);
        l71Var.c.g(k71Var, 0);
        l71Var.b.g(k71Var, 0);
        l71Var.e.g(k71Var, 0);
        l71Var.f.g(k71Var, 0);
        l71Var.g.g(k71Var, 0);
        l71Var.h.g(k71Var, 0);
        l71Var.i.g(k71Var, 0);
        l71Var.d.g(k71Var, 0);
        l71Var.l.f(m20.J(k71Var.a.i(4)));
        l71Var.m.f(m20.J(k71Var.a.i(2)));
        l71Var.n.f(m20.J(k71Var.a.i(1)));
        l71Var.o.f(m20.J(k71Var.a.i(519)));
        l71Var.p.f(m20.J(k71Var.a.i(64)));
        on g = k71Var.a.g();
        j41 j41Var = l71Var.j;
        if (g != null) {
            g10Var = g.a();
        } else {
            g10Var = g10.e;
        }
        j41Var.f(m20.J(g10Var));
        y5 y5Var = null;
        if (g != null) {
            if (Build.VERSION.SDK_INT >= 31) {
                path = p7.b(g.a);
            } else {
                path = null;
            }
            if (path != null) {
                y5Var = new y5(path);
            }
        }
        l71Var.k.setValue(y5Var);
        synchronized (cx0.c) {
            we0 we0Var = cx0.j.h;
            if (we0Var != null) {
                if (we0Var.h()) {
                    z = true;
                }
            }
        }
        if (z) {
            cx0.a();
        }
    }

    public final void a(View view) {
        if (this.t == 0) {
            int i = j51.a;
            k10 k10Var = this.u;
            e51.b(view, k10Var);
            if (view.isAttachedToWindow()) {
                view.requestApplyInsets();
            }
            view.addOnAttachStateChangeListener(k10Var);
            j51.a(view, k10Var);
        }
        this.t++;
    }
}
