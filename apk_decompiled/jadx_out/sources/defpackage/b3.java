package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class b3 extends m20 {
    public final int c;

    public b3(int i) {
        this.c = i;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof b3) && ((b3) obj).c == this.c) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.c * 31;
    }
}
