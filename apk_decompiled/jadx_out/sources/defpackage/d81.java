package defpackage;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class d81 {
    public final AtomicReferenceArray a = new AtomicReferenceArray(128);
    private volatile /* synthetic */ int blockingTasksInBuffer$volatile;
    private volatile /* synthetic */ int consumerIndex$volatile;
    private volatile /* synthetic */ Object lastScheduledTask$volatile;
    private volatile /* synthetic */ int producerIndex$volatile;
    public static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(d81.class, Object.class, "lastScheduledTask$volatile");
    public static final /* synthetic */ long f = qc.a.objectFieldOffset(d81.class.getDeclaredField("lastScheduledTask$volatile"));
    public static final /* synthetic */ AtomicIntegerFieldUpdater c = AtomicIntegerFieldUpdater.newUpdater(d81.class, "producerIndex$volatile");
    public static final /* synthetic */ AtomicIntegerFieldUpdater d = AtomicIntegerFieldUpdater.newUpdater(d81.class, "consumerIndex$volatile");
    public static final /* synthetic */ AtomicIntegerFieldUpdater e = AtomicIntegerFieldUpdater.newUpdater(d81.class, "blockingTasksInBuffer$volatile");

    public final q01 a(q01 q01Var) {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = c;
        if (atomicIntegerFieldUpdater.get(this) - d.get(this) == 127) {
            return q01Var;
        }
        if (q01Var.f) {
            e.incrementAndGet(this);
        }
        int i = atomicIntegerFieldUpdater.get(this) & 127;
        while (true) {
            AtomicReferenceArray atomicReferenceArray = this.a;
            if (atomicReferenceArray.get(i) != null) {
                Thread.yield();
            } else {
                atomicReferenceArray.lazySet(i, q01Var);
                atomicIntegerFieldUpdater.incrementAndGet(this);
                return null;
            }
        }
    }

    public final int b() {
        b.getClass();
        Object objectVolatile = qc.a.getObjectVolatile(this, f);
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = d;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater2 = c;
        if (objectVolatile != null) {
            return (atomicIntegerFieldUpdater2.get(this) - atomicIntegerFieldUpdater.get(this)) + 1;
        }
        return atomicIntegerFieldUpdater2.get(this) - atomicIntegerFieldUpdater.get(this);
    }

    public final q01 c() {
        q01 q01Var;
        while (true) {
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = d;
            int i = atomicIntegerFieldUpdater.get(this);
            if (i - c.get(this) == 0) {
                return null;
            }
            int i2 = i & 127;
            if (atomicIntegerFieldUpdater.compareAndSet(this, i, i + 1) && (q01Var = (q01) this.a.getAndSet(i2, null)) != null) {
                if (q01Var.f) {
                    e.decrementAndGet(this);
                }
                return q01Var;
            }
        }
    }

    public final q01 d() {
        d81 d81Var;
        while (true) {
            b.getClass();
            Unsafe unsafe = qc.a;
            long j = f;
            q01 q01Var = (q01) unsafe.getObjectVolatile(this, j);
            if (q01Var != null && q01Var.f) {
                while (true) {
                    Unsafe unsafe2 = qc.a;
                    d81Var = this;
                    if (unsafe2.compareAndSwapObject(d81Var, f, q01Var, (Object) null)) {
                        return q01Var;
                    }
                    if (unsafe2.getObjectVolatile(d81Var, j) != q01Var) {
                        break;
                    }
                    this = d81Var;
                }
            }
            this = d81Var;
        }
        d81 d81Var2 = this;
        int i = d.get(d81Var2);
        int i2 = c.get(d81Var2);
        while (i != i2 && e.get(d81Var2) != 0) {
            i2--;
            q01 e2 = d81Var2.e(i2, true);
            if (e2 != null) {
                return e2;
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0017, code lost:
    
        if (r6 == false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0019, code lost:
    
        defpackage.d81.e.decrementAndGet(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x001e, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x000f, code lost:
    
        if (r1.f == r6) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0015, code lost:
    
        if (r0.compareAndSet(r5, r1, null) == false) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0023, code lost:
    
        if (r0.get(r5) == r1) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.q01 e(int r5, boolean r6) {
        /*
            r4 = this;
            r5 = r5 & 127(0x7f, float:1.78E-43)
            java.util.concurrent.atomic.AtomicReferenceArray r0 = r4.a
            java.lang.Object r1 = r0.get(r5)
            q01 r1 = (defpackage.q01) r1
            r2 = 0
            if (r1 == 0) goto L25
            boolean r3 = r1.f
            if (r3 != r6) goto L25
        L11:
            boolean r3 = r0.compareAndSet(r5, r1, r2)
            if (r3 == 0) goto L1f
            if (r6 == 0) goto L1e
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = defpackage.d81.e
            r5.decrementAndGet(r4)
        L1e:
            return r1
        L1f:
            java.lang.Object r3 = r0.get(r5)
            if (r3 == r1) goto L11
        L25:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.d81.e(int, boolean):q01");
    }

    public final long f(int i, ep0 ep0Var) {
        int i2;
        d81 d81Var;
        while (true) {
            b.getClass();
            Unsafe unsafe = qc.a;
            long j = f;
            q01 q01Var = (q01) unsafe.getObjectVolatile(this, j);
            if (q01Var != null) {
                if (q01Var.f) {
                    i2 = 1;
                } else {
                    i2 = 2;
                }
                if ((i2 & i) == 0) {
                    return -2L;
                }
                s01.f.getClass();
                long nanoTime = System.nanoTime() - q01Var.e;
                long j2 = s01.b;
                if (nanoTime < j2) {
                    return j2 - nanoTime;
                }
                while (true) {
                    Unsafe unsafe2 = qc.a;
                    d81Var = this;
                    if (unsafe2.compareAndSwapObject(d81Var, f, q01Var, (Object) null)) {
                        ep0Var.e = q01Var;
                        return -1L;
                    }
                    if (unsafe2.getObjectVolatile(d81Var, j) != q01Var) {
                        break;
                    }
                    this = d81Var;
                }
            } else {
                return -2L;
            }
            this = d81Var;
        }
    }
}
