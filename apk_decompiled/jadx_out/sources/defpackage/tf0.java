package defpackage;

import java.util.LinkedHashSet;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class tf0 {
    public t20 a;
    public boolean b;
    public e3 c;

    public abstract void a();

    public abstract void b();

    public abstract void c(rf0 rf0Var);

    public abstract void d(rf0 rf0Var);

    public final void e() {
        e3 e3Var = this.c;
        if (e3Var != null && ((LinkedHashSet) e3Var.c).remove(this)) {
            wf0 wf0Var = (wf0) e3Var.b;
            wf0Var.getClass();
            if (equals(wf0Var.f)) {
                if (wf0Var.g == -1) {
                    a();
                }
                wf0Var.f = null;
                wf0Var.g = 0;
                wf0Var.h = null;
            }
            wf0Var.d.remove(this);
            wf0Var.e.remove(this);
            this.c = null;
            wf0Var.b();
        }
    }
}
