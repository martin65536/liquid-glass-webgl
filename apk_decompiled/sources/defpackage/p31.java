package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p31 {
    public final sl a;
    public final nu b;
    public final int c;
    public final int d;
    public final Object e;

    public p31(sl slVar, nu nuVar, int i, int i2, Object obj) {
        this.a = slVar;
        this.b = nuVar;
        this.c = i;
        this.d = i2;
        this.e = obj;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof p31)) {
            return false;
        }
        p31 p31Var = (p31) obj;
        if (o20.e(this.a, p31Var.a) && o20.e(this.b, p31Var.b) && this.c == p31Var.c && this.d == p31Var.d && o20.e(this.e, p31Var.e)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int hashCode;
        int i = 0;
        sl slVar = this.a;
        if (slVar == null) {
            hashCode = 0;
        } else {
            hashCode = slVar.hashCode();
        }
        int i2 = ((((((hashCode * 31) + this.b.e) * 31) + this.c) * 31) + this.d) * 31;
        Object obj = this.e;
        if (obj != null) {
            i = obj.hashCode();
        }
        return i2 + i;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("TypefaceRequest(fontFamily=");
        sb.append(this.a);
        sb.append(", fontWeight=");
        sb.append(this.b);
        sb.append(", fontStyle=");
        String str2 = "Invalid";
        int i = this.c;
        if (i == 0) {
            str = "Normal";
        } else if (i != 1) {
            str = "Invalid";
        } else {
            str = "Italic";
        }
        sb.append((Object) str);
        sb.append(", fontSynthesis=");
        int i2 = this.d;
        if (i2 == 0) {
            str2 = "None";
        } else if (i2 == 1) {
            str2 = "Weight";
        } else if (i2 == 2) {
            str2 = "Style";
        } else if (i2 == 65535) {
            str2 = "All";
        }
        sb.append((Object) str2);
        sb.append(", resourceLoaderCacheKey=");
        sb.append(this.e);
        sb.append(')');
        return sb.toString();
    }
}
