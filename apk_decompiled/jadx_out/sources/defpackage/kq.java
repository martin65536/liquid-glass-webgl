package defpackage;

import android.os.Build;
import java.util.ArrayList;
import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kq extends o4 {
    public final /* synthetic */ lq k;

    public kq(lq lqVar) {
        this.k = lqVar;
    }

    @Override // defpackage.o4
    public final void R(Throwable th) {
        this.k.a.d(th);
    }

    @Override // defpackage.o4
    public final void S(e3 e3Var) {
        Set<int[]> u;
        lq lqVar = this.k;
        lqVar.c = e3Var;
        e3 e3Var2 = lqVar.c;
        oq oqVar = lqVar.a;
        dt0 dt0Var = oqVar.g;
        tl tlVar = oqVar.i;
        if (Build.VERSION.SDK_INT >= 34) {
            u = uq.a();
        } else {
            u = dl.u();
        }
        lqVar.b = new r7(e3Var2, dt0Var, tlVar, u);
        oq oqVar2 = lqVar.a;
        ArrayList arrayList = new ArrayList();
        oqVar2.a.writeLock().lock();
        try {
            oqVar2.c = 1;
            arrayList.addAll(oqVar2.b);
            oqVar2.b.clear();
            oqVar2.a.writeLock().unlock();
            oqVar2.d.post(new mq(arrayList, oqVar2.c, null));
        } catch (Throwable th) {
            oqVar2.a.writeLock().unlock();
            throw th;
        }
    }
}
