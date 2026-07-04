package defpackage;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ki {
    public static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(ki.class, Object.class, "_next$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater b;
    public static final /* synthetic */ long c;
    public static final /* synthetic */ long d;
    private volatile /* synthetic */ Object _next$volatile;
    private volatile /* synthetic */ Object _prev$volatile;

    static {
        Unsafe unsafe = qc.a;
        c = unsafe.objectFieldOffset(ki.class.getDeclaredField("_next$volatile"));
        b = AtomicReferenceFieldUpdater.newUpdater(ki.class, Object.class, "_prev$volatile");
        d = unsafe.objectFieldOffset(ki.class.getDeclaredField("_prev$volatile"));
    }

    public ki(ku0 ku0Var) {
        this._prev$volatile = ku0Var;
    }

    public final void a() {
        b.getClass();
        qc.a.putObjectVolatile(this, d, (Object) null);
    }

    public final ki b() {
        ki e = e();
        while (e != null && e.f()) {
            b.getClass();
            e = (ki) qc.a.getObjectVolatile(e, d);
        }
        return e;
    }

    public final ki c() {
        Object d2 = d();
        if (d2 == k81.a) {
            return null;
        }
        return (ki) d2;
    }

    public final Object d() {
        a.getClass();
        return qc.a.getObjectVolatile(this, c);
    }

    public final ki e() {
        b.getClass();
        return (ki) qc.a.getObjectVolatile(this, d);
    }

    public abstract boolean f();

    public final boolean g() {
        wq wqVar = k81.a;
        while (true) {
            a.getClass();
            Unsafe unsafe = qc.a;
            long j = c;
            ki kiVar = this;
            if (unsafe.compareAndSwapObject(kiVar, j, (Object) null, wqVar)) {
                return true;
            }
            if (unsafe.getObjectVolatile(kiVar, j) != null) {
                return false;
            }
            this = kiVar;
        }
    }

    public final void h() {
        ki kiVar;
        ki kiVar2;
        Unsafe unsafe;
        if (c() == null) {
            return;
        }
        while (true) {
            ki b2 = b();
            ki c2 = c();
            c2.getClass();
            do {
                kiVar = c2;
                if (!kiVar.f()) {
                    break;
                } else {
                    c2 = kiVar.c();
                }
            } while (c2 != null);
            while (true) {
                b.getClass();
                Unsafe unsafe2 = qc.a;
                long j = d;
                Object objectVolatile = unsafe2.getObjectVolatile(kiVar, j);
                if (((ki) objectVolatile) == null) {
                    kiVar2 = null;
                } else {
                    kiVar2 = b2;
                }
                do {
                    unsafe = qc.a;
                    if (unsafe.compareAndSwapObject(kiVar, d, objectVolatile, kiVar2)) {
                        break;
                    }
                } while (unsafe.getObjectVolatile(kiVar, j) == objectVolatile);
            }
            if (b2 != null) {
                a.getClass();
                unsafe.putObjectVolatile(b2, c, kiVar);
            }
            if (!kiVar.f() || kiVar.c() == null) {
                if (b2 == null || !b2.f()) {
                    return;
                }
            }
        }
    }

    public final boolean i(ku0 ku0Var) {
        while (true) {
            a.getClass();
            Unsafe unsafe = qc.a;
            long j = c;
            ki kiVar = this;
            ku0 ku0Var2 = ku0Var;
            if (unsafe.compareAndSwapObject(kiVar, j, (Object) null, ku0Var2)) {
                return true;
            }
            if (unsafe.getObjectVolatile(kiVar, j) != null) {
                return false;
            }
            this = kiVar;
            ku0Var = ku0Var2;
        }
    }
}
