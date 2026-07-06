package defpackage;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class bb0 {
    public static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(bb0.class, Object.class, "_cur$volatile");
    public static final /* synthetic */ long b = qc.a.objectFieldOffset(bb0.class.getDeclaredField("_cur$volatile"));
    private volatile /* synthetic */ Object _cur$volatile = new db0(8, false);

    public final boolean a(Runnable runnable) {
        bb0 bb0Var;
        while (true) {
            a.getClass();
            Unsafe unsafe = qc.a;
            long j = b;
            db0 db0Var = (db0) unsafe.getObjectVolatile(this, j);
            int a2 = db0Var.a(runnable);
            if (a2 == 0) {
                return true;
            }
            if (a2 != 1) {
                if (a2 != 2) {
                    bb0Var = this;
                } else {
                    return false;
                }
            } else {
                db0 d = db0Var.d();
                while (true) {
                    Unsafe unsafe2 = qc.a;
                    bb0Var = this;
                    if (!unsafe2.compareAndSwapObject(bb0Var, b, db0Var, d) && unsafe2.getObjectVolatile(bb0Var, j) == db0Var) {
                        this = bb0Var;
                    }
                }
            }
            this = bb0Var;
        }
    }

    public final void b() {
        bb0 bb0Var;
        while (true) {
            a.getClass();
            Unsafe unsafe = qc.a;
            long j = b;
            db0 db0Var = (db0) unsafe.getObjectVolatile(this, j);
            if (db0Var.c()) {
                return;
            }
            db0 d = db0Var.d();
            while (true) {
                Unsafe unsafe2 = qc.a;
                bb0Var = this;
                if (!unsafe2.compareAndSwapObject(bb0Var, b, db0Var, d) && unsafe2.getObjectVolatile(bb0Var, j) == db0Var) {
                    this = bb0Var;
                }
            }
            this = bb0Var;
        }
    }

    public final int c() {
        a.getClass();
        db0 db0Var = (db0) qc.a.getObjectVolatile(this, b);
        db0Var.getClass();
        long j = db0.f.get(db0Var);
        return 1073741823 & (((int) ((j & 1152921503533105152L) >> 30)) - ((int) (1073741823 & j)));
    }

    public final Object d() {
        bb0 bb0Var;
        while (true) {
            a.getClass();
            Unsafe unsafe = qc.a;
            long j = b;
            db0 db0Var = (db0) unsafe.getObjectVolatile(this, j);
            Object e = db0Var.e();
            if (e != db0.g) {
                return e;
            }
            db0 d = db0Var.d();
            while (true) {
                Unsafe unsafe2 = qc.a;
                bb0Var = this;
                if (!unsafe2.compareAndSwapObject(bb0Var, b, db0Var, d) && unsafe2.getObjectVolatile(bb0Var, j) == db0Var) {
                    this = bb0Var;
                }
            }
            this = bb0Var;
        }
    }
}
