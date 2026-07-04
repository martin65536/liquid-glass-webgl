package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zm0 {
    public final int a;

    public /* synthetic */ zm0(int i) {
        this.a = i;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zm0) {
            if (this.a != ((zm0) obj).a) {
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
        return "PointerKeyboardModifiers(packedValue=" + this.a + ')';
    }
}
