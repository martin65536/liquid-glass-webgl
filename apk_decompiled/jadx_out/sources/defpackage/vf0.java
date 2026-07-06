package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class vf0 {
    public e3 a;
    public boolean b;

    public final void a() {
        e3 e3Var = this.a;
        if (e3Var != null) {
            if (!this.b) {
                e3Var.j(this, null);
            }
            wf0 wf0Var = (wf0) e3Var.b;
            g2 g2Var = (g2) e3Var.a;
            wf0Var.getClass();
            if (equals(wf0Var.h) && -1 == wf0Var.g) {
                tf0 tf0Var = wf0Var.f;
                if (tf0Var == null) {
                    tf0Var = wf0Var.c(-1);
                }
                wf0Var.f = null;
                wf0Var.g = 0;
                wf0Var.h = null;
                if (tf0Var == null) {
                    ((mh0) g2Var.a).a.run();
                } else {
                    tf0Var.b();
                }
                ky0 ky0Var = wf0Var.a;
                ky0Var.getClass();
                ky0Var.j(null, xf0.a);
            }
            this.b = false;
            return;
        }
        v7.o("This input is not added to any dispatcher.");
    }

    public void b(boolean z) {
    }
}
