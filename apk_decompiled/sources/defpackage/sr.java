package defpackage;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class sr extends nr implements hm {
    public static final /* synthetic */ AtomicReferenceFieldUpdater k = AtomicReferenceFieldUpdater.newUpdater(sr.class, Object.class, "_queue$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater l;
    public static final /* synthetic */ AtomicIntegerFieldUpdater m;
    public static final /* synthetic */ long n;
    public static final /* synthetic */ long o;
    private volatile /* synthetic */ Object _delayed$volatile;
    private volatile /* synthetic */ int _isCompleted$volatile = 0;
    private volatile /* synthetic */ Object _queue$volatile;

    static {
        Unsafe unsafe = qc.a;
        o = unsafe.objectFieldOffset(sr.class.getDeclaredField("_queue$volatile"));
        l = AtomicReferenceFieldUpdater.newUpdater(sr.class, Object.class, "_delayed$volatile");
        n = unsafe.objectFieldOffset(sr.class.getDeclaredField("_delayed$volatile"));
        m = AtomicIntegerFieldUpdater.newUpdater(sr.class, "_isCompleted$volatile");
    }

    public final Runnable A() {
        sr srVar;
        Unsafe unsafe;
        while (true) {
            k.getClass();
            Unsafe unsafe2 = qc.a;
            long j = o;
            Object objectVolatile = unsafe2.getObjectVolatile(this, j);
            if (objectVolatile != null) {
                if (objectVolatile instanceof db0) {
                    db0 db0Var = (db0) objectVolatile;
                    Object e = db0Var.e();
                    if (e != db0.g) {
                        return (Runnable) e;
                    }
                    db0 d = db0Var.d();
                    while (true) {
                        Unsafe unsafe3 = qc.a;
                        srVar = this;
                        if (!unsafe3.compareAndSwapObject(srVar, o, objectVolatile, d) && unsafe3.getObjectVolatile(srVar, j) == objectVolatile) {
                            this = srVar;
                        }
                    }
                } else {
                    srVar = this;
                    if (objectVolatile == o20.f) {
                        return null;
                    }
                    do {
                        unsafe = qc.a;
                        if (unsafe.compareAndSwapObject(srVar, o, objectVolatile, (Object) null)) {
                            return (Runnable) objectVolatile;
                        }
                    } while (unsafe.getObjectVolatile(srVar, j) == objectVolatile);
                }
                this = srVar;
            } else {
                return null;
            }
        }
    }

    public void B(Runnable runnable) {
        C();
        if (D(runnable)) {
            Thread F = F();
            if (Thread.currentThread() != F) {
                LockSupport.unpark(F);
                return;
            }
            return;
        }
        ol.p.B(runnable);
    }

    public final void C() {
        qr qrVar;
        qr qrVar2;
        boolean z;
        l.getClass();
        rr rrVar = (rr) qc.a.getObjectVolatile(this, n);
        if (rrVar == null || y11.b.get(rrVar) == 0) {
            return;
        }
        long nanoTime = System.nanoTime();
        do {
            synchronized (rrVar) {
                try {
                    qr[] qrVarArr = rrVar.a;
                    qrVar = null;
                    if (qrVarArr != null) {
                        qrVar2 = qrVarArr[0];
                    } else {
                        qrVar2 = null;
                    }
                    if (qrVar2 != null) {
                        if (nanoTime - qrVar2.e >= 0) {
                            z = D(qrVar2);
                        } else {
                            z = false;
                        }
                        if (z) {
                            qrVar = rrVar.c(0);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } while (qrVar != null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0062, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean D(java.lang.Runnable r15) {
        /*
            r14 = this;
        L0:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = defpackage.sr.k
            r0.getClass()
            sun.misc.Unsafe r0 = defpackage.qc.a
            long r1 = defpackage.sr.o
            java.lang.Object r7 = r0.getObjectVolatile(r14, r1)
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = defpackage.sr.m
            int r0 = r0.get(r14)
            r3 = 0
            if (r0 == 0) goto L17
            return r3
        L17:
            r0 = 1
            if (r7 != 0) goto L33
        L1a:
            sun.misc.Unsafe r8 = defpackage.qc.a
            long r10 = defpackage.sr.o
            r12 = 0
            r9 = r14
            r13 = r15
            boolean r14 = r8.compareAndSwapObject(r9, r10, r12, r13)
            r4 = r9
            if (r14 == 0) goto L29
            goto L7d
        L29:
            java.lang.Object r14 = r8.getObjectVolatile(r4, r1)
            if (r14 == 0) goto L30
            goto L84
        L30:
            r14 = r4
            r15 = r13
            goto L1a
        L33:
            r4 = r14
            r13 = r15
            boolean r14 = r7 instanceof defpackage.db0
            if (r14 == 0) goto L5e
            r14 = r7
            db0 r14 = (defpackage.db0) r14
            int r15 = r14.a(r13)
            if (r15 == 0) goto L7d
            if (r15 == r0) goto L48
            r14 = 2
            if (r15 == r14) goto L62
            goto L84
        L48:
            db0 r8 = r14.d()
        L4c:
            sun.misc.Unsafe r3 = defpackage.qc.a
            long r5 = defpackage.sr.o
            boolean r14 = r3.compareAndSwapObject(r4, r5, r7, r8)
            if (r14 == 0) goto L57
            goto L84
        L57:
            java.lang.Object r14 = r3.getObjectVolatile(r4, r1)
            if (r14 == r7) goto L4c
            goto L84
        L5e:
            wq r14 = defpackage.o20.f
            if (r7 != r14) goto L63
        L62:
            return r3
        L63:
            db0 r8 = new db0
            r14 = 8
            r8.<init>(r14, r0)
            r14 = r7
            java.lang.Runnable r14 = (java.lang.Runnable) r14
            r8.a(r14)
            r8.a(r13)
        L73:
            sun.misc.Unsafe r3 = defpackage.qc.a
            long r5 = defpackage.sr.o
            boolean r14 = r3.compareAndSwapObject(r4, r5, r7, r8)
            if (r14 == 0) goto L7e
        L7d:
            return r0
        L7e:
            java.lang.Object r14 = r3.getObjectVolatile(r4, r1)
            if (r14 == r7) goto L73
        L84:
            r14 = r4
            r15 = r13
            goto L0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.sr.D(java.lang.Runnable):boolean");
    }

    public final long E() {
        long j;
        qr qrVar;
        a8 a8Var = this.i;
        if (a8Var == null || a8Var.isEmpty()) {
            j = Long.MAX_VALUE;
        } else {
            j = 0;
        }
        if (j != 0) {
            k.getClass();
            Unsafe unsafe = qc.a;
            Object objectVolatile = unsafe.getObjectVolatile(this, o);
            if (objectVolatile != null) {
                if (objectVolatile instanceof db0) {
                    long j2 = db0.f.get((db0) objectVolatile);
                    if (((int) (1073741823 & j2)) != ((int) ((j2 & 1152921503533105152L) >> 30))) {
                        return 0L;
                    }
                } else if (objectVolatile == o20.f) {
                    return Long.MAX_VALUE;
                }
            }
            l.getClass();
            rr rrVar = (rr) unsafe.getObjectVolatile(this, n);
            if (rrVar != null) {
                synchronized (rrVar) {
                    qr[] qrVarArr = rrVar.a;
                    if (qrVarArr != null) {
                        qrVar = qrVarArr[0];
                    } else {
                        qrVar = null;
                    }
                }
                if (qrVar != null) {
                    long nanoTime = qrVar.e - System.nanoTime();
                    if (nanoTime >= 0) {
                        return nanoTime;
                    }
                }
            }
            return Long.MAX_VALUE;
        }
        return 0L;
    }

    public abstract Thread F();

    public final boolean G() {
        boolean z;
        a8 a8Var = this.i;
        if (a8Var != null) {
            z = a8Var.isEmpty();
        } else {
            z = true;
        }
        if (z) {
            l.getClass();
            Unsafe unsafe = qc.a;
            rr rrVar = (rr) unsafe.getObjectVolatile(this, n);
            if (rrVar != null && y11.b.get(rrVar) != 0) {
                return false;
            }
            k.getClass();
            Object objectVolatile = unsafe.getObjectVolatile(this, o);
            if (objectVolatile != null) {
                if (objectVolatile instanceof db0) {
                    long j = db0.f.get((db0) objectVolatile);
                    if (((int) (1073741823 & j)) == ((int) ((j & 1152921503533105152L) >> 30))) {
                        return true;
                    }
                    return false;
                }
                if (objectVolatile == o20.f) {
                }
            }
            return true;
        }
        return false;
    }

    public void H(long j, qr qrVar) {
        ol.p.K(j, qrVar);
    }

    public final void I() {
        qr qrVar;
        long nanoTime = System.nanoTime();
        while (true) {
            l.getClass();
            rr rrVar = (rr) qc.a.getObjectVolatile(this, n);
            if (rrVar != null) {
                synchronized (rrVar) {
                    if (y11.b.get(rrVar) > 0) {
                        qrVar = rrVar.c(0);
                    } else {
                        qrVar = null;
                    }
                }
                if (qrVar != null) {
                    H(nanoTime, qrVar);
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public final void J() {
        k.getClass();
        Unsafe unsafe = qc.a;
        unsafe.putObjectVolatile(this, o, (Object) null);
        l.getClass();
        unsafe.putObjectVolatile(this, n, (Object) null);
    }

    public final void K(long j, qr qrVar) {
        Thread F;
        int L = L(j, qrVar);
        if (L != 0) {
            if (L != 1) {
                if (L != 2) {
                    v7.o("unexpected result");
                    return;
                }
                return;
            }
            H(j, qrVar);
            return;
        }
        if (M(qrVar) && Thread.currentThread() != (F = F())) {
            LockSupport.unpark(F);
        }
    }

    /* JADX WARN: Type inference failed for: r8v0, types: [rr, java.lang.Object] */
    public final int L(long j, qr qrVar) {
        sr srVar;
        Unsafe unsafe;
        if (m.get(this) != 0) {
            return 1;
        }
        l.getClass();
        Unsafe unsafe2 = qc.a;
        long j2 = n;
        rr rrVar = (rr) unsafe2.getObjectVolatile(this, j2);
        if (rrVar == null) {
            ?? obj = new Object();
            obj.c = j;
            while (true) {
                unsafe = qc.a;
                srVar = this;
                if (!unsafe.compareAndSwapObject(srVar, n, (Object) null, (Object) obj) && unsafe.getObjectVolatile(srVar, j2) == null) {
                    this = srVar;
                }
            }
            Object objectVolatile = unsafe.getObjectVolatile(srVar, j2);
            objectVolatile.getClass();
            rrVar = (rr) objectVolatile;
        } else {
            srVar = this;
        }
        return qrVar.d(j, rrVar, srVar);
    }

    public final boolean M(qr qrVar) {
        l.getClass();
        rr rrVar = (rr) qc.a.getObjectVolatile(this, n);
        qr qrVar2 = null;
        if (rrVar != null) {
            synchronized (rrVar) {
                qr[] qrVarArr = rrVar.a;
                if (qrVarArr != null) {
                    qrVar2 = qrVarArr[0];
                }
            }
        }
        if (qrVar2 != qrVar) {
            return false;
        }
        return true;
    }

    public un d(long j, f21 f21Var, yj yjVar) {
        return pl.a.d(j, f21Var, yjVar);
    }

    @Override // defpackage.hm
    public final void f(long j, pc pcVar) {
        long j2 = 0;
        if (j > 0) {
            if (j >= 9223372036854L) {
                j2 = Long.MAX_VALUE;
            } else {
                j2 = 1000000 * j;
            }
        }
        if (j2 < 4611686018427387903L) {
            long nanoTime = System.nanoTime();
            or orVar = new or(this, j2 + nanoTime, pcVar);
            K(nanoTime, orVar);
            pcVar.y(new kc(1, orVar));
        }
    }

    @Override // defpackage.ak
    public final void g(yj yjVar, Runnable runnable) {
        B(runnable);
    }

    @Override // defpackage.nr
    public void shutdown() {
        w11.a.set(null);
        m.set(this, 1);
        z();
        do {
        } while (v() <= 0);
        I();
    }

    @Override // defpackage.nr
    public final long v() {
        if (x()) {
            return 0L;
        }
        C();
        Runnable A = A();
        if (A != null) {
            A.run();
            return 0L;
        }
        return E();
    }

    public final void z() {
        sr srVar;
        Unsafe unsafe;
        wq wqVar = o20.f;
        while (true) {
            k.getClass();
            Unsafe unsafe2 = qc.a;
            long j = o;
            Object objectVolatile = unsafe2.getObjectVolatile(this, j);
            if (objectVolatile == null) {
                while (true) {
                    Unsafe unsafe3 = qc.a;
                    srVar = this;
                    if (!unsafe3.compareAndSwapObject(srVar, o, (Object) null, wqVar)) {
                        if (unsafe3.getObjectVolatile(srVar, j) != null) {
                            break;
                        } else {
                            this = srVar;
                        }
                    } else {
                        return;
                    }
                }
            } else {
                srVar = this;
                if (objectVolatile instanceof db0) {
                    ((db0) objectVolatile).c();
                    return;
                }
                if (objectVolatile != wqVar) {
                    db0 db0Var = new db0(8, true);
                    db0Var.a((Runnable) objectVolatile);
                    do {
                        unsafe = qc.a;
                        if (unsafe.compareAndSwapObject(srVar, o, objectVolatile, db0Var)) {
                            return;
                        }
                    } while (unsafe.getObjectVolatile(srVar, j) == objectVolatile);
                } else {
                    return;
                }
            }
            this = srVar;
        }
    }
}
