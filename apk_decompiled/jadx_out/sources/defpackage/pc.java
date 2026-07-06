package defpackage;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class pc extends kn implements nc, jk, y51 {
    public static final /* synthetic */ AtomicIntegerFieldUpdater j = AtomicIntegerFieldUpdater.newUpdater(pc.class, "_decisionAndIndex$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater k = AtomicReferenceFieldUpdater.newUpdater(pc.class, Object.class, "_state$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater l;
    public static final /* synthetic */ long m;
    public static final /* synthetic */ long n;
    private volatile /* synthetic */ int _decisionAndIndex$volatile;
    private volatile /* synthetic */ Object _parentHandle$volatile;
    private volatile /* synthetic */ Object _state$volatile;
    public final ij h;
    public final yj i;

    static {
        Unsafe unsafe = qc.a;
        n = unsafe.objectFieldOffset(pc.class.getDeclaredField("_state$volatile"));
        l = AtomicReferenceFieldUpdater.newUpdater(pc.class, Object.class, "_parentHandle$volatile");
        m = unsafe.objectFieldOffset(pc.class.getDeclaredField("_parentHandle$volatile"));
    }

    public pc(int i, ij ijVar) {
        super(i);
        this.h = ijVar;
        this.i = ijVar.r();
        this._decisionAndIndex$volatile = 536870911;
        this._state$volatile = u1.a;
    }

    public static void B(wg0 wg0Var, Object obj) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + wg0Var + ", already has " + obj).toString());
    }

    public static Object I(wg0 wg0Var, Object obj, int i, lv lvVar) {
        kc kcVar;
        if (obj instanceof qf) {
            return obj;
        }
        if (i != 1 && i != 2) {
            return obj;
        }
        if (lvVar == null && !(wg0Var instanceof kc)) {
            return obj;
        }
        if (wg0Var instanceof kc) {
            kcVar = (kc) wg0Var;
        } else {
            kcVar = null;
        }
        return new of(obj, kcVar, lvVar, (Throwable) null, 16);
    }

    public final boolean A() {
        if (this.g == 2 && ((in) this.h).l()) {
            return true;
        }
        return false;
    }

    public String C() {
        return "CancellableContinuation";
    }

    public final void D() {
        in inVar;
        Throwable n2;
        ij ijVar = this.h;
        if (ijVar instanceof in) {
            inVar = (in) ijVar;
        } else {
            inVar = null;
        }
        if (inVar != null && (n2 = inVar.n(this)) != null) {
            l();
            x(n2);
        }
    }

    public final boolean E() {
        k.getClass();
        Unsafe unsafe = qc.a;
        long j2 = n;
        Object objectVolatile = unsafe.getObjectVolatile(this, j2);
        if ((objectVolatile instanceof of) && ((of) objectVolatile).d != null) {
            l();
            return false;
        }
        j.set(this, 536870911);
        unsafe.putObjectVolatile(this, j2, u1.a);
        return true;
    }

    public final void F(Object obj, lv lvVar) {
        G(obj, this.g, lvVar);
    }

    public final void G(Object obj, int i, lv lvVar) {
        pc pcVar;
        while (true) {
            k.getClass();
            Unsafe unsafe = qc.a;
            long j2 = n;
            Object objectVolatile = unsafe.getObjectVolatile(this, j2);
            if (objectVolatile instanceof wg0) {
                Object I = I((wg0) objectVolatile, obj, i, lvVar);
                while (true) {
                    Unsafe unsafe2 = qc.a;
                    pcVar = this;
                    if (unsafe2.compareAndSwapObject(pcVar, n, objectVolatile, I)) {
                        if (!pcVar.A()) {
                            pcVar.l();
                        }
                        pcVar.m(i);
                        return;
                    } else if (unsafe2.getObjectVolatile(pcVar, j2) != objectVolatile) {
                        break;
                    } else {
                        this = pcVar;
                    }
                }
            } else {
                pc pcVar2 = this;
                if (objectVolatile instanceof sc) {
                    sc scVar = (sc) objectVolatile;
                    if (sc.c.compareAndSet(scVar, 0, 1)) {
                        if (lvVar != null) {
                            pcVar2.j(lvVar, scVar.a, obj);
                            return;
                        }
                        return;
                    }
                }
                v7.e(obj, "Already resumed, but proposed with update ");
                return;
            }
            this = pcVar;
        }
    }

    public final void H(ak akVar, Object obj) {
        in inVar;
        ak akVar2;
        int i;
        ij ijVar = this.h;
        if (ijVar instanceof in) {
            inVar = (in) ijVar;
        } else {
            inVar = null;
        }
        if (inVar != null) {
            akVar2 = inVar.h;
        } else {
            akVar2 = null;
        }
        if (akVar2 == akVar) {
            i = 4;
        } else {
            i = this.g;
        }
        G(obj, i, null);
    }

    public final wq J(Object obj, lv lvVar) {
        pc pcVar;
        wq wqVar = o4.b;
        while (true) {
            k.getClass();
            Unsafe unsafe = qc.a;
            long j2 = n;
            Object objectVolatile = unsafe.getObjectVolatile(this, j2);
            if (objectVolatile instanceof wg0) {
                Object I = I((wg0) objectVolatile, obj, this.g, lvVar);
                while (true) {
                    Unsafe unsafe2 = qc.a;
                    pcVar = this;
                    if (unsafe2.compareAndSwapObject(pcVar, n, objectVolatile, I)) {
                        if (!pcVar.A()) {
                            pcVar.l();
                        }
                        return wqVar;
                    }
                    if (unsafe2.getObjectVolatile(pcVar, j2) != objectVolatile) {
                        break;
                    }
                    this = pcVar;
                }
            } else {
                return null;
            }
            this = pcVar;
        }
    }

    @Override // defpackage.y51
    public final void a(ku0 ku0Var, int i) {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
        int i2;
        do {
            atomicIntegerFieldUpdater = j;
            i2 = atomicIntegerFieldUpdater.get(this);
            if ((i2 & 536870911) != 536870911) {
                v7.o("invokeOnCancellation should be called at most once");
                return;
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, i2, ((i2 >> 29) << 29) + i));
        y(ku0Var);
    }

    @Override // defpackage.kn
    public final void b(CancellationException cancellationException) {
        CancellationException cancellationException2;
        pc pcVar;
        while (true) {
            k.getClass();
            Unsafe unsafe = qc.a;
            long j2 = n;
            Object objectVolatile = unsafe.getObjectVolatile(this, j2);
            if (!(objectVolatile instanceof wg0)) {
                if (!(objectVolatile instanceof qf)) {
                    if (objectVolatile instanceof of) {
                        of ofVar = (of) objectVolatile;
                        if (ofVar.e == null) {
                            of a = of.a(ofVar, null, cancellationException, 15);
                            while (true) {
                                Unsafe unsafe2 = qc.a;
                                pc pcVar2 = this;
                                if (unsafe2.compareAndSwapObject(pcVar2, n, objectVolatile, a)) {
                                    kc kcVar = ofVar.b;
                                    if (kcVar != null) {
                                        pcVar2.i(kcVar, cancellationException);
                                    }
                                    lv lvVar = ofVar.c;
                                    if (lvVar != null) {
                                        pcVar2.j(lvVar, cancellationException, ofVar.a);
                                        return;
                                    }
                                    return;
                                }
                                if (unsafe2.getObjectVolatile(pcVar2, j2) != objectVolatile) {
                                    cancellationException2 = cancellationException;
                                    pcVar = pcVar2;
                                    break;
                                }
                                this = pcVar2;
                            }
                        } else {
                            v7.o("Must be called at most once");
                            return;
                        }
                    } else {
                        pc pcVar3 = this;
                        CancellationException cancellationException3 = cancellationException;
                        of ofVar2 = new of(objectVolatile, (kc) null, (lv) null, cancellationException3, 14);
                        cancellationException2 = cancellationException3;
                        while (true) {
                            of ofVar3 = ofVar2;
                            Unsafe unsafe3 = qc.a;
                            pcVar = pcVar3;
                            boolean compareAndSwapObject = unsafe3.compareAndSwapObject(pcVar, n, objectVolatile, ofVar3);
                            ofVar2 = ofVar3;
                            if (compareAndSwapObject) {
                                return;
                            }
                            if (unsafe3.getObjectVolatile(pcVar, j2) != objectVolatile) {
                                break;
                            } else {
                                pcVar3 = pcVar;
                            }
                        }
                    }
                    cancellationException = cancellationException2;
                    this = pcVar;
                } else {
                    return;
                }
            } else {
                v7.o("Not completed");
                return;
            }
        }
    }

    @Override // defpackage.kn
    public final ij c() {
        return this.h;
    }

    @Override // defpackage.kn
    public final Throwable d(Object obj) {
        Throwable d = super.d(obj);
        if (d != null) {
            return d;
        }
        return null;
    }

    @Override // defpackage.kn
    public final Object e(Object obj) {
        if (obj instanceof of) {
            return ((of) obj).a;
        }
        return obj;
    }

    @Override // defpackage.jk
    public final jk f() {
        ij ijVar = this.h;
        if (ijVar instanceof jk) {
            return (jk) ijVar;
        }
        return null;
    }

    @Override // defpackage.kn
    public final Object h() {
        return q();
    }

    public final void i(kc kcVar, Throwable th) {
        try {
            switch (kcVar.a) {
                case 0:
                    ((gv) kcVar.b).e(th);
                    return;
                default:
                    ((un) kcVar.b).a();
                    return;
            }
        } catch (Throwable th2) {
            o4.K(this.i, new RuntimeException("Exception in invokeOnCancellation handler for " + this, th2));
        }
    }

    public final void j(lv lvVar, Throwable th, Object obj) {
        yj yjVar = this.i;
        try {
            lvVar.c(th, obj, yjVar);
        } catch (Throwable th2) {
            o4.K(yjVar, new RuntimeException("Exception in resume onCancellation handler for " + this, th2));
        }
    }

    public final void k(ku0 ku0Var, Throwable th) {
        yj yjVar = this.i;
        int i = j.get(this) & 536870911;
        if (i != 536870911) {
            try {
                ku0Var.l(i, yjVar);
                return;
            } catch (Throwable th2) {
                o4.K(yjVar, new RuntimeException("Exception in invokeOnCancellation handler for " + this, th2));
                return;
            }
        }
        v7.o("The index for Segment.onCancellation(..) is broken");
    }

    public final void l() {
        un o = o();
        if (o == null) {
            return;
        }
        o.a();
        l.getClass();
        qc.a.putObjectVolatile(this, m, sg0.e);
    }

    public final void m(int i) {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
        int i2;
        boolean z;
        boolean z2;
        do {
            atomicIntegerFieldUpdater = j;
            i2 = atomicIntegerFieldUpdater.get(this);
            int i3 = i2 >> 29;
            if (i3 != 0) {
                if (i3 == 1) {
                    boolean z3 = false;
                    if (i == 4) {
                        z = true;
                    } else {
                        z = false;
                    }
                    ij ijVar = this.h;
                    if (!z && (ijVar instanceof in)) {
                        if (i != 1 && i != 2) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        int i4 = this.g;
                        if (i4 == 1 || i4 == 2) {
                            z3 = true;
                        }
                        if (z2 == z3) {
                            in inVar = (in) ijVar;
                            ak akVar = inVar.h;
                            yj r = inVar.i.r();
                            if (akVar.k(r)) {
                                akVar.g(r, this);
                                return;
                            }
                            nr a = w11.a();
                            if (a.g >= 4294967296L) {
                                a.r(this);
                                return;
                            }
                            a.u(true);
                            try {
                                o20.z(this, ijVar, true);
                                do {
                                } while (a.x());
                            } finally {
                                try {
                                    return;
                                } finally {
                                }
                            }
                            return;
                        }
                    }
                    o20.z(this, ijVar, z);
                    return;
                }
                v7.o("Already resumed");
                return;
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, i2, 1073741824 + (536870911 & i2)));
    }

    public Throwable n(l30 l30Var) {
        return l30Var.m();
    }

    public final un o() {
        l.getClass();
        return (un) qc.a.getObjectVolatile(this, m);
    }

    public final Object p() {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
        int i;
        d30 d30Var;
        boolean A = A();
        do {
            atomicIntegerFieldUpdater = j;
            i = atomicIntegerFieldUpdater.get(this);
            int i2 = i >> 29;
            if (i2 != 0) {
                if (i2 == 2) {
                    if (A) {
                        D();
                    }
                    Object q = q();
                    if (!(q instanceof qf)) {
                        int i3 = this.g;
                        if ((i3 == 1 || i3 == 2) && (d30Var = (d30) this.i.j(x1.L)) != null && !d30Var.b()) {
                            CancellationException m2 = d30Var.m();
                            b(m2);
                            throw m2;
                        }
                        return e(q);
                    }
                    throw ((qf) q).a;
                }
                v7.o("Already suspended");
                return null;
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, i, 536870912 + (536870911 & i)));
        if (o() == null) {
            t();
        }
        if (A) {
            D();
        }
        return ik.e;
    }

    public final Object q() {
        k.getClass();
        return qc.a.getObjectVolatile(this, n);
    }

    @Override // defpackage.ij
    public final yj r() {
        return this.i;
    }

    public final void s() {
        un t = t();
        if (t != null && !(q() instanceof wg0)) {
            t.a();
            l.getClass();
            qc.a.putObjectVolatile(this, m, sg0.e);
        }
    }

    public final un t() {
        d30 d30Var = (d30) this.i.j(x1.L);
        if (d30Var == null) {
            return null;
        }
        un v = g30.v(d30Var, true, new rd(this));
        while (true) {
            l.getClass();
            Unsafe unsafe = qc.a;
            long j2 = m;
            pc pcVar = this;
            if (!unsafe.compareAndSwapObject(pcVar, j2, (Object) null, v) && unsafe.getObjectVolatile(pcVar, j2) == null) {
                this = pcVar;
            }
        }
        return v;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(C());
        sb.append('(');
        sb.append(dl.S(this.h));
        sb.append("){");
        Object q = q();
        if (q instanceof wg0) {
            str = "Active";
        } else if (q instanceof sc) {
            str = "Cancelled";
        } else {
            str = "Completed";
        }
        sb.append(str);
        sb.append("}@");
        sb.append(dl.v(this));
        return sb.toString();
    }

    @Override // defpackage.ij
    public final void u(Object obj) {
        Throwable a = kq0.a(obj);
        if (a != null) {
            obj = new qf(a, false);
        }
        G(obj, this.g, null);
    }

    @Override // defpackage.nc
    public final wq v(Object obj, lv lvVar) {
        return J(obj, lvVar);
    }

    public final void w(gv gvVar) {
        y(new kc(0, gvVar));
    }

    @Override // defpackage.nc
    public final boolean x(Throwable th) {
        pc pcVar;
        while (true) {
            k.getClass();
            Unsafe unsafe = qc.a;
            long j2 = n;
            Object objectVolatile = unsafe.getObjectVolatile(this, j2);
            boolean z = false;
            if (!(objectVolatile instanceof wg0)) {
                return false;
            }
            if ((objectVolatile instanceof kc) || (objectVolatile instanceof ku0)) {
                z = true;
            }
            sc scVar = new sc(this, th, z);
            while (true) {
                Unsafe unsafe2 = qc.a;
                pcVar = this;
                if (unsafe2.compareAndSwapObject(pcVar, n, objectVolatile, scVar)) {
                    wg0 wg0Var = (wg0) objectVolatile;
                    if (wg0Var instanceof kc) {
                        pcVar.i((kc) objectVolatile, th);
                    } else if (wg0Var instanceof ku0) {
                        pcVar.k((ku0) objectVolatile, th);
                    }
                    if (!pcVar.A()) {
                        pcVar.l();
                    }
                    pcVar.m(pcVar.g);
                    return true;
                }
                if (unsafe2.getObjectVolatile(pcVar, j2) != objectVolatile) {
                    break;
                }
                this = pcVar;
            }
            this = pcVar;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x00c5, code lost:
    
        B(r11, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00c8, code lost:
    
        throw null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void y(defpackage.wg0 r11) {
        /*
            Method dump skipped, instructions count: 201
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.pc.y(wg0):void");
    }

    @Override // defpackage.nc
    public final void z(Object obj) {
        m(this.g);
    }
}
