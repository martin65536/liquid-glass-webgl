package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class b00 {
    public final int a;

    public final boolean equals(Object obj) {
        if (obj instanceof b00) {
            if (this.a != ((b00) obj).a) {
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
        return "IndirectPointerEventPrimaryDirectionalMotionAxis(value=" + this.a + ')';
    }
}
