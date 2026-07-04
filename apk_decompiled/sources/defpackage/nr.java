package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class nr extends ak {
    public static final /* synthetic */ int j = 0;
    public long g;
    public boolean h;
    public a8 i;

    public final void q(boolean z) {
        long j2;
        long j3 = this.g;
        if (z) {
            j2 = 4294967296L;
        } else {
            j2 = 1;
        }
        long j4 = j3 - j2;
        this.g = j4;
        if (j4 <= 0 && this.h) {
            shutdown();
        }
    }

    public final void r(kn knVar) {
        a8 a8Var = this.i;
        if (a8Var == null) {
            a8Var = new a8();
            this.i = a8Var;
        }
        a8Var.addLast(knVar);
    }

    public abstract void shutdown();

    public final void u(boolean z) {
        long j2;
        long j3 = this.g;
        if (z) {
            j2 = 4294967296L;
        } else {
            j2 = 1;
        }
        this.g = j2 + j3;
        if (!z) {
            this.h = true;
        }
    }

    public abstract long v();

    public final boolean x() {
        Object removeFirst;
        a8 a8Var = this.i;
        if (a8Var != null) {
            if (a8Var.isEmpty()) {
                removeFirst = null;
            } else {
                removeFirst = a8Var.removeFirst();
            }
            kn knVar = (kn) removeFirst;
            if (knVar == null) {
                return false;
            }
            knVar.run();
            return true;
        }
        return false;
    }
}
