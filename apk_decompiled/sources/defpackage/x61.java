package defpackage;

import android.graphics.Rect;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class x61 {
    public final k71 a;
    public g10[] b;
    public final Rect[][] c;
    public final Rect[][] d;

    public x61(k71 k71Var) {
        this.c = new Rect[10];
        this.d = new Rect[10];
        this.a = k71Var;
        c(k71Var);
    }

    public final void a() {
        g10[] g10VarArr = this.b;
        if (g10VarArr != null) {
            g10 g10Var = g10VarArr[0];
            g10 g10Var2 = g10VarArr[1];
            k71 k71Var = this.a;
            if (g10Var2 == null) {
                g10Var2 = k71Var.a.h(2);
            }
            if (g10Var == null) {
                g10Var = k71Var.a.h(1);
            }
            g(g10.a(g10Var, g10Var2));
            g10 g10Var3 = this.b[d20.w(16)];
            if (g10Var3 != null) {
                f(g10Var3);
            }
            g10 g10Var4 = this.b[d20.w(32)];
            if (g10Var4 != null) {
                e(g10Var4);
            }
            g10 g10Var5 = this.b[d20.w(64)];
            if (g10Var5 != null) {
                h(g10Var5);
            }
        }
    }

    public abstract k71 b();

    public void c(k71 k71Var) {
        for (int i = 1; i <= 512; i <<= 1) {
            List<Rect> e = k71Var.a.e(i);
            int w = d20.w(i);
            this.c[w] = (Rect[]) e.toArray(new Rect[e.size()]);
            if (i != 8) {
                List<Rect> f = k71Var.a.f(i);
                this.d[w] = (Rect[]) f.toArray(new Rect[f.size()]);
            }
        }
    }

    public void d(int i, g10 g10Var) {
        if (this.b == null) {
            this.b = new g10[10];
        }
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0) {
                this.b[d20.w(i2)] = g10Var;
            }
        }
    }

    public abstract void g(g10 g10Var);

    public x61() {
        this(new k71());
    }

    public void e(g10 g10Var) {
    }

    public void f(g10 g10Var) {
    }

    public void h(g10 g10Var) {
    }
}
