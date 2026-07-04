package defpackage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import sun.misc.Unsafe;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k30 implements sz {
    public static final /* synthetic */ AtomicIntegerFieldUpdater f = AtomicIntegerFieldUpdater.newUpdater(k30.class, "_isCompleting$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater g = AtomicReferenceFieldUpdater.newUpdater(k30.class, Object.class, "_rootCause$volatile");
    public static final /* synthetic */ AtomicReferenceFieldUpdater h;
    public static final /* synthetic */ long i;
    public static final /* synthetic */ long j;
    private volatile /* synthetic */ Object _exceptionsHolder$volatile;
    private volatile /* synthetic */ int _isCompleting$volatile = 0;
    private volatile /* synthetic */ Object _rootCause$volatile;
    public final pg0 e;

    static {
        Unsafe unsafe = qc.a;
        j = unsafe.objectFieldOffset(k30.class.getDeclaredField("_rootCause$volatile"));
        h = AtomicReferenceFieldUpdater.newUpdater(k30.class, Object.class, "_exceptionsHolder$volatile");
        i = unsafe.objectFieldOffset(k30.class.getDeclaredField("_exceptionsHolder$volatile"));
    }

    public k30(pg0 pg0Var, Throwable th) {
        this.e = pg0Var;
        this._rootCause$volatile = th;
    }

    public final void a(Throwable th) {
        Throwable e = e();
        if (e == null) {
            i(th);
            return;
        }
        if (th != e) {
            Object c = c();
            if (c == null) {
                h(th);
                return;
            }
            if (c instanceof Throwable) {
                if (th == c) {
                    return;
                }
                ArrayList arrayList = new ArrayList(4);
                arrayList.add(c);
                arrayList.add(th);
                h(arrayList);
                return;
            }
            if (c instanceof ArrayList) {
                ((ArrayList) c).add(th);
            } else {
                v7.e(c, "State is ");
            }
        }
    }

    @Override // defpackage.sz
    public final boolean b() {
        if (e() == null) {
            return true;
        }
        return false;
    }

    public final Object c() {
        h.getClass();
        return qc.a.getObjectVolatile(this, i);
    }

    @Override // defpackage.sz
    public final pg0 d() {
        return this.e;
    }

    public final Throwable e() {
        g.getClass();
        return (Throwable) qc.a.getObjectVolatile(this, j);
    }

    public final boolean f() {
        if (e() != null) {
            return true;
        }
        return false;
    }

    public final ArrayList g(Throwable th) {
        ArrayList arrayList;
        Object c = c();
        if (c == null) {
            arrayList = new ArrayList(4);
        } else if (c instanceof Throwable) {
            ArrayList arrayList2 = new ArrayList(4);
            arrayList2.add(c);
            arrayList = arrayList2;
        } else if (c instanceof ArrayList) {
            arrayList = (ArrayList) c;
        } else {
            v7.e(c, "State is ");
            return null;
        }
        Throwable e = e();
        if (e != null) {
            arrayList.add(0, e);
        }
        if (th != null && !th.equals(e)) {
            arrayList.add(th);
        }
        h(o20.k);
        return arrayList;
    }

    public final void h(Object obj) {
        h.getClass();
        qc.a.putObjectVolatile(this, i, obj);
    }

    public final void i(Throwable th) {
        g.getClass();
        qc.a.putObjectVolatile(this, j, th);
    }

    public final String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("Finishing[cancelling=");
        sb.append(f());
        sb.append(", completing=");
        if (f.get(this) != 0) {
            z = true;
        } else {
            z = false;
        }
        sb.append(z);
        sb.append(", rootCause=");
        sb.append(e());
        sb.append(", exceptions=");
        sb.append(c());
        sb.append(", list=");
        sb.append(this.e);
        sb.append(']');
        return sb.toString();
    }
}
