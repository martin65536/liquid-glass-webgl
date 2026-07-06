package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gj0 extends g30 {
    public final wo0 a;

    public gj0(wo0 wo0Var) {
        this.a = wo0Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof gj0) {
                if (!this.a.equals(((gj0) obj).a)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // defpackage.g30
    public final wo0 r() {
        return this.a;
    }
}
