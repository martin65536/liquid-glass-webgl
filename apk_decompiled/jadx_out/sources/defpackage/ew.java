package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ew implements vu {
    public final /* synthetic */ fw e;

    public ew(fw fwVar) {
        this.e = fwVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // defpackage.vu
    public final Object a() {
        Object valueOf;
        boolean z;
        Object obj;
        ArrayList arrayList = this.e.a;
        ve0 ve0Var = new ve0(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            w30 w30Var = (w30) arrayList.get(i);
            Object obj2 = w30Var.b;
            int i2 = w30Var.a;
            if (obj2 != null) {
                valueOf = new m30(Integer.valueOf(i2), w30Var.b);
            } else {
                valueOf = Integer.valueOf(i2);
            }
            int f = ve0Var.f(valueOf);
            if (f < 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                obj = null;
            } else {
                obj = ve0Var.c[f];
            }
            if (obj != null) {
                if (obj instanceof pe0) {
                    pe0 pe0Var = (pe0) obj;
                    pe0Var.a(w30Var);
                    w30Var = pe0Var;
                } else {
                    Object[] objArr = yg0.a;
                    pe0 pe0Var2 = new pe0(2);
                    pe0Var2.a(obj);
                    pe0Var2.a(w30Var);
                    w30Var = pe0Var2;
                }
            }
            if (z) {
                int i3 = ~f;
                ve0Var.b[i3] = valueOf;
                ve0Var.c[i3] = w30Var;
            } else {
                ve0Var.c[f] = w30Var;
            }
        }
        return new de0(ve0Var);
    }
}
