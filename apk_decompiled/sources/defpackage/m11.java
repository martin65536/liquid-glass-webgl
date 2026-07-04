package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class m11 {
    public static final long b = n30.d(0, 0);
    public static final /* synthetic */ int c = 0;
    public final long a;

    public /* synthetic */ m11(long j) {
        this.a = j;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof m11) {
            if (this.a != ((m11) obj).a) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        long j = this.a;
        return (int) (j ^ (j >>> 32));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TextRange(");
        long j = this.a;
        sb.append((int) (j >> 32));
        sb.append(", ");
        sb.append((int) (j & 4294967295L));
        sb.append(')');
        return sb.toString();
    }
}
