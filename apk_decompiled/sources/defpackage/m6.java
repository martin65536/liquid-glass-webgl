package defpackage;

import android.view.Choreographer;
import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class m6 implements Choreographer.FrameCallback, Runnable {
    public final /* synthetic */ n6 e;

    public m6(n6 n6Var) {
        this.e = n6Var;
    }

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        this.e.h.removeCallbacks(this);
        n6.q(this.e);
        n6 n6Var = this.e;
        synchronized (n6Var.i) {
            if (!n6Var.n) {
                return;
            }
            n6Var.n = false;
            ArrayList arrayList = n6Var.k;
            n6Var.k = n6Var.l;
            n6Var.l = arrayList;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((Choreographer.FrameCallback) arrayList.get(i)).doFrame(j);
            }
            arrayList.clear();
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        n6.q(this.e);
        n6 n6Var = this.e;
        synchronized (n6Var.i) {
            if (n6Var.k.isEmpty()) {
                n6Var.g.removeFrameCallback(this);
                n6Var.n = false;
            }
        }
    }
}
