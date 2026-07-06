package defpackage;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qf0 extends gv0 {
    public static final /* synthetic */ AtomicReferenceFieldUpdater i = AtomicReferenceFieldUpdater.newUpdater(qf0.class, Object.class, "owner$volatile");
    public static final /* synthetic */ long j = qc.a.objectFieldOffset(qf0.class.getDeclaredField("owner$volatile"));
    private volatile /* synthetic */ Object owner$volatile = k81.e;

    public final boolean d() {
        if (Math.max(gv0.f.get(this), 0) != 0) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0038, code lost:
    
        r5 = defpackage.qf0.i;
        r3 = r0.f;
        r5.set(r3, null);
        r5 = r0.e;
        r5.G(r1, r5.g, new defpackage.oc(0, new defpackage.l(9, r3, r0)));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object e(defpackage.jj r6) {
        /*
            r5 = this;
            int r0 = r5.f()
            x31 r1 = defpackage.x31.a
            if (r0 == 0) goto L6c
            r2 = 0
            r3 = 1
            if (r0 == r3) goto L21
            r5 = 2
            if (r0 == r5) goto L15
            java.lang.String r5 = "unexpected"
            defpackage.v7.o(r5)
            return r2
        L15:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "This mutex is already locked by the specified owner: null"
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L21:
            ij r6 = defpackage.t20.w(r6)
            pc r6 = defpackage.dl.w(r6)
            pf0 r0 = new pf0     // Catch: java.lang.Throwable -> L67
            r0.<init>(r5, r6)     // Catch: java.lang.Throwable -> L67
        L2e:
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r4 = defpackage.gv0.f     // Catch: java.lang.Throwable -> L67
            int r4 = r4.getAndDecrement(r5)     // Catch: java.lang.Throwable -> L67
            if (r4 > r3) goto L2e
            if (r4 <= 0) goto L54
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r5 = defpackage.qf0.i     // Catch: java.lang.Throwable -> L67
            qf0 r3 = r0.f     // Catch: java.lang.Throwable -> L67
            r5.set(r3, r2)     // Catch: java.lang.Throwable -> L67
            pc r5 = r0.e     // Catch: java.lang.Throwable -> L67
            l r2 = new l     // Catch: java.lang.Throwable -> L67
            r4 = 9
            r2.<init>(r4, r3, r0)     // Catch: java.lang.Throwable -> L67
            int r0 = r5.g     // Catch: java.lang.Throwable -> L67
            oc r3 = new oc     // Catch: java.lang.Throwable -> L67
            r4 = 0
            r3.<init>(r4, r2)     // Catch: java.lang.Throwable -> L67
            r5.G(r1, r0, r3)     // Catch: java.lang.Throwable -> L67
            goto L5a
        L54:
            boolean r4 = r5.a(r0)     // Catch: java.lang.Throwable -> L67
            if (r4 == 0) goto L2e
        L5a:
            java.lang.Object r5 = r6.p()
            ik r6 = defpackage.ik.e
            if (r5 != r6) goto L63
            goto L64
        L63:
            r5 = r1
        L64:
            if (r5 != r6) goto L6c
            return r5
        L67:
            r5 = move-exception
            r6.D()
            throw r5
        L6c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.qf0.e(jj):java.lang.Object");
    }

    public final int f() {
        int i2;
        while (true) {
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = gv0.f;
            int i3 = atomicIntegerFieldUpdater.get(this);
            if (i3 > 1) {
                do {
                    i2 = atomicIntegerFieldUpdater.get(this);
                    if (i2 > 1) {
                    }
                } while (!atomicIntegerFieldUpdater.compareAndSet(this, i2, 1));
            } else {
                if (i3 <= 0) {
                    return 1;
                }
                if (atomicIntegerFieldUpdater.compareAndSet(this, i3, i3 - 1)) {
                    i.getClass();
                    qc.a.putObjectVolatile(this, j, (Object) null);
                    return 0;
                }
            }
        }
    }

    public final void g(Object obj) {
        while (this.d()) {
            i.getClass();
            Unsafe unsafe = qc.a;
            long j2 = j;
            Object objectVolatile = unsafe.getObjectVolatile(this, j2);
            wq wqVar = k81.e;
            if (objectVolatile != wqVar) {
                if (objectVolatile != obj && obj != null) {
                    throw new IllegalStateException(("This mutex is locked by " + objectVolatile + ", but " + obj + " is expected").toString());
                }
                while (true) {
                    Unsafe unsafe2 = qc.a;
                    qf0 qf0Var = this;
                    if (unsafe2.compareAndSwapObject(qf0Var, j, objectVolatile, wqVar)) {
                        qf0Var.b();
                        return;
                    } else {
                        if (unsafe2.getObjectVolatile(qf0Var, j2) != objectVolatile) {
                            this = qf0Var;
                            break;
                        }
                        this = qf0Var;
                    }
                }
            }
        }
        v7.o("This mutex is not locked");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Mutex@");
        sb.append(dl.v(this));
        sb.append("[isLocked=");
        sb.append(d());
        sb.append(",owner=");
        i.getClass();
        sb.append(qc.a.getObjectVolatile(this, j));
        sb.append(']');
        return sb.toString();
    }
}
