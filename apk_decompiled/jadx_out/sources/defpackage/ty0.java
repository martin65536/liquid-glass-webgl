package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ty0 implements j7 {
    public final String a;

    public final boolean equals(Object obj) {
        if (obj instanceof ty0) {
            if (!this.a.equals(((ty0) obj).a)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "StringAnnotation(value=" + this.a + ')';
    }
}
