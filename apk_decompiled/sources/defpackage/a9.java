package defpackage;

import android.text.Layout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a9 {
    public final Object a;
    public Serializable b;
    public final Serializable c;
    public Object d;
    public Object e;

    public a9(Layout layout) {
        this.a = layout;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        do {
            int A = uy0.A(((Layout) this.a).getText(), '\n', i, 4);
            if (A < 0) {
                i = ((Layout) this.a).getText().length();
            } else {
                i = A + 1;
            }
            arrayList.add(Integer.valueOf(i));
        } while (i < ((Layout) this.a).getText().length());
        this.b = arrayList;
        int size = arrayList.size();
        ArrayList arrayList2 = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            arrayList2.add(null);
        }
        this.c = arrayList2;
        this.d = new boolean[((ArrayList) this.b).size()];
        ((ArrayList) this.b).size();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [cp0, java.lang.Object] */
    public rc a(z8 z8Var, vu vuVar) {
        int i;
        int i2;
        ?? obj = new Object();
        obj.e = -1;
        synchronized (this.a) {
            Throwable th = (Throwable) this.b;
            if (th != null) {
                z8Var.b(th);
                return x1.x;
            }
            o8 o8Var = (o8) this.c;
            do {
                i = o8Var.get();
                i2 = i + 1;
            } while (!o8Var.compareAndSet(i, i2));
            boolean z = true;
            int i3 = 0;
            if ((134217727 & i2) != 1) {
                z = false;
            }
            obj.e = (i2 >>> 27) & 15;
            ((pe0) this.d).a(z8Var);
            if (z && vuVar != null) {
                try {
                    vuVar.a();
                } catch (Throwable th2) {
                    b(th2);
                }
            }
            return new c4(new y8(z8Var, this, (Object) obj, i3));
        }
    }

    public void b(Throwable th) {
        int i;
        synchronized (this.a) {
            try {
                if (((Throwable) this.b) != null) {
                    return;
                }
                this.b = th;
                pe0 pe0Var = (pe0) this.d;
                Object[] objArr = pe0Var.a;
                int i2 = pe0Var.b;
                for (int i3 = 0; i3 < i2; i3++) {
                    ((z8) objArr[i3]).b(th);
                }
                ((pe0) this.d).d();
                o8 o8Var = (o8) this.c;
                do {
                    i = o8Var.get();
                } while (!o8Var.compareAndSet(i, ((((i >>> 27) & 15) + 1) & 15) << 27));
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public void c(gv gvVar) {
        int i;
        synchronized (this.a) {
            try {
                pe0 pe0Var = (pe0) this.d;
                this.d = (pe0) this.e;
                this.e = pe0Var;
                o8 o8Var = (o8) this.c;
                do {
                    i = o8Var.get();
                } while (!o8Var.compareAndSet(i, ((((i >>> 27) & 15) + 1) & 15) << 27));
                int i2 = pe0Var.b;
                for (int i3 = 0; i3 < i2; i3++) {
                    gvVar.e(pe0Var.f(i3));
                }
                pe0Var.d();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public float d(int i, boolean z) {
        Layout layout = (Layout) this.a;
        int lineEnd = layout.getLineEnd(layout.getLineForOffset(i));
        if (i > lineEnd) {
            i = lineEnd;
        }
        if (z) {
            return layout.getPrimaryHorizontal(i);
        }
        return layout.getSecondaryHorizontal(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:169:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public float e(int r29, boolean r30, boolean r31) {
        /*
            Method dump skipped, instructions count: 750
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.a9.e(int, boolean, boolean):float");
    }

    public int f(int i, int i2) {
        while (i > i2) {
            char charAt = ((Layout) this.a).getText().charAt(i - 1);
            if (charAt != ' ' && charAt != '\n' && charAt != 5760 && ((o20.i(charAt, 8192) < 0 || o20.i(charAt, 8202) > 0 || charAt == 8199) && charAt != 8287 && charAt != 12288)) {
                return i;
            }
            i--;
        }
        return i;
    }

    public void g(Object obj, String str) {
        str.getClass();
        ((LinkedHashMap) this.a).put(str, obj);
        ky0 ky0Var = (ky0) ((LinkedHashMap) this.c).get(str);
        if (ky0Var != null) {
            ky0Var.i(obj);
        }
        ky0 ky0Var2 = (ky0) ((LinkedHashMap) this.d).get(str);
        if (ky0Var2 != null) {
            ky0Var2.i(obj);
        }
    }

    public a9() {
        this.a = new Object();
        this.c = new AtomicInteger(0);
        this.d = new pe0();
        this.e = new pe0();
    }

    public a9(Map map) {
        map.getClass();
        this.a = new LinkedHashMap(map);
        this.b = new LinkedHashMap();
        this.c = new LinkedHashMap();
        this.d = new LinkedHashMap();
        this.e = new vf(2, this);
    }
}
