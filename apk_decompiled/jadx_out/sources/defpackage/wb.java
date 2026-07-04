package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wb extends x {
    public final /* synthetic */ int g = 1;
    public final Object h;

    public wb(Object[] objArr, int i, int i2) {
        super(i, i2);
        this.h = objArr;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        int i = this.g;
        Object obj = this.h;
        switch (i) {
            case 0:
                if (hasNext()) {
                    int i2 = this.e;
                    this.e = i2 + 1;
                    return ((Object[]) obj)[i2];
                }
                v7.n();
                return null;
            default:
                if (hasNext()) {
                    this.e++;
                    return obj;
                }
                v7.n();
                return null;
        }
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        int i = this.g;
        Object obj = this.h;
        switch (i) {
            case 0:
                if (hasPrevious()) {
                    int i2 = this.e - 1;
                    this.e = i2;
                    return ((Object[]) obj)[i2];
                }
                v7.n();
                return null;
            default:
                if (hasPrevious()) {
                    this.e--;
                    return obj;
                }
                v7.n();
                return null;
        }
    }

    public wb(int i, Object obj) {
        super(i, 1);
        this.h = obj;
    }
}
