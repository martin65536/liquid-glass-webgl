package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zw implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;
    public final /* synthetic */ Object g;

    public /* synthetic */ zw(int i, Object obj, Object obj2) {
        this.e = i;
        this.f = obj;
        this.g = obj2;
    }

    private final Object f(Object obj) {
        long j;
        ax0 ax0Var = (ax0) obj;
        synchronized (cx0.c) {
            j = cx0.e;
            cx0.e = 1 + j;
        }
        return new ze0(j, ax0Var, (gv) this.f, (gv) this.g);
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        switch (this.e) {
            case 0:
                return f(obj);
            default:
                c9 c9Var = (c9) this.f;
                Object obj2 = c9Var.c;
                pc pcVar = (pc) this.g;
                synchronized (obj2) {
                    ((ArrayList) c9Var.a).remove(pcVar);
                }
                return x31.a;
        }
    }
}
