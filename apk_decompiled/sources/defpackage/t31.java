package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class t31 implements Comparable {
    public final int e;

    public /* synthetic */ t31(int i) {
        this.e = i;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return o20.i(this.e ^ Integer.MIN_VALUE, ((t31) obj).e ^ Integer.MIN_VALUE);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof t31) {
            if (this.e != ((t31) obj).e) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.e;
    }

    public final String toString() {
        return String.valueOf(this.e & 4294967295L);
    }
}
