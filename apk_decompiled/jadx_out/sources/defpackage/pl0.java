package defpackage;

import java.util.ConcurrentModificationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class pl0 extends nl0 {
    public final ol0 h;
    public Object i;
    public boolean j;
    public int k;

    public pl0(ol0 ol0Var, b31[] b31VarArr) {
        super(ol0Var.f, b31VarArr);
        this.h = ol0Var;
        this.k = ol0Var.h;
    }

    public final void c(int i, a31 a31Var, Object obj, int i2) {
        int i3 = i2 * 5;
        b31[] b31VarArr = this.e;
        if (i3 > 30) {
            b31 b31Var = b31VarArr[i2];
            Object[] objArr = a31Var.d;
            b31Var.a(objArr, objArr.length, 0);
            while (true) {
                b31 b31Var2 = b31VarArr[i2];
                if (!o20.e(b31Var2.e[b31Var2.g], obj)) {
                    b31VarArr[i2].g += 2;
                } else {
                    this.f = i2;
                    return;
                }
            }
        } else {
            int u = 1 << m20.u(i, i3);
            if (a31Var.h(u)) {
                b31VarArr[i2].a(a31Var.d, Integer.bitCount(a31Var.a) * 2, a31Var.f(u));
                this.f = i2;
            } else {
                int t = a31Var.t(u);
                a31 s = a31Var.s(t);
                b31VarArr[i2].a(a31Var.d, Integer.bitCount(a31Var.a) * 2, t);
                c(i, s, obj, i2 + 1);
            }
        }
    }

    @Override // defpackage.nl0, java.util.Iterator
    public final Object next() {
        if (this.h.h == this.k) {
            if (this.g) {
                b31 b31Var = this.e[this.f];
                this.i = b31Var.e[b31Var.g];
                this.j = true;
                return super.next();
            }
            v7.n();
            return null;
        }
        throw new ConcurrentModificationException();
    }

    @Override // defpackage.nl0, java.util.Iterator
    public final void remove() {
        int i;
        if (this.j) {
            boolean z = this.g;
            ol0 ol0Var = this.h;
            if (z) {
                if (z) {
                    b31 b31Var = this.e[this.f];
                    Object obj = b31Var.e[b31Var.g];
                    f31.k(ol0Var).remove(this.i);
                    if (obj != null) {
                        i = obj.hashCode();
                    } else {
                        i = 0;
                    }
                    c(i, ol0Var.f, obj, 0);
                } else {
                    v7.n();
                    return;
                }
            } else {
                f31.k(ol0Var).remove(this.i);
            }
            this.i = null;
            this.j = false;
            this.k = ol0Var.h;
            return;
        }
        throw new IllegalStateException();
    }
}
