package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p11 {
    public final String a;
    public String b;
    public boolean c = false;
    public bk0 d = null;

    public p11(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof p11)) {
            return false;
        }
        p11 p11Var = (p11) obj;
        if (o20.e(this.a, p11Var.a) && o20.e(this.b, p11Var.b) && this.c == p11Var.c && o20.e(this.d, p11Var.d)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i;
        int hashCode;
        int hashCode2 = (this.b.hashCode() + (this.a.hashCode() * 31)) * 31;
        if (this.c) {
            i = 1231;
        } else {
            i = 1237;
        }
        int i2 = (hashCode2 + i) * 31;
        bk0 bk0Var = this.d;
        if (bk0Var == null) {
            hashCode = 0;
        } else {
            hashCode = bk0Var.hashCode();
        }
        return i2 + hashCode;
    }

    public final String toString() {
        return "TextSubstitution(layoutCache=" + this.d + ", isShowingSubstitution=" + this.c + ')';
    }
}
