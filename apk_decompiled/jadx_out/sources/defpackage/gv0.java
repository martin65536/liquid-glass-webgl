package defpackage;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class gv0 {
    public static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(gv0.class, Object.class, "head$volatile");
    public static final /* synthetic */ AtomicLongFieldUpdater c;
    public static final /* synthetic */ AtomicReferenceFieldUpdater d;
    public static final /* synthetic */ AtomicLongFieldUpdater e;
    public static final /* synthetic */ AtomicIntegerFieldUpdater f;
    public static final /* synthetic */ long g;
    public static final /* synthetic */ long h;
    private volatile /* synthetic */ int _availablePermits$volatile;
    public final oc a;
    private volatile /* synthetic */ long deqIdx$volatile;
    private volatile /* synthetic */ long enqIdx$volatile;
    private volatile /* synthetic */ Object head$volatile;
    private volatile /* synthetic */ Object tail$volatile;

    static {
        Unsafe unsafe = qc.a;
        g = unsafe.objectFieldOffset(gv0.class.getDeclaredField("head$volatile"));
        c = AtomicLongFieldUpdater.newUpdater(gv0.class, "deqIdx$volatile");
        d = AtomicReferenceFieldUpdater.newUpdater(gv0.class, Object.class, "tail$volatile");
        h = unsafe.objectFieldOffset(gv0.class.getDeclaredField("tail$volatile"));
        e = AtomicLongFieldUpdater.newUpdater(gv0.class, "enqIdx$volatile");
        f = AtomicIntegerFieldUpdater.newUpdater(gv0.class, "_availablePermits$volatile");
    }

    public gv0() {
        iv0 iv0Var = new iv0(0L, null, 2);
        this.head$volatile = iv0Var;
        this.tail$volatile = iv0Var;
        this._availablePermits$volatile = 1;
        this.a = new oc(2, this);
    }

    public final boolean a(pf0 pf0Var) {
        Object s;
        Unsafe unsafe;
        gv0 gv0Var = this;
        d.getClass();
        Unsafe unsafe2 = qc.a;
        long j = h;
        iv0 iv0Var = (iv0) unsafe2.getObjectVolatile(gv0Var, j);
        long andIncrement = e.getAndIncrement(gv0Var);
        ev0 ev0Var = ev0.l;
        long j2 = andIncrement / hv0.f;
        loop0: while (true) {
            s = k81.s(iv0Var, j2, ev0Var);
            if (!n30.v(s)) {
                ku0 u = n30.u(s);
                while (true) {
                    ku0 ku0Var = (ku0) qc.a.getObjectVolatile(gv0Var, j);
                    if (ku0Var.e >= u.e) {
                        break loop0;
                    }
                    if (!u.n()) {
                        break;
                    }
                    do {
                        unsafe = qc.a;
                        gv0Var = this;
                        if (unsafe.compareAndSwapObject(gv0Var, h, ku0Var, u)) {
                            if (ku0Var.j()) {
                                ku0Var.h();
                            }
                        }
                    } while (unsafe.getObjectVolatile(gv0Var, j) == ku0Var);
                    if (u.j()) {
                        u.h();
                    }
                }
            } else {
                break;
            }
            gv0Var = this;
        }
        iv0 iv0Var2 = (iv0) n30.u(s);
        AtomicReferenceArray atomicReferenceArray = iv0Var2.g;
        int i = (int) (andIncrement % hv0.f);
        while (!atomicReferenceArray.compareAndSet(i, null, pf0Var)) {
            if (atomicReferenceArray.get(i) != null) {
                wq wqVar = hv0.b;
                wq wqVar2 = hv0.c;
                do {
                    int i2 = 0;
                    if (atomicReferenceArray.compareAndSet(i, wqVar, wqVar2)) {
                        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = qf0.i;
                        qf0 qf0Var = pf0Var.f;
                        atomicReferenceFieldUpdater.set(qf0Var, null);
                        pc pcVar = pf0Var.e;
                        pcVar.G(x31.a, pcVar.g, new oc(i2, new l(9, qf0Var, pf0Var)));
                        return true;
                    }
                } while (atomicReferenceArray.get(i) == wqVar);
                return false;
            }
        }
        pf0Var.a(iv0Var2, i);
        return true;
    }

    public final void b() {
        int i;
        do {
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = f;
            int andIncrement = atomicIntegerFieldUpdater.getAndIncrement(this);
            if (andIncrement < 1) {
                if (andIncrement >= 0) {
                    return;
                }
            } else {
                do {
                    i = atomicIntegerFieldUpdater.get(this);
                    if (i <= 1) {
                        break;
                    }
                } while (!atomicIntegerFieldUpdater.compareAndSet(this, i, 1));
                throw new IllegalStateException("The number of released permits cannot be greater than 1".toString());
            }
        } while (!c());
    }

    public final boolean c() {
        Object s;
        Unsafe unsafe;
        b.getClass();
        Unsafe unsafe2 = qc.a;
        long j = g;
        iv0 iv0Var = (iv0) unsafe2.getObjectVolatile(this, j);
        long andIncrement = c.getAndIncrement(this);
        long j2 = andIncrement / hv0.f;
        fv0 fv0Var = fv0.l;
        loop0: while (true) {
            s = k81.s(iv0Var, j2, fv0Var);
            if (n30.v(s)) {
                break;
            }
            ku0 u = n30.u(s);
            while (true) {
                ku0 ku0Var = (ku0) qc.a.getObjectVolatile(this, j);
                if (ku0Var.e >= u.e) {
                    break loop0;
                }
                if (!u.n()) {
                    break;
                }
                do {
                    unsafe = qc.a;
                    if (unsafe.compareAndSwapObject(this, g, ku0Var, u)) {
                        if (ku0Var.j()) {
                            ku0Var.h();
                        }
                    }
                } while (unsafe.getObjectVolatile(this, j) == ku0Var);
                if (u.j()) {
                    u.h();
                }
            }
        }
        iv0 iv0Var2 = (iv0) n30.u(s);
        AtomicReferenceArray atomicReferenceArray = iv0Var2.g;
        iv0Var2.a();
        boolean z = false;
        if (iv0Var2.e <= j2) {
            int i = (int) (andIncrement % hv0.f);
            Object andSet = atomicReferenceArray.getAndSet(i, hv0.b);
            if (andSet == null) {
                int i2 = hv0.a;
                for (int i3 = 0; i3 < i2; i3++) {
                    if (atomicReferenceArray.get(i) == hv0.c) {
                        return true;
                    }
                }
                wq wqVar = hv0.b;
                wq wqVar2 = hv0.d;
                while (true) {
                    if (atomicReferenceArray.compareAndSet(i, wqVar, wqVar2)) {
                        z = true;
                        break;
                    }
                    if (atomicReferenceArray.get(i) != wqVar) {
                        break;
                    }
                }
                return !z;
            }
            if (andSet != hv0.e) {
                if (andSet instanceof nc) {
                    nc ncVar = (nc) andSet;
                    wq v = ncVar.v(x31.a, this.a);
                    if (v != null) {
                        ncVar.z(v);
                        return true;
                    }
                } else {
                    v7.e(andSet, "unexpected: ");
                    return false;
                }
            }
        }
        return false;
    }
}
