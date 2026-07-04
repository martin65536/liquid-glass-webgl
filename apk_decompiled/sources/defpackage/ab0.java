package defpackage;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class ab0 {
    public static final /* synthetic */ AtomicReferenceFieldUpdater e = AtomicReferenceFieldUpdater.newUpdater(ab0.class, Object.class, "_next$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater f;
    public static final /* synthetic */ AtomicReferenceFieldUpdater g;
    public static final /* synthetic */ long h;
    public static final /* synthetic */ long i;
    public static final /* synthetic */ long j;
    private volatile /* synthetic */ Object _next$volatile = this;
    private volatile /* synthetic */ Object _prev$volatile = this;
    private volatile /* synthetic */ Object _removedRef$volatile;

    static {
        Unsafe unsafe = qc.a;
        h = unsafe.objectFieldOffset(ab0.class.getDeclaredField("_next$volatile"));
        f = AtomicReferenceFieldUpdater.newUpdater(ab0.class, Object.class, "_prev$volatile");
        i = unsafe.objectFieldOffset(ab0.class.getDeclaredField("_prev$volatile"));
        g = AtomicReferenceFieldUpdater.newUpdater(ab0.class, Object.class, "_removedRef$volatile");
        j = unsafe.objectFieldOffset(ab0.class.getDeclaredField("_removedRef$volatile"));
    }

    public static ab0 i(ab0 ab0Var) {
        while (ab0Var.n()) {
            f.getClass();
            ab0Var = (ab0) qc.a.getObjectVolatile(ab0Var, i);
        }
        return ab0Var;
    }

    public final boolean e(ab0 ab0Var, int i2) {
        ab0 m;
        do {
            m = m();
            if (m instanceof la0) {
                if ((((la0) m).k & i2) != 0 || !m.e(ab0Var, i2)) {
                    return false;
                }
                return true;
            }
        } while (!m.f(ab0Var, this));
        return true;
    }

    public final boolean f(ab0 ab0Var, ab0 ab0Var2) {
        f.getClass();
        Unsafe unsafe = qc.a;
        unsafe.putObjectVolatile(ab0Var, i, this);
        e.getClass();
        long j2 = h;
        unsafe.putObjectVolatile(ab0Var, j2, ab0Var2);
        while (true) {
            Unsafe unsafe2 = qc.a;
            ab0 ab0Var3 = this;
            ab0 ab0Var4 = ab0Var;
            ab0 ab0Var5 = ab0Var2;
            if (unsafe2.compareAndSwapObject(ab0Var3, h, ab0Var5, ab0Var4)) {
                ab0Var4.j(ab0Var5);
                return true;
            }
            if (unsafe2.getObjectVolatile(ab0Var3, j2) != ab0Var5) {
                return false;
            }
            this = ab0Var3;
            ab0Var2 = ab0Var5;
            ab0Var = ab0Var4;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0034, code lost:
    
        r9 = r4;
        r10 = r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void g(defpackage.pg0 r10) {
        /*
            r9 = this;
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = defpackage.ab0.f
            r0.getClass()
            sun.misc.Unsafe r0 = defpackage.qc.a
            long r1 = defpackage.ab0.i
            r0.putObjectVolatile(r10, r1, r9)
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = defpackage.ab0.e
            r1.getClass()
            long r1 = defpackage.ab0.h
            r0.putObjectVolatile(r10, r1, r9)
        L16:
            java.lang.Object r0 = r9.k()
            if (r0 == r9) goto L1d
            return
        L1d:
            sun.misc.Unsafe r3 = defpackage.qc.a
            long r5 = defpackage.ab0.h
            r7 = r9
            r4 = r9
            r8 = r10
            boolean r9 = r3.compareAndSwapObject(r4, r5, r7, r8)
            if (r9 == 0) goto L2e
            r8.j(r4)
            return
        L2e:
            java.lang.Object r9 = r3.getObjectVolatile(r4, r1)
            if (r9 == r4) goto L37
            r9 = r4
            r10 = r8
            goto L16
        L37:
            r9 = r4
            r10 = r8
            goto L1d
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ab0.g(pg0):void");
    }

    public final ab0 h() {
        ab0 ab0Var;
        ab0 ab0Var2;
        Unsafe unsafe;
        loop0: while (true) {
            f.getClass();
            Unsafe unsafe2 = qc.a;
            long j2 = i;
            ab0 ab0Var3 = (ab0) unsafe2.getObjectVolatile(this, j2);
            ab0 ab0Var4 = null;
            ab0Var = ab0Var3;
            while (true) {
                e.getClass();
                if (ab0Var != null) {
                    Unsafe unsafe3 = qc.a;
                    long j3 = h;
                    Object objectVolatile = unsafe3.getObjectVolatile(ab0Var, j3);
                    if (objectVolatile == this) {
                        if (ab0Var3 == ab0Var) {
                            break;
                        }
                        while (true) {
                            Unsafe unsafe4 = qc.a;
                            ab0 ab0Var5 = this;
                            boolean compareAndSwapObject = unsafe4.compareAndSwapObject(ab0Var5, i, ab0Var3, ab0Var);
                            ab0 ab0Var6 = ab0Var3;
                            ab0Var2 = ab0Var5;
                            if (compareAndSwapObject) {
                                break loop0;
                            }
                            if (unsafe4.getObjectVolatile(ab0Var2, j2) != ab0Var6) {
                                break;
                            }
                            this = ab0Var2;
                            ab0Var3 = ab0Var6;
                        }
                    } else {
                        ab0 ab0Var7 = ab0Var3;
                        ab0Var2 = this;
                        if (ab0Var2.n()) {
                            return null;
                        }
                        if (objectVolatile instanceof rp0) {
                            if (ab0Var4 != null) {
                                ab0 ab0Var8 = ((rp0) objectVolatile).a;
                                do {
                                    ab0 ab0Var9 = ab0Var;
                                    unsafe = qc.a;
                                    boolean compareAndSwapObject2 = unsafe.compareAndSwapObject(ab0Var4, h, ab0Var9, ab0Var8);
                                    ab0Var = ab0Var9;
                                    if (compareAndSwapObject2) {
                                        this = ab0Var2;
                                        ab0Var = ab0Var4;
                                        ab0Var3 = ab0Var7;
                                        ab0Var4 = null;
                                    }
                                } while (unsafe.getObjectVolatile(ab0Var4, j3) == ab0Var);
                            } else if (ab0Var != null) {
                                ab0Var = (ab0) unsafe3.getObjectVolatile(ab0Var, j2);
                            } else {
                                v7.d();
                                return null;
                            }
                        } else {
                            objectVolatile.getClass();
                            ab0Var4 = ab0Var;
                            ab0Var = (ab0) objectVolatile;
                        }
                        this = ab0Var2;
                        ab0Var3 = ab0Var7;
                    }
                } else {
                    v7.d();
                    return null;
                }
            }
            this = ab0Var2;
        }
        return ab0Var;
    }

    public final void j(ab0 ab0Var) {
        ab0 ab0Var2;
        while (true) {
            f.getClass();
            if (ab0Var != null) {
                Unsafe unsafe = qc.a;
                long j2 = i;
                ab0 ab0Var3 = (ab0) unsafe.getObjectVolatile(ab0Var, j2);
                if (this.k() == ab0Var) {
                    while (ab0Var != null) {
                        Unsafe unsafe2 = qc.a;
                        ab0Var2 = this;
                        ab0 ab0Var4 = ab0Var;
                        if (unsafe2.compareAndSwapObject(ab0Var4, i, ab0Var3, ab0Var2)) {
                            if (ab0Var2.n()) {
                                ab0Var4.h();
                                return;
                            }
                            return;
                        } else if (ab0Var4 != null) {
                            ab0Var = ab0Var4;
                            if (unsafe2.getObjectVolatile(ab0Var4, j2) != ab0Var3) {
                                break;
                            } else {
                                this = ab0Var2;
                            }
                        } else {
                            v7.d();
                            return;
                        }
                    }
                    v7.d();
                    return;
                }
                return;
            }
            v7.d();
            return;
            this = ab0Var2;
        }
    }

    public final Object k() {
        e.getClass();
        return qc.a.getObjectVolatile(this, h);
    }

    public final ab0 l() {
        rp0 rp0Var;
        Object k = k();
        if (k instanceof rp0) {
            rp0Var = (rp0) k;
        } else {
            rp0Var = null;
        }
        if (rp0Var != null) {
            return rp0Var.a;
        }
        k.getClass();
        return (ab0) k;
    }

    public final ab0 m() {
        ab0 h2 = h();
        if (h2 == null) {
            f.getClass();
            return i((ab0) qc.a.getObjectVolatile(this, i));
        }
        return h2;
    }

    public boolean n() {
        return k() instanceof rp0;
    }

    public final ab0 o() {
        ab0 ab0Var;
        while (true) {
            Object k = this.k();
            if (k instanceof rp0) {
                return ((rp0) k).a;
            }
            if (k == this) {
                return (ab0) k;
            }
            k.getClass();
            ab0 ab0Var2 = (ab0) k;
            rp0 p = ab0Var2.p();
            while (true) {
                e.getClass();
                Unsafe unsafe = qc.a;
                long j2 = h;
                ab0Var = this;
                if (unsafe.compareAndSwapObject(ab0Var, j2, k, p)) {
                    ab0Var2.h();
                    return null;
                }
                if (unsafe.getObjectVolatile(ab0Var, j2) != k) {
                    break;
                }
                this = ab0Var;
            }
            this = ab0Var;
        }
    }

    public final rp0 p() {
        g.getClass();
        Unsafe unsafe = qc.a;
        long j2 = j;
        rp0 rp0Var = (rp0) unsafe.getObjectVolatile(this, j2);
        if (rp0Var == null) {
            rp0 rp0Var2 = new rp0(this);
            unsafe.putObjectVolatile(this, j2, rp0Var2);
            return rp0Var2;
        }
        return rp0Var;
    }

    public String toString() {
        return new d70(1, 1, dl.class, this, "classSimpleName", "getClassSimpleName(Ljava/lang/Object;)Ljava/lang/String;") + '@' + dl.v(this);
    }
}
