package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ld0 {
    public final long a;

    public final boolean equals(Object obj) {
        if (obj instanceof ld0) {
            if (this.a != ((ld0) obj).a) {
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
        return "IndirectPointerEventData(packedValue=" + this.a + ')';
    }
}
