package defpackage;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.LockSupport;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gk implements Executor, Closeable {
    public static final /* synthetic */ AtomicLongFieldUpdater l = AtomicLongFieldUpdater.newUpdater(gk.class, "parkedWorkersStack$volatile");
    public static final /* synthetic */ AtomicLongFieldUpdater m = AtomicLongFieldUpdater.newUpdater(gk.class, "controlState$volatile");
    public static final /* synthetic */ AtomicIntegerFieldUpdater n = AtomicIntegerFieldUpdater.newUpdater(gk.class, "_isTerminated$volatile");
    public static final wq o = new wq("NOT_IN_STACK", 1);
    private volatile /* synthetic */ int _isTerminated$volatile;
    private volatile /* synthetic */ long controlState$volatile;
    public final int e;
    public final int f;
    public final long g;
    public final String h;
    public final xw i;
    public final xw j;
    public final yp0 k;
    private volatile /* synthetic */ long parkedWorkersStack$volatile;

    /* JADX WARN: Type inference failed for: r4v3, types: [bb0, xw] */
    /* JADX WARN: Type inference failed for: r4v4, types: [bb0, xw] */
    public gk(int i, int i2, long j, String str) {
        this.e = i;
        this.f = i2;
        this.g = j;
        this.h = str;
        if (i >= 1) {
            if (i2 >= i) {
                if (i2 <= 2097150) {
                    if (j > 0) {
                        this.i = new bb0();
                        this.j = new bb0();
                        this.k = new yp0((i + 1) * 2);
                        this.controlState$volatile = i << 42;
                        this._isTerminated$volatile = 0;
                        return;
                    }
                    throw new IllegalArgumentException(("Idle worker keep alive time " + j + " must be positive").toString());
                }
                v7.h("Max pool size ", i2, " should not exceed maximal supported number of threads 2097150");
                throw null;
            }
            throw new IllegalArgumentException(d3.u("Max pool size ", i2, " should be greater than or equals to core pool size ", i).toString());
        }
        v7.h("Core pool size ", i, " should be at least 1");
        throw null;
    }

    public static /* synthetic */ void c(gk gkVar, Runnable runnable, int i) {
        boolean z;
        if ((i & 4) != 0) {
            z = false;
        } else {
            z = true;
        }
        gkVar.b(runnable, false, z);
    }

    public final int a() {
        boolean z;
        synchronized (this.k) {
            try {
                if (n.get(this) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    return -1;
                }
                AtomicLongFieldUpdater atomicLongFieldUpdater = m;
                long j = atomicLongFieldUpdater.get(this);
                int i = (int) (j & 2097151);
                int i2 = i - ((int) ((j & 4398044413952L) >> 21));
                if (i2 < 0) {
                    i2 = 0;
                }
                if (i2 >= this.e) {
                    return 0;
                }
                if (i >= this.f) {
                    return 0;
                }
                int i3 = ((int) (atomicLongFieldUpdater.get(this) & 2097151)) + 1;
                if (i3 > 0 && this.k.b(i3) == null) {
                    ek ekVar = new ek(this, i3);
                    this.k.c(i3, ekVar);
                    if (i3 == ((int) (2097151 & atomicLongFieldUpdater.incrementAndGet(this)))) {
                        int i4 = i2 + 1;
                        ekVar.start();
                        return i4;
                    }
                    throw new IllegalArgumentException("Failed requirement.");
                }
                throw new IllegalArgumentException("Failed requirement.");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void b(Runnable runnable, boolean z, boolean z2) {
        q01 r01Var;
        long j;
        ek ekVar;
        boolean a;
        fk fkVar;
        s01.f.getClass();
        long nanoTime = System.nanoTime();
        if (runnable instanceof q01) {
            r01Var = (q01) runnable;
            r01Var.e = nanoTime;
            r01Var.f = z;
        } else {
            r01Var = new r01(runnable, nanoTime, z);
        }
        boolean z3 = r01Var.f;
        AtomicLongFieldUpdater atomicLongFieldUpdater = m;
        if (z3) {
            j = atomicLongFieldUpdater.addAndGet(this, 2097152L);
        } else {
            j = 0;
        }
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ek) {
            ekVar = (ek) currentThread;
        } else {
            ekVar = null;
        }
        if (ekVar == null || ekVar.l != this) {
            ekVar = null;
        }
        boolean z4 = true;
        if (ekVar != null && (fkVar = ekVar.g) != fk.i && (r01Var.f || fkVar != fk.f)) {
            ekVar.k = true;
            d81 d81Var = ekVar.e;
            if (z2) {
                r01Var = d81Var.a(r01Var);
            } else {
                d81Var.getClass();
                d81.b.getClass();
                q01 q01Var = (q01) qc.a(qc.a, d81Var, d81.f, r01Var);
                if (q01Var == null) {
                    r01Var = null;
                } else {
                    r01Var = d81Var.a(q01Var);
                }
            }
        }
        if (r01Var != null) {
            if (r01Var.f) {
                a = this.j.a(r01Var);
            } else {
                a = this.i.a(r01Var);
            }
            if (!a) {
                throw new RejectedExecutionException(this.h + " was terminated");
            }
        }
        if (!z2 || ekVar == null) {
            z4 = false;
        }
        if (z3) {
            if (!z4 && !f() && !e(j)) {
                f();
                return;
            }
            return;
        }
        if (z4 || f() || e(atomicLongFieldUpdater.get(this))) {
            return;
        }
        f();
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x008a, code lost:
    
        if (r1 == null) goto L38;
     */
    @Override // java.io.Closeable, java.lang.AutoCloseable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void close() {
        /*
            r10 = this;
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = defpackage.gk.n
            r1 = 0
            r2 = 1
            boolean r0 = r0.compareAndSet(r10, r1, r2)
            if (r0 != 0) goto Lb
            return
        Lb:
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            boolean r1 = r0 instanceof defpackage.ek
            r3 = 0
            if (r1 == 0) goto L17
            ek r0 = (defpackage.ek) r0
            goto L18
        L17:
            r0 = r3
        L18:
            if (r0 == 0) goto L1e
            gk r1 = r0.l
            if (r1 == r10) goto L1f
        L1e:
            r0 = r3
        L1f:
            yp0 r1 = r10.k
            monitor-enter(r1)
            java.util.concurrent.atomic.AtomicLongFieldUpdater r4 = defpackage.gk.m     // Catch: java.lang.Throwable -> Lc5
            long r4 = r4.get(r10)     // Catch: java.lang.Throwable -> Lc5
            r6 = 2097151(0x1fffff, double:1.0361303E-317)
            long r4 = r4 & r6
            int r4 = (int) r4
            monitor-exit(r1)
            if (r2 > r4) goto L7a
            r1 = r2
        L31:
            yp0 r5 = r10.k
            java.lang.Object r5 = r5.b(r1)
            r5.getClass()
            ek r5 = (defpackage.ek) r5
            if (r5 == r0) goto L75
        L3e:
            java.lang.Thread$State r6 = r5.getState()
            java.lang.Thread$State r7 = java.lang.Thread.State.TERMINATED
            if (r6 == r7) goto L4f
            java.util.concurrent.locks.LockSupport.unpark(r5)
            r6 = 10000(0x2710, double:4.9407E-320)
            r5.join(r6)
            goto L3e
        L4f:
            d81 r5 = r5.e
            xw r6 = r10.j
            r5.getClass()
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r7 = defpackage.d81.b
            r7.getClass()
            sun.misc.Unsafe r7 = defpackage.qc.a
            long r8 = defpackage.d81.f
            java.lang.Object r7 = defpackage.qc.a(r7, r5, r8, r3)
            q01 r7 = (defpackage.q01) r7
            if (r7 == 0) goto L6a
            r6.a(r7)
        L6a:
            q01 r7 = r5.c()
            if (r7 != 0) goto L71
            goto L75
        L71:
            r6.a(r7)
            goto L6a
        L75:
            if (r1 == r4) goto L7a
            int r1 = r1 + 1
            goto L31
        L7a:
            xw r1 = r10.j
            r1.b()
            xw r1 = r10.i
            r1.b()
        L84:
            if (r0 == 0) goto L8c
            q01 r1 = r0.a(r2)
            if (r1 != 0) goto Lb4
        L8c:
            xw r1 = r10.i
            java.lang.Object r1 = r1.d()
            q01 r1 = (defpackage.q01) r1
            if (r1 != 0) goto Lb4
            xw r1 = r10.j
            java.lang.Object r1 = r1.d()
            q01 r1 = (defpackage.q01) r1
            if (r1 != 0) goto Lb4
            if (r0 == 0) goto La7
            fk r1 = defpackage.fk.i
            r0.h(r1)
        La7:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r0 = defpackage.gk.l
            r1 = 0
            r0.set(r10, r1)
            java.util.concurrent.atomic.AtomicLongFieldUpdater r0 = defpackage.gk.m
            r0.set(r10, r1)
            return
        Lb4:
            r1.run()     // Catch: java.lang.Throwable -> Lb8
            goto L84
        Lb8:
            r1 = move-exception
            java.lang.Thread r3 = java.lang.Thread.currentThread()
            java.lang.Thread$UncaughtExceptionHandler r4 = r3.getUncaughtExceptionHandler()
            r4.uncaughtException(r3, r1)
            goto L84
        Lc5:
            r10 = move-exception
            monitor-exit(r1)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.gk.close():void");
    }

    public final void d(ek ekVar, int i, int i2) {
        while (true) {
            long j = l.get(this);
            int i3 = (int) (2097151 & j);
            long j2 = (2097152 + j) & (-2097152);
            if (i3 == i) {
                if (i2 == 0) {
                    Object c = ekVar.c();
                    while (true) {
                        if (c == o) {
                            i3 = -1;
                            break;
                        }
                        if (c == null) {
                            i3 = 0;
                            break;
                        }
                        ek ekVar2 = (ek) c;
                        int b = ekVar2.b();
                        if (b != 0) {
                            i3 = b;
                            break;
                        }
                        c = ekVar2.c();
                    }
                } else {
                    i3 = i2;
                }
            }
            if (i3 >= 0) {
                gk gkVar = this;
                if (l.compareAndSet(gkVar, j, i3 | j2)) {
                    return;
                } else {
                    this = gkVar;
                }
            }
        }
    }

    public final boolean e(long j) {
        int i = ((int) (2097151 & j)) - ((int) ((j & 4398044413952L) >> 21));
        if (i < 0) {
            i = 0;
        }
        int i2 = this.e;
        if (i < i2) {
            int a = a();
            if (a == 1 && i2 > 1) {
                a();
            }
            if (a > 0) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        c(this, runnable, 6);
    }

    public final boolean f() {
        gk gkVar;
        wq wqVar;
        int i;
        while (true) {
            long j = l.get(this);
            ek ekVar = (ek) this.k.b((int) (2097151 & j));
            if (ekVar == null) {
                ekVar = null;
                gkVar = this;
            } else {
                long j2 = (2097152 + j) & (-2097152);
                Object c = ekVar.c();
                while (true) {
                    wqVar = o;
                    if (c == wqVar) {
                        i = -1;
                        break;
                    }
                    if (c == null) {
                        i = 0;
                        break;
                    }
                    ek ekVar2 = (ek) c;
                    i = ekVar2.b();
                    if (i != 0) {
                        break;
                    }
                    c = ekVar2.c();
                    j = j;
                }
                if (i >= 0) {
                    gk gkVar2 = this;
                    boolean compareAndSet = l.compareAndSet(gkVar2, j, i | j2);
                    gkVar = gkVar2;
                    if (compareAndSet) {
                        ekVar.g(wqVar);
                    }
                    this = gkVar;
                } else {
                    continue;
                }
            }
            if (ekVar == null) {
                return false;
            }
            if (ek.m.compareAndSet(ekVar, -1, 0)) {
                LockSupport.unpark(ekVar);
                return true;
            }
            this = gkVar;
        }
    }

    public final String toString() {
        ArrayList arrayList = new ArrayList();
        yp0 yp0Var = this.k;
        int a = yp0Var.a();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 1; i6 < a; i6++) {
            ek ekVar = (ek) yp0Var.b(i6);
            if (ekVar != null) {
                int b = ekVar.e.b();
                int ordinal = ekVar.g.ordinal();
                if (ordinal != 0) {
                    if (ordinal != 1) {
                        if (ordinal != 2) {
                            if (ordinal != 3) {
                                if (ordinal == 4) {
                                    i5++;
                                } else {
                                    v7.k();
                                    return null;
                                }
                            } else {
                                i4++;
                                if (b > 0) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(b);
                                    sb.append('d');
                                    arrayList.add(sb.toString());
                                }
                            }
                        } else {
                            i3++;
                        }
                    } else {
                        i2++;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(b);
                        sb2.append('b');
                        arrayList.add(sb2.toString());
                    }
                } else {
                    i++;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(b);
                    sb3.append('c');
                    arrayList.add(sb3.toString());
                }
            }
        }
        long j = m.get(this);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.h);
        sb4.append('@');
        sb4.append(dl.v(this));
        sb4.append("[Pool Size {core = ");
        int i7 = this.e;
        sb4.append(i7);
        sb4.append(", max = ");
        sb4.append(this.f);
        sb4.append("}, Worker States {CPU = ");
        sb4.append(i);
        sb4.append(", blocking = ");
        sb4.append(i2);
        sb4.append(", parked = ");
        sb4.append(i3);
        sb4.append(", dormant = ");
        sb4.append(i4);
        sb4.append(", terminated = ");
        sb4.append(i5);
        sb4.append("}, running workers queues = ");
        sb4.append(arrayList);
        sb4.append(", global CPU queue size = ");
        sb4.append(this.i.c());
        sb4.append(", global blocking queue size = ");
        sb4.append(this.j.c());
        sb4.append(", Control State {created workers= ");
        sb4.append((int) (2097151 & j));
        sb4.append(", blocking tasks = ");
        sb4.append((int) ((4398044413952L & j) >> 21));
        sb4.append(", CPUs acquired = ");
        sb4.append(i7 - ((int) ((j & 9223367638808264704L) >> 42)));
        sb4.append("}]");
        return sb4.toString();
    }
}
