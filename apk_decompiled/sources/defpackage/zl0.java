package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zl0 extends x {
    public final Object[] g;
    public final z21 h;

    public zl0(Object[] objArr, Object[] objArr2, int i, int i2, int i3) {
        super(i, i2);
        this.g = objArr2;
        int i4 = (i2 - 1) & (-32);
        this.h = new z21(objArr, i > i4 ? i4 : i, i4, i3);
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        if (hasNext()) {
            z21 z21Var = this.h;
            if (z21Var.hasNext()) {
                this.e++;
                return z21Var.next();
            }
            int i = this.e;
            this.e = i + 1;
            return this.g[i - z21Var.f];
        }
        v7.n();
        return null;
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        if (hasPrevious()) {
            int i = this.e;
            z21 z21Var = this.h;
            int i2 = z21Var.f;
            if (i > i2) {
                int i3 = i - 1;
                this.e = i3;
                return this.g[i3 - i2];
            }
            this.e = i - 1;
            return z21Var.previous();
        }
        v7.n();
        return null;
    }
}
