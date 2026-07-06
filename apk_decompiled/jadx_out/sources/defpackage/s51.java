package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class s51 {
    public final t51 a = new t51();

    public final void a() {
        t51 t51Var = this.a;
        if (t51Var != null && !t51Var.d) {
            t51Var.d = true;
            synchronized (t51Var.a) {
                try {
                    Iterator it = t51Var.b.values().iterator();
                    while (it.hasNext()) {
                        t51.a((AutoCloseable) it.next());
                    }
                    Iterator it2 = t51Var.c.iterator();
                    while (it2.hasNext()) {
                        t51.a((AutoCloseable) it2.next());
                    }
                    t51Var.c.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        b();
    }

    public void b() {
    }
}
