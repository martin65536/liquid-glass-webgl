package defpackage;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class db0 {
    private volatile /* synthetic */ Object _next$volatile;
    private volatile /* synthetic */ long _state$volatile;
    public final int a;
    public final boolean b;
    public final int c;
    public final /* synthetic */ AtomicReferenceArray d;
    public static final /* synthetic */ AtomicReferenceFieldUpdater e = AtomicReferenceFieldUpdater.newUpdater(db0.class, Object.class, "_next$volatile");
    public static final /* synthetic */ long h = qc.a.objectFieldOffset(db0.class.getDeclaredField("_next$volatile"));
    public static final /* synthetic */ AtomicLongFieldUpdater f = AtomicLongFieldUpdater.newUpdater(db0.class, "_state$volatile");
    public static final wq g = new wq("REMOVE_FROZEN", 1);

    public db0(int i, boolean z) {
        this.a = i;
        this.b = z;
        int i2 = i - 1;
        this.c = i2;
        this.d = new AtomicReferenceArray(i);
        if (i2 <= 1073741823) {
            if ((i & i2) == 0) {
                return;
            }
            v7.o("Check failed.");
            throw null;
        }
        v7.o("Check failed.");
        throw null;
    }

    public final int a(Object obj) {
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = f;
            long j = atomicLongFieldUpdater.get(this);
            if ((3458764513820540928L & j) != 0) {
                if ((2305843009213693952L & j) != 0) {
                    return 2;
                }
                return 1;
            }
            int i = (int) (1073741823 & j);
            int i2 = (int) ((1152921503533105152L & j) >> 30);
            int i3 = this.c;
            if (((i2 + 2) & i3) != (i & i3)) {
                boolean z = this.b;
                AtomicReferenceArray atomicReferenceArray = this.d;
                if (!z && atomicReferenceArray.get(i2 & i3) != null) {
                    int i4 = this.a;
                    if (i4 < 1024 || ((i2 - i) & 1073741823) > (i4 >> 1)) {
                        return 1;
                    }
                } else {
                    db0 db0Var = this;
                    if (f.compareAndSet(db0Var, j, ((-1152921503533105153L) & j) | (((i2 + 1) & 1073741823) << 30))) {
                        atomicReferenceArray.set(i2 & i3, obj);
                        db0 db0Var2 = db0Var;
                        while ((atomicLongFieldUpdater.get(db0Var2) & 1152921504606846976L) != 0) {
                            db0Var2 = db0Var2.d();
                            AtomicReferenceArray atomicReferenceArray2 = db0Var2.d;
                            int i5 = db0Var2.c & i2;
                            Object obj2 = atomicReferenceArray2.get(i5);
                            if ((obj2 instanceof cb0) && ((cb0) obj2).a == i2) {
                                atomicReferenceArray2.set(i5, obj);
                            } else {
                                db0Var2 = null;
                            }
                            if (db0Var2 == null) {
                                return 0;
                            }
                        }
                        return 0;
                    }
                    this = db0Var;
                }
            } else {
                return 1;
            }
        }
    }

    public final db0 b(long j) {
        db0 db0Var;
        while (true) {
            e.getClass();
            Unsafe unsafe = qc.a;
            long j2 = h;
            db0 db0Var2 = (db0) unsafe.getObjectVolatile(this, j2);
            if (db0Var2 != null) {
                return db0Var2;
            }
            db0 db0Var3 = new db0(this.a * 2, this.b);
            int i = (int) (1073741823 & j);
            int i2 = (int) ((1152921503533105152L & j) >> 30);
            while (true) {
                int i3 = this.c;
                int i4 = i & i3;
                if (i4 == (i3 & i2)) {
                    break;
                }
                Object obj = this.d.get(i4);
                if (obj == null) {
                    obj = new cb0(i);
                }
                db0Var3.d.set(db0Var3.c & i, obj);
                i++;
            }
            f.set(db0Var3, (-1152921504606846977L) & j);
            while (true) {
                Unsafe unsafe2 = qc.a;
                db0Var = this;
                if (!unsafe2.compareAndSwapObject(db0Var, h, (Object) null, db0Var3) && unsafe2.getObjectVolatile(db0Var, j2) == null) {
                    this = db0Var;
                }
            }
            this = db0Var;
        }
    }

    public final boolean c() {
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = f;
            long j = atomicLongFieldUpdater.get(this);
            if ((j & 2305843009213693952L) != 0) {
                return true;
            }
            if ((1152921504606846976L & j) != 0) {
                return false;
            }
            db0 db0Var = this;
            if (atomicLongFieldUpdater.compareAndSet(db0Var, j, 2305843009213693952L | j)) {
                return true;
            }
            this = db0Var;
        }
    }

    public final db0 d() {
        long j;
        db0 db0Var;
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = f;
            j = atomicLongFieldUpdater.get(this);
            if ((j & 1152921504606846976L) != 0) {
                db0Var = this;
                break;
            }
            long j2 = 1152921504606846976L | j;
            db0Var = this;
            if (atomicLongFieldUpdater.compareAndSet(db0Var, j, j2)) {
                j = j2;
                break;
            }
            this = db0Var;
        }
        return db0Var.b(j);
    }

    public final Object e() {
        db0 db0Var = this;
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = f;
            long j = atomicLongFieldUpdater.get(db0Var);
            if ((j & 1152921504606846976L) != 0) {
                return g;
            }
            int i = (int) (j & 1073741823);
            int i2 = db0Var.c;
            int i3 = i & i2;
            if ((((int) ((1152921503533105152L & j) >> 30)) & i2) == i3) {
                break;
            }
            AtomicReferenceArray atomicReferenceArray = db0Var.d;
            Object obj = atomicReferenceArray.get(i3);
            boolean z = db0Var.b;
            if (obj == null) {
                if (z) {
                    break;
                }
            } else {
                if (obj instanceof cb0) {
                    break;
                }
                long j2 = (i + 1) & 1073741823;
                if (f.compareAndSet(db0Var, j, (j & (-1073741824)) | j2)) {
                    atomicReferenceArray.set(i3, null);
                    return obj;
                }
                db0Var = this;
                if (z) {
                    while (true) {
                        long j3 = atomicLongFieldUpdater.get(db0Var);
                        int i4 = (int) (j3 & 1073741823);
                        if ((j3 & 1152921504606846976L) != 0) {
                            db0Var = db0Var.d();
                        } else {
                            db0 db0Var2 = db0Var;
                            if (f.compareAndSet(db0Var2, j3, (j3 & (-1073741824)) | j2)) {
                                db0Var2.d.set(i4 & db0Var2.c, null);
                                db0Var = null;
                            } else {
                                db0Var = db0Var2;
                            }
                        }
                        if (db0Var == null) {
                            return obj;
                        }
                    }
                }
            }
        }
        return null;
    }
}
