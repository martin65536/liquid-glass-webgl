package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u4 {
    public final int a;

    public final boolean equals(Object obj) {
        if (obj instanceof u4) {
            if (this.a != ((u4) obj).a) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.a;
    }

    public final String toString() {
        return "AndroidContentDataType(androidAutofillType=" + this.a + ')';
    }
}
