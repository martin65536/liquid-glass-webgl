package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qj0 implements vd {
    public final Class a;

    public qj0(Class cls) {
        this.a = cls;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof qj0) {
            if (this.a.equals(((qj0) obj).a)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a.toString() + " (Kotlin reflection is not available)";
    }
}
