package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ud {
    public boolean a;
    public Object b;
    public Object c;
    public Object d;
    public Object e;

    /* JADX WARN: Multi-variable type inference failed */
    public int a(c4 c4Var, b4 b4Var, boolean z) {
        Object[] objArr;
        int i;
        int i2;
        my myVar = (my) this.c;
        py pyVar = (py) this.e;
        if (this.a) {
            return 0;
        }
        try {
            this.a = true;
            c4 l = ((j2) this.d).l(c4Var, b4Var);
            kb0 kb0Var = (kb0) l.f;
            int d = kb0Var.d();
            for (int i3 = 0; i3 < d; i3++) {
                um0 um0Var = (um0) kb0Var.e(i3);
                if (!um0Var.d && !um0Var.h) {
                }
                objArr = false;
                break;
            }
            objArr = true;
            int d2 = kb0Var.d();
            for (int i4 = 0; i4 < d2; i4++) {
                um0 um0Var2 = (um0) kb0Var.e(i4);
                if (objArr != false || g30.l(um0Var2)) {
                    ((z40) this.b).x(um0Var2.c, (py) this.e, um0Var2.i, true);
                    if (!pyVar.e.h()) {
                        myVar.a(um0Var2.a, pyVar, g30.l(um0Var2));
                        pyVar.clear();
                    }
                }
            }
            boolean b = myVar.b(l, z);
            int d3 = kb0Var.d();
            int i5 = 0;
            while (true) {
                if (i5 < d3) {
                    um0 um0Var3 = (um0) kb0Var.e(i5);
                    if (!ch0.c(g30.E(um0Var3, true), 0L) && um0Var3.b()) {
                        i = 1;
                        break;
                    }
                    i5++;
                } else {
                    i = 0;
                    break;
                }
            }
            int d4 = kb0Var.d();
            int i6 = 0;
            while (true) {
                if (i6 < d4) {
                    if (((um0) kb0Var.e(i6)).b()) {
                        i2 = 1;
                        break;
                    }
                    i6++;
                } else {
                    i2 = 0;
                    break;
                }
            }
            int i7 = (b ? 1 : 0) | (i << 1) | (i2 << 2);
            this.a = false;
            return i7;
        } catch (Throwable th) {
            this.a = false;
            throw th;
        }
    }

    public void b(int i, int i2) {
        if (i < 0.0f) {
            t00.a("Index should be non-negative (" + i + ')');
        }
        ((fk0) this.b).h(i);
        k60 k60Var = (k60) this.e;
        if (i != k60Var.f) {
            k60Var.f = i;
            int i3 = (i / 30) * 30;
            k60Var.e.setValue(n30.K(Math.max(i3 - 100, 0), i3 + 130));
        }
        ((fk0) this.c).h(i2);
    }
}
