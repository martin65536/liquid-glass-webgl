package defpackage;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class in extends kn implements jk, ij {
    public static final /* synthetic */ AtomicReferenceFieldUpdater l = AtomicReferenceFieldUpdater.newUpdater(in.class, Object.class, "_reusableCancellableContinuation$volatile");
    public static final /* synthetic */ long m = qc.a.objectFieldOffset(in.class.getDeclaredField("_reusableCancellableContinuation$volatile"));
    private volatile /* synthetic */ Object _reusableCancellableContinuation$volatile;
    public final ak h;
    public final jj i;
    public Object j;
    public final Object k;

    public in(ak akVar, jj jjVar) {
        super(-1);
        this.h = akVar;
        this.i = jjVar;
        this.j = n20.b;
        this.k = k81.N(jjVar.r());
    }

    @Override // defpackage.jk
    public final jk f() {
        return this.i;
    }

    @Override // defpackage.kn
    public final Object h() {
        Object obj = this.j;
        this.j = n20.b;
        return obj;
    }

    public final void i() {
        do {
            l.getClass();
        } while (qc.a.getObjectVolatile(this, m) == n20.c);
    }

    public final pc j() {
        in inVar;
        wq wqVar = n20.c;
        while (true) {
            l.getClass();
            Unsafe unsafe = qc.a;
            long j = m;
            Object objectVolatile = unsafe.getObjectVolatile(this, j);
            if (objectVolatile == null) {
                unsafe.putObjectVolatile(this, j, wqVar);
                return null;
            }
            if (objectVolatile instanceof pc) {
                while (true) {
                    Unsafe unsafe2 = qc.a;
                    in inVar2 = this;
                    boolean compareAndSwapObject = unsafe2.compareAndSwapObject(inVar2, m, objectVolatile, wqVar);
                    inVar = inVar2;
                    if (compareAndSwapObject) {
                        return (pc) objectVolatile;
                    }
                    if (unsafe2.getObjectVolatile(inVar, j) != objectVolatile) {
                        break;
                    }
                    this = inVar;
                }
            } else {
                inVar = this;
                if (objectVolatile != wqVar && !(objectVolatile instanceof Throwable)) {
                    v7.e(objectVolatile, "Inconsistent state ");
                    return null;
                }
            }
            this = inVar;
        }
    }

    public final pc k() {
        l.getClass();
        Object objectVolatile = qc.a.getObjectVolatile(this, m);
        if (objectVolatile instanceof pc) {
            return (pc) objectVolatile;
        }
        return null;
    }

    public final boolean l() {
        l.getClass();
        if (qc.a.getObjectVolatile(this, m) != null) {
            return true;
        }
        return false;
    }

    public final boolean m(Throwable th) {
        in inVar;
        Throwable th2;
        Unsafe unsafe;
        while (true) {
            l.getClass();
            Unsafe unsafe2 = qc.a;
            long j = m;
            Object objectVolatile = unsafe2.getObjectVolatile(this, j);
            wq wqVar = n20.c;
            if (o20.e(objectVolatile, wqVar)) {
                while (true) {
                    Unsafe unsafe3 = qc.a;
                    in inVar2 = this;
                    th2 = th;
                    inVar = inVar2;
                    if (!unsafe3.compareAndSwapObject(inVar2, m, wqVar, th2)) {
                        if (unsafe3.getObjectVolatile(inVar, j) != wqVar) {
                            break;
                        }
                        this = inVar;
                        th = th2;
                    } else {
                        return true;
                    }
                }
            } else {
                inVar = this;
                th2 = th;
                if (objectVolatile instanceof Throwable) {
                    return true;
                }
                do {
                    unsafe = qc.a;
                    if (unsafe.compareAndSwapObject(inVar, m, objectVolatile, (Object) null)) {
                        return false;
                    }
                } while (unsafe.getObjectVolatile(inVar, j) == objectVolatile);
            }
            this = inVar;
            th = th2;
        }
    }

    public final Throwable n(pc pcVar) {
        Unsafe unsafe;
        in inVar;
        pc pcVar2;
        while (true) {
            l.getClass();
            Unsafe unsafe2 = qc.a;
            long j = m;
            Object objectVolatile = unsafe2.getObjectVolatile(this, j);
            wq wqVar = n20.c;
            if (objectVolatile != wqVar) {
                in inVar2 = this;
                if (!(objectVolatile instanceof Throwable)) {
                    v7.e(objectVolatile, "Inconsistent state ");
                    return null;
                }
                do {
                    unsafe = qc.a;
                    if (unsafe.compareAndSwapObject(inVar2, m, objectVolatile, (Object) null)) {
                        return (Throwable) objectVolatile;
                    }
                } while (unsafe.getObjectVolatile(inVar2, j) == objectVolatile);
                v7.m("Failed requirement.");
                return null;
            }
            while (true) {
                Unsafe unsafe3 = qc.a;
                inVar = this;
                pcVar2 = pcVar;
                if (unsafe3.compareAndSwapObject(inVar, m, wqVar, pcVar2)) {
                    return null;
                }
                if (unsafe3.getObjectVolatile(inVar, j) != wqVar) {
                    break;
                }
                this = inVar;
                pcVar = pcVar2;
            }
            this = inVar;
            pcVar = pcVar2;
        }
    }

    @Override // defpackage.ij
    public final yj r() {
        return this.i.r();
    }

    public final String toString() {
        return "DispatchedContinuation[" + this.h + ", " + dl.S(this.i) + ']';
    }

    @Override // defpackage.ij
    public final void u(Object obj) {
        Object qfVar;
        Throwable a = kq0.a(obj);
        if (a == null) {
            qfVar = obj;
        } else {
            qfVar = new qf(a, false);
        }
        jj jjVar = this.i;
        yj r = jjVar.r();
        ak akVar = this.h;
        if (akVar.k(r)) {
            this.j = qfVar;
            this.g = 0;
            akVar.g(jjVar.r(), this);
            return;
        }
        nr a2 = w11.a();
        if (a2.g >= 4294967296L) {
            this.j = qfVar;
            this.g = 0;
            a2.r(this);
            return;
        }
        a2.u(true);
        try {
            yj r2 = jjVar.r();
            Object Q = k81.Q(r2, this.k);
            try {
                jjVar.u(obj);
                do {
                } while (a2.x());
            } finally {
                k81.G(r2, Q);
            }
        } catch (Throwable th) {
            try {
                g(th);
            } finally {
                a2.q(true);
            }
        }
    }

    @Override // defpackage.kn
    public final ij c() {
        return this;
    }
}
