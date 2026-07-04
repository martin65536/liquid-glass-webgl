package defpackage;

import java.util.ConcurrentModificationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class am0 extends x {
    public final yl0 g;
    public int h;
    public z21 i;
    public int j;

    public am0(yl0 yl0Var, int i) {
        super(i, yl0Var.l);
        this.g = yl0Var;
        this.h = yl0Var.e();
        this.j = -1;
        b();
    }

    public final void a() {
        if (this.h == this.g.e()) {
        } else {
            throw new ConcurrentModificationException();
        }
    }

    @Override // defpackage.x, java.util.ListIterator
    public final void add(Object obj) {
        a();
        int i = this.e;
        yl0 yl0Var = this.g;
        yl0Var.add(i, obj);
        this.e++;
        this.f = yl0Var.a();
        this.h = yl0Var.e();
        this.j = -1;
        b();
    }

    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    public final void b() {
        yl0 yl0Var = this.g;
        Object[] objArr = yl0Var.j;
        if (objArr == null) {
            this.i = null;
            return;
        }
        int i = (yl0Var.l - 1) & (-32);
        int i2 = this.e;
        if (i2 > i) {
            i2 = i;
        }
        int i3 = (yl0Var.h / 5) + 1;
        z21 z21Var = this.i;
        if (z21Var == null) {
            this.i = new z21(objArr, i2, i, i3);
            return;
        }
        z21Var.e = i2;
        z21Var.f = i;
        z21Var.g = i3;
        if (z21Var.h.length < i3) {
            z21Var.h = new Object[i3];
        }
        ?? r0 = 0;
        z21Var.h[0] = objArr;
        if (i2 == i) {
            r0 = 1;
        }
        z21Var.i = r0;
        z21Var.b(i2 - r0, 1);
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        a();
        if (hasNext()) {
            int i = this.e;
            this.j = i;
            z21 z21Var = this.i;
            yl0 yl0Var = this.g;
            if (z21Var == null) {
                Object[] objArr = yl0Var.k;
                this.e = i + 1;
                return objArr[i];
            }
            if (z21Var.hasNext()) {
                this.e++;
                return z21Var.next();
            }
            Object[] objArr2 = yl0Var.k;
            int i2 = this.e;
            this.e = i2 + 1;
            return objArr2[i2 - z21Var.f];
        }
        v7.n();
        return null;
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        a();
        if (hasPrevious()) {
            int i = this.e;
            this.j = i - 1;
            z21 z21Var = this.i;
            yl0 yl0Var = this.g;
            if (z21Var == null) {
                Object[] objArr = yl0Var.k;
                int i2 = i - 1;
                this.e = i2;
                return objArr[i2];
            }
            int i3 = z21Var.f;
            if (i > i3) {
                Object[] objArr2 = yl0Var.k;
                int i4 = i - 1;
                this.e = i4;
                return objArr2[i4 - i3];
            }
            this.e = i - 1;
            return z21Var.previous();
        }
        v7.n();
        return null;
    }

    @Override // defpackage.x, java.util.ListIterator, java.util.Iterator
    public final void remove() {
        a();
        int i = this.j;
        if (i != -1) {
            yl0 yl0Var = this.g;
            yl0Var.b(i);
            int i2 = this.j;
            if (i2 < this.e) {
                this.e = i2;
            }
            this.f = yl0Var.a();
            this.h = yl0Var.e();
            this.j = -1;
            b();
            return;
        }
        throw new IllegalStateException();
    }

    @Override // defpackage.x, java.util.ListIterator
    public final void set(Object obj) {
        a();
        int i = this.j;
        if (i != -1) {
            yl0 yl0Var = this.g;
            yl0Var.set(i, obj);
            this.h = yl0Var.e();
            b();
            return;
        }
        throw new IllegalStateException();
    }
}
