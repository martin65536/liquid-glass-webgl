package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c70 {
    public final m70 a;
    public final a70 b;
    public final t50 c;
    public final p5 d;

    public c70(m70 m70Var, a70 a70Var, t50 t50Var, p5 p5Var) {
        this.a = m70Var;
        this.b = a70Var;
        this.c = t50Var;
        this.d = p5Var;
    }

    public final void a(final int i, Object obj, bw bwVar, int i2) {
        int i3;
        int i4;
        int i5;
        boolean z;
        int i6;
        Object obj2;
        bw bwVar2;
        bwVar.W(-462424778);
        if (bwVar.d(i)) {
            i3 = 4;
        } else {
            i3 = 2;
        }
        int i7 = i3 | i2;
        if (bwVar.h(obj)) {
            i4 = 32;
        } else {
            i4 = 16;
        }
        int i8 = i7 | i4;
        if (bwVar.f(this)) {
            i5 = 256;
        } else {
            i5 = 128;
        }
        int i9 = i8 | i5;
        if ((i9 & 147) != 146) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i9 & 1, z)) {
            i6 = i;
            obj2 = obj;
            bwVar2 = bwVar;
            t20.a(obj2, i6, this.a.r, jc0.C(-824725566, new kv() { // from class: b70
                @Override // defpackage.kv
                public final Object d(Object obj3, Object obj4) {
                    boolean z2;
                    bw bwVar3 = (bw) obj3;
                    int intValue = ((Integer) obj4).intValue();
                    if ((intValue & 3) != 2) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (bwVar3.O(intValue & 1, z2)) {
                        c70 c70Var = c70.this;
                        p5 p5Var = c70Var.b.a;
                        int i10 = i;
                        l20 b = p5Var.b(i10);
                        ((gg) b.c.h).h(c70Var.c, Integer.valueOf(i10 - b.a), bwVar3, 0);
                    } else {
                        bwVar3.R();
                    }
                    return x31.a;
                }
            }, bwVar), bwVar2, ((i9 >> 3) & 14) | 3072 | ((i9 << 3) & 112));
        } else {
            i6 = i;
            obj2 = obj;
            bwVar2 = bwVar;
            bwVar2.R();
        }
        mo0 r = bwVar2.r();
        if (r != null) {
            r.d = new f60(this, i6, obj2, i2);
        }
    }

    public final Object b(int i) {
        a70 a70Var = this.b;
        a70Var.getClass();
        l20 b = a70Var.a.b(i);
        return ((gv) b.c.g).e(Integer.valueOf(i - b.a));
    }

    public final int c() {
        a70 a70Var = this.b;
        a70Var.getClass();
        return a70Var.a.a;
    }

    public final Object d(int i) {
        Object obj;
        Object e;
        p5 p5Var = this.d;
        Object[] objArr = (Object[]) p5Var.c;
        int i2 = i - p5Var.a;
        if (i2 >= 0 && i2 < objArr.length) {
            obj = objArr[i2];
        } else {
            obj = null;
        }
        if (obj == null) {
            a70 a70Var = this.b;
            a70Var.getClass();
            l20 b = a70Var.a.b(i);
            int i3 = i - b.a;
            gv gvVar = (gv) b.c.f;
            if (gvVar != null && (e = gvVar.e(Integer.valueOf(i3))) != null) {
                return e;
            }
            return new wl(i);
        }
        return obj;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c70)) {
            return false;
        }
        return o20.e(this.b, ((c70) obj).b);
    }

    public final int hashCode() {
        return this.b.hashCode();
    }
}
