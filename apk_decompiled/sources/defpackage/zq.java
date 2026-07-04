package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zq implements sz {
    public final boolean e;

    public zq(boolean z) {
        this.e = z;
    }

    @Override // defpackage.sz
    public final boolean b() {
        return this.e;
    }

    @Override // defpackage.sz
    public final pg0 d() {
        return null;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("Empty{");
        if (this.e) {
            str = "Active";
        } else {
            str = "New";
        }
        sb.append(str);
        sb.append('}');
        return sb.toString();
    }
}
