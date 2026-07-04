package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class z71 implements h80 {
    public final /* synthetic */ hj e;
    public final /* synthetic */ q6 f;
    public final /* synthetic */ to0 g;
    public final /* synthetic */ ep0 h;

    public z71(hj hjVar, q6 q6Var, to0 to0Var, ep0 ep0Var) {
        this.e = hjVar;
        this.f = q6Var;
        this.g = to0Var;
        this.h = ep0Var;
    }

    @Override // defpackage.h80
    public final void h(j80 j80Var, z70 z70Var) {
        switch (y71.a[z70Var.ordinal()]) {
            case 1:
                f31.G(this.e, null, new bh(this.h, this.g, j80Var, this, null, 6), 1);
                return;
            case 2:
                q6 q6Var = this.f;
                if (q6Var != null) {
                    c9 c9Var = (c9) q6Var.g;
                    synchronized (c9Var.c) {
                        try {
                            if (!c9Var.a()) {
                                ArrayList arrayList = (ArrayList) c9Var.a;
                                c9Var.a = (ArrayList) c9Var.d;
                                c9Var.d = arrayList;
                                c9Var.b = true;
                                int size = arrayList.size();
                                for (int i = 0; i < size; i++) {
                                    ((ij) arrayList.get(i)).u(x31.a);
                                }
                                arrayList.clear();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
                this.g.S();
                return;
            case 3:
                this.g.K();
                return;
            case 4:
                this.g.C();
                return;
            case 5:
            case 6:
            case 7:
                return;
            default:
                v7.k();
                return;
        }
    }
}
