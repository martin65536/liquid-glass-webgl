package defpackage;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ox0 {
    public final x3 a;
    public boolean c;
    public g2 h;
    public nx0 i;
    public final AtomicReference b = new AtomicReference(null);
    public final wa d = new wa(10, this);
    public final l e = new l(18, this);
    public final ef0 f = new ef0(new nx0[16]);
    public final Object g = new Object();
    public long j = -1;

    public ox0(x3 x3Var) {
        this.a = x3Var;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean a() {
        boolean z;
        Set set;
        Set set2;
        synchronized (this.g) {
            z = this.c;
        }
        if (z) {
            return false;
        }
        boolean z2 = false;
        while (true) {
            AtomicReference atomicReference = this.b;
            while (true) {
                Object obj = atomicReference.get();
                set = null;
                List list = null;
                List list2 = null;
                if (obj == null) {
                    break;
                }
                if (obj instanceof Set) {
                    set2 = (Set) obj;
                } else if (obj instanceof List) {
                    List list3 = (List) obj;
                    Set set3 = (Set) list3.get(0);
                    if (list3.size() == 2) {
                        list2 = list3.get(1);
                    } else if (list3.size() > 2) {
                        list2 = list3.subList(1, list3.size());
                    }
                    set2 = set3;
                    list = list2;
                } else {
                    rh.b("Unexpected notification");
                    throw new RuntimeException();
                }
                while (!atomicReference.compareAndSet(obj, list)) {
                    if (atomicReference.get() != obj) {
                        break;
                    }
                }
                set = set2;
                break;
            }
            if (set == null) {
                return z2;
            }
            synchronized (this.g) {
                ef0 ef0Var = this.f;
                Object[] objArr = ef0Var.e;
                int i = ef0Var.g;
                for (int i2 = 0; i2 < i; i2++) {
                    if (!((nx0) objArr[i2]).a(set) && !z2) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0211 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void b(java.lang.Object r23, defpackage.gv r24, defpackage.vu r25) {
        /*
            Method dump skipped, instructions count: 592
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ox0.b(java.lang.Object, gv, vu):void");
    }
}
