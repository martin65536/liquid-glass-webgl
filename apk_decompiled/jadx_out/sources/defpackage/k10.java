package defpackage;

import android.os.Build;
import android.view.View;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k10 extends g61 implements Runnable, fh0, View.OnAttachStateChangeListener {
    public final l71 g;
    public boolean h;
    public boolean i;
    public k71 j;

    public k10(l71 l71Var) {
        super(!l71Var.s ? 1 : 0);
        this.g = l71Var;
    }

    @Override // defpackage.fh0
    public final k71 a(View view, k71 k71Var) {
        this.j = k71Var;
        l71 l71Var = this.g;
        j41 j41Var = l71Var.q;
        g71 g71Var = k71Var.a;
        j41Var.f(m20.J(g71Var.h(8)));
        if (this.h) {
            if (Build.VERSION.SDK_INT == 30) {
                view.post(this);
            }
        } else if (!this.i) {
            l71Var.r.f(m20.J(g71Var.h(8)));
            l71.b(l71Var, k71Var);
        }
        if (l71Var.s) {
            return k71.b;
        }
        return k71Var;
    }

    @Override // defpackage.g61
    public final void b(p61 p61Var) {
        this.h = false;
        this.i = false;
        k71 k71Var = this.j;
        if (p61Var.a.b() > 0 && k71Var != null) {
            g71 g71Var = k71Var.a;
            l71 l71Var = this.g;
            l71Var.r.f(m20.J(g71Var.h(8)));
            l71Var.q.f(m20.J(g71Var.h(8)));
            l71.b(l71Var, k71Var);
        }
        this.j = null;
    }

    @Override // defpackage.g61
    public final void c() {
        this.h = true;
        this.i = true;
    }

    @Override // defpackage.g61
    public final k71 d(k71 k71Var, List list) {
        l71 l71Var = this.g;
        l71.b(l71Var, k71Var);
        if (l71Var.s) {
            return k71.b;
        }
        return k71Var;
    }

    @Override // defpackage.g61
    public final c4 e(p61 p61Var, c4 c4Var) {
        this.h = false;
        return c4Var;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        view.requestApplyInsets();
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.h) {
            this.h = false;
            this.i = false;
            k71 k71Var = this.j;
            if (k71Var != null) {
                l71 l71Var = this.g;
                l71Var.r.f(m20.J(k71Var.a.h(8)));
                l71.b(l71Var, k71Var);
                this.j = null;
            }
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
    }
}
