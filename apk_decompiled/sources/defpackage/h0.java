package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h0 extends o4 {
    @Override // defpackage.o4
    public final void U(i0 i0Var, i0 i0Var2) {
        i0Var.b = i0Var2;
    }

    @Override // defpackage.o4
    public final void V(i0 i0Var, Thread thread) {
        i0Var.a = thread;
    }

    @Override // defpackage.o4
    public final boolean p(j0 j0Var, f0 f0Var) {
        f0 f0Var2 = f0.b;
        synchronized (j0Var) {
            try {
                if (j0Var.f == f0Var) {
                    j0Var.f = f0Var2;
                    return true;
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // defpackage.o4
    public final boolean q(j0 j0Var, Object obj, Object obj2) {
        synchronized (j0Var) {
            try {
                if (j0Var.e == obj) {
                    j0Var.e = obj2;
                    return true;
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // defpackage.o4
    public final boolean r(j0 j0Var, i0 i0Var, i0 i0Var2) {
        synchronized (j0Var) {
            try {
                if (j0Var.g == i0Var) {
                    j0Var.g = i0Var2;
                    return true;
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
