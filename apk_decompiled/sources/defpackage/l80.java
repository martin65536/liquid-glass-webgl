package defpackage;

import android.os.Looper;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l80 {
    public final boolean a;
    public xr b;
    public a80 c;
    public final WeakReference d;
    public int e;
    public boolean f;
    public boolean g;
    public final ArrayList h;
    public final ky0 i;

    public l80(j80 j80Var, boolean z) {
        new AtomicReference(null);
        this.a = z;
        this.b = new xr();
        a80 a80Var = a80.f;
        this.c = a80Var;
        this.h = new ArrayList();
        this.d = new WeakReference(j80Var);
        this.i = o20.c(a80Var);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [k80, java.lang.Object] */
    public final void a(i80 i80Var) {
        h80 amVar;
        k80 k80Var;
        j80 j80Var;
        z70 z70Var;
        i80Var.getClass();
        c("addObserver");
        a80 a80Var = this.c;
        a80 a80Var2 = a80.e;
        if (a80Var != a80Var2) {
            a80Var2 = a80.f;
        }
        ?? obj = new Object();
        HashMap hashMap = q80.a;
        boolean z = i80Var instanceof h80;
        boolean z2 = i80Var instanceof yl;
        int i = 2;
        boolean z3 = false;
        if (z && z2) {
            amVar = new am((yl) i80Var, (h80) i80Var);
        } else if (z2) {
            amVar = new am((yl) i80Var, (h80) null);
        } else if (z) {
            amVar = (h80) i80Var;
        } else {
            Class<?> cls = i80Var.getClass();
            if (q80.c(cls) == 2) {
                Object obj2 = q80.b.get(cls);
                obj2.getClass();
                List list = (List) obj2;
                if (list.size() != 1) {
                    int size = list.size();
                    hw[] hwVarArr = new hw[size];
                    if (size <= 0) {
                        amVar = new vo0(i, hwVarArr);
                    } else {
                        q80.a((Constructor) list.get(0), i80Var);
                        throw null;
                    }
                } else {
                    q80.a((Constructor) list.get(0), i80Var);
                    throw null;
                }
            } else {
                amVar = new am(i80Var);
            }
        }
        obj.b = amVar;
        obj.a = a80Var2;
        xr xrVar = this.b;
        yr0 yr0Var = (yr0) xrVar.i.get(i80Var);
        if (yr0Var != null) {
            k80Var = yr0Var.f;
        } else {
            HashMap hashMap2 = xrVar.i;
            yr0 yr0Var2 = new yr0(i80Var, obj);
            xrVar.h++;
            yr0 yr0Var3 = xrVar.f;
            if (yr0Var3 == null) {
                xrVar.e = yr0Var2;
                xrVar.f = yr0Var2;
            } else {
                yr0Var3.g = yr0Var2;
                yr0Var2.h = yr0Var3;
                xrVar.f = yr0Var2;
            }
            hashMap2.put(i80Var, yr0Var2);
            k80Var = null;
        }
        if (k80Var != null || (j80Var = (j80) this.d.get()) == null) {
            return;
        }
        if (this.e != 0 || this.f) {
            z3 = true;
        }
        a80 b = b(i80Var);
        this.e++;
        while (obj.a.compareTo(b) < 0 && this.b.i.containsKey(i80Var)) {
            a80 a80Var3 = obj.a;
            ArrayList arrayList = this.h;
            arrayList.add(a80Var3);
            x70 x70Var = z70.Companion;
            a80 a80Var4 = obj.a;
            x70Var.getClass();
            a80Var4.getClass();
            int ordinal = a80Var4.ordinal();
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal != 3) {
                        z70Var = null;
                    } else {
                        z70Var = z70.ON_RESUME;
                    }
                } else {
                    z70Var = z70.ON_START;
                }
            } else {
                z70Var = z70.ON_CREATE;
            }
            if (z70Var != null) {
                obj.a(j80Var, z70Var);
                arrayList.remove(arrayList.size() - 1);
                b = b(i80Var);
            } else {
                v7.l(obj.a, "no event up from ");
                return;
            }
        }
        if (!z3) {
            g();
        }
        this.e--;
    }

    public final a80 b(i80 i80Var) {
        yr0 yr0Var;
        a80 a80Var;
        HashMap hashMap = this.b.i;
        a80 a80Var2 = null;
        if (hashMap.containsKey(i80Var)) {
            yr0Var = ((yr0) hashMap.get(i80Var)).h;
        } else {
            yr0Var = null;
        }
        if (yr0Var != null) {
            a80Var = yr0Var.f.a;
        } else {
            a80Var = null;
        }
        ArrayList arrayList = this.h;
        if (!arrayList.isEmpty()) {
            a80Var2 = (a80) arrayList.get(arrayList.size() - 1);
        }
        a80 a80Var3 = this.c;
        a80Var3.getClass();
        if (a80Var == null || a80Var.compareTo(a80Var3) >= 0) {
            a80Var = a80Var3;
        }
        if (a80Var2 != null && a80Var2.compareTo(a80Var) < 0) {
            return a80Var2;
        }
        return a80Var;
    }

    public final void c(String str) {
        u7 u7Var;
        if (this.a) {
            if (u7.b != null) {
                u7Var = u7.b;
            } else {
                synchronized (u7.class) {
                    try {
                        if (u7.b == null) {
                            u7.b = new u7(0);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                u7Var = u7.b;
            }
            ((u7) u7Var.a).getClass();
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                return;
            }
            throw new IllegalStateException(("Method " + str + " must be called on the main thread").toString());
        }
    }

    public final void d(z70 z70Var) {
        z70Var.getClass();
        c("handleLifecycleEvent");
        e(z70Var.a());
    }

    public final void e(a80 a80Var) {
        if (this.c != a80Var) {
            j80 j80Var = (j80) this.d.get();
            a80 a80Var2 = this.c;
            a80Var2.getClass();
            a80 a80Var3 = a80.f;
            a80 a80Var4 = a80.e;
            if (a80Var2 == a80Var3 && a80Var == a80Var4) {
                throw new IllegalStateException(("State must be at least '" + a80.g + "' to be moved to '" + a80Var + "' in component " + j80Var).toString());
            }
            if (a80Var2 == a80Var4 && a80Var2 != a80Var) {
                throw new IllegalStateException(("State is '" + a80Var4 + "' and cannot be moved to `" + a80Var + "` in component " + j80Var).toString());
            }
            this.c = a80Var;
            if (!this.f && this.e == 0) {
                this.f = true;
                g();
                this.f = false;
                if (this.c == a80Var4) {
                    this.b = new xr();
                    return;
                }
                return;
            }
            this.g = true;
        }
    }

    public final void f(i80 i80Var) {
        i80Var.getClass();
        c("removeObserver");
        xr xrVar = this.b;
        WeakHashMap weakHashMap = xrVar.g;
        HashMap hashMap = xrVar.i;
        yr0 yr0Var = (yr0) hashMap.get(i80Var);
        if (yr0Var != null) {
            xrVar.h--;
            if (!weakHashMap.isEmpty()) {
                Iterator it = weakHashMap.keySet().iterator();
                while (it.hasNext()) {
                    ((as0) it.next()).a(yr0Var);
                }
            }
            yr0 yr0Var2 = yr0Var.h;
            yr0 yr0Var3 = yr0Var.g;
            if (yr0Var2 != null) {
                yr0Var2.g = yr0Var3;
            } else {
                xrVar.e = yr0Var3;
            }
            yr0 yr0Var4 = yr0Var.g;
            if (yr0Var4 != null) {
                yr0Var4.h = yr0Var2;
            } else {
                xrVar.f = yr0Var2;
            }
            yr0Var.g = null;
            yr0Var.h = null;
        }
        hashMap.remove(i80Var);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002c, code lost:
    
        r11.g = false;
        r11.i.i(r11.c);
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0035, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void g() {
        /*
            Method dump skipped, instructions count: 368
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l80.g():void");
    }
}
