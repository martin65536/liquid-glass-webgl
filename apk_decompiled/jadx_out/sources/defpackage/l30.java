package defpackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class l30 implements d30 {
    public static final /* synthetic */ AtomicReferenceFieldUpdater e = AtomicReferenceFieldUpdater.newUpdater(l30.class, Object.class, "_state$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater f;
    public static final /* synthetic */ long g;
    public static final /* synthetic */ long h;
    private volatile /* synthetic */ Object _parentHandle$volatile;
    private volatile /* synthetic */ Object _state$volatile;

    static {
        Unsafe unsafe = qc.a;
        h = unsafe.objectFieldOffset(l30.class.getDeclaredField("_state$volatile"));
        f = AtomicReferenceFieldUpdater.newUpdater(l30.class, Object.class, "_parentHandle$volatile");
        g = unsafe.objectFieldOffset(l30.class.getDeclaredField("_parentHandle$volatile"));
    }

    public l30(boolean z) {
        zq zqVar;
        if (z) {
            zqVar = o20.m;
        } else {
            zqVar = o20.l;
        }
        this._state$volatile = zqVar;
    }

    public static td Y(ab0 ab0Var) {
        while (ab0Var.n()) {
            ab0Var = ab0Var.m();
        }
        while (true) {
            ab0Var = ab0Var.l();
            if (!ab0Var.n()) {
                if (ab0Var instanceof td) {
                    return (td) ab0Var;
                }
                if (ab0Var instanceof pg0) {
                    return null;
                }
            }
        }
    }

    public static String h0(Object obj) {
        if (obj instanceof k30) {
            k30 k30Var = (k30) obj;
            if (k30Var.f()) {
                return "Cancelling";
            }
            if (k30.f.get(k30Var) == 0) {
                return "Active";
            }
            return "Completing";
        }
        if (obj instanceof sz) {
            if (((sz) obj).b()) {
                return "Active";
            }
            return "New";
        }
        if (obj instanceof qf) {
            return "Cancelled";
        }
        return "Completed";
    }

    public void B(Object obj) {
        A(obj);
    }

    public final Object C(jj jjVar) {
        Object Q;
        do {
            Q = Q();
            if (!(Q instanceof sz)) {
                if (!(Q instanceof qf)) {
                    return o20.K(Q);
                }
                throw ((qf) Q).a;
            }
        } while (g0(Q) < 0);
        i30 i30Var = new i30(t20.w(jjVar), this);
        i30Var.s();
        i30Var.y(new kc(1, g30.v(this, true, new lq0(i30Var))));
        return i30Var.p();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0034, code lost:
    
        r0 = defpackage.o20.g;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0038, code lost:
    
        if (r0 != defpackage.o20.h) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x00cb, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:
    
        r0 = k0(r0, new defpackage.qf(J(r8), false));
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0031, code lost:
    
        if (r0 == defpackage.o20.i) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003e, code lost:
    
        if (r0 != defpackage.o20.g) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0040, code lost:
    
        r0 = null;
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0042, code lost:
    
        r4 = Q();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0048, code lost:
    
        if ((r4 instanceof defpackage.k30) == false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x008c, code lost:
    
        if ((r4 instanceof defpackage.sz) == false) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x008e, code lost:
    
        if (r1 != null) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0090, code lost:
    
        r1 = J(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0094, code lost:
    
        r5 = (defpackage.sz) r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:2:0x0008, code lost:
    
        if (N() != false) goto L4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x009b, code lost:
    
        if (r5.b() == false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a6, code lost:
    
        r5 = k0(r4, new defpackage.qf(r1, false));
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b1, code lost:
    
        if (r5 == defpackage.o20.g) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b5, code lost:
    
        if (r5 == defpackage.o20.i) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b7, code lost:
    
        r0 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:3:0x000a, code lost:
    
        r0 = Q();
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b9, code lost:
    
        defpackage.v7.e(r4, "Cannot happen in ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00be, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00a1, code lost:
    
        if (j0(r5, r1) == false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00a3, code lost:
    
        r8 = defpackage.o20.g;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0059, code lost:
    
        r0 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x0010, code lost:
    
        if ((r0 instanceof defpackage.sz) == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00bf, code lost:
    
        r8 = defpackage.o20.j;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x004a, code lost:
    
        monitor-enter(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0054, code lost:
    
        if (((defpackage.k30) r4).c() != defpackage.o20.k) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0056, code lost:
    
        r8 = defpackage.o20.j;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0058, code lost:
    
        monitor-exit(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x005e, code lost:
    
        r5 = ((defpackage.k30) r4).f();
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0065, code lost:
    
        if (r1 != null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0067, code lost:
    
        r1 = J(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x006b, code lost:
    
        ((defpackage.k30) r4).a(r1);
        r8 = ((defpackage.k30) r4).e();
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0078, code lost:
    
        if (r5 != false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x007a, code lost:
    
        r0 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x007b, code lost:
    
        monitor-exit(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x007c, code lost:
    
        if (r0 == null) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x007e, code lost:
    
        Z(((defpackage.k30) r4).e, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0085, code lost:
    
        r8 = defpackage.o20.g;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0014, code lost:
    
        if ((r0 instanceof defpackage.k30) == false) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00c4, code lost:
    
        if (r0 != defpackage.o20.g) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00c9, code lost:
    
        if (r0 != defpackage.o20.h) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00ce, code lost:
    
        if (r0 != defpackage.o20.j) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00d0, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00d1, code lost:
    
        A(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00d4, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x001f, code lost:
    
        if (defpackage.k30.f.get((defpackage.k30) r0) == 0) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean D(java.lang.Object r8) {
        /*
            Method dump skipped, instructions count: 213
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l30.D(java.lang.Object):boolean");
    }

    public void E(CancellationException cancellationException) {
        D(cancellationException);
    }

    public final boolean F(Throwable th) {
        if (!V()) {
            boolean z = th instanceof CancellationException;
            sd P = P();
            if (P != null && P != sg0.e) {
                if (!P.c(th) && !z) {
                    return false;
                }
                return true;
            }
            return z;
        }
        return true;
    }

    public String G() {
        return "Job was cancelled";
    }

    public boolean H(Throwable th) {
        if (!(th instanceof CancellationException)) {
            if (D(th) && M()) {
                return true;
            }
            return false;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [rf, java.lang.RuntimeException] */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.Throwable, rf] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.RuntimeException] */
    /* JADX WARN: Type inference failed for: r1v5 */
    public final void I(sz szVar, Object obj) {
        qf qfVar;
        Throwable th;
        sd P = P();
        if (P != null) {
            P.a();
            f0(sg0.e);
        }
        rf rfVar = 0;
        if (obj instanceof qf) {
            qfVar = (qf) obj;
        } else {
            qfVar = null;
        }
        if (qfVar != null) {
            th = qfVar.a;
        } else {
            th = null;
        }
        if (szVar instanceof h30) {
            try {
                ((h30) szVar).s(th);
                return;
            } catch (Throwable th2) {
                S(new RuntimeException("Exception in completion handler " + szVar + " for " + this, th2));
                return;
            }
        }
        pg0 d = szVar.d();
        if (d != null) {
            d.e(new la0(1), 1);
            Object k = d.k();
            k.getClass();
            ab0 ab0Var = (ab0) k;
            while (!ab0Var.equals(d)) {
                if (ab0Var instanceof h30) {
                    try {
                        ((h30) ab0Var).s(th);
                    } catch (Throwable th3) {
                        if (rfVar != 0) {
                            o20.d(rfVar, th3);
                        } else {
                            rfVar = new RuntimeException("Exception in completion handler " + ab0Var + " for " + this, th3);
                        }
                    }
                }
                ab0Var = ab0Var.l();
                rfVar = rfVar;
            }
            if (rfVar != 0) {
                S(rfVar);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.Throwable] */
    public final Throwable J(Object obj) {
        CancellationException cancellationException;
        if (obj instanceof Throwable) {
            return (Throwable) obj;
        }
        l30 l30Var = (l30) obj;
        Object Q = l30Var.Q();
        CancellationException cancellationException2 = null;
        if (Q instanceof k30) {
            cancellationException = ((k30) Q).e();
        } else if (Q instanceof qf) {
            cancellationException = ((qf) Q).a;
        } else if (!(Q instanceof sz)) {
            cancellationException = null;
        } else {
            v7.e(Q, "Cannot be cancelling child in this state: ");
            return null;
        }
        if (cancellationException instanceof CancellationException) {
            cancellationException2 = cancellationException;
        }
        if (cancellationException2 == null) {
            return new e30("Parent job is ".concat(h0(Q)), cancellationException, l30Var);
        }
        return cancellationException2;
    }

    public final Object K(k30 k30Var, Object obj) {
        qf qfVar;
        k30 k30Var2;
        Throwable th;
        Throwable L;
        Object obj2;
        l30 l30Var;
        k30 k30Var3;
        Throwable th2 = null;
        if (obj instanceof qf) {
            qfVar = (qf) obj;
        } else {
            qfVar = null;
        }
        if (qfVar != null) {
            th2 = qfVar.a;
        }
        synchronized (k30Var) {
            try {
                k30Var.f();
                ArrayList g2 = k30Var.g(th2);
                L = L(k30Var, g2);
                if (L != null) {
                    try {
                        if (g2.size() > 1) {
                            Set newSetFromMap = Collections.newSetFromMap(new IdentityHashMap(g2.size()));
                            int size = g2.size();
                            int i = 0;
                            while (i < size) {
                                Object obj3 = g2.get(i);
                                i++;
                                Throwable th3 = (Throwable) obj3;
                                if (th3 != L && th3 != L && !(th3 instanceof CancellationException) && newSetFromMap.add(th3)) {
                                    o20.d(L, th3);
                                }
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        k30Var2 = k30Var;
                        throw th;
                    }
                }
            } catch (Throwable th5) {
                k30Var2 = k30Var;
                th = th5;
            }
        }
        if (L != null && L != th2) {
            obj = new qf(L, false);
        }
        if (L != null && (F(L) || R(L))) {
            obj.getClass();
            qf.b.compareAndSet((qf) obj, 0, 1);
        }
        a0(obj);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = e;
        if (obj instanceof sz) {
            obj2 = new tz((sz) obj);
        } else {
            obj2 = obj;
        }
        while (true) {
            atomicReferenceFieldUpdater.getClass();
            Unsafe unsafe = qc.a;
            long j = h;
            l30Var = this;
            k30Var3 = k30Var;
            if (!unsafe.compareAndSwapObject(l30Var, j, k30Var3, obj2) && unsafe.getObjectVolatile(l30Var, j) == k30Var3) {
                this = l30Var;
                k30Var = k30Var3;
            }
        }
        l30Var.I(k30Var3, obj);
        return obj;
    }

    public final Throwable L(k30 k30Var, ArrayList arrayList) {
        Object obj;
        Object obj2 = null;
        if (arrayList.isEmpty()) {
            if (!k30Var.f()) {
                return null;
            }
            return new e30(G(), null, this);
        }
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 < size) {
                obj = arrayList.get(i2);
                i2++;
                if (!(((Throwable) obj) instanceof CancellationException)) {
                    break;
                }
            } else {
                obj = null;
                break;
            }
        }
        Throwable th = (Throwable) obj;
        if (th != null) {
            return th;
        }
        Throwable th2 = (Throwable) arrayList.get(0);
        if (th2 instanceof e21) {
            int size2 = arrayList.size();
            while (true) {
                if (i >= size2) {
                    break;
                }
                Object obj3 = arrayList.get(i);
                i++;
                Throwable th3 = (Throwable) obj3;
                if (th3 != th2 && (th3 instanceof e21)) {
                    obj2 = obj3;
                    break;
                }
            }
            Throwable th4 = (Throwable) obj2;
            if (th4 != null) {
                return th4;
            }
        }
        return th2;
    }

    public boolean M() {
        return true;
    }

    public boolean N() {
        return this instanceof nf;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [pg0, ab0] */
    public final pg0 O(sz szVar) {
        pg0 d = szVar.d();
        if (d == null) {
            if (szVar instanceof zq) {
                return new ab0();
            }
            if (szVar instanceof h30) {
                d0((h30) szVar);
                return null;
            }
            v7.e(szVar, "State should have list: ");
            return null;
        }
        return d;
    }

    public final sd P() {
        f.getClass();
        return (sd) qc.a.getObjectVolatile(this, g);
    }

    public final Object Q() {
        e.getClass();
        return qc.a.getObjectVolatile(this, h);
    }

    public boolean R(Throwable th) {
        return false;
    }

    public final void T(d30 d30Var) {
        sg0 sg0Var = sg0.e;
        if (d30Var == null) {
            f0(sg0Var);
            return;
        }
        d30Var.e();
        sd h2 = d30Var.h(this);
        f0(h2);
        if (!(Q() instanceof sz)) {
            h2.a();
            f0(sg0Var);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0074, code lost:
    
        return r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.un U(boolean r7, defpackage.h30 r8) {
        /*
            r6 = this;
            r8.k = r6
        L2:
            java.lang.Object r4 = r6.Q()
            boolean r0 = r4 instanceof defpackage.zq
            if (r0 == 0) goto L33
            r0 = r4
            zq r0 = (defpackage.zq) r0
            boolean r1 = r0.e
            if (r1 == 0) goto L2d
        L11:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = defpackage.l30.e
            r0.getClass()
            sun.misc.Unsafe r0 = defpackage.qc.a
            long r2 = defpackage.l30.h
            r1 = r6
            r5 = r8
            boolean r6 = r0.compareAndSwapObject(r1, r2, r4, r5)
            if (r6 == 0) goto L23
            goto L74
        L23:
            java.lang.Object r6 = r0.getObjectVolatile(r1, r2)
            if (r6 == r4) goto L2a
            goto L75
        L2a:
            r6 = r1
            r8 = r5
            goto L11
        L2d:
            r1 = r6
            r5 = r8
            r1.c0(r0)
            goto L75
        L33:
            r1 = r6
            r5 = r8
            boolean r6 = r4 instanceof defpackage.sz
            sg0 r8 = defpackage.sg0.e
            r0 = 0
            if (r6 == 0) goto L78
            r6 = r4
            sz r6 = (defpackage.sz) r6
            pg0 r2 = r6.d()
            if (r2 != 0) goto L4b
            h30 r4 = (defpackage.h30) r4
            r1.d0(r4)
            goto L75
        L4b:
            boolean r3 = r5.r()
            if (r3 == 0) goto L6d
            boolean r3 = r6 instanceof defpackage.k30
            if (r3 == 0) goto L58
            k30 r6 = (defpackage.k30) r6
            goto L59
        L58:
            r6 = r0
        L59:
            if (r6 == 0) goto L5f
            java.lang.Throwable r0 = r6.e()
        L5f:
            if (r0 != 0) goto L67
            r6 = 5
            boolean r6 = r2.e(r5, r6)
            goto L72
        L67:
            if (r7 == 0) goto L8d
            r5.s(r0)
            return r8
        L6d:
            r6 = 1
            boolean r6 = r2.e(r5, r6)
        L72:
            if (r6 == 0) goto L75
        L74:
            return r5
        L75:
            r6 = r1
            r8 = r5
            goto L2
        L78:
            if (r7 == 0) goto L8d
            java.lang.Object r6 = r1.Q()
            boolean r7 = r6 instanceof defpackage.qf
            if (r7 == 0) goto L85
            qf r6 = (defpackage.qf) r6
            goto L86
        L85:
            r6 = r0
        L86:
            if (r6 == 0) goto L8a
            java.lang.Throwable r0 = r6.a
        L8a:
            r5.s(r0)
        L8d:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l30.U(boolean, h30):un");
    }

    public boolean V() {
        return this instanceof ga;
    }

    public final Object W(Object obj) {
        Object k0;
        qf qfVar;
        do {
            k0 = k0(Q(), obj);
            if (k0 == o20.g) {
                String str = "Job " + this + " is already complete or completing, but is being completed with " + obj;
                Throwable th = null;
                if (obj instanceof qf) {
                    qfVar = (qf) obj;
                } else {
                    qfVar = null;
                }
                if (qfVar != null) {
                    th = qfVar.a;
                }
                throw new IllegalStateException(str, th);
            }
        } while (k0 == o20.i);
        return k0;
    }

    public String X() {
        return getClass().getSimpleName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.Throwable, rf] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.RuntimeException] */
    /* JADX WARN: Type inference failed for: r1v5 */
    public final void Z(pg0 pg0Var, Throwable th) {
        pg0Var.e(new la0(4), 4);
        Object k = pg0Var.k();
        k.getClass();
        ab0 ab0Var = (ab0) k;
        rf rfVar = 0;
        while (!ab0Var.equals(pg0Var)) {
            if ((ab0Var instanceof h30) && ((h30) ab0Var).r()) {
                try {
                    ((h30) ab0Var).s(th);
                } catch (Throwable th2) {
                    if (rfVar != 0) {
                        o20.d(rfVar, th2);
                    } else {
                        rfVar = new RuntimeException("Exception in completion handler " + ab0Var + " for " + this, th2);
                    }
                }
            }
            ab0Var = ab0Var.l();
            rfVar = rfVar;
        }
        if (rfVar != 0) {
            S(rfVar);
        }
        F(th);
    }

    @Override // defpackage.d30
    public void a(CancellationException cancellationException) {
        if (cancellationException == null) {
            cancellationException = new e30(G(), null, this);
        }
        E(cancellationException);
    }

    @Override // defpackage.d30
    public boolean b() {
        Object Q = Q();
        if ((Q instanceof sz) && ((sz) Q).b()) {
            return true;
        }
        return false;
    }

    public Object c(n8 n8Var) {
        return C(n8Var);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [pg0, ab0] */
    public final void c0(zq zqVar) {
        rz rzVar;
        ?? ab0Var = new ab0();
        if (zqVar.e) {
            rzVar = ab0Var;
        } else {
            rzVar = new rz(ab0Var);
        }
        while (true) {
            e.getClass();
            Unsafe unsafe = qc.a;
            long j = h;
            l30 l30Var = this;
            zq zqVar2 = zqVar;
            if (unsafe.compareAndSwapObject(l30Var, j, zqVar2, rzVar) || unsafe.getObjectVolatile(l30Var, j) != zqVar2) {
                return;
            }
            this = l30Var;
            zqVar = zqVar2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [pg0, ab0] */
    public final void d0(h30 h30Var) {
        h30Var.g(new ab0());
        ab0 l = h30Var.l();
        Object obj = h30Var;
        while (true) {
            e.getClass();
            Unsafe unsafe = qc.a;
            long j = h;
            l30 l30Var = this;
            Object obj2 = obj;
            if (unsafe.compareAndSwapObject(l30Var, j, obj2, l) || unsafe.getObjectVolatile(l30Var, j) != obj2) {
                return;
            }
            this = l30Var;
            obj = obj2;
        }
    }

    @Override // defpackage.d30
    public final boolean e() {
        int g0;
        do {
            g0 = g0(Q());
            if (g0 == 0) {
                return false;
            }
        } while (g0 != 1);
        return true;
    }

    public final void e0(h30 h30Var) {
        l30 l30Var;
        while (true) {
            Object Q = this.Q();
            if (Q instanceof h30) {
                if (Q == h30Var) {
                    zq zqVar = o20.m;
                    while (true) {
                        e.getClass();
                        Unsafe unsafe = qc.a;
                        long j = h;
                        l30Var = this;
                        if (!unsafe.compareAndSwapObject(l30Var, j, Q, zqVar)) {
                            if (unsafe.getObjectVolatile(l30Var, j) != Q) {
                                break;
                            } else {
                                this = l30Var;
                            }
                        } else {
                            return;
                        }
                    }
                } else {
                    return;
                }
            } else {
                if ((Q instanceof sz) && ((sz) Q).d() != null) {
                    h30Var.o();
                    return;
                }
                return;
            }
            this = l30Var;
        }
    }

    public final void f0(sd sdVar) {
        f.getClass();
        qc.a.putObjectVolatile(this, g, sdVar);
    }

    public final int g0(Object obj) {
        Unsafe unsafe;
        Unsafe unsafe2;
        boolean z = obj instanceof zq;
        long j = h;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = e;
        if (z) {
            if (!((zq) obj).e) {
                zq zqVar = o20.m;
                do {
                    atomicReferenceFieldUpdater.getClass();
                    unsafe2 = qc.a;
                    if (unsafe2.compareAndSwapObject(this, h, obj, zqVar)) {
                        b0();
                        return 1;
                    }
                } while (unsafe2.getObjectVolatile(this, j) == obj);
                return -1;
            }
            return 0;
        }
        if (obj instanceof rz) {
            pg0 pg0Var = ((rz) obj).e;
            do {
                atomicReferenceFieldUpdater.getClass();
                unsafe = qc.a;
                if (unsafe.compareAndSwapObject(this, h, obj, pg0Var)) {
                    b0();
                    return 1;
                }
            } while (unsafe.getObjectVolatile(this, j) == obj);
            return -1;
        }
        return 0;
    }

    @Override // defpackage.wj
    public final xj getKey() {
        return x1.L;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x007a, code lost:
    
        return r5;
     */
    @Override // defpackage.d30
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.sd h(defpackage.l30 r7) {
        /*
            r6 = this;
            td r5 = new td
            r5.<init>(r7)
            r5.k = r6
        L7:
            java.lang.Object r4 = r6.Q()
            boolean r7 = r4 instanceof defpackage.zq
            if (r7 == 0) goto L35
            r7 = r4
            zq r7 = (defpackage.zq) r7
            boolean r0 = r7.e
            if (r0 == 0) goto L30
        L16:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r7 = defpackage.l30.e
            r7.getClass()
            sun.misc.Unsafe r0 = defpackage.qc.a
            long r2 = defpackage.l30.h
            r1 = r6
            boolean r6 = r0.compareAndSwapObject(r1, r2, r4, r5)
            if (r6 == 0) goto L27
            goto L7a
        L27:
            java.lang.Object r6 = r0.getObjectVolatile(r1, r2)
            if (r6 == r4) goto L2e
            goto L4b
        L2e:
            r6 = r1
            goto L16
        L30:
            r1 = r6
            r1.c0(r7)
            goto L4b
        L35:
            r1 = r6
            boolean r6 = r4 instanceof defpackage.sz
            sg0 r7 = defpackage.sg0.e
            r0 = 0
            if (r6 == 0) goto L7c
            r6 = r4
            sz r6 = (defpackage.sz) r6
            pg0 r6 = r6.d()
            if (r6 != 0) goto L4d
            h30 r4 = (defpackage.h30) r4
            r1.d0(r4)
        L4b:
            r6 = r1
            goto L7
        L4d:
            r2 = 7
            boolean r2 = r6.e(r5, r2)
            if (r2 == 0) goto L55
            goto L7a
        L55:
            r2 = 3
            boolean r6 = r6.e(r5, r2)
            java.lang.Object r1 = r1.Q()
            boolean r2 = r1 instanceof defpackage.k30
            if (r2 == 0) goto L69
            k30 r1 = (defpackage.k30) r1
            java.lang.Throwable r0 = r1.e()
            goto L75
        L69:
            boolean r2 = r1 instanceof defpackage.qf
            if (r2 == 0) goto L70
            qf r1 = (defpackage.qf) r1
            goto L71
        L70:
            r1 = r0
        L71:
            if (r1 == 0) goto L75
            java.lang.Throwable r0 = r1.a
        L75:
            r5.s(r0)
            if (r6 == 0) goto L7b
        L7a:
            return r5
        L7b:
            return r7
        L7c:
            java.lang.Object r6 = r1.Q()
            boolean r1 = r6 instanceof defpackage.qf
            if (r1 == 0) goto L87
            qf r6 = (defpackage.qf) r6
            goto L88
        L87:
            r6 = r0
        L88:
            if (r6 == 0) goto L8c
            java.lang.Throwable r0 = r6.a
        L8c:
            r5.s(r0)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l30.h(l30):sd");
    }

    @Override // defpackage.yj
    public final yj i(yj yjVar) {
        return jc0.z(this, yjVar);
    }

    public final boolean i0(sz szVar, Object obj) {
        Object obj2;
        if (obj instanceof sz) {
            obj2 = new tz((sz) obj);
        } else {
            obj2 = obj;
        }
        while (true) {
            e.getClass();
            Unsafe unsafe = qc.a;
            long j = h;
            l30 l30Var = this;
            sz szVar2 = szVar;
            if (unsafe.compareAndSwapObject(l30Var, j, szVar2, obj2)) {
                l30Var.a0(obj);
                l30Var.I(szVar2, obj);
                return true;
            }
            if (unsafe.getObjectVolatile(l30Var, j) != szVar2) {
                return false;
            }
            this = l30Var;
            szVar = szVar2;
        }
    }

    @Override // defpackage.yj
    public final wj j(xj xjVar) {
        return jc0.p(this, xjVar);
    }

    public final boolean j0(sz szVar, Throwable th) {
        pg0 O = O(szVar);
        if (O != null) {
            k30 k30Var = new k30(O, th);
            while (true) {
                e.getClass();
                Unsafe unsafe = qc.a;
                long j = h;
                l30 l30Var = this;
                sz szVar2 = szVar;
                if (unsafe.compareAndSwapObject(l30Var, j, szVar2, k30Var)) {
                    l30Var.Z(O, th);
                    return true;
                }
                if (unsafe.getObjectVolatile(l30Var, j) != szVar2) {
                    return false;
                }
                this = l30Var;
                szVar = szVar2;
            }
        } else {
            return false;
        }
    }

    public final Object k0(Object obj, Object obj2) {
        k30 k30Var;
        boolean z;
        qf qfVar;
        if (!(obj instanceof sz)) {
            return o20.g;
        }
        if (((obj instanceof zq) || (obj instanceof h30)) && !(obj instanceof td) && !(obj2 instanceof qf)) {
            if (i0((sz) obj, obj2)) {
                return obj2;
            }
            return o20.i;
        }
        sz szVar = (sz) obj;
        pg0 O = O(szVar);
        if (O == null) {
            return o20.i;
        }
        Throwable th = null;
        if (szVar instanceof k30) {
            k30Var = (k30) szVar;
        } else {
            k30Var = null;
        }
        if (k30Var == null) {
            k30Var = new k30(O, null);
        }
        synchronized (k30Var) {
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = k30.f;
            if (atomicIntegerFieldUpdater.get(k30Var) != 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return o20.g;
            }
            atomicIntegerFieldUpdater.set(k30Var, 1);
            if (k30Var != szVar) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = e;
                while (!atomicReferenceFieldUpdater.compareAndSet(this, szVar, k30Var)) {
                    if (atomicReferenceFieldUpdater.get(this) != szVar) {
                        return o20.i;
                    }
                }
            }
            boolean f2 = k30Var.f();
            if (obj2 instanceof qf) {
                qfVar = (qf) obj2;
            } else {
                qfVar = null;
            }
            if (qfVar != null) {
                k30Var.a(qfVar.a);
            }
            Throwable e2 = k30Var.e();
            if (!f2) {
                th = e2;
            }
            if (th != null) {
                Z(O, th);
            }
            td Y = Y(O);
            if (Y != null && l0(k30Var, Y, obj2)) {
                return o20.h;
            }
            O.e(new la0(2), 2);
            td Y2 = Y(O);
            if (Y2 != null && l0(k30Var, Y2, obj2)) {
                return o20.h;
            }
            return K(k30Var, obj2);
        }
    }

    public final boolean l0(k30 k30Var, td tdVar, Object obj) {
        while (g30.v(tdVar.l, false, new j30(this, k30Var, tdVar, obj)) == sg0.e) {
            tdVar = Y(tdVar);
            if (tdVar == null) {
                return false;
            }
        }
        return true;
    }

    @Override // defpackage.d30
    public final CancellationException m() {
        Object Q = Q();
        CancellationException cancellationException = null;
        if (Q instanceof k30) {
            Throwable e2 = ((k30) Q).e();
            if (e2 != null) {
                String concat = getClass().getSimpleName().concat(" is cancelling");
                if (e2 instanceof CancellationException) {
                    cancellationException = (CancellationException) e2;
                }
                if (cancellationException == null) {
                    return new e30(concat, e2, this);
                }
                return cancellationException;
            }
            v7.e(this, "Job is still new or active: ");
            return null;
        }
        if (!(Q instanceof sz)) {
            if (Q instanceof qf) {
                Throwable th = ((qf) Q).a;
                if (th instanceof CancellationException) {
                    cancellationException = (CancellationException) th;
                }
                if (cancellationException == null) {
                    return new e30(G(), th, this);
                }
                return cancellationException;
            }
            return new e30(getClass().getSimpleName().concat(" has completed normally"), null, this);
        }
        v7.e(this, "Job is still new or active: ");
        return null;
    }

    @Override // defpackage.yj
    public final Object n(kv kvVar, Object obj) {
        return kvVar.d(obj, this);
    }

    @Override // defpackage.d30
    public final un p(gv gvVar) {
        return U(true, new a30(gvVar));
    }

    @Override // defpackage.yj
    public final yj s(xj xjVar) {
        return jc0.x(this, xjVar);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(X() + '{' + h0(Q()) + '}');
        sb.append('@');
        sb.append(dl.v(this));
        return sb.toString();
    }

    @Override // defpackage.d30
    public final Object w(jj jjVar) {
        Object Q;
        x31 x31Var;
        do {
            Q = Q();
            boolean z = Q instanceof sz;
            x31Var = x31.a;
            if (!z) {
                g30.p(jjVar.r());
                return x31Var;
            }
        } while (g0(Q) < 0);
        pc pcVar = new pc(1, t20.w(jjVar));
        pcVar.s();
        pcVar.y(new kc(1, g30.v(this, true, new mq0(pcVar))));
        Object p = pcVar.p();
        ik ikVar = ik.e;
        if (p != ikVar) {
            p = x31Var;
        }
        if (p == ikVar) {
            return p;
        }
        return x31Var;
    }

    @Override // defpackage.d30
    public final un y(boolean z, boolean z2, e eVar) {
        h30 a30Var;
        if (z) {
            a30Var = new z20(eVar);
        } else {
            a30Var = new a30(eVar);
        }
        return U(z2, a30Var);
    }

    public void b0() {
    }

    public void A(Object obj) {
    }

    public void S(rf rfVar) {
        throw rfVar;
    }

    public void a0(Object obj) {
    }
}
