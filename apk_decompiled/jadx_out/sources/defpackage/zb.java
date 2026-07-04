package defpackage;

import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class zb implements ed {
    public static final /* synthetic */ AtomicLongFieldUpdater f = AtomicLongFieldUpdater.newUpdater(zb.class, "sendersAndCloseStatus$volatile");
    public static final /* synthetic */ AtomicLongFieldUpdater g = AtomicLongFieldUpdater.newUpdater(zb.class, "receivers$volatile");
    public static final /* synthetic */ AtomicLongFieldUpdater h = AtomicLongFieldUpdater.newUpdater(zb.class, "bufferEnd$volatile");
    public static final /* synthetic */ AtomicLongFieldUpdater i = AtomicLongFieldUpdater.newUpdater(zb.class, "completedExpandBuffersAndPauseFlag$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater j = AtomicReferenceFieldUpdater.newUpdater(zb.class, Object.class, "sendSegment$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater k;
    public static final /* synthetic */ AtomicReferenceFieldUpdater l;
    public static final /* synthetic */ AtomicReferenceFieldUpdater m;
    public static final /* synthetic */ AtomicReferenceFieldUpdater n;
    public static final /* synthetic */ long o;
    public static final /* synthetic */ long p;
    public static final /* synthetic */ long q;
    public static final /* synthetic */ long r;
    public static final /* synthetic */ long s;
    private volatile /* synthetic */ Object _closeCause$volatile;
    private volatile /* synthetic */ long bufferEnd$volatile;
    private volatile /* synthetic */ Object bufferEndSegment$volatile;
    private volatile /* synthetic */ Object closeHandler$volatile;
    private volatile /* synthetic */ long completedExpandBuffersAndPauseFlag$volatile;
    public final int e;
    private volatile /* synthetic */ Object receiveSegment$volatile;
    private volatile /* synthetic */ long receivers$volatile;
    private volatile /* synthetic */ Object sendSegment$volatile;
    private volatile /* synthetic */ long sendersAndCloseStatus$volatile;

    static {
        Unsafe unsafe = qc.a;
        s = unsafe.objectFieldOffset(zb.class.getDeclaredField("sendSegment$volatile"));
        k = AtomicReferenceFieldUpdater.newUpdater(zb.class, Object.class, "receiveSegment$volatile");
        r = unsafe.objectFieldOffset(zb.class.getDeclaredField("receiveSegment$volatile"));
        l = AtomicReferenceFieldUpdater.newUpdater(zb.class, Object.class, "bufferEndSegment$volatile");
        p = unsafe.objectFieldOffset(zb.class.getDeclaredField("bufferEndSegment$volatile"));
        m = AtomicReferenceFieldUpdater.newUpdater(zb.class, Object.class, "_closeCause$volatile");
        o = unsafe.objectFieldOffset(zb.class.getDeclaredField("_closeCause$volatile"));
        n = AtomicReferenceFieldUpdater.newUpdater(zb.class, Object.class, "closeHandler$volatile");
        q = unsafe.objectFieldOffset(zb.class.getDeclaredField("closeHandler$volatile"));
    }

    public zb(int i2) {
        long j2;
        this.e = i2;
        if (i2 >= 0) {
            od odVar = bc.a;
            if (i2 != 0) {
                if (i2 != Integer.MAX_VALUE) {
                    j2 = i2;
                } else {
                    j2 = Long.MAX_VALUE;
                }
            } else {
                j2 = 0;
            }
            this.bufferEnd$volatile = j2;
            this.completedExpandBuffersAndPauseFlag$volatile = h.get(this);
            od odVar2 = new od(0L, null, this, 3);
            this.sendSegment$volatile = odVar2;
            this.receiveSegment$volatile = odVar2;
            if (A()) {
                odVar2 = bc.a;
                odVar2.getClass();
            }
            this.bufferEndSegment$volatile = odVar2;
            this._closeCause$volatile = bc.s;
            return;
        }
        v7.h("Invalid channel capacity: ", i2, ", should be >=0");
        throw null;
    }

    public static Object D(zb zbVar, sz0 sz0Var) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = k;
        atomicReferenceFieldUpdater.getClass();
        if (zbVar != null) {
            od odVar = (od) qc.a.getObjectVolatile(zbVar, r);
            while (true) {
                zbVar.getClass();
                AtomicLongFieldUpdater atomicLongFieldUpdater = f;
                boolean z = true;
                if (!zbVar.x(atomicLongFieldUpdater.get(zbVar), true)) {
                    AtomicLongFieldUpdater atomicLongFieldUpdater2 = g;
                    long andIncrement = atomicLongFieldUpdater2.getAndIncrement(zbVar);
                    long j2 = bc.b;
                    long j3 = andIncrement / j2;
                    int i2 = (int) (andIncrement % j2);
                    if (odVar.e != j3) {
                        od m2 = zbVar.m(j3, odVar);
                        if (m2 == null) {
                            continue;
                        } else {
                            odVar = m2;
                        }
                    }
                    Object J = zbVar.J(odVar, i2, andIncrement, null);
                    Object obj = bc.m;
                    if (J != obj) {
                        Object obj2 = bc.o;
                        if (J == obj2) {
                            if (andIncrement < zbVar.t()) {
                                odVar.a();
                            }
                        } else {
                            if (J == bc.n) {
                                pc w = dl.w(t20.w(sz0Var));
                                try {
                                    Object J2 = zbVar.J(odVar, i2, andIncrement, w);
                                    if (J2 == obj) {
                                        w.a(odVar, i2);
                                    } else if (J2 == obj2) {
                                        if (andIncrement < zbVar.t()) {
                                            odVar.a();
                                        }
                                        od odVar2 = (od) atomicReferenceFieldUpdater.get(zbVar);
                                        while (true) {
                                            if (zbVar.x(atomicLongFieldUpdater.get(zbVar), z)) {
                                                w.u(new jq0(zbVar.r()));
                                                break;
                                            }
                                            long andIncrement2 = atomicLongFieldUpdater2.getAndIncrement(zbVar);
                                            long j4 = bc.b;
                                            AtomicLongFieldUpdater atomicLongFieldUpdater3 = atomicLongFieldUpdater2;
                                            long j5 = andIncrement2 / j4;
                                            int i3 = (int) (andIncrement2 % j4);
                                            if (odVar2.e != j5) {
                                                od m3 = zbVar.m(j5, odVar2);
                                                if (m3 == null) {
                                                    continue;
                                                    atomicLongFieldUpdater2 = atomicLongFieldUpdater3;
                                                    z = true;
                                                } else {
                                                    odVar2 = m3;
                                                }
                                            }
                                            Object J3 = zbVar.J(odVar2, i3, andIncrement2, w);
                                            if (J3 == bc.m) {
                                                w.a(odVar2, i3);
                                                break;
                                            }
                                            if (J3 == bc.o) {
                                                if (andIncrement2 < zbVar.t()) {
                                                    odVar2.a();
                                                }
                                                atomicLongFieldUpdater2 = atomicLongFieldUpdater3;
                                                z = true;
                                            } else if (J3 != bc.n) {
                                                odVar2.a();
                                                w.F(J3, null);
                                            } else {
                                                throw new IllegalStateException("unexpected");
                                            }
                                        }
                                    } else {
                                        odVar.a();
                                        w.F(J2, null);
                                    }
                                    return w.p();
                                } catch (Throwable th) {
                                    w.D();
                                    throw th;
                                }
                            }
                            odVar.a();
                            return J;
                        }
                    } else {
                        v7.o("unexpected");
                        return null;
                    }
                } else {
                    Throwable r2 = zbVar.r();
                    int i4 = cy0.a;
                    throw r2;
                }
            }
        } else {
            v7.d();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0158 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.Object F(defpackage.zb r26, java.lang.Object r27, defpackage.ij r28) {
        /*
            Method dump skipped, instructions count: 367
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zb.F(zb, java.lang.Object, ij):java.lang.Object");
    }

    public static boolean H(Object obj) {
        if (obj instanceof nc) {
            nc ncVar = (nc) obj;
            od odVar = bc.a;
            wq v = ncVar.v(x31.a, null);
            if (v == null) {
                return false;
            }
            ncVar.z(v);
            return true;
        }
        v7.e(obj, "Unexpected waiter: ");
        return false;
    }

    public static final void b(zb zbVar, Object obj, pc pcVar) {
        pcVar.u(new jq0(zbVar.s()));
    }

    public static final int c(zb zbVar, od odVar, int i2, Object obj, long j2, Object obj2, boolean z) {
        odVar.r(i2, obj);
        if (z) {
            return zbVar.K(odVar, i2, obj, j2, obj2, z);
        }
        Object p2 = odVar.p(i2);
        if (p2 == null) {
            if (zbVar.e(j2)) {
                if (odVar.o(i2, null, bc.d)) {
                    return 1;
                }
            } else {
                if (obj2 == null) {
                    return 3;
                }
                if (odVar.o(i2, null, obj2)) {
                    return 2;
                }
            }
        } else if (p2 instanceof y51) {
            odVar.r(i2, null);
            if (zbVar.G(p2, obj)) {
                odVar.s(i2, bc.i);
                return 0;
            }
            wq wqVar = bc.k;
            if (odVar.h.getAndSet((i2 * 2) + 1, wqVar) != wqVar) {
                odVar.q(i2, true);
                return 5;
            }
            return 5;
        }
        return zbVar.K(odVar, i2, obj, j2, obj2, z);
    }

    public static void v(zb zbVar) {
        AtomicLongFieldUpdater atomicLongFieldUpdater = i;
        if ((atomicLongFieldUpdater.addAndGet(zbVar, 1L) & 4611686018427387904L) == 0) {
            return;
        }
        do {
        } while ((atomicLongFieldUpdater.get(zbVar) & 4611686018427387904L) != 0);
    }

    public final boolean A() {
        long j2 = h.get(this);
        if (j2 != 0 && j2 != Long.MAX_VALUE) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0063, code lost:
    
        if (r5.j() == false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0065, code lost:
    
        r5.h();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void B(long r7, defpackage.od r9) {
        /*
            r6 = this;
        L0:
            long r0 = r9.e
            int r0 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r0 >= 0) goto L11
            ki r0 = r9.c()
            od r0 = (defpackage.od) r0
            if (r0 != 0) goto Lf
            goto L11
        Lf:
            r9 = r0
            goto L0
        L11:
            r5 = r9
        L12:
            boolean r7 = r5.f()
            if (r7 == 0) goto L23
            ki r7 = r5.c()
            od r7 = (defpackage.od) r7
            if (r7 != 0) goto L21
            goto L23
        L21:
            r5 = r7
            goto L12
        L23:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r7 = defpackage.zb.l
            r7.getClass()
            sun.misc.Unsafe r7 = defpackage.qc.a
            long r8 = defpackage.zb.p
            java.lang.Object r7 = r7.getObjectVolatile(r6, r8)
            r4 = r7
            ku0 r4 = (defpackage.ku0) r4
            long r0 = r4.e
            long r2 = r5.e
            int r7 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r7 < 0) goto L3c
            goto L58
        L3c:
            boolean r7 = r5.n()
            if (r7 != 0) goto L44
            r9 = r5
            goto L11
        L44:
            sun.misc.Unsafe r0 = defpackage.qc.a
            long r2 = defpackage.zb.p
            r1 = r6
            boolean r6 = r0.compareAndSwapObject(r1, r2, r4, r5)
            if (r6 == 0) goto L59
            boolean r6 = r4.j()
            if (r6 == 0) goto L58
            r4.h()
        L58:
            return
        L59:
            java.lang.Object r6 = r0.getObjectVolatile(r1, r8)
            if (r6 == r4) goto L6a
            boolean r6 = r5.j()
            if (r6 == 0) goto L68
            r5.h()
        L68:
            r6 = r1
            goto L23
        L6a:
            r6 = r1
            goto L44
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zb.B(long, od):void");
    }

    public final Object C(ij ijVar, Object obj) {
        pc pcVar = new pc(1, t20.w(ijVar));
        pcVar.s();
        pcVar.u(new jq0(s()));
        Object p2 = pcVar.p();
        if (p2 == ik.e) {
            return p2;
        }
        return x31.a;
    }

    public final void E(y51 y51Var, boolean z) {
        Throwable s2;
        if (y51Var instanceof nc) {
            ij ijVar = (ij) y51Var;
            if (z) {
                s2 = r();
            } else {
                s2 = s();
            }
            ijVar.u(new jq0(s2));
            return;
        }
        if (y51Var instanceof yb) {
            yb ybVar = (yb) y51Var;
            pc pcVar = ybVar.f;
            pcVar.getClass();
            ybVar.f = null;
            ybVar.e = bc.l;
            Throwable p2 = ybVar.g.p();
            if (p2 == null) {
                pcVar.u(Boolean.FALSE);
                return;
            } else {
                pcVar.u(new jq0(p2));
                return;
            }
        }
        v7.e(y51Var, "Unexpected waiter: ");
    }

    public final boolean G(Object obj, Object obj2) {
        if (obj instanceof yb) {
            yb ybVar = (yb) obj;
            pc pcVar = ybVar.f;
            pcVar.getClass();
            ybVar.f = null;
            ybVar.e = obj2;
            Boolean bool = Boolean.TRUE;
            ybVar.g.getClass();
            od odVar = bc.a;
            wq v = pcVar.v(bool, null);
            if (v == null) {
                return false;
            }
            pcVar.z(v);
            return true;
        }
        if (obj instanceof nc) {
            nc ncVar = (nc) obj;
            od odVar2 = bc.a;
            wq v2 = ncVar.v(obj2, null);
            if (v2 == null) {
                return false;
            }
            ncVar.z(v2);
            return true;
        }
        v7.e(obj, "Unexpected receiver type: ");
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x00ae, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object I(java.lang.Object r14) {
        /*
            r13 = this;
            wq r6 = defpackage.bc.d
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = defpackage.zb.j
            r0.getClass()
            sun.misc.Unsafe r0 = defpackage.qc.a
            long r1 = defpackage.zb.s
            java.lang.Object r0 = r0.getObjectVolatile(r13, r1)
            od r0 = (defpackage.od) r0
        L11:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r1 = defpackage.zb.f
            long r1 = r1.getAndIncrement(r13)
            r3 = 1152921504606846975(0xfffffffffffffff, double:1.2882297539194265E-231)
            long r3 = r3 & r1
            r5 = 0
            boolean r7 = r13.x(r1, r5)
            int r8 = defpackage.bc.b
            long r9 = (long) r8
            long r1 = r3 / r9
            long r11 = r3 % r9
            int r5 = (int) r11
            long r11 = r0.e
            int r11 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r11 == 0) goto L47
            od r1 = r13.n(r1, r0)
            if (r1 != 0) goto L42
            if (r7 == 0) goto L11
            java.lang.Throwable r13 = r13.s()
            md r14 = new md
            r14.<init>(r13)
            return r14
        L42:
            r0 = r13
            r2 = r5
        L44:
            r4 = r3
            r3 = r14
            goto L4b
        L47:
            r1 = r0
            r2 = r5
            r0 = r13
            goto L44
        L4b:
            int r13 = c(r0, r1, r2, r3, r4, r6, r7)
            r14 = r0
            r0 = r1
            x31 r1 = defpackage.x31.a
            if (r13 == 0) goto Laf
            r11 = 1
            if (r13 == r11) goto Lae
            r11 = 2
            r12 = 0
            if (r13 == r11) goto L89
            r1 = 3
            if (r13 == r1) goto L83
            r1 = 4
            if (r13 == r1) goto L6c
            r1 = 5
            if (r13 == r1) goto L66
            goto L69
        L66:
            r0.a()
        L69:
            r13 = r14
            r14 = r3
            goto L11
        L6c:
            java.util.concurrent.atomic.AtomicLongFieldUpdater r13 = defpackage.zb.g
            long r1 = r13.get(r14)
            int r13 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r13 >= 0) goto L79
            r0.a()
        L79:
            java.lang.Throwable r13 = r14.s()
            md r14 = new md
            r14.<init>(r13)
            return r14
        L83:
            java.lang.String r13 = "unexpected"
            defpackage.v7.o(r13)
            return r12
        L89:
            if (r7 == 0) goto L98
            r0.m()
            java.lang.Throwable r13 = r14.s()
            md r14 = new md
            r14.<init>(r13)
            return r14
        L98:
            boolean r13 = r6 instanceof defpackage.y51
            if (r13 == 0) goto L9f
            r12 = r6
            y51 r12 = (defpackage.y51) r12
        L9f:
            if (r12 == 0) goto La6
            int r5 = r2 + r8
            r12.a(r0, r5)
        La6:
            long r3 = r0.e
            long r3 = r3 * r9
            long r5 = (long) r2
            long r3 = r3 + r5
            r14.i(r3)
        Lae:
            return r1
        Laf:
            r0.a()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zb.I(java.lang.Object):java.lang.Object");
    }

    public final Object J(od odVar, int i2, long j2, Object obj) {
        AtomicReferenceArray atomicReferenceArray = odVar.h;
        Object p2 = odVar.p(i2);
        AtomicLongFieldUpdater atomicLongFieldUpdater = f;
        if (p2 == null) {
            if (j2 >= (atomicLongFieldUpdater.get(this) & 1152921504606846975L)) {
                if (obj == null) {
                    return bc.n;
                }
                if (odVar.o(i2, p2, obj)) {
                    j();
                    return bc.m;
                }
            }
        } else if (p2 == bc.d && odVar.o(i2, p2, bc.i)) {
            j();
            Object obj2 = atomicReferenceArray.get(i2 * 2);
            odVar.r(i2, null);
            return obj2;
        }
        while (true) {
            Object p3 = odVar.p(i2);
            if (p3 != null && p3 != bc.e) {
                if (p3 == bc.d) {
                    if (odVar.o(i2, p3, bc.i)) {
                        j();
                        Object obj3 = atomicReferenceArray.get(i2 * 2);
                        odVar.r(i2, null);
                        return obj3;
                    }
                } else {
                    wq wqVar = bc.j;
                    if (p3 == wqVar) {
                        return bc.o;
                    }
                    if (p3 == bc.h) {
                        return bc.o;
                    }
                    if (p3 == bc.l) {
                        j();
                        return bc.o;
                    }
                    if (p3 != bc.g && odVar.o(i2, p3, bc.f)) {
                        boolean z = p3 instanceof z51;
                        if (z) {
                            p3 = ((z51) p3).a;
                        }
                        if (H(p3)) {
                            odVar.s(i2, bc.i);
                            j();
                            Object obj4 = atomicReferenceArray.get(i2 * 2);
                            odVar.r(i2, null);
                            return obj4;
                        }
                        odVar.s(i2, wqVar);
                        odVar.m();
                        if (z) {
                            j();
                        }
                        return bc.o;
                    }
                }
            } else if (j2 < (atomicLongFieldUpdater.get(this) & 1152921504606846975L)) {
                if (odVar.o(i2, p3, bc.h)) {
                    j();
                    return bc.o;
                }
            } else {
                if (obj == null) {
                    return bc.n;
                }
                if (odVar.o(i2, p3, obj)) {
                    j();
                    return bc.m;
                }
            }
        }
    }

    public final int K(od odVar, int i2, Object obj, long j2, Object obj2, boolean z) {
        while (true) {
            Object p2 = odVar.p(i2);
            if (p2 == null) {
                if (e(j2) && !z) {
                    if (odVar.o(i2, null, bc.d)) {
                        break;
                    }
                } else if (z) {
                    if (odVar.o(i2, null, bc.j)) {
                        odVar.m();
                        return 4;
                    }
                } else {
                    if (obj2 == null) {
                        return 3;
                    }
                    if (odVar.o(i2, null, obj2)) {
                        return 2;
                    }
                }
            } else if (p2 == bc.e) {
                if (odVar.o(i2, p2, bc.d)) {
                    break;
                }
            } else {
                wq wqVar = bc.k;
                if (p2 == wqVar) {
                    odVar.r(i2, null);
                    return 5;
                }
                if (p2 == bc.h) {
                    odVar.r(i2, null);
                    return 5;
                }
                if (p2 == bc.l) {
                    odVar.r(i2, null);
                    y();
                    return 4;
                }
                odVar.r(i2, null);
                if (p2 instanceof z51) {
                    p2 = ((z51) p2).a;
                }
                if (G(p2, obj)) {
                    odVar.s(i2, bc.i);
                    return 0;
                }
                if (odVar.h.getAndSet((i2 * 2) + 1, wqVar) != wqVar) {
                    odVar.q(i2, true);
                }
                return 5;
            }
        }
        return 1;
    }

    public final void L(long j2) {
        AtomicLongFieldUpdater atomicLongFieldUpdater;
        boolean z;
        zb zbVar = this;
        if (!zbVar.A()) {
            while (true) {
                atomicLongFieldUpdater = h;
                if (atomicLongFieldUpdater.get(zbVar) > j2) {
                    break;
                } else {
                    zbVar = this;
                }
            }
            int i2 = bc.c;
            int i3 = 0;
            while (true) {
                AtomicLongFieldUpdater atomicLongFieldUpdater2 = i;
                if (i3 < i2) {
                    long j3 = atomicLongFieldUpdater.get(zbVar);
                    if (j3 != (4611686018427387903L & atomicLongFieldUpdater2.get(zbVar)) || j3 != atomicLongFieldUpdater.get(zbVar)) {
                        i3++;
                    } else {
                        return;
                    }
                } else {
                    while (true) {
                        long j4 = atomicLongFieldUpdater2.get(zbVar);
                        if (atomicLongFieldUpdater2.compareAndSet(zbVar, j4, (j4 & 4611686018427387903L) + 4611686018427387904L)) {
                            break;
                        } else {
                            zbVar = this;
                        }
                    }
                    while (true) {
                        long j5 = atomicLongFieldUpdater.get(zbVar);
                        long j6 = atomicLongFieldUpdater2.get(zbVar);
                        long j7 = j6 & 4611686018427387903L;
                        if ((j6 & 4611686018427387904L) != 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (j5 == j7 && j5 == atomicLongFieldUpdater.get(zbVar)) {
                            break;
                        }
                        if (!z) {
                            zbVar = this;
                            atomicLongFieldUpdater2.compareAndSet(zbVar, j6, 4611686018427387904L + j7);
                        } else {
                            zbVar = this;
                        }
                    }
                    while (true) {
                        long j8 = atomicLongFieldUpdater2.get(zbVar);
                        if (atomicLongFieldUpdater2.compareAndSet(zbVar, j8, j8 & 4611686018427387903L)) {
                            return;
                        } else {
                            zbVar = this;
                        }
                    }
                }
            }
        }
    }

    @Override // defpackage.ed
    public final void a(CancellationException cancellationException) {
        if (cancellationException == null) {
            cancellationException = new CancellationException("Channel was cancelled");
        }
        g(cancellationException, true);
    }

    @Override // defpackage.jv0
    public Object d(ij ijVar, Object obj) {
        return F(this, obj, ijVar);
    }

    public final boolean e(long j2) {
        if (j2 >= h.get(this) && j2 >= g.get(this) + this.e) {
            return false;
        }
        return true;
    }

    public final od f() {
        l.getClass();
        Unsafe unsafe = qc.a;
        Object objectVolatile = unsafe.getObjectVolatile(this, p);
        j.getClass();
        od odVar = (od) unsafe.getObjectVolatile(this, s);
        if (odVar.e > ((od) objectVolatile).e) {
            objectVolatile = odVar;
        }
        k.getClass();
        od odVar2 = (od) unsafe.getObjectVolatile(this, r);
        if (odVar2.e > ((od) objectVolatile).e) {
            objectVolatile = odVar2;
        }
        ki kiVar = (ki) objectVolatile;
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = ki.a;
            Object d = kiVar.d();
            if (d == k81.a) {
                break;
            }
            ki kiVar2 = (ki) d;
            if (kiVar2 == null) {
                if (kiVar.g()) {
                    break;
                }
            } else {
                kiVar = kiVar2;
            }
        }
        return (od) kiVar;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0047, code lost:
    
        if (r15 != false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0049, code lost:
    
        r5 = r3.get(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0055, code lost:
    
        if (r3.compareAndSet(r4, r5, 3458764513820540928L + (r5 & 1152921504606846975L)) == false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0075, code lost:
    
        r4.y();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0078, code lost:
    
        if (r13 == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x007a, code lost:
    
        r4.w();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x007d, code lost:
    
        return r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0058, code lost:
    
        r5 = r3.get(r4);
        r14 = (int) (r5 >> 60);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005f, code lost:
    
        if (r14 == 0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0061, code lost:
    
        if (r14 == 1) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0064, code lost:
    
        r14 = (r5 & 1152921504606846975L) + 3458764513820540928L;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0073, code lost:
    
        if (r3.compareAndSet(r4, r5, r14) == false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0069, code lost:
    
        r14 = (r5 & 1152921504606846975L) + 2305843009213693952L;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean g(java.lang.Throwable r14, boolean r15) {
        /*
            r13 = this;
            r0 = 60
            r1 = 1152921504606846975(0xfffffffffffffff, double:1.2882297539194265E-231)
            java.util.concurrent.atomic.AtomicLongFieldUpdater r3 = defpackage.zb.f
            if (r15 == 0) goto L25
        Lb:
            long r5 = r3.get(r13)
            long r7 = r5 >> r0
            int r4 = (int) r7
            if (r4 != 0) goto L25
            long r7 = r5 & r1
            od r4 = defpackage.bc.a
            r9 = 1152921504606846976(0x1000000000000000, double:1.2882297539194267E-231)
            long r7 = r7 + r9
            r4 = r13
            boolean r13 = r3.compareAndSet(r4, r5, r7)
            if (r13 == 0) goto L23
            goto L26
        L23:
            r13 = r4
            goto Lb
        L25:
            r4 = r13
        L26:
            wq r8 = defpackage.bc.s
        L28:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r13 = defpackage.zb.m
            r13.getClass()
            r5 = r4
            sun.misc.Unsafe r4 = defpackage.qc.a
            long r6 = defpackage.zb.o
            r9 = r14
            boolean r13 = r4.compareAndSwapObject(r5, r6, r8, r9)
            r14 = r4
            r4 = r5
            r10 = 1
            if (r13 == 0) goto L3e
            r13 = r10
            goto L45
        L3e:
            java.lang.Object r13 = r14.getObjectVolatile(r4, r6)
            if (r13 == r8) goto L7e
            r13 = 0
        L45:
            r11 = 3458764513820540928(0x3000000000000000, double:1.727233711018889E-77)
            if (r15 == 0) goto L58
        L49:
            long r5 = r3.get(r4)
            long r14 = r5 & r1
            long r7 = r11 + r14
            boolean r14 = r3.compareAndSet(r4, r5, r7)
            if (r14 == 0) goto L49
            goto L75
        L58:
            long r5 = r3.get(r4)
            long r14 = r5 >> r0
            int r14 = (int) r14
            if (r14 == 0) goto L69
            if (r14 == r10) goto L64
            goto L75
        L64:
            long r14 = r5 & r1
            long r14 = r14 + r11
        L67:
            r7 = r14
            goto L6f
        L69:
            long r14 = r5 & r1
            r7 = 2305843009213693952(0x2000000000000000, double:1.4916681462400413E-154)
            long r14 = r14 + r7
            goto L67
        L6f:
            boolean r14 = r3.compareAndSet(r4, r5, r7)
            if (r14 == 0) goto L58
        L75:
            r4.y()
            if (r13 == 0) goto L7d
            r4.w()
        L7d:
            return r13
        L7e:
            r14 = r9
            goto L28
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zb.g(java.lang.Throwable, boolean):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0046, code lost:
    
        r1 = (defpackage.od) r1.e();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.od h(long r12) {
        /*
            Method dump skipped, instructions count: 217
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zb.h(long):od");
    }

    public final void i(long j2) {
        k.getClass();
        od odVar = (od) qc.a.getObjectVolatile(this, r);
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = g;
            long j3 = atomicLongFieldUpdater.get(this);
            if (j2 < Math.max(this.e + j3, h.get(this))) {
                return;
            }
            zb zbVar = this;
            if (atomicLongFieldUpdater.compareAndSet(zbVar, j3, 1 + j3)) {
                long j4 = bc.b;
                long j5 = j3 / j4;
                int i2 = (int) (j3 % j4);
                if (odVar.e != j5) {
                    od m2 = zbVar.m(j5, odVar);
                    if (m2 != null) {
                        odVar = m2;
                    }
                }
                od odVar2 = odVar;
                if (zbVar.J(odVar2, i2, j3, null) == bc.o) {
                    if (j3 < zbVar.t()) {
                        odVar2.a();
                    }
                } else {
                    odVar2.a();
                }
                this = zbVar;
                odVar = odVar2;
            }
            this = zbVar;
        }
    }

    public final void j() {
        zb zbVar;
        if (A()) {
            return;
        }
        l.getClass();
        od odVar = (od) qc.a.getObjectVolatile(this, p);
        loop0: while (true) {
            long andIncrement = h.getAndIncrement(this);
            long j2 = bc.b;
            long j3 = andIncrement / j2;
            if (this.t() <= andIncrement) {
                if (odVar.e < j3 && odVar.c() != null) {
                    this.B(j3, odVar);
                }
                v(this);
                return;
            }
            zbVar = this;
            if (odVar.e != j3) {
                od l2 = zbVar.l(j3, odVar, andIncrement);
                if (l2 == null) {
                    continue;
                    this = zbVar;
                } else {
                    odVar = l2;
                }
            }
            int i2 = (int) (andIncrement % j2);
            Object p2 = odVar.p(i2);
            boolean z = p2 instanceof y51;
            AtomicLongFieldUpdater atomicLongFieldUpdater = g;
            if (z && andIncrement >= atomicLongFieldUpdater.get(zbVar) && odVar.o(i2, p2, bc.g)) {
                if (H(p2)) {
                    odVar.s(i2, bc.d);
                    break;
                } else {
                    odVar.s(i2, bc.j);
                    odVar.m();
                    v(zbVar);
                }
            } else {
                while (true) {
                    Object p3 = odVar.p(i2);
                    if (p3 instanceof y51) {
                        if (andIncrement < atomicLongFieldUpdater.get(zbVar)) {
                            if (odVar.o(i2, p3, new z51((y51) p3))) {
                                break loop0;
                            }
                        } else if (odVar.o(i2, p3, bc.g)) {
                            if (H(p3)) {
                                odVar.s(i2, bc.d);
                                break;
                            } else {
                                odVar.s(i2, bc.j);
                                odVar.m();
                            }
                        }
                    } else if (p3 != bc.j) {
                        if (p3 == null) {
                            if (odVar.o(i2, p3, bc.e)) {
                                break loop0;
                            }
                        } else if (p3 == bc.d || p3 == bc.h || p3 == bc.i || p3 == bc.k || p3 == bc.l) {
                            break loop0;
                        } else if (p3 != bc.f) {
                            v7.e(p3, "Unexpected cell state: ");
                            return;
                        }
                    } else {
                        break;
                    }
                }
                v(zbVar);
            }
            this = zbVar;
        }
        v(zbVar);
    }

    @Override // defpackage.ed
    public final Object k(sz0 sz0Var) {
        return D(this, sz0Var);
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x009b, code lost:
    
        if ((r0.addAndGet(r17, r2 - r21) & 4611686018427387904L) != 0) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a4, code lost:
    
        if ((r0.get(r17) & 4611686018427387904L) == 0) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a7, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.od l(long r18, defpackage.od r20, long r21) {
        /*
            r17 = this;
            r1 = r17
            r6 = r18
            od r0 = defpackage.bc.a
            ac r8 = defpackage.ac.l
            r9 = r20
        La:
            java.lang.Object r10 = defpackage.k81.s(r9, r6, r8)
            boolean r0 = defpackage.n30.v(r10)
            if (r0 != 0) goto L5c
            ku0 r5 = defpackage.n30.u(r10)
        L18:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = defpackage.zb.l
            r0.getClass()
            sun.misc.Unsafe r0 = defpackage.qc.a
            long r11 = defpackage.zb.p
            java.lang.Object r0 = r0.getObjectVolatile(r1, r11)
            r4 = r0
            ku0 r4 = (defpackage.ku0) r4
            long r2 = r4.e
            long r13 = r5.e
            int r0 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
            if (r0 < 0) goto L31
            goto L5c
        L31:
            boolean r0 = r5.n()
            if (r0 != 0) goto L38
            goto La
        L38:
            sun.misc.Unsafe r0 = defpackage.qc.a
            long r2 = defpackage.zb.p
            boolean r2 = r0.compareAndSwapObject(r1, r2, r4, r5)
            if (r2 == 0) goto L4c
            boolean r0 = r4.j()
            if (r0 == 0) goto L5c
            r4.h()
            goto L5c
        L4c:
            java.lang.Object r0 = r0.getObjectVolatile(r1, r11)
            if (r0 == r4) goto L38
            boolean r0 = r5.j()
            if (r0 == 0) goto L18
            r5.h()
            goto L18
        L5c:
            boolean r0 = defpackage.n30.v(r10)
            r8 = 0
            if (r0 == 0) goto L6d
            r1.y()
            r17.B(r18, r20)
            v(r1)
            return r8
        L6d:
            ku0 r0 = defpackage.n30.u(r10)
            od r0 = (defpackage.od) r0
            long r2 = r0.e
            int r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r4 <= 0) goto Lac
            r4 = 1
            long r4 = r21 + r4
            int r0 = defpackage.bc.b
            long r6 = (long) r0
            long r2 = r2 * r6
            java.util.concurrent.atomic.AtomicLongFieldUpdater r0 = defpackage.zb.h
            r15 = r4
            r4 = r2
            r2 = r15
            boolean r0 = r0.compareAndSet(r1, r2, r4)
            if (r0 == 0) goto La8
            long r2 = r4 - r21
            java.util.concurrent.atomic.AtomicLongFieldUpdater r0 = defpackage.zb.i
            long r2 = r0.addAndGet(r1, r2)
            r4 = 4611686018427387904(0x4000000000000000, double:2.0)
            long r2 = r2 & r4
            r6 = 0
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 == 0) goto La7
        L9d:
            long r2 = r0.get(r1)
            long r2 = r2 & r4
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 == 0) goto La7
            goto L9d
        La7:
            return r8
        La8:
            v(r1)
            return r8
        Lac:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zb.l(long, od, long):od");
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x00d0, code lost:
    
        if (r8.j() == false) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00d2, code lost:
    
        r8.h();
     */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0107 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.od m(long r16, defpackage.od r18) {
        /*
            Method dump skipped, instructions count: 264
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zb.m(long, od):od");
    }

    public final od n(long j2, od odVar) {
        Object s2;
        long j3;
        long j4;
        Unsafe unsafe;
        od odVar2 = bc.a;
        ac acVar = ac.l;
        loop0: while (true) {
            s2 = k81.s(odVar, j2, acVar);
            if (!n30.v(s2)) {
                ku0 u = n30.u(s2);
                while (true) {
                    j.getClass();
                    Unsafe unsafe2 = qc.a;
                    long j5 = s;
                    ku0 ku0Var = (ku0) unsafe2.getObjectVolatile(this, j5);
                    if (ku0Var.e >= u.e) {
                        break loop0;
                    }
                    if (!u.n()) {
                        break;
                    }
                    do {
                        unsafe = qc.a;
                        if (unsafe.compareAndSwapObject(this, s, ku0Var, u)) {
                            if (ku0Var.j()) {
                                ku0Var.h();
                            }
                        }
                    } while (unsafe.getObjectVolatile(this, j5) == ku0Var);
                    if (u.j()) {
                        u.h();
                    }
                }
            } else {
                break;
            }
        }
        boolean v = n30.v(s2);
        AtomicLongFieldUpdater atomicLongFieldUpdater = g;
        if (v) {
            y();
            if (odVar.e * bc.b < atomicLongFieldUpdater.get(this)) {
                odVar.a();
                return null;
            }
        } else {
            od odVar3 = (od) n30.u(s2);
            long j6 = odVar3.e;
            if (j6 > j2) {
                long j7 = j6 * bc.b;
                do {
                    j3 = f.get(this);
                    j4 = 1152921504606846975L & j3;
                    if (j4 >= j7) {
                        break;
                    }
                } while (!f.compareAndSet(this, j3, j4 + (((int) (j3 >> 60)) << 60)));
                if (j6 * bc.b < atomicLongFieldUpdater.get(this)) {
                    odVar3.a();
                }
            } else {
                return odVar3;
            }
        }
        return null;
    }

    @Override // defpackage.ed
    public final Object o() {
        od odVar;
        nd ndVar = dl.i;
        AtomicLongFieldUpdater atomicLongFieldUpdater = g;
        long j2 = atomicLongFieldUpdater.get(this);
        AtomicLongFieldUpdater atomicLongFieldUpdater2 = f;
        long j3 = atomicLongFieldUpdater2.get(this);
        if (x(j3, true)) {
            return new md(p());
        }
        if (j2 >= (j3 & 1152921504606846975L)) {
            return ndVar;
        }
        Object obj = bc.k;
        k.getClass();
        od odVar2 = (od) qc.a.getObjectVolatile(this, r);
        while (!this.x(atomicLongFieldUpdater2.get(this), true)) {
            long andIncrement = atomicLongFieldUpdater.getAndIncrement(this);
            long j4 = bc.b;
            long j5 = andIncrement / j4;
            int i2 = (int) (andIncrement % j4);
            if (odVar2.e != j5) {
                od m2 = this.m(j5, odVar2);
                if (m2 == null) {
                    continue;
                } else {
                    odVar = m2;
                }
            } else {
                odVar = odVar2;
            }
            zb zbVar = this;
            Object J = zbVar.J(odVar, i2, andIncrement, obj);
            odVar2 = odVar;
            y51 y51Var = null;
            if (J == bc.m) {
                if (obj instanceof y51) {
                    y51Var = (y51) obj;
                }
                if (y51Var != null) {
                    y51Var.a(odVar2, i2);
                }
                zbVar.L(andIncrement);
                odVar2.m();
                return ndVar;
            }
            if (J == bc.o) {
                if (andIncrement < zbVar.t()) {
                    odVar2.a();
                }
                this = zbVar;
            } else {
                if (J != bc.n) {
                    odVar2.a();
                    return J;
                }
                v7.o("unexpected");
                return null;
            }
        }
        return new md(this.p());
    }

    public final Throwable p() {
        m.getClass();
        return (Throwable) qc.a.getObjectVolatile(this, o);
    }

    @Override // defpackage.jv0
    public Object q(Object obj) {
        boolean z;
        nd ndVar = dl.i;
        AtomicLongFieldUpdater atomicLongFieldUpdater = f;
        long j2 = atomicLongFieldUpdater.get(this);
        boolean z2 = false;
        long j3 = 1152921504606846975L;
        if (x(j2, false)) {
            z = false;
        } else {
            z = !e(j2 & 1152921504606846975L);
        }
        if (z) {
            return ndVar;
        }
        Object obj2 = bc.j;
        j.getClass();
        od odVar = (od) qc.a.getObjectVolatile(this, s);
        while (true) {
            long andIncrement = atomicLongFieldUpdater.getAndIncrement(this);
            long j4 = andIncrement & j3;
            boolean x = x(andIncrement, z2);
            int i2 = bc.b;
            long j5 = i2;
            long j6 = j4 / j5;
            int i3 = (int) (j4 % j5);
            if (odVar.e != j6) {
                od n2 = n(j6, odVar);
                if (n2 == null) {
                    if (x) {
                        return new md(s());
                    }
                    z2 = false;
                    j3 = 1152921504606846975L;
                } else {
                    odVar = n2;
                }
            }
            int c = c(this, odVar, i3, obj, j4, obj2, x);
            x31 x31Var = x31.a;
            if (c != 0) {
                if (c != 1) {
                    y51 y51Var = null;
                    if (c != 2) {
                        if (c != 3) {
                            if (c != 4) {
                                if (c == 5) {
                                    odVar.a();
                                }
                                z2 = false;
                                j3 = 1152921504606846975L;
                            } else {
                                if (j4 < g.get(this)) {
                                    odVar.a();
                                }
                                return new md(s());
                            }
                        } else {
                            v7.o("unexpected");
                            return null;
                        }
                    } else {
                        if (x) {
                            odVar.m();
                            return new md(s());
                        }
                        if (obj2 instanceof y51) {
                            y51Var = (y51) obj2;
                        }
                        if (y51Var != null) {
                            y51Var.a(odVar, i3 + i2);
                        }
                        odVar.m();
                        return ndVar;
                    }
                } else {
                    return x31Var;
                }
            } else {
                odVar.a();
                return x31Var;
            }
        }
    }

    public final Throwable r() {
        Throwable p2 = p();
        if (p2 == null) {
            return new NoSuchElementException("Channel was closed");
        }
        return p2;
    }

    public final Throwable s() {
        Throwable p2 = p();
        if (p2 == null) {
            return new IllegalStateException("Channel was closed");
        }
        return p2;
    }

    public final long t() {
        return f.get(this) & 1152921504606846975L;
    }

    /* JADX WARN: Code restructure failed: missing block: B:92:0x019e, code lost:
    
        r15 = r8;
        r3 = (defpackage.od) r3.c();
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01a6, code lost:
    
        if (r3 != null) goto L81;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.String toString() {
        /*
            Method dump skipped, instructions count: 480
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zb.toString():java.lang.String");
    }

    public final boolean u() {
        while (true) {
            k.getClass();
            Unsafe unsafe = qc.a;
            long j2 = r;
            od odVar = (od) unsafe.getObjectVolatile(this, j2);
            AtomicLongFieldUpdater atomicLongFieldUpdater = g;
            long j3 = atomicLongFieldUpdater.get(this);
            if (t() > j3) {
                long j4 = bc.b;
                long j5 = j3 / j4;
                if (odVar.e != j5 && (odVar = m(j5, odVar)) == null) {
                    if (((od) unsafe.getObjectVolatile(this, j2)).e < j5) {
                        return false;
                    }
                } else {
                    odVar.a();
                    int i2 = (int) (j3 % j4);
                    while (true) {
                        Object p2 = odVar.p(i2);
                        if (p2 != null && p2 != bc.e) {
                            if (p2 != bc.d) {
                                if (p2 != bc.j && p2 != bc.l && p2 != bc.i && p2 != bc.h) {
                                    if (p2 != bc.g) {
                                        if (p2 != bc.f && j3 == atomicLongFieldUpdater.get(this)) {
                                            return true;
                                        }
                                    } else {
                                        return true;
                                    }
                                }
                            } else {
                                return true;
                            }
                        } else if (odVar.o(i2, p2, bc.h)) {
                            j();
                            break;
                        }
                    }
                    g.compareAndSet(this, j3, j3 + 1);
                }
            } else {
                return false;
            }
        }
    }

    public final void w() {
        Object objectVolatile;
        wq wqVar;
        zb zbVar;
        loop0: while (true) {
            n.getClass();
            Unsafe unsafe = qc.a;
            long j2 = q;
            objectVolatile = unsafe.getObjectVolatile(this, j2);
            if (objectVolatile == null) {
                wqVar = bc.q;
            } else {
                wqVar = bc.r;
            }
            wq wqVar2 = wqVar;
            while (true) {
                Unsafe unsafe2 = qc.a;
                zbVar = this;
                if (unsafe2.compareAndSwapObject(zbVar, q, objectVolatile, wqVar2)) {
                    break loop0;
                } else if (unsafe2.getObjectVolatile(zbVar, j2) != objectVolatile) {
                    break;
                } else {
                    this = zbVar;
                }
            }
            this = zbVar;
        }
        if (objectVolatile == null) {
            return;
        }
        f31.n(1, objectVolatile);
        ((gv) objectVolatile).e(zbVar.p());
    }

    /* JADX WARN: Code restructure failed: missing block: B:84:0x00a3, code lost:
    
        r10 = (defpackage.od) r10.e();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean x(long r10, boolean r12) {
        /*
            Method dump skipped, instructions count: 242
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.zb.x(long, boolean):boolean");
    }

    public final boolean y() {
        return x(f.get(this), false);
    }

    public boolean z() {
        return false;
    }
}
