package defpackage;

import android.os.Handler;
import android.view.Choreographer;
import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n6 extends ak {
    public static final a01 q = new a01(n2.n);
    public static final l6 r = new l6(0);
    public final Choreographer g;
    public final Handler h;
    public boolean m;
    public boolean n;
    public final q6 p;
    public final Object i = new Object();
    public final a8 j = new a8();
    public ArrayList k = new ArrayList();
    public ArrayList l = new ArrayList();
    public final m6 o = new m6(this);

    public n6(Choreographer choreographer, Handler handler) {
        this.g = choreographer;
        this.h = handler;
        this.p = new q6(choreographer, this);
    }

    public static final void q(n6 n6Var) {
        boolean z;
        do {
            Runnable r2 = n6Var.r();
            while (r2 != null) {
                r2.run();
                r2 = n6Var.r();
            }
            synchronized (n6Var.i) {
                if (n6Var.j.isEmpty()) {
                    z = false;
                    n6Var.m = false;
                } else {
                    z = true;
                }
            }
        } while (z);
    }

    @Override // defpackage.ak
    public final void g(yj yjVar, Runnable runnable) {
        synchronized (this.i) {
            this.j.addLast(runnable);
            if (!this.m) {
                this.m = true;
                this.h.post(this.o);
                if (!this.n) {
                    this.n = true;
                    this.g.postFrameCallback(this.o);
                }
            }
        }
    }

    public final Runnable r() {
        Object removeFirst;
        Runnable runnable;
        synchronized (this.i) {
            a8 a8Var = this.j;
            if (a8Var.isEmpty()) {
                removeFirst = null;
            } else {
                removeFirst = a8Var.removeFirst();
            }
            runnable = (Runnable) removeFirst;
        }
        return runnable;
    }
}
