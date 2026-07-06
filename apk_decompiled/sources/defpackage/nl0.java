package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class nl0 implements Iterator, q30 {
    public final b31[] e;
    public int f;
    public boolean g = true;

    public nl0(a31 a31Var, b31[] b31VarArr) {
        this.e = b31VarArr;
        b31VarArr[0].a(a31Var.d, Integer.bitCount(a31Var.a) * 2, 0);
        this.f = 0;
        a();
    }

    public final void a() {
        int i = this.f;
        b31[] b31VarArr = this.e;
        b31 b31Var = b31VarArr[i];
        if (b31Var.g < b31Var.f) {
            return;
        }
        while (-1 < i) {
            int b = b(i);
            if (b == -1) {
                b31 b31Var2 = b31VarArr[i];
                int i2 = b31Var2.g;
                Object[] objArr = b31Var2.e;
                if (i2 < objArr.length) {
                    int length = objArr.length;
                    b31Var2.g = i2 + 1;
                    b = b(i);
                }
            }
            if (b != -1) {
                this.f = b;
                return;
            }
            if (i > 0) {
                b31 b31Var3 = b31VarArr[i - 1];
                int i3 = b31Var3.g;
                int length2 = b31Var3.e.length;
                b31Var3.g = i3 + 1;
            }
            b31VarArr[i].a(a31.e.d, 0, 0);
            i--;
        }
        this.g = false;
    }

    public final int b(int i) {
        b31[] b31VarArr = this.e;
        b31 b31Var = b31VarArr[i];
        int i2 = b31Var.g;
        if (i2 < b31Var.f) {
            return i;
        }
        Object[] objArr = b31Var.e;
        if (i2 < objArr.length) {
            int length = objArr.length;
            Object obj = objArr[i2];
            obj.getClass();
            a31 a31Var = (a31) obj;
            if (i == 6) {
                b31 b31Var2 = b31VarArr[i + 1];
                Object[] objArr2 = a31Var.d;
                b31Var2.a(objArr2, objArr2.length, 0);
            } else {
                b31VarArr[i + 1].a(a31Var.d, Integer.bitCount(a31Var.a) * 2, 0);
            }
            return b(i + 1);
        }
        return -1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.g;
    }

    @Override // java.util.Iterator
    public Object next() {
        if (this.g) {
            Object next = this.e[this.f].next();
            a();
            return next;
        }
        v7.n();
        return null;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
