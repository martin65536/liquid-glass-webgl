package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vm {
    public final long a;
    public final long b;

    public vm(long j, long j2) {
        this.a = j;
        this.b = j2;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof vm) {
                vm vmVar = (vm) obj;
                if (c20.a(this.a, vmVar.a) && this.b == vmVar.b) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        long j = this.a;
        int i = ((int) (j ^ (j >>> 32))) * 31;
        long j2 = this.b;
        return i + ((int) ((j2 >>> 32) ^ j2));
    }
}
