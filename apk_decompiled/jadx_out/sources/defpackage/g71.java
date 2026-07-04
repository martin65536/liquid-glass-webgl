package defpackage;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class g71 {
    public static final k71 b;
    public final k71 a;

    static {
        x61 q61Var;
        int i = Build.VERSION.SDK_INT;
        if (i >= 36) {
            q61Var = new w61();
        } else if (i >= 35) {
            q61Var = new v61();
        } else if (i >= 34) {
            q61Var = new u61();
        } else if (i >= 31) {
            q61Var = new t61();
        } else if (i >= 30) {
            q61Var = new s61();
        } else if (i >= 29) {
            q61Var = new r61();
        } else {
            q61Var = new q61();
        }
        b = q61Var.b().a.a().a.b().a.c();
    }

    public g71(k71 k71Var) {
        this.a = k71Var;
    }

    public k71 a() {
        return this.a;
    }

    public k71 b() {
        return this.a;
    }

    public k71 c() {
        return this.a;
    }

    public List<Rect> e(int i) {
        return Collections.EMPTY_LIST;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof g71)) {
            return false;
        }
        g71 g71Var = (g71) obj;
        if (r() == g71Var.r() && q() == g71Var.q() && Objects.equals(m(), g71Var.m()) && Objects.equals(k(), g71Var.k()) && Objects.equals(g(), g71Var.g())) {
            return true;
        }
        return false;
    }

    public List<Rect> f(int i) {
        return Collections.EMPTY_LIST;
    }

    public on g() {
        return null;
    }

    public g10 h(int i) {
        return g10.e;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(r()), Boolean.valueOf(q()), m(), k(), g());
    }

    public g10 i(int i) {
        if ((i & 8) == 0) {
            return g10.e;
        }
        v7.m("Unable to query the maximum insets for IME");
        return null;
    }

    public g10 j() {
        return m();
    }

    public g10 k() {
        return g10.e;
    }

    public g10 l() {
        return m();
    }

    public g10 m() {
        return g10.e;
    }

    public g10 n() {
        return m();
    }

    public boolean q() {
        return false;
    }

    public boolean r() {
        return false;
    }

    public boolean s(int i) {
        return true;
    }

    public void p() {
    }

    public void d(View view) {
    }

    public void o(View view) {
    }

    public void t(qn qnVar) {
    }

    public void u(g10[] g10VarArr) {
    }

    public void v(k71 k71Var) {
    }

    public void w(g10 g10Var) {
    }

    public void x(int i) {
    }

    public void y(Rect[][] rectArr) {
    }

    public void z(Rect[][] rectArr) {
    }
}
