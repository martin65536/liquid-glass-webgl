package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class y6 {
    public final c4 a;
    public final Object b;
    public final d7 c;
    public final ik0 d;
    public final ik0 e;
    public final of0 f;
    public final ay0 g;
    public final i7 h;
    public final i7 i;
    public final i7 j;
    public final i7 k;

    public y6(Object obj, c4 c4Var, Object obj2, int i) {
        i7 i7Var;
        i7 i7Var2;
        obj2 = (i & 4) != 0 ? null : obj2;
        this.a = c4Var;
        this.b = obj2;
        d7 d7Var = new d7(c4Var, obj, null, 60);
        this.c = d7Var;
        this.d = n30.B(Boolean.FALSE);
        this.e = n30.B(obj);
        this.f = new of0();
        this.g = new ay0(1.0f, 1500.0f, obj2);
        i7 i7Var3 = d7Var.g;
        boolean z = i7Var3 instanceof e7;
        if (z) {
            i7Var = dl.e;
        } else if (i7Var3 instanceof f7) {
            i7Var = dl.f;
        } else if (i7Var3 instanceof g7) {
            i7Var = dl.g;
        } else {
            i7Var = dl.h;
        }
        this.h = i7Var;
        if (z) {
            i7Var2 = dl.a;
        } else if (i7Var3 instanceof f7) {
            i7Var2 = dl.b;
        } else if (i7Var3 instanceof g7) {
            i7Var2 = dl.c;
        } else {
            i7Var2 = dl.d;
        }
        this.i = i7Var2;
        this.j = i7Var;
        this.k = i7Var2;
    }

    public static final Object a(y6 y6Var, Object obj) {
        c4 c4Var = y6Var.a;
        i7 i7Var = y6Var.k;
        i7 i7Var2 = y6Var.j;
        if (!o20.e(i7Var2, y6Var.h) || !o20.e(i7Var, y6Var.i)) {
            i7 i7Var3 = (i7) ((gv) c4Var.f).e(obj);
            int b = i7Var3.b();
            boolean z = false;
            for (int i = 0; i < b; i++) {
                if (i7Var3.a(i) < i7Var2.a(i) || i7Var3.a(i) > i7Var.a(i)) {
                    i7Var3.e(n30.i(i7Var3.a(i), i7Var2.a(i), i7Var.a(i)), i);
                    z = true;
                }
            }
            if (z) {
                return ((gv) c4Var.g).e(i7Var3);
            }
        }
        return obj;
    }

    public static final void b(y6 y6Var) {
        d7 d7Var = y6Var.c;
        d7Var.g.d();
        d7Var.h = Long.MIN_VALUE;
        y6Var.d.setValue(Boolean.FALSE);
    }

    public static Object c(y6 y6Var, Object obj, c7 c7Var, Float f, uk ukVar, ij ijVar, int i) {
        if ((i & 2) != 0) {
            c7Var = y6Var.g;
        }
        c7 c7Var2 = c7Var;
        Object obj2 = f;
        if ((i & 4) != 0) {
            obj2 = ((gv) y6Var.a.g).e(y6Var.c.g);
        }
        if ((i & 8) != 0) {
            ukVar = null;
        }
        Object d = y6Var.d();
        c4 c4Var = y6Var.a;
        return of0.a(y6Var.f, new w6(y6Var, obj2, new p01(c7Var2, c4Var, d, obj, (i7) ((gv) c4Var.f).e(obj2)), y6Var.c.h, ukVar, null), ijVar);
    }

    public final Object d() {
        return this.c.f.getValue();
    }

    public final Object e(Object obj, sz0 sz0Var) {
        Object a = of0.a(this.f, new x6(this, obj, null), sz0Var);
        if (a == ik.e) {
            return a;
        }
        return x31.a;
    }
}
