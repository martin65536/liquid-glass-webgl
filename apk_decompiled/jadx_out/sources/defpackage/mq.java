package defpackage;

import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mq implements Runnable {
    public final ArrayList e;
    public final int f;

    public mq(List list, int i, Throwable th) {
        m20.k(list, "initCallbacks cannot be null");
        this.e = new ArrayList(list);
        this.f = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ArrayList arrayList = this.e;
        int size = arrayList.size();
        int i = 0;
        if (this.f != 1) {
            while (i < size) {
                ((ul) arrayList.get(i)).b.f = n20.d;
                i++;
            }
            return;
        }
        while (i < size) {
            ul ulVar = (ul) arrayList.get(i);
            ulVar.a.setValue(Boolean.TRUE);
            ulVar.b.f = new pz(true);
            i++;
        }
    }
}
