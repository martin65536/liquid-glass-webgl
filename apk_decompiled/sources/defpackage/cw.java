package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class cw implements vh {
    public final sh e;

    public cw(sh shVar) {
        this.e = shVar;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof cw) {
            if (this.e.equals(((cw) obj).e)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int hashCode() {
        return this.e.hashCode() * 31;
    }
}
