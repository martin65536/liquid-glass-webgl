package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vi {
    public final int a;
    public final long b;
    public final wi c;
    public final u41 d;

    public vi(int i, long j, wi wiVar, u41 u41Var) {
        this.a = i;
        this.b = j;
        this.c = wiVar;
        this.d = u41Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof vi) {
                vi viVar = (vi) obj;
                if (this.a != viVar.a || this.b != viVar.b || this.c != viVar.c || !o20.e(this.d, viVar.d)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int hashCode;
        int i = this.a * 31;
        long j = this.b;
        int hashCode2 = (this.c.hashCode() + ((i + ((int) (j ^ (j >>> 32)))) * 31)) * 31;
        u41 u41Var = this.d;
        if (u41Var == null) {
            hashCode = 0;
        } else {
            hashCode = u41Var.hashCode();
        }
        return hashCode2 + hashCode;
    }

    public final String toString() {
        return "ContentCaptureEvent(id=" + this.a + ", timestamp=" + this.b + ", type=" + this.c + ", structureCompat=" + this.d + ')';
    }
}
